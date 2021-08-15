package controllers;

import com.jfoenix.controls.JFXDialog;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateGenderDialogController implements Initializable {

    /**
     * variable initialization
     */
    @FXML
    public RadioButton radioMale, radioFemale, radioOthers;
    public Button dialogConfirmButton, dialogCancelButton;

    private JFXDialog dialog;

    private boolean isConfirmButtonPressed;
    private static String fieldData;

    /**
     * to close the dialog on clicking the cancel button
     */
    public void onClickDialogCancelButton() {

        dialog.close();
        isConfirmButtonPressed = false;
    }

    /**
     * to close the dialog on clicking the confirm button
     */
    public void onClickDialogConfirmButton() {

        dialog.close();
        isConfirmButtonPressed = true;
    }

    /**
     * to set the dialog from the actual scene
     */
    public void setDialog(JFXDialog dialog){

        this.dialog = dialog;
    }

    /**
     * to return the value of the boolean which shows if the confirm button is pressed or not
     */
    public boolean isConfirmButtonPressed() {
        return isConfirmButtonPressed;
    }

    /**
     * this method returns the gender data
     */
    public String getData(){

        if (radioFemale.isSelected()){

            return "Female";
        } else if(radioMale.isSelected()){

            return "Male";
        } else if(radioOthers.isSelected()){

            return "Others";
        }

        return null;
    }

    /**
     * to check if the updated data is duplicate or not
     */
    public static void checkData(String  data){

        fieldData = data;
    }

    public void onClickARadioButton(){

        dialogConfirmButton.setDisable(false);
    }

    /**
     * initializes the dialog
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        //this toggle group is to toggle the gender radio buttons

        ToggleGroup genderGroup = new ToggleGroup();

        radioMale.setToggleGroup(genderGroup);
        radioFemale.setToggleGroup(genderGroup);
        radioOthers.setToggleGroup(genderGroup);

        switch (fieldData) {
            case "Male":

                radioMale.setDisable(true);
                break;
            case "Female":

                radioFemale.setDisable(true);
                break;
            case "Others":

                radioOthers.setDisable(true);
                break;
        }
    }

}
