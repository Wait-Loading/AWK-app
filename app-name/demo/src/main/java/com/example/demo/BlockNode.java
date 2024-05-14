package com.example.demo;

import java.util.LinkedList;
import java.util.Optional;

class BlockNode extends Node
{
    private LinkedList<StatementNode> statements;
    private Optional<Node> condition;

    public BlockNode() //initlization null values
    {
        this.statements = null;
        this.condition = null;
    }
   //Mutator for BlockNode
    public void setCondition(Optional<Node> condition) 
    {
        this.condition = condition;
    }
   
    public void setStatements(LinkedList<StatementNode> statements) 
    {
        this.statements = statements;
    }
    //Accessors for BlockNode
    public LinkedList<StatementNode> getStatements() 
    {
        return statements;
    }
    public Optional<Node> getCondition()
    {
        return condition;
    }
    public String toString() {
    	String output=("Condition: "+condition+"\nStatemnts : \n");
    	if(statements!=null)
    	{
    	for (StatementNode statement : statements)
    	{
           output=output+statement.toString()+"\n \n \n";
    	}
    	}
		return output;
				
    }
}
