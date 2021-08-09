package controllers;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ConfirmationPageController implements Initializable {

    /**
     * variable initialization
     */
    @FXML
    public Button crossButton, homeButton, bookTicketBtn, purchaseTicketBtn;
    public Pane logOutBtn, transBtn, cancelBtn, reserveBtn, blurPane;
    public Label logOutInfo, tranLogInfo, cancelInfo, reserveInfo;
    public Text confirmation, page, passName, tripDate, seats, totalFare, boardingPoint, destination, reportingTime, departureTime, coachNo, coachType;
    public StackPane dialogStack;
    public HBox paymentOptionsHBox;

    public void updateTripData(String seat, String fare){

        seats.setText(seat);
        totalFare.setText(fare);
    }

    /**
     *  this method is to close the application
     */
    public void onClickCrossButton(){

        System.exit(0);
    }

    /**
     * this method is used for user logging out
     */
    public void onClickLogOutButton(javafx.event.Event actionEvent) {

        try{

            Parent page = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resources/home.fxml")));
            Scene scene = new Scene(page);

            Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();

        } catch (IOException ignored){

        }
    }

    /**
     * this method is to animate in the log out menu information
     */
    public void onHoverLogOutButton(){

        translateIt(100, logOutInfo, 220, 1);

    }

    /**
     * this method is to animate out the log out menu information
     */
    public void onExitLogOutButton(){

        translateIt(100, logOutInfo, 0, 1);

    }

    /**
     * this method is called when book button is clicked
     */
    public void onClickBookButton(){

        // error dialog box is generated here
        BoxBlur blur = new BoxBlur(6, 6, 6);

        FXMLLoader continueMsg = new FXMLLoader(getClass().getResource("/resources/continueDialog.fxml"));
        try {

            Region conLoader = continueMsg.load();

            ContinueDialogController cdc = continueMsg.getController();
            cdc.setDialogBody("You will have to pay 20% of the fare right now in order to book the ticket and have to arrive at the counter 30 minutes before the departure time and pay the rest of the fare. Do you wish to continue?");
            cdc.setDialogTitle("Booking the tickets?");

            JFXDialog contDialog = new JFXDialog(dialogStack, conLoader, JFXDialog.DialogTransition.TOP);

            cdc.setDialog(contDialog);
            contDialog.show();
            contDialog.setOnDialogClosed((JFXDialogEvent event) -> {

                blurPane.setEffect(null);

                if(cdc.isYesPressed()){

                    scaleIt(200, paymentOptionsHBox, 1, 2);
                }
            });
            blurPane.setEffect(blur);



        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * this method is called when purchase button is clicked
     */
    public void onClickPurchaseButton(){

        // error dialog box is generated here
        BoxBlur blur = new BoxBlur(6, 6, 6);

        FXMLLoader continueMsg = new FXMLLoader(getClass().getResource("/resources/continueDialog.fxml"));
        try {

            Region conLoader = continueMsg.load();

            ContinueDialogController cdc = continueMsg.getController();
            cdc.setDialogBody("You will get an instant 10% discount if you purchase the tickets right now through the dashboard app. Do you wish to continue?");
            cdc.setDialogTitle("Purchasing the tickets?");

            JFXDialog contDialog = new JFXDialog(dialogStack, conLoader, JFXDialog.DialogTransition.TOP);

            cdc.setDialog(contDialog);
            contDialog.show();
            contDialog.setOnDialogClosed((JFXDialogEvent event) -> {

                blurPane.setEffect(null);

                if(cdc.isYesPressed()){

                    scaleIt(200, paymentOptionsHBox, 1, 2);
                }
            });
            blurPane.setEffect(blur);



        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * this method is used to switch to transaction log page
     */
    public void onClickTransactionLogButton(javafx.event.Event actionEvent) {

        try{

            Parent page = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resources/transactionLogPage.fxml")));
            Scene scene = new Scene(page);

            Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();

        } catch (IOException ignored){

        }
    }

    /**
     *  this method is to animate in the transaction log menu information
     */
    public void onHoverTransactionLogButton(){

        translateIt(100, tranLogInfo, 220, 1);

    }

    /**
     * this method is to animate out the transaction log menu information
     */
    public void onExitTransactionLogButton(){

        translateIt(100, tranLogInfo, 0, 1);

    }

    /**
     * this method is used to switch to cancellation page
     */
    public void onClickCancellationButton(javafx.event.Event actionEvent) {

        try{

            Parent page = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resources/cancellationPage.fxml")));
            Scene scene = new Scene(page);

            Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();

        } catch (IOException ignored){

        }
    }

    /**
     * this method is to animate in the cancellation menu information
     */
    public void onHoverCancellationButton(){

        translateIt(100, cancelInfo, 220, 1);

    }

    /**
     *  this method is to animate out the cancellation menu information
     */
    public void onExitCancellationButton(){

        translateIt(100, cancelInfo, 0, 1);

    }

    /**
     * this method is used to switch to reservation page
     */
    public void onClickReservationButton(javafx.event.Event actionEvent) {

        try{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/reservationPage.fxml"));
            Parent page = loader.load();
            Scene scene = new Scene(page);

            ReservationPageController.setRpc(loader.getController());

            Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();

        } catch (IOException ignored){

        }
    }

    /**
     * this method is to animate in the reservation menu information
     */
    public void onHoverReservationButton(){

        translateIt(100, reserveInfo, 220, 1);

    }

    /**
     * this method is to animate out the reservation menu information
     */
    public void onExitReservationButton(){

        translateIt(100, reserveInfo, 0, 1);

    }

    /**
     * this method allows user to return to the dashboard
     */
    public void onClickHomeButton(javafx.event.Event actionEvent){

        try{

            Parent homePage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resources/dashboard.fxml")));
            Scene homePageScene = new Scene(homePage);

            Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(homePageScene);
            window.show();

        } catch (IOException ignored){

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        translateIt(500, page, -197, 1);
        translateIt(500, confirmation, -393, 1);
    }

    public void translateIt(double duration, Node node, double translateTo, int type){

        TranslateTransition transition = new TranslateTransition(Duration.millis(duration), node);

        if(type == 1){

            transition.setToX(translateTo);
        } else if(type == 2){

            transition.setToY(translateTo);
        }

        transition.play();

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
