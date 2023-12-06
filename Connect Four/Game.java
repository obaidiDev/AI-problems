package ICS381.HW3;

import java.util.Arrays;

public class Game {
    public static void main(String[] args) {
        int rows = 6;
        int columns = 7;
        Board board = new Board(rows, columns);

        // Create two agents
        AgentA agentA1 = new AgentA(1, 2);
        AgentB agentB = new AgentB(2,1,0.5);
        AgentA agentA2 = new AgentA(2, 1);
        AgentB agentB1 = new AgentB(1,2,0.75);
        AgentB agentB2 = new AgentB(1,2,0.5);
        AgentB agentB3 = new AgentB(1,2,0.25);

        AgentC agentC = new AgentC();


        int wins = 0, losses = 0;
        long[] times = new long[10];
        int[] prunes = new int[10];
        long time;
        System.out.println("AgentA vs AgentA");
        for (int i = 0; i < 10; i++){
            time = System.nanoTime();
            runGame(agentA1,agentA2,board);
            time = System.nanoTime() - time;
            wins = wins + (board.isWin()?1:0);
            losses = losses + (board.isLose()?1:0);
            prunes[i] = agentA1.pruningCounter+agentA2.pruningCounter;
            times[i] = time/1000000;
            agentA1.resetCounter();
            agentA2.resetCounter();
            board = board.reset();
        }
        System.out.println("Wins per loses: " + wins +" per "+ losses);
        printArrayStatistics(times);
        printArrayStatistics(prunes);
        wins = 0; losses = 0;
        System.out.println("\nAgentA vs AgentB (p=0.5)");
        for (int i = 0; i < 10; i++){
            time = System.nanoTime();
            runGame(agentA1,agentB,board);
            wins = wins + (board.isWin()?1:0);
            losses = losses + (board.isLose()?1:0);
            prunes[i] = agentA1.pruningCounter+agentB.pruningCounter;
            times[i] = time/1000000;
            agentA1.resetCounter();
            agentB.resetCounter();
            board = board.reset();
        }
        System.out.println("Wins per loses: " + wins +" per "+ losses);
        printArrayStatistics(times);
        printArrayStatistics(prunes);
        wins = 0; losses = 0;
        System.out.println("\nAgentA vs AgentC");
        for (int i = 0; i < 10; i++){
            time = System.nanoTime();
            runGame(agentA1,agentC,board);
            time = System.nanoTime() - time;
            wins = wins + (board.isWin()?1:0);
            losses = losses + (board.isLose()?1:0);
            prunes[i] = agentA1.pruningCounter;
            times[i] = time/1000000;
            agentA1.resetCounter();
            board = board.reset();
        }
        System.out.println("Wins per loses: " + wins +" per "+ losses);
        printArrayStatistics(times);
        printArrayStatistics(prunes);
        wins = 0; losses = 0;
        System.out.println("\nAgentB (0.75) vs AgentC");
        for (int i = 0; i < 10; i++){
            time = System.nanoTime();
            runGame(agentB1,agentC,board);
            time = System.nanoTime() - time;
            wins = wins + (board.isWin()?1:0);
            losses = losses + (board.isLose()?1:0);
            prunes[i] = agentB1.pruningCounter;
            times[i] = time/1000000;
            agentB1.resetCounter();
            board = board.reset();
        }
        System.out.println("Wins per loses: " + wins +" per "+ losses);
        printArrayStatistics(times);
        printArrayStatistics(prunes);
        wins = 0; losses = 0;
        System.out.println("\nAgentB (0.5) vs AgentC");
        for (int i = 0; i < 10; i++){
            time = System.nanoTime();
            runGame(agentB2,agentC,board);
            time = System.nanoTime() - time;
            wins = wins + (board.isWin()?1:0);
            losses = losses + (board.isLose()?1:0);
            prunes[i] = agentB2.pruningCounter;
            times[i] = time/1000000;
            agentB2.resetCounter();
            board = board.reset();
        }
        System.out.println("Wins per loses: " + wins +" per "+ losses);
        printArrayStatistics(times);
        printArrayStatistics(prunes);
        wins = 0; losses = 0;
        System.out.println("\nAgentB (0.25) vs AgentC");
        for (int i = 0; i < 10; i++){
            time = System.nanoTime();
            runGame(agentB3,agentC,board);
            time = System.nanoTime() - time;
            wins = wins + (board.isWin()?1:0);
            losses = losses + (board.isLose()?1:0);
            prunes[i] = agentB3.pruningCounter;
            times[i] = time/1000000;
            agentB3.resetCounter();
            board = board.reset();
        }
        System.out.println("Wins per loses: " + wins +" per "+ losses);
        printArrayStatistics(times);
        printArrayStatistics(prunes);
    }
    static void runGame(Agent a, Agent b, Board board){
        boolean agentA1Turn = true;
        while (!board.isGameOver()) {
            int move = agentA1Turn ? a.choice(board) : b.choice(board);
            int number = agentA1Turn?1:2;
            board.move(move, number);
            agentA1Turn = !agentA1Turn;
        }
    }
    public static void printArrayStatistics(int[] array) {
        long max = Arrays.stream(array).max().orElse(0);
        long min = Arrays.stream(array).min().orElse(0);
        double average = Arrays.stream(array).average().orElse(0);
        double sumOfSquares = Arrays.stream(array).mapToDouble(num -> Math.pow(num - average, 2)).sum();
        double standardDeviation = Math.sqrt(sumOfSquares / array.length);

        System.out.println("Maximum prunes: " + max);
        System.out.println("Minimum prunes: " + min);
        System.out.println("Average prunes: " + average);
        System.out.println("Standard Deviation of prunes: " + standardDeviation);
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
