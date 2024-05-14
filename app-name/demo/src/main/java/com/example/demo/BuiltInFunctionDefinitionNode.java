package com.example.demo;

import java.util.HashMap;
import java.util.function.Function;

public class BuiltInFunctionDefinitionNode extends FunctionDefinitionNode 
{
     Function<HashMap<String, InterpreterDataType>, String> execute;
     boolean Variadic;
    public BuiltInFunctionDefinitionNode(Function<HashMap<String, InterpreterDataType>, String> execute, boolean Variadic)
    {
        this.execute = execute;
        this.Variadic = Variadic;
    }

    public String execute(HashMap<String, InterpreterDataType> parameters)
    {
        return execute.apply(parameters);
    }
    public boolean isVariadic()
    {
        return Variadic;
    }
}
