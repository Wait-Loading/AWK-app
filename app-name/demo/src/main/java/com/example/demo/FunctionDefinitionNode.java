package com.example.demo;

import java.util.LinkedList;

// Node representing a function definition
class FunctionDefinitionNode extends Node 
{
    private String FUNCTION_Name;
    private LinkedList<String> parameters;
    private LinkedList<StatementNode> statements;

    public FunctionDefinitionNode() 
    {
        this.FUNCTION_Name = "";
        this.parameters = new LinkedList<String>();
        this.statements = new LinkedList<StatementNode>();
    }
    //Mutators for Function node
    public void addName(String FUNCTION_Name)
    {
        this.FUNCTION_Name = FUNCTION_Name;
    }
    public void addParameters(String parameter)
    {
    	parameters.add(parameter);
    }
  public void addStatement(LinkedList<StatementNode> linkedList)
  {
	  statements=linkedList;
  }
  // Accessor method for FUNCTION_Name
  public String getName() {
      return FUNCTION_Name;
  }

  // Accessor method for parameters
  public LinkedList<String> getParameters() {
      return parameters;
  }

  // Accessor method for statements
  public LinkedList<StatementNode> getStatements()
  {
      return statements;
  }
  /**
   * The to string to test the methods
   */
    public String toString()
    {
		return ("The Function Name:"+ FUNCTION_Name+" \n parameters:"+parameters.toString()+"\n Statements: "+statements.toString());
    }
}

