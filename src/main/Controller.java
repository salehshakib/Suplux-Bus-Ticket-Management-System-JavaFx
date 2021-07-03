package main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Controller {

    @FXML
    Button crossButton;

    public void onClickCrossButton(){

        System.exit(0);
    }
}
