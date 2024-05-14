package com.example.demo;

public class TernaryNode extends Node 
{
    private Node condition; //To store the condition of the parser
    private Node trueCase;//To store the true case 
    private Node falseCase;//To store the false case
/**
 * The constructor  while making this node
 * @param condition To store the condition of the parser
 * @param trueCase To store the true case 
 * @param falseCase To store the false case
 */
    public TernaryNode(Node condition, Node trueCase, Node falseCase) {
        this.condition = condition;
        this.trueCase = trueCase;
        this.falseCase = falseCase;
    }
//The Accessors for the present fields
    public Node getCondition() 
    {
        return condition;
    }

    public Node getTrueCase()
    {
        return trueCase;
    }

    public Node getFalseCase() 
    {
        return falseCase;
    }
    //The toString to test the nodes
    public String toString() 
    {
        return ("CONDITION: " + condition.toString() + "\nTRUECASE:" + trueCase.toString() + "\nFALSECASE:" + falseCase.toString());
    }
}
