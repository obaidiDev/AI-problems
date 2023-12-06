package ICS381.HW3;

public class Board{
    final int[][] board;
    int ro;
    int col;
    int[] nextAvailableRowPerCol;
    int[] numberOfPlayedMovesPerRow;
    int movesCounter = 0;
    int[] lastPlay = new int[2];
    public Board(int ro, int col){
        this.ro = ro;
        this.col = col;
        this.board = new int[ro][col];
        nextAvailableRowPerCol = new int[col];
        numberOfPlayedMovesPerRow = new int[ro];
        for (int i = 0; i < col; i++){
            nextAvailableRowPerCol[i] = ro-1;
        }
        for (int i = 0; i < ro; i++){
            numberOfPlayedMovesPerRow[i] = 0;
        }
    }
    private Board(int ro, int col, int[] nextAvailableRowPerCol, int[] numberOfPlayedMovesPerRow, int[][] board) {
        this.ro = ro;
        this.col = col;
        this.nextAvailableRowPerCol = nextAvailableRowPerCol.clone();
        this.numberOfPlayedMovesPerRow = numberOfPlayedMovesPerRow.clone();
        this.board = new int[ro][col];
        for (int i = 0; i < ro; i++) {
            this.board[i] = board[i].clone();
        }
    }
    private Board(Board that){
        this(that.ro,that.col,that.nextAvailableRowPerCol,that.numberOfPlayedMovesPerRow,that.board);
        that.undo();
    }
    public boolean isWin(){
        return isFourConsecutive(1);
    }
    public boolean isLose(){
        return isFourConsecutive(2);
    }
    public boolean isValidMove(int col){
        return nextAvailableRowPerCol[col] - 1 >= 0;
    }
    public boolean isGameOver(){
        return isWin() || isLose() || movesCounter == ro*col;
    }
    private boolean isFourConsecutive(int n){
        return isFourConsecutiveColumns(n) || isFourConsecutiveRows(n) || isFourConsecutiveDiagonals(n);
    }
    private boolean isFourConsecutiveRows(int n){
        int j;
        for (int i = 0; i < col; i++){
            j = nextAvailableRowPerCol[i];
            while (ro - j >= 4 && isValidMove(i)) {
                if (board[j][i] == n && board[j + 1][i] == n
                        && board[j + 2][i] == n && board[j + 3][i] == n)
                    return true;
                j++;
            }
        }
        return false;
    }
    private boolean isFourConsecutiveColumns(int n){
        for (int i = 0; i < ro; i++) {
            if (numberOfPlayedMovesPerRow[i] >= 4)
                for (int j = 0; j < col - 3; j++)
                    if (board[i][j] == n && board[i][j + 1] == n
                            && board[i][j + 2] == n && board[i][j + 3] == n)
                        return true;
        }
        return false;
    }
    private boolean isFourConsecutiveDiagonals(int n){
        for (int i = 0; i < ro - 3; i++) {
            for (int j = 0; j < col - 3; j++) {
                if (board[i][j] == n && board[i + 1][j + 1] == n &&
                    board[i + 2][j + 2] == n && board[i + 3][j + 3] == n) {
                    return true;
                }
                if (board[i][j + 3] == n && board[i + 1][j + 2] == n &&
                        board[i + 2][j + 1] == n && board[i + 3][j] == n) {
                    return true;
                }
            }
        }
        return false;
    }
    public void move(int col, int player_n){
        int nextAvailableRow = nextAvailableRowPerCol[col];
        lastPlay[0] = nextAvailableRow;
        lastPlay[1] = col;
        board[nextAvailableRow][col] = player_n;
        numberOfPlayedMovesPerRow[nextAvailableRow] += 1;
        nextAvailableRowPerCol[col] = nextAvailableRow-1;
        movesCounter++;
    }
    public void undo(){
        int nextAvailableRow = lastPlay[0];
        int col = lastPlay[1];
        board[nextAvailableRow][col] = 0;
        numberOfPlayedMovesPerRow[nextAvailableRow] -= 1;
        nextAvailableRowPerCol[col] = nextAvailableRow;
        movesCounter--;
    }
    public Board play(int col, int player_n){
        move(col,player_n);
        return new Board(this);
    }
    public void showBoard(){
        System.out.println("_".repeat(col+4));
        for (int row = 0; row < ro; row++) {
            System.out.print("| ");
            for (int col = 0; col < this.col; col++) {
                if (board[row][col] == 0) {
                    System.out.print(0);
                } else if (board[row][col] == 1) {
                    System.out.print(1);
                } else if (board[row][col] == 2) {
                    System.out.print(2);
                }
            }
            System.out.print(" |");
            System.out.println();
        }
        System.out.println("_".repeat(col+4));
    }
    public Board reset(){
        return new Board(ro,col);
    }
}
