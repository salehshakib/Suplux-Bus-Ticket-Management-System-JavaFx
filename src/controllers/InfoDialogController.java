package controllers;

import com.jfoenix.controls.JFXDialog;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;


public class InfoDialogController {

    /**
     * variable initialization
     */
    @FXML
    public Label dialogBody, dialogTitle;
    public Button dialogButton;
    public AnchorPane rootPane;

    private JFXDialog dialog;

    /**
     * to close the dialog on clicking the button
     */
    public void onClickDialogButton() {

        dialog.close();
    }

    /**
     * to set the dialog from the actual scene
     */
    public void setDialog(JFXDialog dialog){

        this.dialog = dialog;
    }

    /**
     * to set the dialog body text
     */
    public void setDialogBody(String body){

        dialogBody.setText(body);
    }

    /**
     * to set the dialog button text
     */
    public void setDialogButtonText(String text){

        dialogButton.setText(text);
    }

    /**
     * to set the root anchor pane's height and width
     */
    public void setRootPaneHeightAndWidth(double height, double width){

        rootPane.setPrefHeight(height);
        rootPane.setPrefWidth(width);
    }

    /**
     * to set the dialog body's height and width
     */
    public void setDialogBodyHeightAndWidth(double height, double width){

        dialogBody.setPrefHeight(height);
        dialogBody.setPrefWidth(width);
    }

    /**
     * to set the dialog button's layoutX and layoutY
     */
    public void setDialogButtonPosition(double x, double y){

        dialogButton.setLayoutX(x);
        dialogButton.setLayoutY(y);
    }

    /**
     * to set the dialog body's layoutX and layoutY
     */
    public void setDialogBodyPosition(double x, double y){

        dialogBody.setLayoutX(x);
        dialogBody.setLayoutY(y);
    }

    /**
     * to set the dialog title text
     */
    public void setDialogTitle(String title){

        dialogTitle.setText(title);
    }

    /**
     * to set the dialog title's layoutX and layoutY
     */
    public void setDialogTitlePosition(double x, double y){

        dialogTitle.setLayoutX(x);
        dialogTitle.setLayoutY(y);
    }
}
