package com.example.demo;

import java.util.Optional;

public class OperationNode extends StatementNode 
{
  enum operations {EQ, NE, LT, LE, GT, GE, AND, OR, NOT, MATCH, NOTMATCH, DOLLAR,
      PREINC,POSTINC,PREDEC, POSTDEC,UNARYPOS, UNARYNEG, IN,
      EXPONENT, ADD, SUBTRACT,MULTIPLY, DIVIDE,MODULO, CONCATENATION}//ENUMS as specified in the assignment
  private Node Left;//To contain left node 
  operations op;//Operations to store the enums
  private Optional<Node> Right= Optional.empty();//to store the right node
  /**
   * The constructor for storing Binary operations
   * @param Left
   * @param op
   * @param Right
   */
  public OperationNode(Node Left,operations op,Optional<Node>Right)
  {
	  this.Left = Left;
	  this.op=op;
	  this.Right=Right;
  }
 
   /**
    * To get the left node
    * @return the left value
    */
   public Node getLeft() {
	return Left;
}
   /**
    * To set the left node
    * @param left the left node parsed
    */
public void setLeft(Node left) {
	Left = left;
}
/**
 * To get the value of operation Node
 * @return The operation node
 */
public operations getOp() {
	return op;
}
/**
 * To set the Operation Node
 * @param op The Enum passed when the node was created 
 */
public void setOp(operations op) {
	this.op = op;
}
/**
 * To get the right node
 * @return The right node
 */
public Optional<Node> getRight() {
	return Right;
}
/**
 * To set the right node
 * @param right The right node
 */
public void setRight(Optional<Node> right) {
	Right = right;
}
/**
 * The constructor for storing Binary operations
 * @param op The operation ENUM
 * @param Left The left node
 */
public OperationNode(OperationNode.operations op, Node Left ) 
   {
	   this.op=op;
	   this.Left = Left;
	
   }
/**
 * To display the node
 */
public String toString() {
    String output = "OperationNode:\n";
    output += "  Left Node: " + Left.toString() + "\n";
    output += "  Operation: " + op + "\n";

    if (Right.isPresent()) {
        output += "  Right Node: " + Right.get().toString() + "\n";
    }

    return output;
}

}

