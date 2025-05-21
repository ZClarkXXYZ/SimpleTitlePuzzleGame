module puzzlegame.simpletilepuzzlegame {
    requires javafx.controls;
    requires javafx.fxml;


    opens puzzlegame.simpletilepuzzlegame to javafx.fxml;
    exports puzzlegame.simpletilepuzzlegame;
}