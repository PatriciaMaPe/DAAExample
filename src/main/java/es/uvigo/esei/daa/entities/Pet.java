package es.uvigo.esei.daa.entities;

import static java.util.Objects.requireNonNull;

/**
 * An entity that represents a person's pet.
 * 
 * @author Patricia Martin Perez
 */

public class Pet{
	private int id;
	private String name;
	private String species;
	private String breed;
	private int owner;
	
	// Constructor for the JSON conversion
	Pet(){}
	
	
	/**
	 * Constructs a new instance of {@link Pet}.
	 *
	 * @param id identifier of the pet.
	 * @param name name of the pet.
	 * @param species species of the pet.
	 * @param breed breed of the pet.
	 * @param owner identifier of the person's pet.
	 */
	public Pet(int id, String name, String species, String breed, int owner) {
		this.id = id;
		this.setName(name);
		this.setSpecies(species);
		this.setBreed(breed);
		this.setOwner(owner);
	}
	
	/**
	 * Returns the identifier of the pet.
	 * 
	 * @return the identifier of the pet.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Returns the name of the pet.
	 * 
	 * @return the name of the pet.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set the name of this pet.
	 * 
	 * @param name the new name of the pet.
	 * @throws NullPointerException if the {@code name} is {@code null}.
	 */
	public void setName(String name) {
		this.name = requireNonNull(name, "Name can't be null");
	}
	
	/**
	 * Returns the species of the pet.
	 * 
	 * @return the species of the pet.
	 */
	public String getSpecies() {
		return species;
	}
	
	/**
	 * Set the species of this pet.
	 * 
	 * @param species the species of the pet.
	 * @throws NullPointerException if the {@code species} is {@code null}.
	 */
	public void setSpecies(String species) {
		this.species = requireNonNull(species, "Species can't be null");
	}
	
	/**
	 * Returns the breed of the pet.
	 * 
	 * @return the breed of the pet.
	 */
	public String getBreed() {
		return breed;
	}
	
	/**
	 * Set the breed of this pet.
	 * 
	 * @param breed the breed of the pet.
	 */
	public void setBreed(String breed) {
		this.breed = breed;
	}
	
	/**
	 * Returns the owner of the pet.
	 * 
	 * @return the owner of the pet.
	 */
	public int getOwner() {
		return owner;
	}
	
	/**
	 * Set the owner of this pet.
	 * 
	 * @param owner the owner of the pet.
	 * @throws NullPointerException if the {@code owner} is {@code null}.
	 */
	public void setOwner(int owner) {
		this.owner = requireNonNull(owner, "Owner can't be null");
	}
	
	@Override
	public int hashCode() {
		return 7 * this.id;		   
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Pet))
			return false;
		Pet other = (Pet) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Mascota " + this.id + ": " + this.name + " " + this.species + " " + this.breed + " " + this.owner;
	}
	
	
}