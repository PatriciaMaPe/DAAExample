package es.uvigo.esei.daa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.uvigo.esei.daa.entities.Person;
import es.uvigo.esei.daa.entities.Pet;

/**
 * DAO class for the {@link Pet} entities.
 * 
 * @author Patricia Martin Perez
 *
 */
public class PetsDAO extends DAO{
	private final static Logger LOG = Logger.getLogger(PetsDAO.class.getName());
	private PeopleDAO peopleDAO;
	
	public PetsDAO() {
		this.peopleDAO = null;
	}
	
	/**
	 * Returns a pet stored persisted in the system.
	 * 
	 * @param id identifier of the pet.
	 * @return a pet with the provided identifier.
	 * @throws DAOException if an error happens while retrieving the pet.
	 * @throws IllegalArgumentException if the provided id does not corresponds
	 * with any persisted pet.
	 */
	public Pet get(int id) throws DAOException, IllegalArgumentException {
				try (final Connection conn = this.getConnection()) {
					final String query = "SELECT * FROM pets WHERE id=?";
					
					try (final PreparedStatement statement = conn.prepareStatement(query)) {
						statement.setInt(1, id);
						
						try (final ResultSet result = statement.executeQuery()) {
							if (result.next()) {
								return rowToEntity(result);
							} else {
								throw new IllegalArgumentException("Invalid id");
							}
						}
					}
				} catch (SQLException e) {
					LOG.log(Level.SEVERE, "Error getting a pet", e);
					throw new DAOException(e);
				}
	}
	
	/**
	 * Returns a list with all the pets persisted in the system.
	 * 
	 * @return a list with all the pets persisted in the system.
	 * @throws DAOException if an error happens while retrieving the pets.
	 */
	public List<Pet> list() throws DAOException {
		try (final Connection conn = this.getConnection()) {
			final String query = "SELECT * FROM pets";
			
			try (final PreparedStatement statement = conn.prepareStatement(query)) {
				try (final ResultSet result = statement.executeQuery()) {
					final List<Pet> pets = new LinkedList<>();
					
					while (result.next()) {	
						pets.add(rowToEntity(result));
					}
					
					return pets;
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error listing pets", e);
			throw new DAOException(e);
		}
	}
	
	/**
	 * Returns a list with all the pets of a owner.
	 * 
	 * @return a list with all the pets of a owner.
	 * @throws DAOException if an error happens while retrieving the pets.
	 */
	public List<Pet> listPetsOwner(int owner) throws DAOException {
		try (final Connection conn = this.getConnection()) {
			final String query = "SELECT * FROM pets WHERE owner=?";
			
			try (final PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setInt(1, owner);
				try (final ResultSet result = statement.executeQuery()) {
					final List<Pet> pets = new LinkedList<>();
					
					while (result.next()) {	
						pets.add(rowToEntity(result));
					}
					
					return pets;
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error listing pets of owner", e);
			throw new DAOException(e);
		}
	}
	
	/**
	 * Persists a new pet in the system. An identifier will be assigned
	 * automatically to the new pet.
	 * 
	 * @param name name of the new pet. Can't be {@code null}.
	 * @param species species of the new pet. Can't be {@code null}.
	 * @param breed breed of the new pet.
	 * @param owner owner of the new pet. Can't be {@code null}.
	 * @return a {@link Pet} entity representing the persisted pet.
	 * @throws DAOException if an error happens while persisting the new pet.
	 * @throws IllegalArgumentException if the name, species or owner are {@code null}.
	 */
	public Pet add(String name, String species, String breed, int owner)
	throws DAOException, IllegalArgumentException {
		if (name == null || species == null || owner == 0) {
			throw new IllegalArgumentException("name, species and owner can't be null");
		}
		
		try (Connection conn = this.getConnection()) {
			final String query = "INSERT INTO pets VALUES(null, ?, ?, ?, ?)";
			
			try (PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
				statement.setString(1, name);
				statement.setString(2, species);
				statement.setString(3, breed);
				statement.setInt(4, owner);
				
				if (statement.executeUpdate() == 1) {
					try (ResultSet resultKeys = statement.getGeneratedKeys()) {
						if (resultKeys.next()) {
							return new Pet(resultKeys.getInt(1), name, species, breed, owner);
						} else {
							LOG.log(Level.SEVERE, "Error retrieving inserted id");
							throw new SQLException("Error retrieving inserted id");
						}
					}
				} else {
					LOG.log(Level.SEVERE, "Error inserting value");
					throw new SQLException("Error inserting value");
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error adding a pet", e);
			throw new DAOException(e);
		}
	}
	
	/**
	 * Modifies a pet previously persisted in the system. The pet will be
	 * retrieved by the provided id and its current name, species, breed 
	 * and owner will be replaced with the provided.
	 * 
	 * @param pet a {@link Pet} entity with the new data.
	 * @throws DAOException if an error happens while modifying the new pet.
	 * @throws IllegalArgumentException if the pet is {@code null}.
	 */
	public void modify(Pet pet)
	throws DAOException, IllegalArgumentException {
		if (pet == null) {
			throw new IllegalArgumentException("pet can't be null");
		}
		
		try (Connection conn = this.getConnection()) {
			final String query = "UPDATE pet SET name=?, species=?, breed=?, owner=? WHERE id=?";
			
			try (PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setString(1, pet.getName());
				statement.setString(2, pet.getSpecies());
				statement.setString(2, pet.getBreed());
				statement.setInt(3, pet.getOwner());
				
				if (statement.executeUpdate() != 1) {
					throw new IllegalArgumentException("name, species and owner can't be null");
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error modifying a pet", e);
			throw new DAOException();
		}
	}
	
	/**
	 * Removes a persisted pet from the system.
	 * 
	 * @param id identifier of the pet to be deleted.
	 * @throws DAOException if an error happens while deleting the pet.
	 * @throws IllegalArgumentException if the provided id does not corresponds
	 * with any persisted pet.
	 */
	public void delete(int id)
	throws DAOException, IllegalArgumentException {
		try (final Connection conn = this.getConnection()) {
			final String query = "DELETE FROM pet WHERE id=?";
			
			try (final PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setInt(1, id);
				
				if (statement.executeUpdate() != 1) {
					throw new IllegalArgumentException("Invalid id");
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error deleting a pet", e);
			throw new DAOException(e);
		}
	}
	
	/**
	 * Return a Pet object with the data from the database.
	 * 
	 * @param resulset from database with the data of a pet.
	 * @throws DAOException if an error happens while retrieving the pet.
	 * @throws IllegalArgumentException if the provided id does not corresponds
	 * with any persisted people.
	 * @throws SQLException if an error happens while getting a person.
	 */
	private Pet rowToEntity(ResultSet row) throws SQLException, IllegalArgumentException, DAOException {

		return new Pet(
			row.getInt("id"),
			row.getString("name"),
			row.getString("species"),
			row.getString("breed"),
			row.getInt("owner")
			//new Person(44,"hola", "lala")
			//peopleDAO.get(row.getInt("owner"))	
			
		);
	}
}