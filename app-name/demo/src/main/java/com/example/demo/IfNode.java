package com.example.demo;

import java.util.LinkedList;
public class IfNode extends StatementNode {
    private Node condition;
    private LinkedList<StatementNode> statements;
    private IfNode next;

    public IfNode(Node condition, LinkedList<StatementNode> statements) {
        this.condition = condition;
        this.statements = statements;
    }

    @Override
	public String toString() {
		return "IF_NODE [condition=" + condition + ", statements=" + statements + ", next=" + next + "]";
	}

	public Node getCondition() {
        return condition;
    }

    public LinkedList<StatementNode> getStatements() {
        return statements;
    }

    public IfNode getNext() {
        return next;
    }

    public void setNext(IfNode next) {
        this.next = next;
    }
}

/*
public class IF_NODE extends StatementNode 
{
    private Node condition;
    private BlockNode ifBlock;
    private LinkedList<IF_NODE> elseifNodes; // Linked list to handle "else if" clauses
    private BlockNode elseBlock;

    public IF_NODE(Node condition, BlockNode ifBlock) 
    {
        this.condition = condition;
        this.ifBlock = ifBlock;
        this.elseifNodes = new LinkedList<>();
        this.elseBlock = null;
    }

    // MUTATOR to add an "else if" clause
    public void AddElseIf(IF_NODE elseifNode) 
    {
        elseifNodes.add(elseifNode);
    }

    // MUTATOR to set the "else" block
    public void SetElseBlock(BlockNode elseBlock) 
    {
        this.elseBlock = elseBlock;
    }
    // ACCESSOR FOR condition, ifBlock, elseifNodes, and elseBlock
    public Node getCondition()
    {
        return condition;
    }

    public BlockNode getIfBlock()
    {
        return ifBlock;
    }

    public LinkedList<IF_NODE> getElseIfNodes()
    {
        return elseifNodes;
    }

    public BlockNode getElseBlock()
    {
        return elseBlock;
    }

    public String toString() 
    {
        String result = "if (" + condition.toString() + ") {\n" + ifBlock.toString() + "\n";

        for (IF_NODE elseifNode : elseifNodes) 
        {
            result += "} else " + elseifNode.toString();
        }

        if (elseBlock != null) 
        {
            result += "} else {\n" + elseBlock.toString() + "\n";
        }

        result += "}";

        return result;
    }
}*/
