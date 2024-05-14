package com.example.demo;


public class DoNode extends StatementNode
{
  private BlockNode BN;
  private Node ON;
public DoNode(BlockNode bN, Node oN) {
	super();
	BN = bN;
	ON = oN;
}
public BlockNode getBN() 
{
	return BN;
}
public void setBN(BlockNode bN) 
{
	BN = bN;
}
public Node getON() 
{
	return ON;
}
public void setON(Node oN) 
{
	ON = oN;
}
public String toString()
{
	String OP= ("Do{"+BN.toString()+"}"+"While("+ON.toString()+")");
	return OP;
}
  
}
