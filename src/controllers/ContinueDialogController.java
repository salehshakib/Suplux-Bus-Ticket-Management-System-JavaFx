package controllers;

import com.jfoenix.controls.JFXDialog;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class ContinueDialogController {

    /**
     * variable initialization
     */
    @FXML
    public Label dialogBody;
    public Text dialogTitle;
    public Button dialogYesButton, dialogNoButton;

    private JFXDialog dialog;
    private boolean isYesPressed;

    public void onClickDialogYesButton() {

        dialog.close();
        isYesPressed = true;
    }

    public void onClickDialogNoButton() {

        dialog.close();
        isYesPressed = false;
    }

    public void setDialog(JFXDialog dialog){

        this.dialog = dialog;
    }

    public void setDialogBody(String body){

        dialogBody.setText(body);
    }

    public void setDialogTitle(String title){

        dialogTitle.setText(title);
    }

    public boolean isYesPressed() {
        return isYesPressed;
    }

}
