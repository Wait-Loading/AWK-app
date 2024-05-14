package com.example.demo;

import java.util.Optional;

public class VariableReferenceNode extends Node
{
	private String VAR_name;
	private Optional<Node> Index_exp= Optional.empty();
	//Accessors and Mutators for the Variable Reference NOde
	public String getVAR_name() {
		return VAR_name;
	}
	public void setVAR_name(String vAR_name) {
		VAR_name = vAR_name;
	}
	public Optional<Node> getIndex_exp() {
		return Index_exp;
	}
	public void setIndex_exp(Optional<Node> index_exp) {
		Index_exp = index_exp;
	}

   public  VariableReferenceNode(String VAR_name,Optional<Node> Index_exp)//Constructors for Variable Node it's Index of the array 
    {
    	this.VAR_name=VAR_name;
    	this.Index_exp= Index_exp;
    }
   public  VariableReferenceNode(String VAR_name)//Constructor for Variable reference node
   {
   	this.VAR_name=VAR_name;
   }
   public String toString()// the To string method to print the contents of the node
   {
	   String output=("The variable node  is "+ VAR_name);
	   if(Index_exp.isPresent() )
	   {
		   output+=("with index "+ Index_exp.get().toString());
	   }
	   return output;
   }
}
