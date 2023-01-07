package com.example.tictactoe.controller;

import com.example.tictactoe.TicTacToeApplication;
import com.example.tictactoe.model.Stats;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class StatsController {
    @FXML
    GridPane gridOfStats;
    @FXML
    GridPane playingTable;
    protected static Map<String, Stats> statsList = new HashMap<>();

    @FXML
    public void gridConstructor() {
        System.out.println("Grid Contructor");
        int rows = 1;
        System.out.println(gridOfStats);
        System.out.println(playingTable);

        for (Node node : gridOfStats.getChildren()) {
            Label btnCell = (Label) node;
            System.out.println(btnCell.getText());
            System.out.println("aaaaaaaa");

        }
        /*for (Stats stat : statsList.values()) {
            Label name = new Label(stat.getName());
            Label win = new Label(stat.getWins() + "");
            Label loses = new Label(stat.getLoses() + "");
            Label tied = new Label(stat.getTied() + "");

            this.statsGrid.add(name, 0, rows);
            this.statsGrid.add(win, 1, rows);
            this.statsGrid.add(loses, 2, rows);
            this.statsGrid.add(tied, 3, rows);
            rows++;
        }*/
    }

    @FXML
    protected void onClickBackBtn() {
        try {
            TicTacToeApplication.replaceSceneContent("main-window.fxml");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
