package com.example.tictactoe.controller;

import com.example.tictactoe.TicTacToeApplication;
import com.example.tictactoe.model.OpenCSV;
import com.example.tictactoe.model.Stats;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.scene.layout.GridPane.*;

public class StatsController implements Initializable {
    @FXML
    GridPane statsGrid;
    protected static HashMap<String, Stats> statsList = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gridConstructor();
    }

    /**
     * Gets the Stats information and sets the values into the Grid
     */
    @FXML
    public void gridConstructor() {
        int rows = 1;
        for (Stats stat : statsList.values()) {
            Label name = new Label(stat.getName());
            Label wins = new Label(stat.getWins() + "");
            Label loses = new Label(stat.getLoses() + "");
            Label tied = new Label(stat.getTied() + "");

            statsGrid.add(name, 0, rows);
            statsGrid.add(wins, 1, rows);
            statsGrid.add(loses, 2, rows);
            statsGrid.add(tied, 3, rows);

            setHalignment(name, HPos.CENTER);
            setHalignment(wins, HPos.CENTER);
            setHalignment(loses, HPos.CENTER);
            setHalignment(tied, HPos.CENTER);
            rows++;
        }
    }

    /**
     * Go back Button.
     */
    @FXML
    protected void onClickBackBtn() {
        try {
            TicTacToeApplication.replaceSceneContent("main-window.fxml");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads Stats from the file
     */
    protected static void readStats() {
        List<String[]> listStatsReaded;
        try {
            listStatsReaded = OpenCSV.readCSV("src/main/resources/data/stats.csv");
            listStatsReaded.forEach(strings -> statsList.put(strings[0], new Stats(strings[0], Integer.parseInt(strings[1]), Integer.parseInt(strings[2]), Integer.parseInt(strings[3]))));
        } catch (IOException e) {
            System.out.println("None Stats");
        }
    }

}
