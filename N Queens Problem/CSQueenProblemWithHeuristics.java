package ICS381.HW4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class CSQueenProblemWithHeuristics {
    private final ArrayList<Integer>[] domain;
    private final int[] solutionVector;
    private final int n;

    public CSQueenProblemWithHeuristics(int n) {
        domain = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            domain[i] = new ArrayList<>(n);
            for (int j = 1; j <= n; j++)
                domain[i].add(j);
        }
        solutionVector = new int[n];
        this.n = n;
    }

    public int[] solve() {
        Node currentNode = new Node(0,0);
        Stack<Node> stack = new Stack<>();
        generateOrderedChildren(currentNode);
        while (!currentNode.children.isEmpty()) {
            stack.push(currentNode.children.remove());
        }
        int column, position;
        while (!isSolution()) {
            currentNode = stack.pop();
            column = currentNode.col;
            position = currentNode.value;
            solutionVector[column] = position;
            generateValidDomain();
            domain[column].remove((Integer) position);
            if (!forwardCheck() || !isValid()) {
                domain[column].remove((Integer) position);
                solutionVector[column] = 0;
                while (stack.peek().depth != currentNode.depth) {
                    solutionVector[currentNode.col] = 0;
                    currentNode = currentNode.parent;
                }
                generateValidDomain();
            }
            else {
                generateOrderedChildren(currentNode);
                while (!currentNode.children.isEmpty())
                    stack.push(currentNode.children.remove());
            }
        }
        return solutionVector;
    }
    private void generateOrderedChildren(Node parent){
        if (parent.children.isEmpty())
            for (int i = 1; i <= n; i++)
                if (domain[MRV()].contains(i))
                    parent.addChild(new Node(i,nLCV(i)),MRV());
    }

    private int nLCV(int value){
        int count = 0;
        for (int i = 0; i < n; i++)
            count = domain[i].contains(value)?count+1:count;
        return count;
    }

    private int MRV() {
        int col = 0;
        int minimum = solutionVector[0] == 0 ? domain[0].size() : n + 1;
        for (int i = 0; i < n; i++) {
            if (solutionVector[i] == 0 && domain[i].size() < minimum) {
                minimum = domain[i].size();
                col = i;
            }
        }
        return col;
    }

    private void generateValidDomain(){
        boolean threat;
        for (int i = 0; i < n; i++) {
            domain[i] = new ArrayList<>(n);
            for (int j = 1; j <= n; j++) {
                threat = false;
                for (int p = 0; p < n && !threat; p++)
                    threat = wasThreat(p, i, j);
                if (!threat)
                    domain[i].add(j);
            }
        }
    }

    private boolean isSolution() {
        for (int i = 0; i < n - 1; i++)
            for (int j = i + 1; j < n; j++)
                if (isThreat(i, j))
                    return false;
        return true;
    }

    private boolean isThreat(int i, int j) {
        return solutionVector[i] == solutionVector[j] ||
                solutionVector[i] == solutionVector[j] + (i - j) ||
                solutionVector[i] == solutionVector[j] - (i - j);
    }

    private boolean wasThreat(int index, int col, int value) {
        return solutionVector[index] != 0 && (solutionVector[index] == value ||
                solutionVector[index] == value + (col - index) ||
                solutionVector[index] == value - (col - index));
    }

    private boolean isValid(){
        int i, j;
        for (i = 1; i < n; i++)
            for(j = 0; j < i && solutionVector[i] > 0; j++)
                if (isThreat(i,j) && solutionVector[j] > 0)
                    return false;
        return true;
    }

    private boolean forwardCheck() {
        for (int i = 0; i < n; i++)
            if (domain[i].isEmpty() && solutionVector[i] == 0)
                return false;
        return true;
    }

//    private boolean isFutureThreat(int thatValue, int thatCol, int thisValue, int thisCol) {
//        return thatValue == thisValue ||
//                thatValue == thisValue + (thisCol - thatCol) ||
//                thatValue == thisValue - (thisCol - thatCol);
//    }
//    private void getPrevDomain(int col, int value) {
//        int[] threatenedPositions = new int[3];
//        for (int i = 0; i < n; i++) {
//            if (i!=col) {
//                threatenedPositions[0] = value;
//                threatenedPositions[1] = value + col - i;
//                threatenedPositions[2] = value - (col - i);
//                for (Integer position : threatenedPositions)
//                    for (int j = 0; j < n; j++)
//                        if (!domain[i].contains(position) && !wasThreat(j, i, value) && position > 0 && position <= n && solutionVector[j] == 0)
//                            domain[i].add(position);
//            }
//        }
//    }
//    private int countZeros(){
//        int zeros = 0;
//        for (int i = 0; i < n; i++)
//            if (solutionVector[i] == 0)
//                zeros++;
//        return zeros;
//    }
//    private int LCV(int col) {
//        int value = 1;
//        int[] counter = new int[n];
//        for (int v : domain[col])
//            for (int i = 0; i < n; i++)
//                if (domain[i].contains(v))
//                    counter[v-1]+=1;
//
//        int minCount = counter[0];
//        for (int i = 1; i < n; i++){
//            if ((counter[i] < minCount && counter[i] > 0) || minCount == 0){
//                minCount = counter[i];
//                value = i + 1;
//            }
//        }
//        return value;
//    }
//    private void updateDomain(int col) {
//        int takenPosition = solutionVector[col];
//        int[] threatenedPositions = new int[3];
//        for (int i = 0; i < n; i++) {
//            if (i != col) {
//                threatenedPositions[0] = takenPosition;
//                threatenedPositions[1] = takenPosition + col - i;
//                threatenedPositions[2] = takenPosition - (col - i);
//                for (Integer position : threatenedPositions)
//                    domain[i].remove(position);
//            }
//        }
//    }
//    private int sumDistinctDomain(){
//        int commonDomainCounter;
//        int distinctDomain = 0;
//        int zeros = countZeros();
//        boolean cont;
//        for (int i = 0; i < n-1; i++){
//            commonDomainCounter = 0;
//            if (solutionVector[i] == 0)
//                for (int j = i+1; j < n; j++)
//                    if (solutionVector[j] == 0)
//                        for (Integer thisValue: domain[i]) {
//                            cont = true;
//                            for (int k = 0; k < domain[j].size()&&cont; k++)
//                                if (isFutureThreat(domain[j].get(k), j, thisValue, i)) {
//                                    commonDomainCounter++;
//                                    cont = false;
//                                }
//                        }
//            distinctDomain = distinctDomain + domain[i].size() - commonDomainCounter;
//        }

//    }

//    private void printDomain() {
//        for(ArrayList<Integer> list :domain) {
//            System.out.print("[ ");
//            for (int i = 0; i < list.size(); i++) {
//                System.out.print(list.get(i));
//                if (i != list.size() - 1) {
//                    System.out.print(", ");
//                }
//            }
//            System.out.println(" ]");
//        }
//    }

    public static void main(String[] args) {
        long start;
        long[] times = new long[10];
        CSQueenProblemWithHeuristics queensProblem;
        for (int n = 50; n <= 350; n += 50) {
            for (int i = 0; i < 10; i++) {
                start = System.currentTimeMillis();
                queensProblem = new CSQueenProblemWithHeuristics(n);
                queensProblem.solve();
                times[i] = System.currentTimeMillis() - start;
            }
            System.out.println("n = "+n);
            printArrayStatistics(times);
        }
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
