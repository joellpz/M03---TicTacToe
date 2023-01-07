package com.example.tictactoe;

import com.example.tictactoe.controller.StatsController;
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
        scene.getStylesheets().add(TicTacToeApplication.class.getResource("style_light.css").toExternalForm());
        System.out.println(TicTacToeApplication.class.getResource("style_light.css").toExternalForm());

        principalStage.setTitle("TicTacToe - By Joel López - DAM2A");
        principalStage.setScene(scene);
        principalStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static Parent replaceSceneContent(String fxml) throws Exception {
        Parent page = (Parent) FXMLLoader.load(Objects.requireNonNull(TicTacToeApplication.class.getResource(fxml)), null, new JavaFXBuilderFactory());
        Scene scene = TicTacToeApplication.principalStage.getScene();
        if (scene == null) {
            System.out.println("SCENE");
            scene = new Scene(page);
            TicTacToeApplication.principalStage.setScene(scene);
        } else {
            System.out.println("ELSE");
            StatsController statsController = new StatsController();
            statsController.gridConstructor();
            TicTacToeApplication.principalStage.getScene().setRoot(page);
        }
        TicTacToeApplication.principalStage.sizeToScene();

        return page;
    }
}

//TO DO boton About us
//TO DO FUNCIONAR TRES EN RAYA
//TO DO IA To Play
//TO DO STATS Guardadas
//TODO CSV O XML CON STATS PARA CARGARLAS EN LA TABLA
//TO DO Cambio de CSS en directo
//TODO