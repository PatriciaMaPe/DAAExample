package es.uvigo.esei.daa.dataset;

import static java.util.Arrays.binarySearch;
import static java.util.Arrays.stream;

import java.util.Arrays;
import java.util.function.Predicate;

import es.uvigo.esei.daa.entities.Pet;

public final class PetsDataset {
	private PetsDataset() {}
	
	public static Pet[] pets() {
		return new Pet[] {
			new Pet(1, "Niki", "Gato","Persa", 1),
			new Pet(2, "Lukas","Perro", "Caniche", 3),
			new Pet(3, "Hamburguesa","Perro", "Chihuahua", 4),
			new Pet(4, "Luna","Gato", "Angora", 5),
			new Pet(5, "Choco","Perro", "Mastín", 1),
			new Pet(6, "Princesa","Perro", "Galgo", 4)
		};
	}
	
	public static Pet[] petsWithout(int ... ids) {
		Arrays.sort(ids);
		
		final Predicate<Pet> hasValidId = pet ->
			binarySearch(ids, pet.getId()) < 0;
		
		return stream(pets())
			.filter(hasValidId)
		.toArray(Pet[]::new);
	}
	
	public static Pet pet(int id) {
		return stream(pets())
			.filter(pet -> pet.getId() == id)
			.findAny()
		.orElseThrow(IllegalArgumentException::new);
	}
	
	public static int existentId() {
		return 5;
	}
	
	public static int nonExistentId() {
		return 1234;
	}

	public static int existentIdOwner() {
		return 5;
	}
	
	public static int nonExistentIdOwner() {
		return 1234;
	}
	public static Pet existentPet() {
		return pet(existentId());
	}
	
	public static Pet nonExistentPet() {
		return new Pet(nonExistentId(), "Jane", "Gato", "Siames", nonExistentId());
	}
	
	public static String newName() {
		return "John";
	}
	
	public static String newSpecies() {
		return "Perro";
	}
	
	public static String newBreed() {
		return "Doberman";
	}
	
	public static int newOwner() {
		return 1;
	}
	
	public static Pet newPet() {
		return new Pet(pets().length + 1, newName(), newSpecies(), newBreed(), newOwner());
	}
	
	public static Pet newPetNullBreed() {
		return new Pet(pets().length + 1, newName(), newSpecies(), null, newOwner());
	}
	
	
}
