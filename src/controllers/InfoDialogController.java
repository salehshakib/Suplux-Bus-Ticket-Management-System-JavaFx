package controllers;

import com.jfoenix.controls.JFXDialog;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class InfoDialogController {

    /**
     * variable initialization
     */
    @FXML
    public Label dialogBody;
    public Button dialogButton;

    private JFXDialog dialog;

    public void onClickDialogButton() {

        dialog.close();
    }

    public void setDialog(JFXDialog dialog){

        this.dialog = dialog;
    }

    public void setDialogBody(String body){

        dialogBody.setText(body);
    }
}
