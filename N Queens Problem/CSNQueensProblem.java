package ICS381.HW4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class CSNQueensProblem {
    // I will use an array of linked lists the index of the array represents the variable and the arrays list
    // represents the domain
    private final ArrayList<Integer>[] domain;
    private final int[] solutionVector;
    private final int n;

    public CSNQueensProblem(int n){
        domain = new ArrayList[n];
        for (int i = 1; i <= n; i++) {
            domain[i - 1] = new ArrayList<>();
        }
        solutionVector = new int[n];
        this.n = n;
    }

    public int[] solve(boolean withForwardCheck){
        Node root = new Node(0);
        backtrackingSearch(root,withForwardCheck);
        return solutionVector;
    }
    private void backtrackingSearch(Node currentNode, boolean withForwardCheck){
        Stack<Node> stack = new Stack<>();
        generateChildren(currentNode);
        while (!currentNode.children.isEmpty()) {
            stack.push(currentNode.children.remove());
        }
        int index;
        while (!isValid(true)){
            currentNode = stack.pop();
            index = currentNode.depth - 1;
            solutionVector[index] = currentNode.value;
            if (!isValid(false)||withForwardCheck&&!forwardCheck(index)){
                solutionVector[index] = 0;
//                if (stack.peek().depth != currentNode.depth)
//                    solutionVector[index-1] = 0;
                while (stack.peek().depth != currentNode.depth) {
                    index-=1;
                    solutionVector[index] = 0;
                    currentNode = currentNode.parent;
                }
                continue;
            }
            generateChildren(currentNode);
            while (!currentNode.children.isEmpty())
                stack.add(currentNode.children.remove());
        }
    }
    private boolean isValid(boolean solutionCheck){
        int i, j = 0;
        for (i = 1; i < n && (solutionVector[i] > 0); i++)
            for(j = 0; j < i && (solutionVector[j] > 0); j++)
                if (isThreat(i,j) && (solutionVector[i] > 0 && solutionVector[j] > 0))
                    return false;
        return !solutionCheck || (i == n && j == n - 1);
    }

    private void generateChildren(Node parent){
        if (parent.children.isEmpty())
            for (int i = n; i >= 1; i--)
                if (!parent.isTaken(i))
                    parent.addChild(new Node(i));
    }

    private boolean isValidValue(int value, int col){
        for (int i = 0; i < col; i++) {
            if (solutionVector[i] == value ||
                    solutionVector[i] == value + (col - i) ||
                    solutionVector[i] == value - (col - i))
                return false;
        }
        return true;
    }

    private boolean isThreat(int i, int j){
        return  solutionVector[i] == solutionVector[j] ||
                solutionVector[i] == solutionVector[j] + (i - j) ||
                solutionVector[i] == solutionVector[j] - (i - j);
    }

    private boolean forwardCheck(int col){
        // Found by experiment
        int lookForward = n/5;
        int threshold = col <= n-lookForward?lookForward:1;
        boolean valid = true;
        int i;
        for (i = col; i < col + threshold && valid; i++) {
            valid = false;
            for (int v = 1; v <= n && !valid; v++)
                valid = (isValidValue(v, i));
        }
        return valid;
    }

    public static void main(String[] args) {
        CSNQueensProblem queensProblem = new CSNQueensProblem(8);
        System.out.println(Arrays.toString(queensProblem.solve(true)));
//        long start;
//        boolean forwardCheck = false;
//        long[] times = new long[10];
//        String statement;
//        for (int n = 5; n <= 8; n+=1) {
//            if (n == 8 && !forwardCheck) {
//                forwardCheck = true;
//                n = 0;
//                continue;
//            }
//            for (int i = 0; i < 10; i++) {
//                start = System.currentTimeMillis();
//                queensProblem = new CSNQueensProblem(n);
//                queensProblem.solve(forwardCheck);
//                times[i] = System.currentTimeMillis() - start;
//            }
//            statement = forwardCheck?"Backtracking with forward checking":"Backtracking only";
//            System.out.println(statement + " n = " + n);
//            printArrayStatistics(times);
//        }
    }
    public static void printArrayStatistics(long[] array) {
        long max = Arrays.stream(array).max().orElse(0);
        long min = Arrays.stream(array).min().orElse(0);
        double average = Arrays.stream(array).average().orElse(0);
        double sumOfSquares = Arrays.stream(array).mapToDouble(num -> Math.pow(num - average, 2)).sum();
        double standardDeviation = Math.sqrt(sumOfSquares / array.length);

        System.out.println("Maximum time in millisecond: " + max);
        System.out.println("Minimum time in millisecond: " + min);
        System.out.println("Average time in millisecond: " + average);
        System.out.println("Standard Deviation of time in millisecond: " + standardDeviation);
    }
}

