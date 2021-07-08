package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    Button crossButton;
    @FXML
    RadioButton radioMale, radioFemale, radioOthers;

    //this method is to close the application
    public void onClickCrossButton(){

        System.exit(0);
    }

    //this method is to set the initial stage
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //this toggle group is to toggle the gender radio buttons

        ToggleGroup genderGroup = new ToggleGroup();

        radioMale.setToggleGroup(genderGroup);
        radioFemale.setToggleGroup(genderGroup);
        radioOthers.setToggleGroup(genderGroup);
    }
}
