package modele;

import java.util.*;

public class Node {
  private int x;
  private int y;
  private int dist;
  private Node parent;

  public Node(int y, int x, int dist, Node parent) {
    this.x = x;
    this.y = y;
    this.dist = dist;
    this.parent = parent;
  }

  public String toString() {
    return "(" + y + ", " + x + ')';
  }

  public static void nodeToArray(ArrayList<ArrayList<Integer>> array,Node node) {
    if (node == null) {
      return;
    }
    ArrayList<Integer> coordCase = new ArrayList<>();
    coordCase.add(node.getY());coordCase.add(node.getX());
    nodeToArray(array,node.parent);
    array.add(coordCase);
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


  public int getDist() {
    return this.dist;
  }

  public void setDist(int dist) {
    this.dist = dist;
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
