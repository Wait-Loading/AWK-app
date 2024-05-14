package com.example.demo;

// Importing the HashMap class from the java.util package
import java.util.HashMap;

// This is a public class named InterpreterArrayDataType that extends the InterpreterDataType class
public class InterpreterArrayDataType extends InterpreterDataType
{
    // Instance variable of type HashMap
    HashMap<String,InterpreterDataType> Variables;

    // Getter method for the 'Variables' variable
    public HashMap<String, InterpreterDataType> getVariables() {
		return Variables;
	}

    // Setter method for the 'Variables' variable
	public void setVariables(HashMap<String, InterpreterDataType> variables) {
		Variables = variables;
	}

    // Default constructor
    public InterpreterArrayDataType() 
    {
		Variables =  new HashMap<String,InterpreterDataType>();
	}
}
