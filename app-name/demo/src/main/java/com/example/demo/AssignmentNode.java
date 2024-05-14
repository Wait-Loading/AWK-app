package com.example.demo;

public class AssignmentNode extends StatementNode
{
    private Node target;//The target node to which the assignment is needed
    private Node expression;//The expression that is being assigned to the target
/**
 * The Constructor of the assignment node
 * @param target The target node to which the assignment is needed
 * @param expression The expression that is being assigned to the target
 */
    public AssignmentNode(Node target, Node expression) 
    {
        this.target = target;
        this.expression = expression;
    }
/**
 * The Accesser for target and and expression
 */
    public Node getTarget()
    {
        return target;
    }

    public Node getExpression() 
    {
        return expression;
    }
    /**
     * To print the nodes for the test
     */
    public String toString() 
    {
        return "Target:"+target.toString() + "\n EXPRESSION= " + expression.toString();
    }
}
