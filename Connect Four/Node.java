package ICS381.HW3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Node <T>{
    int depth = 0;
    T data;
    double value;
    Node<T> parent = null;
    List<Node<T>> children;
    public Node (T data){
        this.data = data;
        children = new ArrayList<>();
    }
    public void addChild(Node<T> child){
        child.parent = this;
        child.depth = this.depth + 1;
        children.add(child);
        PriorityQueue<Node<T>> pq = new PriorityQueue<>(Comparator.comparing(Node::getDepth).reversed());
    }

    private static double getDepth(Object o){
        Node node = (Node) o;
        return node.value;
    }

}
