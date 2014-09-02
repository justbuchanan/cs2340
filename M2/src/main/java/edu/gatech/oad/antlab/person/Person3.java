package edu.gatech.oad.antlab.person;
/**
 *  A simple class for person 3
 *  returns their name and a
 *  reversed string 
 *  
 *  @author  Bob
 *  @version 1.1
 */
public class Person3 {
   /** Holds the persons real name */  
	private String name;
	
	/**
	 * The constructor, takes in the persons
	 * name
	 * @param pname the person's real name
	 */
	public Person3(String pname){
	  name = pname;
	}
	
	/**
	 * Return a string rep of this object
	 * that varies with an input string
	 *
	 * @param input the varying string
	 * @return the string representing the 
	 *         object
	 */
	public String toString(String input) {
	  return name + calc(input);
	}
	
	/**
	 * This method should take the string
	 * input and return its reverse.
	 * given "gtg123b" it should return
	 * b321gtg.
	 *
	 * @param input the string to be reversed
	 * @return the reversed string
	 */
	private String calc(String input) {
	  //Person 3 put your implementation here
	  return null;
	}
}
