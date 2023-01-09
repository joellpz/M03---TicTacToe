package com.example.tictactoe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class TicTacToeApplication extends Application {

    public static Stage principalStage;

    @Override
    public void start(Stage stage) throws Exception {
        principalStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(TicTacToeApplication.class.getResource("main-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(Objects.requireNonNull(TicTacToeApplication.class.getResource("style_light.css")).toExternalForm());
        System.out.println(Objects.requireNonNull(TicTacToeApplication.class.getResource("style_light.css")).toExternalForm());

        principalStage.setTitle("TicTacToe - By Joel López - DAM2A");
        principalStage.setScene(scene);
        principalStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void replaceSceneContent(String fxml) throws Exception {
        Parent page = FXMLLoader.load(Objects.requireNonNull(TicTacToeApplication.class.getResource(fxml)), null, new JavaFXBuilderFactory());
        Scene scene = TicTacToeApplication.principalStage.getScene();
        if (scene == null) {
            System.out.println("SCENE");
            scene = new Scene(page);
            TicTacToeApplication.principalStage.setScene(scene);
        } else {
            TicTacToeApplication.principalStage.getScene().setRoot(page);
        }
        TicTacToeApplication.principalStage.sizeToScene();

    }
}