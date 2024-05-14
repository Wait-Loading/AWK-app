package com.example.demo;

import java.util.LinkedList;
public class ProgramNode 
{
    private LinkedList<FunctionDefinitionNode> functionNodes;//LinkedList of the FunctionDefinitionNode
    private LinkedList<BlockNode> begin_Blocks;//LinkedList of the Start Blocks
    private LinkedList<BlockNode> end_Blocks;//LInkedList of the End Blocks
    private LinkedList<BlockNode> blocks;//Linked list of the block
/**
 * Constructor of the Program Node 
 */
    public ProgramNode() 
    {
        functionNodes = new LinkedList<>();
        begin_Blocks = new LinkedList<>();
        end_Blocks = new LinkedList<>();
        blocks = new LinkedList<>();
    }
//Mutators for all the nodes in the ProgramNode
    public void addFunctionNode(FunctionDefinitionNode functionNode) 
    {
        functionNodes.add(functionNode);
    }

    public void addStartBlock(BlockNode blockNode)
    {
        begin_Blocks.add(blockNode);
        
    }

    public void addEndBlock(BlockNode blockNode) 
    {
        end_Blocks.add(blockNode);
    }

    public void addBlock(BlockNode blockNode) 
    {
        blocks.add(blockNode);
    } 
  //Accessors for all the nodes in the ProgramNode
    public LinkedList<FunctionDefinitionNode> getFunctionNodes() 
    {
        return functionNodes;
    }
    public LinkedList<BlockNode> getStartBlocks()
    {
        return begin_Blocks;
    }
    public LinkedList<BlockNode> getEndBlocks() 
    {
        return end_Blocks;
    }
    public LinkedList<BlockNode> getBlocks()
    {
        return blocks;
    }
/**
 * Making the tString to test the code
 * 
 */
    public String toString()
    {
        String output="";
        System.out.println("Program\n");
        for (FunctionDefinitionNode functionNode : functionNodes) 
        {
        	 output=output+functionNode.toString();
        }
        for (BlockNode blockNode : begin_Blocks) 
        {
        	output=output+blockNode.toString();
        }
        for (BlockNode blockNode : blocks) {
        	output=output+blockNode.toString();
        }
        for (BlockNode blockNode : end_Blocks) 
        {
        	output=output+blockNode.toString();
        }
        return output;
    }
}
