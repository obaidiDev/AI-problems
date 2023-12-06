package ICS381.HW1and2;

import java.util.Arrays;

public class PriorityQ {
    private SLL<TreeNode<int[][]>>[] costClasses;
    int maxCost;
    int length;

    public PriorityQ(int n, String heuristic){
        maxCost = 0;
        length = 0;
        switch (heuristic) {
            case "misplaced" -> maxCost = n * n;
            case "manhattan" -> maxManhattan(n);
            case "euclidean" -> maxCost = (int) Math.round(n * n * Math.sqrt(2 * (n - 1) * (n - 1)));
            default -> throw new RuntimeException("This heuristic is not supported yet");
        }
        switch (n){
            case 3 -> maxCost += 31;
            case 4 -> maxCost += 80;
            case 5 -> maxCost += 152;
            case 6 -> maxCost += 312;
            default -> throw new RuntimeException("Unsupported value for n");
        }
        costClasses = new SLL[maxCost+1]; // assumes the maximum depth for the solution is 13
    }
    public void add(TreeNode<int[][]> node, int cost){
        if (cost >= maxCost){
            maxCost = cost*2;
            costClasses = Arrays.copyOf(costClasses,maxCost);
        }
        if (costClasses[cost] == null)
            costClasses[cost] = new SLL<>();
        costClasses[cost].addToTail(node);
        length++;
    }
    public TreeNode<int[][]> remove(){
        for (int i = 0; i < maxCost; i++)
            if (costClasses[i] != null && costClasses[i].head != null) {
                TreeNode<int[][]> removedNode = costClasses[i].head.info;
                costClasses[i].deleteFromHead();
                length--;
                return removedNode;
            }
        throw new RuntimeException("Queue is empty");
    }
    private void maxManhattan(int n){
        if (n == 2)
            maxCost+=8;
        else if (n == 3)
            maxCost+=24;
        else{
            maxCost += 8 * (n - 1);
            maxManhattan(n-2);
            int limit;
            if (n%2 == 0)
                limit = n/2;
            else {
                limit = n/3 + 1;
                maxCost+= 8*(n-n/3-2);
            }
            for (int i = 2; i <= limit; i++)
                maxCost += 16 * (n - i);
        }
    }

    public static void main(String[] args) {
        PriorityQ q = new PriorityQ(7,"manhattan");
        q.add(new TreeNode<>(new int[][]{{1},{2}}),0);
        System.out.println(Arrays.deepToString(q.remove().data));
    }
}
