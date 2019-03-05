package es.uvigo.esei.daa.entities;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class PetUnitTest{
	
	@Test
	public void testPetIntStringStringStringInt() {
		final int id = 1;
		final String name = "Niki";
		final String species = "Gato";
		final String breed = "Persa";
		final int owner = 2;
		
		final Pet pet = new Pet(id, name, species, breed, owner);	
		
		assertThat(pet.getId(), is(equalTo(id)));
		assertThat(pet.getName(), is(equalTo(name)));
		assertThat(pet.getSpecies(), is(equalTo(species)));
		assertThat(pet.getBreed(), is(equalTo(breed)));
		assertThat(pet.getOwner(), is(equalTo(owner)));
	}
	
	@Test(expected = NullPointerException.class)
	public void testPetIntStringStringStringIntNullName() {
		new Pet(1, null, "Gato", "Persa", 2);
	}
	
	@Test(expected = NullPointerException.class)
	public void testPetIntStringStringStringIntNullSpecies() {
		new Pet(1, "Niki", null, "Persa", 2);
	}
	
	@Test
	public void testPetIntStringStringStringIntNullBreed() {
		final int id = 1;
		final String name = "Niki";
		final String species = "Gato";
		final int owner = 2;
		
		final Pet pet = new Pet(id, name, species, null, owner);
		
		assertThat(pet.getId(), is(equalTo(id)));
		assertThat(pet.getName(), is(equalTo(name)));
		assertThat(pet.getSpecies(), is(equalTo(species)));
		assertThat(pet.getBreed(), is(equalTo(null)));
		assertThat(pet.getOwner(), is(equalTo(owner)));
	}
	
	@Test(expected = NumberFormatException.class)
	public void testPetIntStringStringStringIntNullOwner() {
		new Pet(1, "Niki", "Gato", "Persa", new Integer(null));
	}
	
	
	@Test
	public void testSetName() {
		final int id = 1;
		final String species = "Gato";
		final String breed = "Persa";
		final int owner = 2;
		
		final Pet pet = new Pet(id, "Niki", species, breed, owner);
		pet.setName("Luna");
		
		assertThat(pet.getId(), is(equalTo(id)));
		assertThat(pet.getName(), is(equalTo("Luna")));
		assertThat(pet.getSpecies(), is(equalTo(species)));
		assertThat(pet.getBreed(), is(equalTo(breed)));
		assertThat(pet.getOwner(), is(equalTo(owner)));
	}
	
	@Test(expected = NullPointerException.class)
	public void testSetNullName() {
		final Pet pet = new Pet(1, "Niki", "Gato", "Persa", 2);
		
		pet.setName(null);
	}
	
	@Test
	public void testSetSpecies() {
		final int id = 1;
		final String name = "Niki";
		final String breed = "Persa";
		final int owner = 2;
		
		final Pet pet = new Pet(id, name, "Gato", breed, owner);
		pet.setSpecies("Perro");
		
		assertThat(pet.getId(), is(equalTo(id)));
		assertThat(pet.getName(), is(equalTo(name)));
		assertThat(pet.getSpecies(), is(equalTo("Perro")));
		assertThat(pet.getBreed(), is(equalTo(breed)));
		assertThat(pet.getOwner(), is(equalTo(owner)));
	}
	
	@Test(expected = NullPointerException.class)
	public void testSetNullSpecies() {
		final Pet pet = new Pet(1, "Niki", "Gato", "Persa", 2);
		
		pet.setSpecies(null);
	}
	
	@Test
	public void testSetBreed() {
		final int id = 1;
		final String name = "Niki";
		final String species = "Gato";
		final int owner = 2;
		
		final Pet pet = new Pet(id, name, species, "Persa", owner);
		pet.setBreed("Siames");
		
		assertThat(pet.getId(), is(equalTo(id)));
		assertThat(pet.getName(), is(equalTo(name)));
		assertThat(pet.getSpecies(), is(equalTo(species)));
		assertThat(pet.getBreed(), is(equalTo("Siames")));
		assertThat(pet.getOwner(), is(equalTo(owner)));
	}
	
	@Test
	public void testSetNullBreed() {
		final int id = 1;
		final String name = "Niki";
		final String species = "Gato";
		final String breed = "Persa";
		final int owner = 2;
		
		final Pet pet = new Pet(id, name, species, breed, owner);
		
		pet.setBreed(null);
		
		assertThat(pet.getId(), is(equalTo(id)));
		assertThat(pet.getName(), is(equalTo(name)));
		assertThat(pet.getSpecies(), is(equalTo(species)));
		assertThat(pet.getBreed(), is(equalTo(null)));
		assertThat(pet.getOwner(), is(equalTo(owner)));
	}
	
	@Test
	public void testSetOwner() {
		final int id = 1;
		final String name = "Niki";
		final String species = "Gato";
		final String breed = "Persa";
		
		final Pet pet = new Pet(id, name, species, breed, 2);
		pet.setOwner(3);
		
		assertThat(pet.getId(), is(equalTo(id)));
		assertThat(pet.getName(), is(equalTo(name)));
		assertThat(pet.getSpecies(), is(equalTo(species)));
		assertThat(pet.getBreed(), is(equalTo(breed)));
		assertThat(pet.getOwner(), is(equalTo(3)));
	}
	
	@Test(expected = NumberFormatException.class)
	public void testSetNullOwner() {
		final Pet pet = new Pet(1, "Niki", "Gato", "Persa", 2);
		
		pet.setOwner(new Integer(null));
	}
	
	@Test
	public void testEqualsObject() {
		
		final Pet petA = new Pet(1, "Name A", "Specie A", "Breed A", 2);
		final Pet petB = new Pet(1, "Name B", "Specie B", "Breed B", 3);
		
		assertTrue(petA.equals(petB));
	}
	
	@Test
	public void testEqualsHashcode() {
		EqualsVerifier.forClass(Pet.class)
			.withIgnoredFields("name", "species", "breed", "owner")
			.suppress(Warning.STRICT_INHERITANCE)
			.suppress(Warning.NONFINAL_FIELDS)
		.verify();
	}
	
	
	
	
	
	
	
}