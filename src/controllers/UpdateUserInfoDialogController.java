package controllers;

import com.jfoenix.controls.JFXDialog;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class UpdateUserInfoDialogController {

    /**
     * variable initialization
     */
    @FXML
    public Label dialogTitle, warningMsg;
    public TextField updateField;
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

        if(!fieldData.equals(updateField.getText())){

            dialog.close();
            isConfirmButtonPressed = true;
        } else{

            scaleIt(200, warningMsg, 1, 2);
        }


    }

    /**
     * to enable the button if the field is not empty
     */
    public void onKeyPressedUpdateField(){

        if(warningMsg.getScaleY() == 1){

            scaleIt(200, warningMsg, 0, 2);
        }

        dialogConfirmButton.setDisable(updateField.getText().isEmpty() || updateField.getText().isBlank());
    }

    /**
     * to set the dialog from the actual scene
     */
    public void setDialog(JFXDialog dialog){

        this.dialog = dialog;
    }

    /**
     * to set the dialog title text
     */
    public void setDialogTitle(String title){

        dialogTitle.setText(title);
    }



    /**
     * to set the dialog text field's prompt text
     */
    public void setTextFieldPromptText(String promptText){

        updateField.setPromptText(promptText);
    }

    /**
     * to return the value of the boolean which shows if the confirm button is pressed or not
     */
    public boolean isConfirmButtonPressed() {
        return isConfirmButtonPressed;
    }

    /**
     * this method returns the string of the text field
     */
    public String getData(){

        return updateField.getText();
    }

    /**
     * to check if the updated data is duplicate or not
     */
    public static void checkData(String  data){

        fieldData = data;
    }

    public void scaleIt(double duration, Node node, double scaleTo, int type){

        ScaleTransition transition = new ScaleTransition(Duration.millis(duration), node);

        if(type == 1){

            transition.setToX(scaleTo);
        } else if(type == 2){

            transition.setToY(scaleTo);
        } else if(type == 3){

            transition.setToX(scaleTo);
            transition.setToY(scaleTo);
        }

        transition.play();
    }

}
