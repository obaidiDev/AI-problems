package ICS381.HW3;

import java.util.Random;

public class AgentC extends Agent{
    private final Random random;

    public AgentC() {
        random = new Random();
    }

    public int choice(Board board) {
        int col = board.col;
        int randomMove;

        do {
            randomMove = random.nextInt(col);
        } while (!board.isValidMove(randomMove));

        return randomMove;
    }
}
