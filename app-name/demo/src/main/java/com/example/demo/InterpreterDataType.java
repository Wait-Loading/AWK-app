package com.example.demo;

// This is a public class named InterpreterDataType
public class InterpreterDataType 
{
    // Instance variable of type String
    String type;
    // Getter method for the 'type' variable
    public String getType() {
		return type;
	}

    // Parameterized constructor
    public InterpreterDataType(String type) 
    {
		this.type = type;
	}

    // Setter method for the 'type' variable
    public void setType(String type) {
		this.type = type;
	}

    // Default constructor
    public InterpreterDataType() 
    {
		this.type = null;
	}
}
