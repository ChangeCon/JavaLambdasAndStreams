package domain;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents a sublass of the superclass of the class hierarchy.
 * 
 * @author Aleksander
 */
public class Programmer extends Person {
	
	private String primaryLanguage;
	private LocalDate programmingStartDate;
	private BigDecimal salary;
	
	/**
	 * Initializes all details for a programmer.
	 * 
	 * @param firstName
	 * @param lastName
	 * @param birthDate
	 * @param primaryLanguage
	 * @param programmingStartDate
	 * @param salary
	 */
	public Programmer(String firstName, String lastName, LocalDate birthDate, String primaryLanguage,
			LocalDate programmingStartDate, BigDecimal salary) {
		super(firstName, lastName, birthDate);
		this.primaryLanguage = primaryLanguage;
		this.programmingStartDate = programmingStartDate;
		this.salary = salary;
	}
	
	public String getPrimaryLanguage() {
		return primaryLanguage;
	}
	public void setPrimaryLanguage(String primaryLanguage) {
		this.primaryLanguage = primaryLanguage;
	}
	public LocalDate getProgrammingStartDate() {
		return programmingStartDate;
	}
	public void setProgrammingStartDate(LocalDate programmingStartDate) {
		this.programmingStartDate = programmingStartDate;
	}
	public BigDecimal getSalary() {
		return salary;
	}
	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}
	
	

}
