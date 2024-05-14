package com.example.demo;


public class ForNode extends StatementNode
{	 

	private Node Assignmet;
	private Node condition;
    private Node Operator;
    private BlockNode Statement;
    public ForNode(Node assignmet, Node condition, Node operator,BlockNode Statement)
	 {
		Assignmet = assignmet;
		this.condition = condition;
		Operator = operator;
		this.Statement=(Statement);
	 }
    public Node getAssignmet() {
		return Assignmet;
	}
	public void setAssignmet(Node assignmet) {
		Assignmet = assignmet;
	}
	public Node getCondition() {
		return condition;
	}
	public void setCondition(Node condition) {
		this.condition = condition;
	}
	public Node getOperator() {
		return Operator;
	}
	public void setOperator(Node operator) {
		Operator = operator;
	}
	public BlockNode getStatement() {
		return Statement;
	}
	public void setStatement(BlockNode statement) {
		Statement = statement;
	}
	public String toString()
	{
		String output= "FOR("+Assignmet.toString()+";"+condition.toString()+";"+Operator.toString();
		return output+")";
		
	}

}
