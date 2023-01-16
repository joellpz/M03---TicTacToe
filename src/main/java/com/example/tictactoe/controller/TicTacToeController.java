package com.example.tictactoe.controller;

import com.example.tictactoe.TicTacToeApplication;
import com.example.tictactoe.model.OpenCSV;
import com.example.tictactoe.model.Stats;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static com.example.tictactoe.controller.StatsController.statsList;

public class TicTacToeController implements Initializable {
    String turn = "";
    String winnerPlayer;
    boolean winner = false;
    int IA = 0;
    boolean turnIA;
    String[][] playingTableString;
    @FXML
    GridPane playingTable;
    @FXML
    Button btnStart;
    @FXML
    Text playerTurnText;
    @FXML
    ToggleGroup gameModeGroup;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StatsController.readStats();
    }

    /**
     * On Click the Start Button Sequence.
     */
    @FXML
    protected void onClickStartBtn() {
        //Reads the stats file to have it updated
        RadioButton gameMenuButton = (RadioButton) gameModeGroup.getSelectedToggle();
        IA = 0;
        turnIA = true;

        if (gameMenuButton == null) {
            playerTurnText.setText("Select one option!");
            playerTurnText.setStyle("-fx-fill: red;");
        } else {
            restartTable("first");

            playingTableString = new String[playingTable.getColumnCount()][playingTable.getRowCount()];
            turn = "X";
            btnStart.setDisable(true);
            playerTurnText.setText("It's " + turn + " turn!");
            playerTurnText.setStyle("-fx-fill: black;");
            switch (gameMenuButton.getId()) {
                case "compvscomp" -> {
                    IA = 2;
                    moveIA();
                }
                case "plavscomp" -> IA = 1;
            }
        }
    }

    /**
     * When click a Grid Cell it active the sequence to change the state.
     * @param event Source of the event
     */
    @FXML
    protected void onClickCell(@NotNull ActionEvent event) {
        Button btn = (Button) event.getSource();
        btn.setText(turn);
        btn.setDisable(true);
        updateTablero();
        winnerPlayer = checkWin();
        if (winnerPlayer != null) {
            winnerStage(winnerPlayer);
            playerTurnText.setText("Next Game...");
        } else changePlayer();
    }
    /**
     * Crea la pantalla de información sobre el programa en el menú superior dentro de Help.
     */
    @FXML
    protected void onClickAboutBtnMenu() {
        Stage stage = new Stage();
        Label label = new Label("""
                This is a Project of JavaFX for the class of M03.\s
                - INS Puig Castellar -

                 * Developed by Joel López *
                \s""");
        label.setTextAlignment(TextAlignment.CENTER);
        ImageView imageView = new ImageView("https://elpuig.xeill.net/logo.png");
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(250);

        VBox vBox = new VBox(label, imageView);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10, 20, 20, 20));
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.setTitle("About");
        stage.show();
    }

    /**
     * Change the Scene into the Stats Grid.
     */
    @FXML
    protected void onClickStatsBtn() {
        try {
            TicTacToeApplication.replaceSceneContent("stats-window.fxml");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Changes the Theme of the Scene
     */
    @FXML
    protected void onClickChangeThemeBtn() {
        if (btnStart.getScene().getStylesheets().get(0).contains("light")) {
            btnStart.getScene().getStylesheets().set(0, Objects.requireNonNull(TicTacToeApplication.class.getResource("style_dark.css")).toExternalForm());
        }
        else {
            btnStart.getScene().getStylesheets().set(0, Objects.requireNonNull(TicTacToeApplication.class.getResource("style_light.css")).toExternalForm());
        }
    }

    /**
     * Close the Window
     */
    @FXML
    private void onClickCloseBtn(){
        TicTacToeApplication.principalStage.close();
    }

    /**
     * Restarts the table to play another Game.
     * @param from String than defines if it is the first game.
     */
    protected void restartTable(String from) {
        for (Node node : playingTable.getChildren()) {
            Button btnCell = (Button) node;
            if (from.equals("first")) {
                btnCell.setDisable(false);
                btnCell.setText("");
            } else {
                btnCell.setDisable(true);
                btnStart.setDisable(false);
            }
        }
    }

    /**
     * Updates the Table every time anyone make a play, and shows the differences.
     */
    protected void updateTablero() {
        List<Node> nodes = playingTable.getChildren();
        int pos = 0;
        for (int i = 0; i < playingTable.getColumnCount(); i++) {
            for (int j = 0; j < playingTable.getRowCount(); j++) {
                Button btnCell = (Button) nodes.get(pos);
                playingTableString[i][j] = btnCell.getText();
                pos++;
            }
        }
    }

    /**
     * Method to make the player change and detects if it is the IA or Player turn.
     */
    protected void changePlayer() {
        if (turn.equals("X")) turn = "O";
        else turn = "X";
        playerTurnText.setText("It's " + turn + " turn!");

        if (IA == 1) {
            if (turnIA) {
                moveIA();
            } else {
                turnIA = true;
            }
        } else if (IA == 2) {
            moveIA();
        }
    }

    /**
     * Makes an IA move. Selects Random Grid Cell and checks if it is possible to click on it.
     */
    protected void moveIA() {
        //Randomize the position
        List<Node> nodes = playingTable.getChildren();
        boolean clicked;
        int randomBtnClick;
        turnIA = false;

        do {
            clicked = false;
            randomBtnClick = (int) (Math.random() * 9);
            Button btnCell = (Button) nodes.get(randomBtnClick);
            if (!btnCell.isDisabled()) {
                btnCell.fire();
            } else {
                clicked = true;
            }

        } while (clicked);


    }

    /**
     * Checks if any option of winning happens in every signle move.
     *
     * @return Players ID
     */
    protected String checkWin() {
        String player;
        //Check Horizontal possibilities
        for (int i = 0; i < playingTable.getColumnCount(); i++) {
            winner = true;
            player = playingTableString[i][0];

            if (!player.equals("")) {
                for (int j = 1; j < playingTable.getRowCount(); j++) {
                    if (!player.equals(playingTableString[i][j])) {
                        winner = false;
                    }
                }
                if (winner) {
                    return player;
                }
            }
        }

        //Check Vertical Possibilities
        for (int j = 0; j < playingTable.getRowCount(); j++) {
            winner = true;
            player = playingTableString[0][j];

            if (!player.equals("")) {
                for (int i = 1; i < playingTable.getColumnCount(); i++) {
                    if (!player.equals(playingTableString[i][j])) {
                        winner = false;
                    }
                }
                if (winner) {
                    return player;
                }
            }
        }

        //Check Diagonals
        if ((Objects.equals(playingTableString[0][0], playingTableString[1][1]) && Objects.equals(playingTableString[1][1], playingTableString[2][2])) && !playingTableString[0][0].equals("")) {
            return playingTableString[0][0];
        } else if ((Objects.equals(playingTableString[0][2], playingTableString[1][1]) && Objects.equals(playingTableString[1][1], playingTableString[2][0])) && !playingTableString[0][2].equals("")) {
            return playingTableString[0][2];
        }

        int blanks = 0;
        for (String[] s : playingTableString) {
            if (!Arrays.stream(s).toList().contains("")) blanks++;
        }

        if (blanks == 3) return "noBlanks";
        return null;

    }

    /**
     * If anyone has won, this create the Stage to show and read diferents values
     * @param player Player who has won the match.
     */
    protected void winnerStage(String player) {
        Insets indvPadding = new Insets(10, 10, 10, 10);
        Stage winnerStage = new Stage();

        //Image TicTacToe PNG
        Text winnerText = new Text();
        ImageView imageView = new ImageView("https://www.pngkey.com/png/detail/296-2966115_game-png-background-image-tic-tac-toe-logo.png");
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(65);
        HBox hBox = new HBox(winnerText, imageView);
        hBox.setPadding(indvPadding);
        hBox.setSpacing(35);
        hBox.setAlignment(Pos.CENTER);

        //Player X Form
        Label playerXLabel = new Label("Player X Name:");
        TextField playerXName = new TextField();
        playerXName.setPrefHeight(10);
        playerXName.setPrefWidth(145);
        HBox hBox1 = new HBox(playerXLabel, playerXName);
        hBox1.setPadding(indvPadding);
        hBox1.setPrefHeight(30);
        hBox1.setSpacing(10);
        hBox1.setAlignment(Pos.CENTER);

        //Player O Form
        Label playerOLabel = new Label("Player O Name:");
        TextField playerOName = new TextField();
        playerOName.setPrefHeight(15);
        playerOName.setPrefWidth(145);
        HBox hBox2 = new HBox(playerOLabel, playerOName);
        hBox2.setPadding(indvPadding);
        hBox2.setSpacing(10);
        hBox2.setAlignment(Pos.CENTER);

        //Buttons to Send Form.
        Button submitBtn = new Button("Submit");
        submitBtn.setPrefWidth(60);
        submitBtn.onActionProperty().set(e -> {
            if (player.equals("X")) {
                submitStats(playerXName.getText().trim(), "win");
                submitStats(playerOName.getText().trim(), "lose");
            } else if (player.equals("O")) {
                submitStats(playerXName.getText().trim(), "lose");
                submitStats(playerOName.getText().trim(), "win");
            } else {
                submitStats(playerXName.getText().trim(), "tie");
                submitStats(playerOName.getText().trim(), "tie");
            }
            winnerStage.close();
        });
        Button cancelBtn = new Button("Cancel");
        cancelBtn.onActionProperty().set(e -> winnerStage.close());
        cancelBtn.setPrefWidth(60);
        HBox buttonsBox = new HBox(submitBtn, cancelBtn);
        buttonsBox.setSpacing(10);
        buttonsBox.setPadding(indvPadding);
        buttonsBox.setAlignment(Pos.CENTER_RIGHT);

        if (IA >= 1) {
            playerOName.setDisable(true);
            if (IA == 2) {
                playerXName.setDisable(true);
                submitBtn.setDisable(true);
            }
        }

        VBox vBox = new VBox(hBox, hBox1, hBox2, buttonsBox);
        Scene scene = new Scene(vBox);
        winnerStage.setTitle("Match Finished!");
        winnerStage.setScene(scene);
        winnerStage.show();

        if (player.equals("noBlanks")) {
            winnerText.setText("NO GANA NADIE");
        } else {
            winnerText.setText("El jugador " + player + " ha GANADO!");
        }


        restartTable("none");
    }

    /**
     * Submit stats updated into the file.
     * @param playerName Player Code (X or O)
     * @param stat Stats to Increment
     */
    @FXML
    protected void submitStats(String playerName, String stat) {
        if (!Objects.equals(playerName, "")) {
            if (statsList.get(playerName) != null) {
                switch (stat) {
                    case "win" -> statsList.get(playerName).plusWin();
                    case "lose" -> statsList.get(playerName).plusLoses();
                    case "tie" -> statsList.get(playerName).plusTied();
                }
            } else {
                switch (stat) {
                    case "win" -> statsList.put(playerName, new Stats(playerName, 1, 0, 0));
                    case "lose" -> statsList.put(playerName, new Stats(playerName, 0, 1, 0));
                    case "tie" -> statsList.put(playerName, new Stats(playerName, 0, 0, 1));
                }
            }
            ArrayList<String[]> list = new ArrayList<>();
            for (Stats stats : statsList.values()) {
                String[] statsPlayer = new String[4];
                statsPlayer[0] = stats.getName();
                statsPlayer[1] = stats.getWins() + "";
                statsPlayer[2] = stats.getLoses() + "";
                statsPlayer[3] = stats.getTied() + "";
                list.add(statsPlayer);
            }
            try {
                OpenCSV.writeToCSV(list, "src/main/resources/data/stats.csv");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
