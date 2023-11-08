import java.util.Scanner;

public class Main {
        // Метод для проверки победителя в игре
        public static String checkWinner() {
                GameBoard gameBoard = GameBoard.getInstance();

                // Проверка строк
                for (int i = 0; i < 9; i += 3) {
                        if (gameBoard.board[i].equals(gameBoard.board[i + 1]) && gameBoard.board[i].equals(gameBoard.board[i + 2])) {
                                return gameBoard.board[i];
                        }
                }

                // Проверка столбцов
                for (int i = 0; i < 3; ++i) {
                        if (gameBoard.board[i].equals(gameBoard.board[i + 3]) && gameBoard.board[i].equals(gameBoard.board[i + 6])) {
                                return gameBoard.board[i];
                        }
                }

                // Проверка диагоналей
                if (gameBoard.board[0].equals(gameBoard.board[4]) && gameBoard.board[0].equals(gameBoard.board[8])) {
                        return gameBoard.board[0];
                }
                if (gameBoard.board[2].equals(gameBoard.board[4]) && gameBoard.board[2].equals(gameBoard.board[6])) {
                        return gameBoard.board[2];
                }

                // Проверка, заполнено ли поле
                for (int i = 0; i < 9; ++i) {
                        if (gameBoard.board[i].equals(String.valueOf(i + 1))) {
                                // Если есть пустая ячейка, игра продолжается
                                return null;
                        }
                }

                // Если нет победителя и поле заполнено, то ничья
                return "Ничья";
        }

        public static void main(String[] args) {
                // Singleton
                GameBoard gameBoard = GameBoard.getInstance();

                // Strategy
                PrintStrategy printStrategy = new SimplePrintStrategy();

                // Factory Method
                PlayerFactory playerFactory = new ConcretePlayerFactory();
                Player playerX = playerFactory.getPlayer("X");
                Player playerO = playerFactory.getPlayer("O");

                // Adapter
                PlayerAdapter playerAdapterX = new PlayerAdapter(playerX);
                PlayerAdapter playerAdapterO = new PlayerAdapter(playerO);

                // Observer
                GameObserver gameObserver = new GameObserver();

                // Game loop
                String winner = null;
                Player currentPlayer = playerX;
                PlayerAdapter currentPlayerAdapter = playerAdapterX;
                while (winner == null) {
                        printStrategy.printBoard(gameBoard.board);
                        System.out.println(currentPlayerAdapter.makeMove());

                        // Получение хода игрока
                        Scanner in = new Scanner(System.in);
                        int numInput = in.nextInt();

                        // Проверка, что ход допустим (т.е. выбранная ячейка пуста)
                        if (gameBoard.board[numInput - 1].equals(String.valueOf(numInput))) {
                                // Если ход допустим, обновляем игровую доску и переключаем текущего игрока
                                gameBoard.board[numInput - 1] = currentPlayer.playerSymbol;
                                if (currentPlayer == playerX) {
                                        currentPlayer = playerO;
                                        currentPlayerAdapter = playerAdapterO;
                                } else {
                                        currentPlayer = playerX;
                                        currentPlayerAdapter = playerAdapterX;
                                }

                                // Проверяем, есть ли победитель
                                winner = checkWinner();
                        } else {
                                System.out.println("Ячейка занята, введите другой номер:");
                        }

                        // Уведомляем наблюдателя
                        if (winner != null) {
                                gameObserver.update(winner);
                        }
                }
        }
}