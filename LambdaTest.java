package domain;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * Test case scenarios. The first part initializes the random objects and the second part
 * performs tests.
 * 
 * @author Aleksander
 *
 */
public class LambdaTest {

	private static final int NUMBER_OF_OBJECTS = 10000000;
	private static final int STRING_LENGTH = 25;

	public static void main(String[] args) {
		
		System.out.println("NUMBER OF OBJECTS: " + NUMBER_OF_OBJECTS);

		SecureRandom random = new SecureRandom();
		List<Programmer> programmers = new ArrayList<>();
		String characters = new String("ABCDEFGHIJKLMNOPRSTUVWXYZ");

		LocalDateTime start = LocalDateTime.now();
		
		//Generating objects
		for (int i = 0; i < NUMBER_OF_OBJECTS; i++) {
			String firstName = generateString(random, characters, STRING_LENGTH);
			String lastName = generateString(random, characters, STRING_LENGTH);
			LocalDate birthDate = generateBirthDate();
			String programmingLanguage = generateRandomProgrammingLanguage(random);
			LocalDate programmingStartDate = generateProgrammingStartDate(birthDate);
			BigDecimal randomSalary = generateRandomSalary(random);
			Programmer programmer = new Programmer(firstName, lastName, birthDate, programmingLanguage, programmingStartDate, randomSalary);
			programmers.add(programmer);
		}
		
		LocalDateTime end = LocalDateTime.now();
		long milliseconds = start.until(end, ChronoUnit.MILLIS);
		System.out.println("TIME: " + milliseconds + " ms");
		
		//performing benchmark tests
		findTheYoungestProgrammerByIterator(programmers);
		findTheYoungestProgrammerForeachLoop(programmers);
		findTheYoungestProgrammerSorting(programmers);
		findTheYoungestProgrammerLambdaSequential(programmers);
		findTheYoungestProgrammerLambdaParallel(programmers);
		
		findTheProgrammerWithHighestSalaryByIterator(programmers);
		findTheProgrammerWithHighestSalaryForLoop(programmers);
		findTheProgrammerWithHighestSalarySorting(programmers);
		findTheProgrammerWithHighestSalaryLambdaSerial(programmers);
		Programmer programmerWithTheHighestSalary = findTheProgrammerWithHighestSalaryLambdaParallel(programmers);
		
		findTheProgrammersByNameAndIterator(programmerWithTheHighestSalary.getLastName().substring(0, 2), programmers);
		findTheProgrammersByNameAndForLoop(programmerWithTheHighestSalary.getLastName().substring(0, 2), programmers);
		findTheProgrammersByNameAndLambdaSerial(programmerWithTheHighestSalary.getLastName().substring(0, 2), programmers);
		findTheProgrammersByNameAndLambdaParallel(programmerWithTheHighestSalary.getLastName().substring(0, 2), programmers);

	}

	/**
	 * Generates random string of specified length.
	 * 
	 * @param rng
	 * @param characters
	 * @param length
	 * @return
	 */
	private static String generateString(Random rng, String characters, int length) {
		char[] text = new char[length];
		for (int i = 0; i < length; i++) {
			text[i] = characters.charAt(rng.nextInt(characters.length()));
		}
		return new String(text);
	}
	
	/**
	 * Generated a random birth date between 01.01.1950. and 31.12.1998.
	 * 
	 * @return
	 */
	private static LocalDate generateBirthDate() {
		long minDay = LocalDate.of(1950, 1, 1).toEpochDay();
	    long maxDay = LocalDate.of(1998, 12, 31).toEpochDay();
	    long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
	    LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
	    return randomDate;
	}
	
	/**
	 * Generates programming start date between a birthdate + 7 years and the conference date.
	 * 
	 * @param birthDate
	 * @return
	 */
	private static LocalDate generateProgrammingStartDate(LocalDate birthDate) {
		long minDay = birthDate.plusYears(7).toEpochDay();
	    long maxDay = LocalDate.of(2016, 10, 15).toEpochDay();
	    long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
	    LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
	    return randomDate;
	}
	
	/**
	 * Geerates a name of random programming language.
	 * 
	 * @param random
	 * @return
	 */
	private static String generateRandomProgrammingLanguage(Random random) {
		return ProgrammingLanguages.values()[random.nextInt(ProgrammingLanguages.values().length)].toString();
	}
	
	/**
	 * Generated a random salary between 5.000 and 10.000 Kunas.
	 * 
	 * @param random
	 * @return
	 */
	private static BigDecimal generateRandomSalary(Random random) {
		BigDecimal max = new BigDecimal("5000.0");
        BigDecimal randFromDouble = new BigDecimal(Math.random());
        BigDecimal actualRandomDec = randFromDouble.divide(max,BigDecimal.ROUND_DOWN);
        actualRandomDec = actualRandomDec.add(max);
        return actualRandomDec;
	}
	
	/**
	 * It finds the jungest programmer by using operator.
	 * 
	 * @param programmers
	 * @return
	 */
	private static Programmer findTheYoungestProgrammerByIterator(List<Programmer> programmers) {
		System.out.println("YOUNGEST PROGRAMMER (ITERATOR)");
		LocalDateTime start = LocalDateTime.now();
		
		Iterator<Programmer> iter = programmers.iterator();
		Programmer youngestProgrammer = programmers.get(0);
		iter.next();
		
		while(iter.hasNext()) {
			Programmer newProgrammer = iter.next();
			if(newProgrammer.getBirthDate().isAfter(youngestProgrammer.getBirthDate())) {
				youngestProgrammer = newProgrammer;
			}
		}
		
		System.out.println("RESULT: " + youngestProgrammer.getLastName() + " " + youngestProgrammer.getFirstName());
		
		LocalDateTime end = LocalDateTime.now();
		long milliseconds = start.until(end, ChronoUnit.MILLIS);
		System.out.println("TIME: " + milliseconds + " ms");
		
		return youngestProgrammer;
	}
	
	/**
	 * It finds the youngest programmer with foreach loop.
	 * 
	 * @param programmers
	 * @return
	 */
	private static Programmer findTheYoungestProgrammerForeachLoop(List<Programmer> programmers) {
		System.out.println("YOUNGEST PROGRAMMER (FOREACH LOOP)");
		LocalDateTime start = LocalDateTime.now();
		
		Programmer youngestProgrammer = programmers.get(0);
		
		for(Programmer newProgrammer : programmers.subList(1, programmers.size())) {
			if(newProgrammer.getBirthDate().isAfter(youngestProgrammer.getBirthDate())) {
				youngestProgrammer = newProgrammer;
			}
		}
		
		System.out.println("RESULT: " + youngestProgrammer.getLastName() + " " + youngestProgrammer.getFirstName());
		
		LocalDateTime end = LocalDateTime.now();
		long milliseconds = start.until(end, ChronoUnit.MILLIS);
		System.out.println("TIME: " + milliseconds + " ms");
		
		return youngestProgrammer;
	}
	
	/**
	 * It finds the youngest programmer with comparator method for sorting.
	 * 
	 * @param programmers
	 * @return
	 */
	private static Programmer findTheYoungestProgrammerSorting(List<Programmer> programmers) {
		System.out.println("YOUNGEST PROGRAMMER (SORTING WITH COMPARATOR)");
		LocalDateTime start = LocalDateTime.now();
		
		Collections.sort(programmers, new Comparator<Programmer>() {
			@Override
			public int compare(Programmer p1, Programmer p2) {
				return p1.getBirthDate().compareTo(p2.getBirthDate());
			}
		});
		
		Programmer youngestProgrammer = programmers.get(programmers.size() - 1);
		
		System.out.println("RESULT: " + youngestProgrammer.getLastName() + " " + youngestProgrammer.getFirstName());
		
		LocalDateTime end = LocalDateTime.now();
		
		long milliseconds = start.until(end, ChronoUnit.MILLIS);
		System.out.println("TIME: " + milliseconds + " ms");
		
		return youngestProgrammer;
	}
	
	/**
	 * It finds the jungest programmer using sequential stream.
	 * 
	 * @param programmers
	 * @return
	 */
	private static Programmer findTheYoungestProgrammerLambdaSequential(List<Programmer> programmers) {
		
		System.out.println("YOUNGEST PROGRAMMER (LAMBDA SEQUENTIAL STREAM)");
		LocalDateTime start = LocalDateTime.now();
		
		Programmer youngestProgrammer = programmers.stream().max(Comparator.comparing(Programmer::getBirthDate)).get();
		
		System.out.println("RESULT: " + youngestProgrammer.getLastName() + " " + youngestProgrammer.getFirstName());
		
		LocalDateTime end = LocalDateTime.now();
		
		long milliseconds = start.until(end, ChronoUnit.MILLIS);
		System.out.println("TIME: " + milliseconds + " ms");
		
		return youngestProgrammer;
	}
	
	/**
	 * It finds the youngest programmer using parallel streams.
	 * 
	 * @param programmers
	 * @return
	 */
	private static Programmer findTheYoungestProgrammerLambdaParallel(List<Programmer> programmers) {
		
		System.out.println("YOUNGEST PROGRAMMER (LAMBDA PARALLEL STREAM)");
		LocalDateTime start = LocalDateTime.now();
		
		Programmer youngestProgrammer = programmers.parallelStream().max(Comparator.comparing(Programmer::getBirthDate)).get();
		
		System.out.println("RESULT: " + youngestProgrammer.getLastName() + " " + youngestProgrammer.getFirstName());
		
		LocalDateTime end = LocalDateTime.now();
		
		long milliseconds = start.until(end, ChronoUnit.MILLIS);
		System.out.println("TIME: " + milliseconds + " ms");
		
		return youngestProgrammer;
	}
	
	/**
	 * It finds the programmer with the highest salary by iterator.
	 * 
	 * @param programmers
	 * @return
	 */
	private static Programmer findTheProgrammerWithHighestSalaryByIterator(List<Programmer> programmers) {
		System.out.println("HIGHEST SALARY (ITERATOR)");
		LocalDateTime start = LocalDateTime.now();
		
		Iterator<Programmer> iter = programmers.iterator();
		Programmer programmerWithHighestSalary = programmers.get(0);
		iter.next();
		
		while(iter.hasNext()) {
			Programmer newProgrammer = iter.next();
			if(newProgrammer.getSalary().compareTo(programmerWithHighestSalary.getSalary()) == 1) {
				programmerWithHighestSalary = newProgrammer;
			}
		}
		
		System.out.println("RESULT: " + programmerWithHighestSalary.getLastName() + " " + programmerWithHighestSalary.getFirstName());
		
		LocalDateTime end = LocalDateTime.now();
		long milliseconds = start.until(end, ChronoUnit.MILLIS);
		System.out.println("TIME: " + milliseconds + " ms");
		
		return programmerWithHighestSalary;
	}
	
	/**
	 * It finds the programmer with the highest salary by for loop.
	 * 
	 * @param programmers
	 * @return
	 */
	private static Programmer findTheProgrammerWithHighestSalaryForLoop(List<Programmer> programmers) {
		System.out.println("HIGHEST SALARY (FOREACH LOOP)");
		LocalDateTime start = LocalDateTime.now();
		
		Programmer programmerWithHighestSalary = programmers.get(0);
		
		for(Programmer newProgrammer : programmers.subList(1, programmers.size())) {
			if(newProgrammer.getSalary().compareTo(programmerWithHighestSalary.getSalary()) == 1) {
				programmerWithHighestSalary = newProgrammer;
			}
		}
		
		System.out.println("RESULT: " + programmerWithHighestSalary.getLastName() + " " + programmerWithHighestSalary.getFirstName());
		
		LocalDateTime end = LocalDateTime.now();
		long milliseconds = start.until(end, ChronoUnit.MILLIS);
		System.out.println("TIME: " + milliseconds + " ms");
		
		return programmerWithHighestSalary;
	}
	
	/**
	 * It fonds the programmer with the highest salary by using comparator and sorting.
	 * 
	 * @param programmers
	 * @return
	 */
	private static Programmer findTheProgrammerWithHighestSalarySorting(List<Programmer> programmers) {
		System.out.println("HIGHEST SALARY (SORTING WITH COMPARATOR)");
		LocalDateTime start = LocalDateTime.now();
		
		Collections.sort(programmers, new Comparator<Programmer>() {
			@Override
			public int compare(Programmer p1, Programmer p2) {
				return p1.getSalary().compareTo(p2.getSalary());
			}
		});
		
		Programmer programmerWithHighestSalary = programmers.get(programmers.size() - 1);
		
		System.out.println("RESULT: " + programmerWithHighestSalary.getLastName() + " " + programmerWithHighestSalary.getFirstName());
		
		LocalDateTime end = LocalDateTime.now();
		
		long milliseconds = start.until(end, ChronoUnit.MILLIS);
		System.out.println("TIME: " + milliseconds + " ms");
		
		return programmerWithHighestSalary;
	}
	
	/**
	 * It finds the programmer with the highest salary by sequential lambda.
	 * 
	 * @param programmers
	 * @return
	 */
	private static Programmer findTheProgrammerWithHighestSalaryLambdaSerial(List<Programmer> programmers) {
		
		System.out.println("HIGHEST SALARY (LAMBDA SEQUENTIAL STREAM)");
		LocalDateTime start = LocalDateTime.now();
		
		Programmer programmerWithHighestSalary = programmers.stream().max(Comparator.comparing(Programmer::getSalary)).get();
		
		LocalDateTime end = LocalDateTime.now();
		
		System.out.println("RESULT: " + programmerWithHighestSalary.getLastName() + " " + programmerWithHighestSalary.getFirstName());
		
		long milliseconds = start.until(end, ChronoUnit.MILLIS);
		System.out.println("TIME: " + milliseconds + " ms");
		
		return programmerWithHighestSalary;
	}
	
	/**
	 * It finds the programmer with the highest salarz by parallel streams.
	 * 
	 * @param programmers
	 * @return
	 */
	private static Programmer findTheProgrammerWithHighestSalaryLambdaParallel(List<Programmer> programmers) {
		
		System.out.println("HIGHEST SALARY (LAMBDA PARALLEL STREAM)");
		LocalDateTime start = LocalDateTime.now();
		
		Programmer programmerWithHighestSalary = programmers.parallelStream().max(Comparator.comparing(Programmer::getSalary)).get();
		
		LocalDateTime end = LocalDateTime.now();
		
		System.out.println("RESULT: " + programmerWithHighestSalary.getLastName() + " " + programmerWithHighestSalary.getFirstName());
		
		long milliseconds = start.until(end, ChronoUnit.MILLIS);
		System.out.println("TIME: " + milliseconds + " ms");
		
		return programmerWithHighestSalary;
	}
	
	/**
	 * It finds the programmer by name with iterator.
	 * 
	 * @param name
	 * @param programmers
	 * @return
	 */
	private static List<Programmer> findTheProgrammersByNameAndIterator(String name, List<Programmer> programmers) {
		System.out.println("PROGRAMMERS BY NAME (ITERATOR)");
		List<Programmer> filteredProgrammers = new ArrayList<>();
		LocalDateTime start = LocalDateTime.now();
		
		Iterator<Programmer> iter = programmers.iterator();

		while(iter.hasNext()) {
			Programmer newProgrammer = iter.next();
			if(newProgrammer.getLastName().contains(name)) {
				filteredProgrammers.add(newProgrammer);
			}
		}
		
		LocalDateTime end = LocalDateTime.now();
		long milliseconds = start.until(end, ChronoUnit.MILLIS);
		System.out.println("TIME: " + milliseconds + " ms");
		
		System.out.println("RESULT: " + filteredProgrammers.size() + " programmers.");
		
		return filteredProgrammers;
	}
	
	/**
	 * It finds the programmer by name with for loop.
	 * 
	 * @param name
	 * @param programmers
	 * @return
	 */
	private static  List<Programmer> findTheProgrammersByNameAndForLoop(String name, List<Programmer> programmers) {
		System.out.println("PROGRAMMERS BY NAME (FOREACH LOOP)");
		
		List<Programmer> filteredProgrammers = new ArrayList<>();
		
		LocalDateTime start = LocalDateTime.now();
		
		for(Programmer newProgrammer : programmers) {
			if(newProgrammer.getLastName().contains(name)) {
				filteredProgrammers.add(newProgrammer);
			}
		}
		
		LocalDateTime end = LocalDateTime.now();
		long milliseconds = start.until(end, ChronoUnit.MILLIS);
		System.out.println("TIME: " + milliseconds + " ms");
		
		System.out.println("RESULT: " + filteredProgrammers.size() + " programmers.");
		
		return filteredProgrammers;
	}
	
	/**
	 * It finds the programmer by name and sequential stream.
	 * 
	 * @param name
	 * @param programmers
	 * @return
	 */
	private static List<Programmer> findTheProgrammersByNameAndLambdaSerial(final String name, List<Programmer> programmers) {		
		System.out.println("PROGRAMMERS BY NAME (LAMBDA SEQUENTIAL STREAM)");
		
		LocalDateTime start = LocalDateTime.now();
		
		List<Programmer> filteredProgrammers = programmers.stream().filter(p -> p.getLastName().contains(name)).collect(Collectors.toList());
		
		LocalDateTime end = LocalDateTime.now();

		long milliseconds = start.until(end, ChronoUnit.MILLIS);
		System.out.println("TIME: " + milliseconds + " ms");
		
		System.out.println("RESULT: " + filteredProgrammers.size() + " programmers.");
		
		return filteredProgrammers;
	}
	
	/**
	 * It finds the programmer by name and parallel stream.
	 * 
	 * @param name
	 * @param programmers
	 * @return
	 */
	private static List<Programmer> findTheProgrammersByNameAndLambdaParallel(final String name, List<Programmer> programmers) {
		
		System.out.println("PROGRAMMERS BY NAME (LAMBDA PARALLEL STREAM)");
		LocalDateTime start = LocalDateTime.now();
		
		List<Programmer> filteredProgrammers = programmers.parallelStream().filter(p -> p.getLastName().contains(name)).collect(Collectors.toList());
		
		LocalDateTime end = LocalDateTime.now();
		
		long milliseconds = start.until(end, ChronoUnit.MILLIS);
		System.out.println("TIME: " + milliseconds + " ms");
		
		System.out.println("RESULT: " + filteredProgrammers.size() + " programmers.");
		
		return filteredProgrammers;
	}

}
