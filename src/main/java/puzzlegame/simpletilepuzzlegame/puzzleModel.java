package puzzlegame.simpletilepuzzlegame;
import java.util.ArrayList;
import java.util.Random;


public class puzzleModel {
    /*
     * Initialized class variables
     */
    private int difficulty = 1;
    private ArrayList<puzzleTileObject> board; //the current board
    private ArrayList<puzzleTileObject> goalBoard; //The board the player wants to get to
    private ArrayList<puzzleTileObject> startingBoard; //The board the player starts with. Used in code in similar fashion as "temp val" variables
    private int boardSize = 6;
    private int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53}; //used for prime tile
    private int moves = 1;


    //default Constructor
    public puzzleModel() { //The Puzzle Model Object primary focuses on a "board" of puzzleTileObjects, with a few other variables to keep track of
        ArrayList<puzzleTileObject> board = new ArrayList<puzzleTileObject>();
        for (int i = 0; i < boardSize*boardSize; i++) {
            board.add(puzzleTileObject.getTileObjectFromID("00000"));
        }
        this.board = board;
        this.difficulty = 0;
    }

    //make board
    public void makeBoard(String[][] boardList) { // variables in puzzle tile object: int tileNum, int tileColor, boolean notModifier //6x6 board
        ArrayList<puzzleTileObject> board = new ArrayList<puzzleTileObject>();
        for (int i = 0; i < boardSize; ++i) {
            for (int j = 0; j < boardSize; ++j) {
                board.add(puzzleTileObject.getTileObjectFromID(boardList[i][j]));
            }
        }
        this.board = board;

    }

    /*
     * PUBLIC METHODS: Setting and Returning
     */
    public void setGoalBoard() { //Sets Goal Board to Current Board
        ArrayList<puzzleTileObject> newGoalBoard = new ArrayList<puzzleTileObject>();
        for (int i = 0; i < boardSize*boardSize; ++i) {
            newGoalBoard.add(puzzleTileObject.getTileObjectFromID(String.valueOf(this.board.get(i).getID())));
            }
        this.goalBoard = newGoalBoard;
        //printGoal();
    }

    public void resetBoard() { //Sets the current board to the starting board
        ArrayList<puzzleTileObject> newBoard = new ArrayList<puzzleTileObject>();
        for (int i = 0; i < boardSize*boardSize; ++i) {
            newBoard.add(puzzleTileObject.getTileObjectFromID(String.valueOf(this.startingBoard.get(i).getID())));
        }
        this.board = newBoard;
    }

    public void setStartingBoard() {
        ArrayList<puzzleTileObject> newStartBoard = new ArrayList<puzzleTileObject>();

        for (int i = 0; i < boardSize*boardSize; ++i) {
            newStartBoard.add(puzzleTileObject.getTileObjectFromID(String.valueOf(this.board.get(i).getID())));
        }
        this.startingBoard = newStartBoard;
    }

    public ArrayList<puzzleTileObject> getBoard() {
        return(this.board);
    }

    public ArrayList<puzzleTileObject> getGoalBoard() {
        return(this.goalBoard);
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    public void useMove() {
        this.moves = this.moves - 1;
    }

    public int getMoves() {
        return (this.moves);
    }

    public int getBoardSize() {
        return(this.boardSize);
    }


    public boolean checkIfGoal() { //TODO: Does this work how I think it should? If not, panic and fix
        for (int i = 0; i < boardSize*boardSize; i++) {
                if (!this.goalBoard.get(i).getID().equals(this.board.get(i).getID())) {
                    return(false);
                }
            }

        return(true);
    }

    public void increaseDifficulty() {
        this.difficulty = this.difficulty + 1;
    }

    public int getDifficulty() {
        return(this.difficulty);
    }

    public void resetDifficulty() {
        this.difficulty = this.difficulty = 0;
    }


    /*
     * PUBLIC METHODS: Flipping Tiles Logic
     */

    public void flipTileAtLocation(int locationID) {
        this.board.get(locationID).flipTile();
    }


    //Contains the logic to find the flipped tiles, and then flips them.
    public void findTilesToFlip(int initialLocationID) {
        ArrayList<Integer> flipList = new ArrayList<Integer>();

        //Tests:
        //Check if initialLocationID can flip itself (not modifier)
        //Check Pattern types:  Basic, ROW, COLUMN, PLUS, STAR, PRIME
        if (!this.board.get(initialLocationID).getNotModifier()) {
            flipList.add(initialLocationID);
        }

        int tileType = this.board.get(initialLocationID).getTitleType();

        //Remove if mirror tile is added
        if (tileType == 6) { //base case, up to 8 other squares to flip
            tileType = 0;
            System.out.println("Mirror tile not added");
        }

        if (tileType == 0) { //base case, up to 8 other squares to flip
            if (((initialLocationID - 1) % boardSize == (initialLocationID % boardSize) - 1) && ((initialLocationID - 1 > -1))) { //1 to left
                flipList.add(initialLocationID - 1);
            }
            if ((initialLocationID + 1) % boardSize == (initialLocationID % boardSize) + 1) { //1 to right
                flipList.add(initialLocationID + 1);
            }
            if ((initialLocationID + boardSize) < boardSize * boardSize) {  // 1 down
                flipList.add(initialLocationID + boardSize);
                //System.out.print("1down");
            }
            if ((initialLocationID - boardSize) > -1) {  // 1 up
                flipList.add(initialLocationID - boardSize);
                //System.out.print("1up");
            }
            if (((initialLocationID - 1) % boardSize == (initialLocationID % boardSize) - 1) && (initialLocationID - 1 - boardSize > -1)) { // left up corner
                flipList.add(initialLocationID - 1 - boardSize);
                //System.out.print("Lup");
            }
            if (((initialLocationID + 1) % boardSize == (initialLocationID % boardSize) + 1) && (initialLocationID + 1 - boardSize > -1)) { // right up corner
                flipList.add(initialLocationID + 1 - boardSize);
                //System.out.print("Rup");
            }
            if (((initialLocationID - 1) % boardSize == (initialLocationID % boardSize) - 1) && (initialLocationID - 1 + boardSize < boardSize * boardSize)&& (initialLocationID - 1 > -1)) {  // left down corner
                flipList.add(initialLocationID - 1 + boardSize);
                //System.out.print("LDown");
            }
            if (((initialLocationID + 1) % boardSize == (initialLocationID % boardSize) + 1) && (initialLocationID + 1 + boardSize < boardSize * boardSize)) {  // right down corner
                flipList.add(initialLocationID + 1 + boardSize);
                //System.out.print("RDown");
            }
        } else if (tileType == 1) { //row
            for (int i = 0; i < boardSize; ++i) {
                if ((initialLocationID / boardSize) * boardSize  + i != initialLocationID) {
                    flipList.add((initialLocationID / boardSize) * boardSize  + i);
                }
            }

        } else if (tileType == 2) { //column
            for (int i = 0; i < boardSize; ++i) {
                if (initialLocationID % boardSize + (i * boardSize) != initialLocationID) {
                    flipList.add(initialLocationID % boardSize + (i * boardSize));
                }
            }
        } else if (tileType == 3) { //plus, up to 4 squares to flip
            if ((initialLocationID - 1) % boardSize == initialLocationID % boardSize - 1) { //1 to left
                flipList.add(initialLocationID - 1);
            }
            if ((initialLocationID + 1) % boardSize == initialLocationID % boardSize + 1) { //1 to right
                flipList.add(initialLocationID + 1);
            }
            if ((initialLocationID + boardSize) < boardSize * boardSize) {  // 1 down
                flipList.add(initialLocationID + boardSize);
            }
            if ((initialLocationID - boardSize) > -1) {  // 1 up
                flipList.add(initialLocationID - boardSize);
            }
        } else if (tileType == 4) { //star, up to 4 squares to flip
            if (((initialLocationID - 1) % boardSize == initialLocationID % boardSize - 1) && (initialLocationID - 1 - boardSize > -1)) { // left up corner
                flipList.add(initialLocationID - 1 - boardSize);
            }
            if (((initialLocationID + 1) % boardSize == initialLocationID % boardSize + 1) && (initialLocationID + 1 - boardSize > -1)) { // right up corner
                flipList.add(initialLocationID + 1 - boardSize);
            }
            if (((initialLocationID - 1) % boardSize == initialLocationID % boardSize - 1) && (initialLocationID - 1 + boardSize < boardSize * boardSize) && (initialLocationID - 1 > -1)) {  // left down corner
                flipList.add(initialLocationID - 1 + boardSize);
            }
            if (((initialLocationID + 1) % boardSize == initialLocationID % boardSize + 1) && (initialLocationID + 1 + boardSize < boardSize * boardSize)) {  // right down corner
                flipList.add(initialLocationID + 1 + boardSize);
                }
            } else if (tileType == 5) { //Prime
                for (int i = 0; i < primes.length; ++i) {
                    if ((primes[i] < boardSize * boardSize) && (primes[i] != initialLocationID)) {
                        flipList.add(primes[i]);
                    }
                }
            } else if (tileType == 6) { //Mirror, scrapped
                //do nothing, unless added later
                System.out.println("Mirror tile not added");
            }


        //Now that all the tiles that art going to flip are recorded, we now flip them
        for (int i = 0; i < flipList.size(); ++i) {
            flipTileAtLocation(flipList.get(i));
            //System.out.println(i);
        }

    }

    /*
     * Public Printing Methods
     */
    public void printGoal() {
        for (int i = 0; i < boardSize; ++i) {
            for (int j = 0; j < boardSize; ++j) {
                System.out.print((this.goalBoard.get(i*boardSize + j).getID() + " "));
            }
            System.out.println("");
        }
        System.out.println("");
    }
    public void printBoard() {
        for (int i = 0; i < boardSize; ++i) {
            for (int j = 0; j < boardSize; ++j) {
                System.out.print((this.board.get(i*boardSize + j).getID() + " "));
            }
            System.out.println("");
        }
        System.out.println("");
    }


    /*
     * METHODS: Creating a "Random" Board based on difficulty.
     */
    private int chooseRandomSpecial() {
        //Mild logic for choosing a random special tile type
        Random random = new Random();
        if (this.difficulty > 5) {
            return(random.nextInt(5) + 1);
        }
        else {
            return(random.nextInt(3) + 1);
        }
    }



    private void createBaseBoard() {
        String[][] baseBoard = new String[boardSize][boardSize];
        for (int i = 0; i < boardSize; ++i) {
            for (int j = 0; j < boardSize; ++j) {
                baseBoard[i][j] = "00000";
            }
        }
        Random random = new Random();
        int maxModifiers = 7;
        int modifiers = 0;
        int maxSpecialTiles = 5;
        int specialTiles = 0;

        int modiferChance = 8; //8 out of 20
        int specialChance = 3; //3 out of 20



        if (this.difficulty-1 >= 0) {
            for (int i = 0; i < this.difficulty-1; i++) {
                if ((random.nextInt(20) < modiferChance) && (modifiers < maxModifiers)) {
                    baseBoard[random.nextInt(boardSize)][random.nextInt(boardSize)] = "00010";
                    modifiers = modifiers +1;
                }
            }
        }
        if (this.difficulty-3 >= 0) {
            for (int i = 0; i < this.difficulty-3; i++) {
                if ((random.nextInt(20) < specialChance) && (specialTiles < maxSpecialTiles)) {
                    int num1 = random.nextInt(boardSize);
                    int num2 = random.nextInt(boardSize);
                    if (baseBoard[num1][num2] == "00010") {
                        baseBoard[num1][num2] = "0" + chooseRandomSpecial() + "010";
                        specialTiles = specialTiles + 1;
                    }
                    else if (baseBoard[num1][num2] == "00000") {
                        baseBoard[num1][num2] = "0" + chooseRandomSpecial() + "000";
                        specialTiles = specialTiles + 1;
                    }
                    if (!(specialTiles < maxSpecialTiles)) {
                        break;
                    }
                }
            }
        }

        makeBoard(baseBoard);
        setStartingBoard();
    }


    /*
     * PUBLIC/Private METHODS: Make Puzzle.
     */

    public void makePuzzle() {
        createBaseBoard();

        ArrayList<Integer> pattern = choosePattern();
        setMoves(pattern.size());


        //Take the pattern, and flip in that pattern to set the goal to match
        for (int i = 0; i < pattern.size(); ++i) {
            findTilesToFlip(pattern.get(i));
        }
        //sets the goal board to the patterned flip.
        setGoalBoard();
        resetBoard();
    }

    private ArrayList<Integer> choosePattern() {
        //Contains all the information of how to select a set of squares. The order of flips is randomized
        //Really, a better method should be used instead of hardcoding the patterns, but as I am not making too many, this is fine as a temporary solution.
        int shiftHorz = 0;
        int shiftVert = 0;
        int puzzlePatterns = 11;
        ArrayList<Integer> pattern = new ArrayList<Integer>();

        Random random = new Random();
        int patternNum = random.nextInt(puzzlePatterns);

        System.out.println("Pattern Num Choosen");
        System.out.println(patternNum);

        if (patternNum == 0) {
            shiftHorz = random.nextInt(3);
            shiftVert = random.nextInt(4)*boardSize;

            pattern.add(0 + shiftHorz + shiftVert);
            pattern.add(3 + shiftHorz + shiftVert);
            pattern.add(6 + shiftHorz + shiftVert);
            pattern.add(7 + shiftHorz + shiftVert);
            pattern.add(8 + shiftHorz + shiftVert);
            pattern.add(9 + shiftHorz + shiftVert);
            pattern.add(12 + shiftHorz + shiftVert);
            pattern.add(15 + shiftHorz + shiftVert);
        }
        if (patternNum == 1) {
            shiftHorz = random.nextInt(2);
            shiftVert = random.nextInt(4)*boardSize;

            pattern.add(0 + shiftHorz + shiftVert);
            pattern.add(3 + shiftHorz + shiftVert);
            pattern.add(6 + shiftHorz + shiftVert);
            pattern.add(7 + shiftHorz + shiftVert);
            pattern.add(8 + shiftHorz + shiftVert);
            pattern.add(9 + shiftHorz + shiftVert);
            pattern.add(10 + shiftHorz + shiftVert);
            pattern.add(12 + shiftHorz + shiftVert);
            pattern.add(15 + shiftHorz + shiftVert);
        }
        if (patternNum == 2) {
            shiftHorz = random.nextInt(2);
            shiftVert = random.nextInt(4)*boardSize;

            pattern.add(0 + shiftHorz + shiftVert);
            pattern.add(3 + shiftHorz + shiftVert);
            pattern.add(4 + shiftHorz + shiftVert);
            pattern.add(6 + shiftHorz + shiftVert);
            pattern.add(7 + shiftHorz + shiftVert);
            pattern.add(8 + shiftHorz + shiftVert);
            pattern.add(9 + shiftHorz + shiftVert);
            pattern.add(12 + shiftHorz + shiftVert);
            pattern.add(15 + shiftHorz + shiftVert);
            pattern.add(16 + shiftHorz + shiftVert);
        }
        if (patternNum == 3) {
            shiftHorz = 0;
            shiftVert = random.nextInt(4)*boardSize;

            pattern.add(0 + shiftHorz + shiftVert);
            pattern.add(1 + shiftHorz + shiftVert);
            pattern.add(4 + shiftHorz + shiftVert);
            pattern.add(7 + shiftHorz + shiftVert);
            pattern.add(8 + shiftHorz + shiftVert);
            pattern.add(9 + shiftHorz + shiftVert);
            pattern.add(10 + shiftHorz + shiftVert);
            pattern.add(13 + shiftHorz + shiftVert);
            pattern.add(16 + shiftHorz + shiftVert);
            pattern.add(17 + shiftHorz + shiftVert);
        }
        if (patternNum == 4) {
            shiftHorz = 0;
            shiftVert = random.nextInt(4)*boardSize;

            pattern.add(1 + shiftHorz + shiftVert);
            pattern.add(4 + shiftHorz + shiftVert);
            pattern.add(5 + shiftHorz + shiftVert);
            pattern.add(6 + shiftHorz + shiftVert);
            pattern.add(7 + shiftHorz + shiftVert);
            pattern.add(8 + shiftHorz + shiftVert);
            pattern.add(9 + shiftHorz + shiftVert);
            pattern.add(10 + shiftHorz + shiftVert);
            pattern.add(13 + shiftHorz + shiftVert);
            pattern.add(16 + shiftHorz + shiftVert);
            pattern.add(17 + shiftHorz + shiftVert);
        }
        if (patternNum == 5) {
            shiftHorz = random.nextInt(3);
            shiftVert = random.nextInt(2)*boardSize;

            pattern.add(1 + shiftHorz + shiftVert);
            pattern.add(6 + shiftHorz + shiftVert);
            pattern.add(9 + shiftHorz + shiftVert);
            pattern.add(12 + shiftHorz + shiftVert);
            pattern.add(13 + shiftHorz + shiftVert);
            pattern.add(14 + shiftHorz + shiftVert);
            pattern.add(15 + shiftHorz + shiftVert);
            pattern.add(18 + shiftHorz + shiftVert);
            pattern.add(21 + shiftHorz + shiftVert);
            pattern.add(16 + shiftHorz + shiftVert);
            pattern.add(26 + shiftHorz + shiftVert);
        }
        if (patternNum == 6) {
            shiftHorz = random.nextInt(4);
            shiftVert = random.nextInt(4)*boardSize;

            pattern.add(1 + shiftHorz + shiftVert);
            pattern.add(6 + shiftHorz + shiftVert);
            pattern.add(7 + shiftHorz + shiftVert);
            pattern.add(8 + shiftHorz + shiftVert);
            pattern.add(13 + shiftHorz + shiftVert);
        }
        if (patternNum == 7) {
            shiftHorz = random.nextInt(4);
            shiftVert = random.nextInt(2)*boardSize;

            pattern.add(2 + shiftHorz + shiftVert);
            pattern.add(7 + shiftHorz + shiftVert);
            pattern.add(12 + shiftHorz + shiftVert);
            pattern.add(13 + shiftHorz + shiftVert);
            pattern.add(14 + shiftHorz + shiftVert);
            pattern.add(19 + shiftHorz + shiftVert);
            pattern.add(26 + shiftHorz + shiftVert);
        }
        if (patternNum == 8) {
            shiftHorz = random.nextInt(3);
            shiftVert = random.nextInt(4)*boardSize;

            pattern.add(1 + shiftHorz + shiftVert);
            pattern.add(6 + shiftHorz + shiftVert);
            pattern.add(7 + shiftHorz + shiftVert);
            pattern.add(8 + shiftHorz + shiftVert);
            pattern.add(9 + shiftHorz + shiftVert);
            pattern.add(13 + shiftHorz + shiftVert);
        }
        if (patternNum == 9) {
            shiftHorz = random.nextInt(4);
            shiftVert = random.nextInt(4)*boardSize;

            pattern.add(1 + shiftHorz + shiftVert);
            pattern.add(2 + shiftHorz + shiftVert);
            pattern.add(6 + shiftHorz + shiftVert);
            pattern.add(7 + shiftHorz + shiftVert);
            pattern.add(8 + shiftHorz + shiftVert);
            pattern.add(13 + shiftHorz + shiftVert);
        }
        if (patternNum == 10) { //This one choose 3 + difficulty (up to 8)  random squares. These will not make interesting puzzles all the time though
            for (int i = 0; i < Integer.min(3 + this.difficulty, 7); ++i) {
                pattern.add(random.nextInt(boardSize*boardSize));
            }
        }

        pattern = shufflePattern(pattern);
        //printing for cheating
        for (int i = 0; i < pattern.size(); ++i) {
            System.out.print(pattern.get(i) + " ");
        }
        System.out.println("");

        return(pattern);
    }


    private ArrayList<Integer> shufflePattern(ArrayList<Integer> pattern) {
        ArrayList<Integer> shuffledPattern = pattern;

        Random random = new Random();
        int shuffles = random.nextInt(21) + 10; //10 to 30 shuffles of pattern

        for (int i = 0; i < shuffles; ++i) {
            int j = random.nextInt(pattern.size());
            int k = random.nextInt(pattern.size());

            int tempVal = shuffledPattern.get(j);
            shuffledPattern.set(j, shuffledPattern.get(k));
            shuffledPattern.set(k, tempVal);
        }

        return(shuffledPattern);
    }

}