package com.example.demo;


public class PatternNode extends Node
{
	private String Value;//pattern token value
	PatternNode(String Value)
	{
		this.Value=Value;
	}
	public String getValue()//Accessor for the get value 
	{
		return Value;
	}
	public String toString()//the to string method which displays the contents of the node
	{
		return ("The pattern is"+Value);
	}
	
}
