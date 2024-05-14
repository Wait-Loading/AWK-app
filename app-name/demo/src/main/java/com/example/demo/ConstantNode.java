package com.example.demo;

public class ConstantNode extends Node 
{
   private String Value;//The value of the constant token (the value stored in the token)
   //Accessors and Mutators  for value
   public String getValue() 
   {
	return Value;
}
public void setValue(String value) {
	Value = value;
}
/**
 * The constant node constructor 
 * @param Value the value i=passed when the constructor is called 
 */
public ConstantNode(String Value)
   {
	   this.Value=Value;
   }
/**
 * The tostring to display constant node
 * @return the content of the node
 */
public String toString()
{
	return ("The Value in this node is "+Value);
}  
}
