package controleur;

import java.util.*;

public class Node {
  private int x;
  private int y;
  private Node parent;
  private final static int taillePlateau = 16;

  public Node(int y, int x, Node parent) {
    this.x = x;
    this.y = y;
    this.parent = parent;
  }

  public String toString() {
    return "(" + y + ", " + x + ')';
  }

  public static void nodeToArray(ArrayList<Node> array,Node node) {
    if (node == null) {
      return;
    }
    nodeToArray(array,node.parent);
    array.add(node);
    return ;
  }

  public static int printPath(Node node) {
    if (node == null) {
      return 0;
    }
    int len = printPath(node.parent);
    System.out.print(node + " ");
    return len + 1;
  }


  public int getX() {
    return this.x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return this.y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public Node getParent() {
    return this.parent;
  }

  public void setParent(Node parent) {
    this.parent = parent;
  }


}
