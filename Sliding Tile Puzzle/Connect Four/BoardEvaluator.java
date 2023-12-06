package ICS381.HW3;

import java.util.*;

public class BoardEvaluator {
    int[] vector = {0,0,0,0};
    int[] w = {40, 19, 2, -244, -9, -1};
    int n;
    int hisN;
    public BoardEvaluator(int agentNumber, int opponentNumber){
        this.n = agentNumber;
        this.hisN = opponentNumber;

    }
    public BoardEvaluator(int agentNumber, int opponentNumber, int p){
        this.n = agentNumber;
        this.hisN = opponentNumber;

    }
    public Integer[] orderMoves(Board board, int sign) {
        Integer[] bestMoves = {0, 1, 2, 3, 4, 5, 6};
        List<Integer> randomMoves = new ArrayList<>(7);
        Collections.addAll(randomMoves, bestMoves);
        Collections.shuffle(randomMoves);
        for (int i = 1; i < randomMoves.size(); i++) {
            int key = randomMoves.get(i);
            int j = i - 1;
            if (board.isValidMove(randomMoves.get(j)) && board.isValidMove(key)) {
                board.move(randomMoves.get(j), n);
                int prev = sign * evaluate(board);
                board.undo();
                board.move(key, n);
                int suc = sign * evaluate(board);
                board.undo();
                while (j >= 0 && prev < suc) {
                    randomMoves.set(j + 1, randomMoves.get(j));
                    j--;
                }
                randomMoves.set(j + 1, key);
            }
        }
        bestMoves = randomMoves.toArray(new Integer[randomMoves.size()]);
        return bestMoves;
    }
    public int evaluate(Board board){
        if (board.isWin())
            return 10000;
        if (board.isLose())
            return -10000;
        int heuristicSum = 0;
        int myThree;
        int myTwo;
        int myOne;
        int hisThree;
        int hisTwo;
        int hisOne;
        for (int i = 0; i < board.ro; i++) {
            if (board.numberOfPlayedMovesPerRow[i] >= 4)
                for (int j = 0; j < board.col - 3; j++) {
                    vector[0] = board.board[i][j];
                    vector[1] = board.board[i][j + 1];
                    vector[2] = board.board[i][j + 2];
                    vector[3] = board.board[i][j + 3];
                    myThree = checkThree(n);
                    myTwo = checkTwo(n);
                    myOne = checkOne(n);
                    hisThree = checkThree(hisN);
                    hisTwo = checkTwo(hisN);
                    hisOne = checkOne(hisN);
                    heuristicSum+= myThree*w[0]+myTwo*w[1]+myOne*w[2]+hisThree*w[3]+hisTwo*w[4]+hisOne*w[5];
                }
        }
        for (int i = 0; i < board.ro - 3; i++) {
            for (int j = 0; j < board.col - 3; j++) {
                vector[0] = board.board[i][j];
                vector[1] = board.board[i + 1][j + 1];
                vector[2] = board.board[i + 2][j + 2];
                vector[3] = board.board[i + 3][j + 3];
                myThree = checkThree(n);
                myTwo = checkTwo(n);
                myOne = checkOne(n);
                hisThree = checkThree(hisN);
                hisTwo = checkTwo(hisN);
                hisOne = checkOne(hisN);
                heuristicSum+= myThree*w[0]+myTwo*w[1]+myOne*w[2]+hisThree*w[3]+hisTwo*w[4]+hisOne*w[5];
                vector[0] = board.board[i][j + 3];
                vector[1] = board.board[i + 1][j + 2];
                vector[2] = board.board[i + 2][j + 1];
                vector[3] = board.board[i + 3][j];
                myThree = checkThree(n);
                myTwo = checkTwo(n);
                myOne = checkOne(n);
                hisThree = checkThree(hisN);
                hisTwo = checkTwo(hisN);
                hisOne = checkOne(hisN);
                heuristicSum+= myThree*w[0]+myTwo*w[1]+myOne*w[2]+hisThree*w[3]+hisTwo*w[4]+hisOne*w[5];
            }
        }
        int j;
        for (int i = 0; i < board.col; i++){
            j = board.nextAvailableRowPerCol[i];
            while (board.ro - j >= 4 && board.isValidMove(i)) {
                vector[0] = board.board[j][i];
                vector[1] = board.board[j + 1][i];
                vector[2] = board.board[j + 2][i];
                vector[3] = board.board[j + 3][i];
                j++;
                myThree = checkThree(n);
                myTwo = checkTwo(n);
                myOne = checkOne(n);
                hisThree = checkThree(hisN);
                hisTwo = checkTwo(hisN);
                hisOne = checkOne(hisN);
                heuristicSum+= myThree*w[0]+myTwo*w[1]+myOne*w[2]+hisThree*w[3]+hisTwo*w[4]+hisOne*w[5];
            }
        }
        return heuristicSum;
    }
    private int checkThree(int n){
        if (vector[0]*vector[0] + vector[1]*vector[1] + vector[2]*vector[2] + vector[3]*vector[3] == 3*n*n)
            return 1;
        return 0;
    }
    private int checkTwo(int n){
        if (vector[0]*vector[0] + vector[1]*vector[1] + vector[2]*vector[2] + vector[3]*vector[3] == 2*n*n)
            return 1;
        return 0;
    }
    private int checkOne(int n){
        if (vector[0]*vector[0] + vector[1]*vector[1] + vector[2]*vector[2] + vector[3]*vector[3] == n*n)
            return 1;
        return 0;
    }
}
