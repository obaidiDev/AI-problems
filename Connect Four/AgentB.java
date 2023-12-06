package ICS381.HW3;

import java.util.Random;

public class AgentB extends Agent{
    private static final int DEPTH_LIMIT = 7; // Maximum depth for the search algorithm
    private final double opponentProbability; // Probability of opponent selecting their best move
    BoardEvaluator evaluator;
    private final int MAX_SCORE = 10000;
    private final int MIN_SCORE = -MAX_SCORE;
    int n;
    int opn;
    int pruningCounter = 0;
    public AgentB(int n, int opponent_n, double opponentProbability) {
        this.opponentProbability = opponentProbability;
        this.n = n;
        this.opn = opponent_n;
        evaluator = new BoardEvaluator(n, opponent_n);
    }

    public double expectimax(Board board, int depth, double alpha,double beta, boolean maximizingPlayer, boolean bestMove) {
        if (depth == 0 || board.isGameOver()) {
            return evaluator.evaluate(board);
        }

        if (maximizingPlayer) {
            double maxEval = Integer.MIN_VALUE;
            double sumEval = 0;
            int count = 0;
            for (int col: evaluator.orderMoves(board,1)) {
                if (board.isValidMove(col)) {
                    board.move(col, 1);
                    double eval = expectimax(board, depth - 1, alpha,beta,false,true);
                    sumEval += eval;
                    board.undo();
                    maxEval = Math.max(maxEval, eval);
                    alpha = Math.max(alpha, eval);
                    count++;
                    if (MAX_SCORE <= alpha && bestMove) {
                        pruningCounter++;
                        break; // Beta cutoff
                    }
                }
            }
            return bestMove?maxEval:(sumEval-maxEval)/(count-1);
        } else {
            double expectedEval = 0;
            for (int col = 0; col < board.col; col++) {
                if (board.isValidMove(col)) {
                    board.move(col, 2);
                    double bestEval = expectimax(board, depth - 1, alpha,beta,true,true);
                    double worstEval = expectimax(board, depth - 1, alpha,beta,true,false);
                    board.undo();
                    expectedEval = opponentProbability*bestEval+(1-opponentProbability)*worstEval;
                    beta = Math.min(beta, expectedEval);
                    if (beta <= MIN_SCORE) {
                        break; // Alpha cutoff
                    }
                }
            }
            return expectedEval;
        }
    }
    public int choice(Board board){
        Random random = new Random();
        int bestMove;
        do {
            bestMove = random.nextInt(6);
        } while (!board.isValidMove(bestMove));
        double bestScore = Integer.MIN_VALUE;

        for (int col = 0; col < board.col; col++) {
            if (board.isValidMove(col)) {
                double score = expectimax(board.play(col,n), DEPTH_LIMIT, Integer.MIN_VALUE, Integer.MAX_VALUE, true,true);
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = col;
                }
            }
        }
        return bestMove;
    }
    public void resetCounter(){
        pruningCounter = 0;
    }
}
