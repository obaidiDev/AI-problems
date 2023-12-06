package ICS381.HW4;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Node{
    int value;
    int heuristic;
    int depth = 0;
    int col = -1;
    Node parent;
    Queue<Node> children;
    public Node (int value){
        this.value = value;
        children = new LinkedList<>();
    }
    public Node (int value, int heuristic){
        this.value = value;
        this.heuristic = heuristic;
        children = new PriorityQueue<>(Comparator.comparing(Node::getHeuristic).reversed().thenComparing(Node::getValue));
    }
    public void addChild(Node child){
        child.parent = this;
        child.depth = this.depth + 1;
        children.add(child);
    }
    public void addChild(Node child, int col){
        child.parent = this;
        child.col = col;
        child.depth = this.depth + 1;
        children.add(child);
    }
    public boolean isTaken(int value){
        if (this.value == value)
            return true;
        Node current = this.parent;
        while (current != null){
            if (current.value == value)
                return true;
            current = current.parent;
        }
        return false;
    }
    public void removeChild(Node child){
        this.children.remove(child);
    }
    private int getHeuristic(){
        return heuristic;
    }
    private int getValue(){return value;}
    public static void main(String[] args) {
        // Create a node with a value and a queue of children
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        node1.addChild(node3);
        node1.addChild(node2);

        // Print the value of the node and its children
        System.out.println("Node 1 value: " + node1.value);
        System.out.println("Node 1 children:");
        for (Node child : node1.children) {
            System.out.println(child.value);
        }

        // Create a node with a value, heuristic, and a priority queue of children
        Node node4 = new Node(4, 10);
        Node node5 = new Node(50, 5);
        Node node6 = new Node(6, 15);

        node4.addChild(node6);
        node4.addChild(node5);

        // Print the value and heuristic of the node and its children
        System.out.println("Node 4 value: " + node4.value);
        System.out.println("Node 4 heuristic: " + node4.heuristic);
        System.out.println("Node 4 children:");
        while (!node4.children.isEmpty()){
            Node child = node4.children.remove();
            System.out.println(child.value + " (Heuristic: " + child.heuristic + ")");
        }
    }
}