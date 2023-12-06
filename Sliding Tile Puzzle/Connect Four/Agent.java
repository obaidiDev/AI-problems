package ICS381.HW3;

public abstract class Agent {
    int pruningCounter = 0;
    abstract int choice(Board board);
}
