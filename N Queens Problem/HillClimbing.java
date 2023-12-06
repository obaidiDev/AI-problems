package ICS381.HW4;

import java.util.Arrays;

public class HillClimbing {
    private final int n;
    private final int[] vector;
    public HillClimbing(int n){
        this.n = n;
        vector = new int[n];
    }
    public int[] solve(){
        getInitialState();
        int threats = evaluateThreats(), futureThreats, worstPosition;
        boolean localMinima = false, betterState;
        while (threats > 0 && !localMinima){
            betterState = false;
            worstPosition = worstPosition();
            // tweaking
            for (int i = 0; i < n && !betterState; i++){
                interchange(worstPosition,i);
                futureThreats = evaluateThreats();
                if (futureThreats < threats){
                    threats = futureThreats;
                    betterState = true;
                }
                if (!betterState)
                    interchange(i,worstPosition);
            }
            localMinima = !betterState;
        }
        System.out.println(evaluateThreats());
        return vector;
    }
    private int worstPosition(){
        int threats, maxThreats = 0, index = -1;
        for (int i = 0; i < n; i++) {
            threats = 0;
            for (int j = 0; j < n; j++)
                threats = isThreat(i, j) && i != j ? threats + 1 : threats;
            if (threats > maxThreats){
                maxThreats = threats;
                index = i;
            }
        }
        return index;
    }
    private int evaluateThreats(){
        int threats = 0;
        for (int i = 0; i < n - 1; i++)
            for (int j = i + 1; j < n; j++)
                threats = isThreat(i,j)?threats+1:threats;
        return threats;
    }
    private void getInitialState(){
        int i, j;
        for (i = 0; i*2+1 <= n; i++)
            vector[i] = i*2+1;
        for (j = 1; 2*j <= n; i++,j++)
            vector[i] = 2*j;
    }
    private boolean isThreat(int i, int j){
        return  vector[i] == vector[j] ||
                vector[i] == vector[j] + (i - j) ||
                vector[i] == vector[j] - (i - j);
    }
    private void interchange(int i, int j){
        int temp = vector[i];
        vector[i] = vector[j];
        vector[j] = temp;
    }
//    private void generateInitialState(){
//        SLL<Integer> positions = new SLL<>();
//        boolean valid;
//        positions.addToTail(1);
//        for (int i = 0; i < n - 1; i++) {
//            positions.addToTail(i+2);
//            vector[i] = positions.head.info;
//            valid = true;
//            for (int j = i + 1; j < n && valid; j++)
//                valid = !isThreat(i,j);
//            vector[i] = valid?vector[i]:0;
//            if (valid)
//                positions.deleteFromHead();
//            else
//                positions.head = positions.head.next;
//        }
//    }

    public static void main(String[] args) {
        long start;
        long[] times = new long[10];
        HillClimbing queensProblem;
        for (int n = 5000; n <= 9000; n += 1000) {
            for (int i = 0; i < 10; i++) {
                start = System.currentTimeMillis();
                queensProblem = new HillClimbing(n);
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
