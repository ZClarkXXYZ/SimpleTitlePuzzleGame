package puzzlegame.simpletilepuzzlegame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ResourceBundle;

public class puzzleController implements Initializable {

    @FXML
    private Label difficultyLabel;

    @FXML
    private Label movesLabel;

    @FXML
    private FlowPane boardFlowPane;

    @FXML
    private FlowPane goalFlowPane;


    @FXML
    void resetGame(ActionEvent event) {}

    @FXML
    private Button resetButton;

    @FXML
    private Button cheatButton;
    private boolean disableMoves = false;

    puzzleModel board = new puzzleModel();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTileImagesBoard();
        //initializeTileImagesGoal();

        resetButton.setDisable(false);
        resetButton.setOnAction(Event -> {
            board.resetDifficulty();
            board.makePuzzle();
            updateBoards();
            updateMovesLabel();
            updateDifficultyLabel();
        });

        cheatButton.setOnAction (Event -> {
            if(!disableMoves) {
                disableMoves = true;
                movesLabel.setVisible(false);
            }
            else {
                disableMoves = false;
                movesLabel.setVisible(true);
            }

        });

        board.makePuzzle();

        // board.printBoard();
        // board.printGoal();
        updateBoards();
        updateMovesLabel();
        updateDifficultyLabel();
    }


    public void initializeTileImagesBoard() {
        for (int i = 0; i < boardFlowPane.getChildren().size(); i++) {
            ImageView imageView = (ImageView) boardFlowPane.getChildren().get(i);
            imageView.setUserData(i);
            imageView.setOnMouseClicked(Event -> {
                System.out.print(imageView.getUserData() + " ");
                if ((board.getMoves() > 0) || (disableMoves)) {
                    board.findTilesToFlip((int) (imageView.getUserData()));
                    board.useMove();
                    if (board.checkIfGoal()) { //incase a win is found in less moves
                        System.out.println("Win");
                        nextLevel();
                        updateDifficultyLabel();
                    }
                }
                else {
                    if (board.checkIfGoal()) {
                        System.out.println("Win");
                        nextLevel();
                        updateDifficultyLabel();
                    }
                    else {
                        board.makePuzzle();
                    }
                }
                updateBoards();
                updateMovesLabel();
            });
        }
    }

    public void updateBoards() {
        //Update the images to the current state of the puzzle and goal board

        for (int i = 0; i < goalFlowPane.getChildren().size(); i++) {
            ImageView imageView = (ImageView) goalFlowPane.getChildren().get(i);
            imageView.setImage(((board.getGoalBoard().get(i)).getImage()));
            //System.out.println(board.getGoalBoard().get(i).getID());
        }

        for (int i = 0; i < boardFlowPane.getChildren().size(); i++) {
            ImageView imageView = (ImageView) boardFlowPane.getChildren().get(i);
            imageView.setImage(((board.getBoard().get(i)).getImage()));
        }
    }

    public void updateMovesLabel() {
        movesLabel.setText("Moves Left: " + String.valueOf(board.getMoves()));
    }

    public void updateDifficultyLabel() {
        difficultyLabel.setText("Round " + (board.getDifficulty() + 1));
    }

    public void nextLevel() {
        board.increaseDifficulty();
        board.makePuzzle();
    }

}
