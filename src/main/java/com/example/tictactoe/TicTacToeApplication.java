package com.example.tictactoe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TicTacToeApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(TicTacToeApplication.class.getResource("mainwindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("TicTacToe - By Joel López - DAM2A");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

//TO DO boton About us
//TO DO FUNCIONAR TRES EN RAYA
//TO DO IA To Play
//TO DO STATS Guardadas
//TODO CSV O XML CON STATS PARA CARGARLAS EN LA TABLA
//TODO Cambio de CSS en directo
//TODO