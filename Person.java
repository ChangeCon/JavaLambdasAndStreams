package domain;

import java.time.LocalDate;

/**
 * Represents the superclass of the class hierarchy.
 * 
 * @author Aleksander
 */
public abstract class Person {
	
	private String firstName;
	private String lastName;
	private LocalDate birthDate;
	
	/**
	 * Initializes three basic properties of a person.
	 * 
	 * @param firstName
	 * @param lastName
	 * @param birthDate
	 */
	public Person(String firstName, String lastName, LocalDate birthDate) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public LocalDate getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	
	

}
