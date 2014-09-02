package edu.gatech.oad.antlab.pkg1;


import edu.cs2335.antlab.pkg3.*;
import edu.gatech.oad.antlab.person.*;
import edu.gatech.oad.antlab.pkg2.*;


/**
 * CS2335 Ant Lab
 *
 * Prints out a simple message gathered from all of the other classes
 * in the package structure
 */
 public class AntLabMain {
    
    /**antlab11.java message class*/
    private AntLab11 ant11;
    
    /**antlab12.java message class*/
    private AntLab12 ant12;
    
    /**antlab21.java message class*/
    private AntLab21 ant21;
    
    /**antlab22.java message class*/
    private AntLab22 ant22;
    
    /**antlab31 java message class which is contained in a jar resource file*/
    private AntLab31 ant31;
    
    
    
    /**
     * the constructor that intializes all the helper classes
     */
    public AntLabMain () {
        
        ant11 = new AntLab11();
        ant12 = new AntLab12();
        ant21 = new AntLab21();
        ant22 = new AntLab22();
        ant31 = new AntLab31();
        
        
    }
    
    /**
     * gathers a string from all the other classes and prints the message
     * out to the console     
     * 
     */
    public void printOutMessage() {
        
        String toPrint = 
            ant11.getMessage() + ant12.getMessage() + ant21.getMessage()
          + ant22.getMessage() + ant31.getMessage();
		  //Person1 replace P1 with your name
		  //and gburdell1 with your gt id
		  Person1 p1 = new Person1("P1");
		  toPrint += p1.toString("gburdell1");
		  //Person2 replace P2 with your name
		  //and gburdell with your gt id
		  Person2 p2 = new Person2("P2");
		  toPrint += p2.toString("gburdell2");
		  //Person3 replace P3 with your name
		  //and gburdell3 with your gt id
		  Person3 p3 = new Person3("P3");
		  toPrint += p3.toString("gburdell3");
                  //Person4 replace P4 with your name
                  //and gburdell4 with your gt id
                  Person4 p4 = new Person4("P4");
                  toPrint += p4.toString("gburdell4");
                  //Person5 replace P4 with your name
                  //and gburdell5 with your gt id
                  Person5 p5 = new Person5("P5");
                  toPrint += p5.toString("gburdell5");
		  
        System.out.println(toPrint);
        
        
    }
     
    
    /**
     * entry point for the program
     */
     public static void main(String[] args) {
        
        new AntLabMain().printOutMessage();
        
     } 
    
    
    
    
 } 