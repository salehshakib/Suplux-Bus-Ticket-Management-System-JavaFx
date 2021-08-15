package controllers;

import com.jfoenix.controls.JFXDialog;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Duration;


public class PaymentDialogController {

    /**
     * variable initialization
     */
    @FXML
    public Label dialogTitle, paymentErrorMsg;
    public Text discountText, payableText, totalFare, discountFare, payableFare;
    public TextField numberField;
    public PasswordField pinField;
    public Button dialogConfirmButton, dialogCancelButton;

    private JFXDialog dialog;

    private boolean isConfirmButtonPressed;

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

        if(!numberField.getText().isEmpty() && !pinField.getText().isEmpty()
                && !numberField.getText().isBlank() && !pinField.getText().isBlank()
                && isNumeric(numberField.getText()) && isNumeric(pinField.getText())
                && numberField.getText().length() == 11 && pinField.getText().length() > 3){

            dialog.close();
            isConfirmButtonPressed = true;
        } else{

            scaleIt(paymentErrorMsg, 1);

            if(numberField.getText().isEmpty() || numberField.getText().isBlank()
                    || !isNumeric(numberField.getText()) || numberField.getText().length() != 11){

                numberField.setStyle("-fx-border-color: red");
            }

            if(pinField.getText().isEmpty() || pinField.getText().isBlank()
                    || !isNumeric(pinField.getText()) || pinField.getText().length() < 4){

                pinField.setStyle("-fx-border-color: red");
            }
        }

    }

    /**
     * this method changes the color of the border of the text field when clicked if left empty or blank
     */
    public void onClickedNumberField(){

        numberField.setStyle("-fx-border-color: #007EfC");
        scaleIt(paymentErrorMsg, 0);
    }

    /**
     * this method changes the color of the border of the password field when clicked if left empty or blank
     */
    public void onClickPINField(){

        pinField.setStyle("-fx-border-color: #007EfC");
        scaleIt(paymentErrorMsg, 0);
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
     * to set the dialog body text
     */
    public void setDialogBodyText(String discountOrPayable, String payableOrDue){

        payableText.setText(payableOrDue);
        discountText.setText(discountOrPayable);
    }

    /**
     * to set the dialog body's fare text
     */
    public void setDialogBodyFare(String discountOrPayableFare, String payableOrDueFare){

        payableFare.setText(payableOrDueFare);
        discountFare.setText(discountOrPayableFare);
    }

    /**
     * to set the total fare from the previous scene
     */
    public void setTotalFare(String total){

        totalFare.setText(total);
    }

    /**
     * to set the dialog text field's prompt text
     */
    public void setTextFieldPromptText(String promptText){

        numberField.setPromptText(promptText);
    }

    /**
     * to set the dialog password field's prompt text
     */
    public void setPasswordFieldFieldPromptText(String promptText){

        pinField.setPromptText(promptText);
    }

    private void scaleIt(Node node, double scaleTo){

        ScaleTransition transition = new ScaleTransition(Duration.millis(200), node);

        transition.setToY(scaleTo);
        transition.play();
    }

    /**
     * to return the value of the boolean which shows if the confirm button is pressed or not
     */
    public boolean isConfirmButtonPressed() {
        return isConfirmButtonPressed;
    }

    public static boolean isNumeric(String text) {
        try {
            Double.parseDouble(text);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

}
