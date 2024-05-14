package com.example.demo;


public class ForEachNode extends StatementNode
{
	private Node Assignement;
	private BlockNode blocknode;
	public ForEachNode(Node node, BlockNode blocknode) 
	{
		Assignement = node;
		this.blocknode = blocknode;
	}
	public Node getAssignement() 
	{
		return Assignement;
	}
	public void setAssignement(Node assignement) 
	{
		Assignement = assignement;
	}
	public BlockNode getBlocknode()
	{
		return blocknode;
	}
	public void setBlocknode(BlockNode blocknode) 
	{
		this.blocknode = blocknode;
	}
}
