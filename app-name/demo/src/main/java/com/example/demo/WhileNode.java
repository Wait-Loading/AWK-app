package com.example.demo;

public class WhileNode extends StatementNode
{
   private Node on;
   private BlockNode statements;
public WhileNode(Node on, BlockNode statements) 
{
	this.setOn(on);
	this.setStatements(statements);
}
public BlockNode getStatements() 
{
	return statements;
}
public void setStatements(BlockNode statements)
{
	this.statements = statements;
}
public Node getOn() 
{
	return on;
}
public void setOn(Node on) 
{
	this.on = on;
}
   
}
