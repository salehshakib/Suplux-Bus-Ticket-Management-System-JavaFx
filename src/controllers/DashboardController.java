package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    Button crossButton;


    public void onClickCrossButton(){

        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
