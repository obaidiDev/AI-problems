package ICS381.HW1and2;

import java.util.Arrays;

public class BinaryHeap {
    protected TreeNode<int[][]>[] array;
    int count;
    private final String heuristic;
    int n;
    int[][] goal;

    public BinaryHeap(int n,String heuristic, int[][] goal) {
        array = new TreeNode[10000];
        this.heuristic = heuristic;
        this.n = n;
        this.goal = goal;
    }

    private void buildHeapBottomUp() {
        // percolating down every node
        for (int i = count / 2; i >= 1; i--)
            percolateDown(i);
    }


    private void buildHeapTopDown() {
        // percolating up every node
        for (int i = 2; i <= count; i++)
            percolateUp(i);
    }

    private void percolateDown(int index) {

        // The parent will be stored in temp
        TreeNode<int[][]> temp = array[index];
        int hole;

        for (hole = index; hole * 2 <= count; hole = index) {
            index = hole * 2;
            // if the child is less than the parent it will be interchanged.
            if (index != count && cost(array[index + 1]) < cost(array[index]))
                index++;
            // This is repeated to use break.
            if (cost(array[index]) < cost(temp))
                array[hole] = array[index];
            else
                break;
        }
        array[hole] = temp;
    }

    private void percolateUp(int index) {
        // The parent will be stored in temp and if it's less than its child they will be interchanged.
        TreeNode<int[][]> temp = array[index];
        while (index > 1 && cost(temp) < cost(array[index / 2])) {
            array[index] = array[index / 2];
            index /= 2; // update index.
        }
        array[index] = temp;
    }

    public void purge() {
        while (count > 0)
            array[count--] = null;
    }

    public void add(TreeNode<int[][]> comparable) {
        int index = ++count;
        if (index >= array.length) {
            int recount = count * 2;
            array = Arrays.copyOf(array, recount);
        }
        //percolate up via a gap
        while (index > 1 && cost(array[index / 2]) > cost(comparable)) {
            array[index] = array[index / 2];
            index = index / 2;
        }

        array[index] = comparable;
    }

    public TreeNode<int[][]> findMin() {
        return array[1];
    }

    public TreeNode<int[][]> remove() {
        TreeNode<int[][]> minItem = array[1];
        array[1] = array[count];
        count--;
        percolateDown(1);
        return minItem;
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