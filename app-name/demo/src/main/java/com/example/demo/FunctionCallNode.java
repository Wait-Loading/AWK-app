package com.example.demo;

import java.util.List;

class FunctionCallNode extends StatementNode 
{
    public String getFunctionName() {
		return functionName;
	}

	public List<Node> getParameters() {
		return parameters;
	}

	private String functionName;
    private List<Node> parameters;
    public FunctionCallNode(String functionName, List<Node> parameters) 
    {
        this.functionName = functionName;
        this.parameters = parameters;
    }

    public String toString() 
    {
    	String  Output =functionName + "(";
        for (Node parameter : parameters) 
        {
           Output= Output+ parameter.toString()+",";
        }
        Output= Output+")";
        return Output;
    }
}