package ICS381.HW1and2;
import java.util.ArrayList;
import java.util.List;

public class PriorityQueue<T> {
    private List<TreeNode<int[][]>> heap;
    private String heuristic;
    int n;
    int[][] goal;
    int length;

    public PriorityQueue(String heuristic, int n, int[][] goal) {
        heap = new ArrayList<>();
        this.heuristic = heuristic;
        this.n = n;
        this.goal = goal;
        length = 0;
    }

    public void add(TreeNode<int[][]> element) {
        heap.add(element);
        siftUp(heap.size() - 1);
        length++;
    }

    public TreeNode<int[][]> remove() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }

        TreeNode<int[][]> removedElement = heap.get(0);
        int lastIndex = heap.size() - 1;
        heap.set(0, heap.get(lastIndex));
        heap.remove(lastIndex);
        siftDown(0);
        length--;

        return removedElement;
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    private void siftUp(int index) {
        int parentIndex = (index - 1) / 2;

        while (index > 0 && cost(heap.get(index)) > cost(heap.get(parentIndex))) {
            swap(index, parentIndex);
            index = parentIndex;
            parentIndex = (index - 1) / 2;
        }
    }

    private void siftDown(int index) {
        int leftChildIndex = 2 * index + 1;
        int rightChildIndex = 2 * index + 2;
        int largestIndex = index;

        if (leftChildIndex < heap.size() && cost(heap.get(leftChildIndex)) > cost(heap.get(largestIndex))) {
            largestIndex = leftChildIndex;
        }

        if (rightChildIndex < heap.size() && cost(heap.get(rightChildIndex)) > cost(heap.get(largestIndex))) {
            largestIndex = rightChildIndex;
        }

        if (largestIndex != index) {
            swap(index, largestIndex);
            siftDown(largestIndex);
        }
    }

    private void swap(int i, int j) {
        TreeNode<int[][]> temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
    private int cost(TreeNode<int[][]> currentState){
        return switch (heuristic) {
            case "manhattan" -> manhattanDistance(currentState.data) + currentState.depth;
            case "misplaced" -> misplaced(currentState.data) + currentState.depth;
            case "euclidean" -> (int) euclideanDistance(currentState.data) + currentState.depth;
            default -> throw new RuntimeException("This heuristic function is not supported yet");
        };
    }
    private int misplaced(int[][] currentState) {
        int misplacement = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (currentState[i][j] != goal[i][j])
                    misplacement++;
        return misplacement;
    }
    private int manhattanDistance(int[][] currentState){
        int distance = 0, gi, gj;
        for (int i = 0; i < n; i++)
            for(int j = 0; j < n; j++) {
                gi = find(currentState[i][j])[0];
                gj = find(currentState[i][j])[1];
                distance += Math.abs(gi-i) + Math.abs(gj - j);
            }
        return distance;
    }
    private double euclideanDistance(int[][] currentState){
        double distance = 0, gi, gj;
        for (int i = 0; i < n; i++)
            for(int j = 0; j < n; j++) {
                gi = find(currentState[i][j])[0];
                gj = find(currentState[i][j])[1];
                distance += Math.sqrt((gi-i)*(gi-i) + (gj - j)*(gj - j));
            }
        return distance;
    }
    private int[] find(int x){
        for (int i = 0; i < n; i++)
            for(int j = 0; j < n; j++)
                if (goal[i][j] == x)
                    return new int[]{i, j};
        throw new RuntimeException("Element does not exist");
    }

}