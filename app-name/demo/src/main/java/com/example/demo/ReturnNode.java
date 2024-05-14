package com.example.demo;

public class ReturnNode extends StatementNode
{
	private Node node;

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public ReturnNode(Node node) {
		this.node = node;
	}
	

}
