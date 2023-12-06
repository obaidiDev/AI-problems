package ICS381.HW3;

import java.util.Random;

public class AgentA extends Agent{
    int pruningCounter = 0;
    final int DEPTH_LIMIT = 20;
    int n, opn;
    BoardEvaluator evaluator;
    public AgentA(int n, int opponent_n){
        this.n = n;
        opn = opponent_n;
        evaluator = new BoardEvaluator(n,opponent_n);
    }
    public int choice(Board board){
        Random random = new Random();
        int bestMove;
        do {
            bestMove = random.nextInt(6);
        } while (!board.isValidMove(bestMove));

        int bestScore = Integer.MIN_VALUE;

        for (int col = 0; col < board.col; col++) {
            if (board.isValidMove(col)) {
                int score = minimax(board.play(col,n), DEPTH_LIMIT, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = col;
                }
            }
        }
        return bestMove;
    }
    private int minimax(Board board, int depth, int alpha, int beta, boolean maximizingPlayer) {
        if (depth == 0 || board.isGameOver()) {
            return evaluator.evaluate(board);
        }

        if (maximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (int col: evaluator.orderMoves(board,1)) {
                if (board.isValidMove(col)) {
//                    Board newBoard = board.play(col,n);
                    board.move(col,n);
                    int eval = minimax(board, depth - 1, alpha, beta, false);
                    board.undo();
                    maxEval = Math.max(maxEval, eval);
                    alpha = Math.max(alpha, eval);
                    if (beta <= alpha) {
                        pruningCounter++;
                        break; // Beta cutoff
                    }
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int col: evaluator.orderMoves(board,-1)) {
                if (board.isValidMove(col)) {
//                    Board newBoard = board.play(col,opn);
                    board.move(col,opn);
                    int eval = minimax(board, depth - 1, alpha, beta, true);
                    board.undo();
                    minEval = Math.min(minEval, eval);
                    beta = Math.min(beta, eval);
                    if (beta <= alpha) {
                        pruningCounter++;
                        break; // Alpha cutoff
                    }
                }
            }
            return minEval;
        }
    }
    public void resetCounter(){
        pruningCounter = 0;
    }
}
