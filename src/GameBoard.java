// Singleton Pattern
public class GameBoard {
    private static GameBoard single_instance = null;
    public String[] board;
    private GameBoard() {
        board = new String[9];
        for (int a = 0; a < 9; a++) {
            board[a] = String.valueOf(a + 1);
        }
    }
    public static GameBoard getInstance() {
        if (single_instance == null)
            single_instance = new GameBoard();
        return single_instance;
    }
}

// Strategy Pattern
interface PrintStrategy {
    void printBoard(String[] board);
}

class SimplePrintStrategy implements PrintStrategy {
    public void printBoard(String[] board) {
        System.out.println("|---|---|---|");
        System.out.println("| " + board[0] + " | " + board[1] + " | " + board[2] + " |");
        System.out.println("|-----------|");
        System.out.println("| " + board[3] + " | " + board[4] + " | " + board[5] + " |");
        System.out.println("|-----------|");
        System.out.println("| " + board[6] + " | " + board[7] + " | " + board[8] + " |");
        System.out.println("|---|---|---|");
    }
}

// Decorator Pattern
abstract class PlayerDecorator extends Player {
    protected Player decoratedPlayer;
    public PlayerDecorator(Player decoratedPlayer) {
        super(decoratedPlayer.playerSymbol);
        this.decoratedPlayer = decoratedPlayer;
    }
    public abstract String makeMove();
}

class Player {
    protected String playerSymbol;
    public Player(String playerSymbol) {
        this.playerSymbol = playerSymbol;
    }
    public String makeMove() {
        return playerSymbol;
    }
}

class XPlayer extends Player {
    public XPlayer(String playerSymbol) {
        super(playerSymbol);
    }

    public String makeMove() {
        return "X";
    }
}

class OPlayer extends Player {
    public OPlayer(String playerSymbol) {
        super(playerSymbol);
    }

    public String makeMove() {
        return "O";
    }
}


// Adapter Pattern
class PlayerAdapter extends Player {
    private Player player;
    public PlayerAdapter(Player player) {
        super(player.playerSymbol);
        this.player = player;
    }
    public String makeMove() {
        return "Player " + player.makeMove() + " makes a move.";
    }
}

// Observer Pattern
interface Observer {
    void update(String winner);
}

class GameObserver implements Observer {
    public void update(String winner) {
        if (winner.equalsIgnoreCase("Ничья")) {
            System.out.println("Ничья!");
        } else {
            System.out.println("Поздравляем! " + winner + " победил!");
        }
    }
}

// Factory Method Pattern
abstract class PlayerFactory {
    abstract Player getPlayer(String type);
}

class ConcretePlayerFactory extends PlayerFactory {
    Player getPlayer(String type) {
        if (type.equals("X")) {
            return new XPlayer("X");
        } else if (type.equals("O")) {
            return new OPlayer("0");
        }
        return null;
    }
}

