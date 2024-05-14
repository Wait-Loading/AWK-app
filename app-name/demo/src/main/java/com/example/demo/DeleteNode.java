package com.example.demo;


public class DeleteNode extends StatementNode
{
	private Node Name;
	private String index;
    DeleteNode(Node name)
   {
	 this.setName(name);
   }
    DeleteNode(Node name, String Index)
    {
 	 this.setName(name);
 	 this.setIndex(Index);
    }
	public Node getName() 
	{
		return Name;
	}
	public void setName(Node name2) 
	{
		Name = name2;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	} 
}
