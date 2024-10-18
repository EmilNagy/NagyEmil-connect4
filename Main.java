import java.util.Random;
import java.util.Scanner;

public class Connect4 {
    private static final char EMPTY = '.';
    private static final char YELLOW = 'Y';
    private static final char RED = 'R';

    private final char[][] board;
    private final int rows;
    private final int cols;
    private final Random random = new Random();

    public Connect4(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.board = new char[rows][cols];


        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        boolean humanTurn = true;
        boolean gameWon = false;

        printBoard();

        while (!gameWon && !isBoardFull()) {
            if (humanTurn) {
                System.out.println("Humán játékos (sárga), válassz egy oszlopot (1-" + cols + "):");
                int col = scanner.nextInt() - 1;
                if (isValidMove(col)) {
                    makeMove(col, YELLOW);
                    humanTurn = false;
                } else {
                    System.out.println("Érvénytelen lépés. Próbáld újra.");
                }
            } else {
                System.out.println("Gépi játékos (piros) lép.");
                int col = getRandomMove();
                makeMove(col, RED);
                humanTurn = true;
            }

            printBoard();
            gameWon = checkWin();
        }

        if (gameWon) {
            System.out.println((humanTurn ? "Gépi" : "Humán") + " játékos nyert!");
        } else {
            System.out.println("Döntetlen! A tábla megtelt.");
        }
        scanner.close();
    }

    private boolean isValidMove(int col) {
        return col >= 0 && col < cols && board[0][col] == EMPTY;
    }

    private void makeMove(int col, char player) {
        for (int row = rows - 1; row >= 0; row--) {
            if (board[row][col] == EMPTY) {
                board[row][col] = player;
                break;
            }
        }
    }

    private boolean isBoardFull() {
        for (int col = 0; col < cols; col++) {
            if (board[0][col] == EMPTY) {
                return false;
            }
        }
        return true;
    }

    private int getRandomMove() {
        int col;
        do {
            col = random.nextInt(cols);
        } while (!isValidMove(col));
        return col;
    }

    private boolean checkWin() {

        return checkHorizontalWin() || checkVerticalWin() || checkDiagonalWin();
    }

    private boolean checkHorizontalWin() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols - 3; col++) {
                if (board[row][col] != EMPTY &&
                        board[row][col] == board[row][col + 1] &&
                        board[row][col] == board[row][col + 2] &&
                        board[row][col] == board[row][col + 3]) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkVerticalWin() {
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows - 3; row++) {
                if (board[row][col] != EMPTY &&
                        board[row][col] == board[row + 1][col] &&
                        board[row][col] == board[row + 2][col] &&
                        board[row][col] == board[row + 3][col]) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDiagonalWin() {

        for (int row = 0; row < rows - 3; row++) {
            for (int col = 0; col < cols - 3; col++) {
                if (board[row][col] != EMPTY &&
                        board[row][col] == board[row + 1][col + 1] &&
                        board[row][col] == board[row + 2][col + 2] &&
                        board[row][col] == board[row + 3][col + 3]) {
                    return true;
                }
            }
        }


        for (int row = 0; row < rows - 3; row++) {
            for (int col = 3; col < cols; col++) {
                if (board[row][col] != EMPTY &&
                        board[row][col] == board[row + 1][col - 1] &&
                        board[row][col] == board[row + 2][col - 2] &&
                        board[row][col] == board[row + 3][col - 3]) {
                    return true;
                }
            }
        }

        return false;
    }

    private void printBoard() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Connect4 game = new Connect4(6, 7);
        game.playGame();
    }
}
