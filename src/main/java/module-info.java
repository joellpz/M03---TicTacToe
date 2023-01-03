module com.example.tictactoe {
    requires javafx.controls;
    requires javafx.fxml;
    requires opencsv;
    requires org.jetbrains.annotations;


    opens com.example.tictactoe to javafx.fxml;
    exports com.example.tictactoe;
}