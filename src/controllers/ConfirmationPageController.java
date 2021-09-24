package controllers;

import com.gluonhq.charm.glisten.control.ProgressBar;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.concurrent.Task;
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
    public Button crossButton, bookTicketBtn, purchaseTicketBtn, backToReserveBtn;
    public Pane logOutBtn, transBtn, cancelBtn, reserveBtn, blurPane, bkashPane, rocketPane, nagadPane, containerPane;
    public Label logOutInfo, tranLogInfo, cancelInfo, reserveInfo;
    public Text confirmation, page, passName, tripDate, seats, totalFare, boardingPoint, destination, reportingTime, departureTime, coachNo, coachType, paymentMethodText;
    public StackPane dialogStack;
    public HBox paymentOptionsHBox;
    public ProgressBar fetchProg;

    private String bookOrPurchase;
    private String paymentMethod;

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
     * this method is to animate in the log-out menu information
     */
    public void onHoverLogOutButton(){

        translateIt(100, logOutInfo, 220, 1);

    }

    /**
     * this method is to animate out the log-out menu information
     */
    public void onExitLogOutButton(){

        translateIt(100, logOutInfo, 0, 1);

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
    public void onClickBackToReserveButton(javafx.event.Event actionEvent){

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
     * this method is called when book button is clicked
     */
    public void onClickBookButton(){

        if(bookTicketBtn.getText().equals("Book")){

            // booking confirmation dialog box is generated here
            BoxBlur blur = new BoxBlur(6, 6, 6);

            FXMLLoader continueMsg = new FXMLLoader(getClass().getResource("/resources/continueDialog.fxml"));
            try {

                Region conLoader = continueMsg.load();

                ContinueDialogController cdc = continueMsg.getController();
                cdc.setDialogBody("You will have to pay 20% of the fare right now in order to book the ticket and have " +
                        "to arrive at the counter 30 minutes before the departure time and pay the rest of the fare. " +
                        "Do you wish to continue?");

                cdc.setDialogTitle("Booking the tickets?");

                JFXDialog contDialog = new JFXDialog(dialogStack, conLoader, JFXDialog.DialogTransition.TOP);

                cdc.setDialog(contDialog);
                contDialog.show();
                contDialog.setOnDialogClosed((JFXDialogEvent event) -> {

                    blurPane.setEffect(null);

                    if(cdc.isYesPressed()){

                        bookTicketBtn.setText("Cancel");
                        purchaseTicketBtn.setDisable(true);
                        paymentMethodText.setText("Select Your Booking Method:");
                        scaleIt(200, paymentOptionsHBox, 1, 2);
                        bookOrPurchase = "book";
                    }
                });
                blurPane.setEffect(blur);

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        } else{

            scaleIt(200, paymentOptionsHBox, 0, 2);
            purchaseTicketBtn.setDisable(false);
            bookTicketBtn.setText("Book");
        }


    }

    /**
     * this method is called when purchase button is clicked
     */
    public void onClickPurchaseButton(){

        if(purchaseTicketBtn.getText().equals("Purchase")){

            // purchase confirmation dialog box is generated here
            BoxBlur blur = new BoxBlur(6, 6, 6);

            FXMLLoader continueMsg = new FXMLLoader(getClass().getResource("/resources/continueDialog.fxml"));
            try {

                Region conLoader = continueMsg.load();

                ContinueDialogController cdc = continueMsg.getController();
                cdc.setDialogBody("You will get an instant 10% discount if you purchase the tickets " +
                        "right now through the dashboard app. Do you wish to continue?");

                cdc.setDialogTitle("Purchasing the tickets?");

                JFXDialog contDialog = new JFXDialog(dialogStack, conLoader, JFXDialog.DialogTransition.TOP);

                cdc.setDialog(contDialog);
                contDialog.show();
                contDialog.setOnDialogClosed((JFXDialogEvent event) -> {

                    blurPane.setEffect(null);

                    if(cdc.isYesPressed()){

                        purchaseTicketBtn.setText("Cancel");
                        bookTicketBtn.setDisable(true);
                        paymentMethodText.setText("Select Your Purchase Method:");
                        scaleIt(200, paymentOptionsHBox, 1, 2);
                        bookOrPurchase = "purchase";
                    }
                });
                blurPane.setEffect(blur);

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } else{

            scaleIt(200, paymentOptionsHBox, 0, 2);
            bookTicketBtn.setDisable(false);
            purchaseTicketBtn.setText("Purchase");
        }

    }

    /**
     * this method is called when the user chooses Bkash as a payment method
     */
    public void onClickingBkashPane(){

        int fare = Integer.parseInt(totalFare.getText().substring(4));

        paymentMethod = "bkash";

        if(bookOrPurchase.equals("book")){

            createPaymentDialog("Payable Fare:", "Due Fare: ", "Bkash Booking Payment",
                    totalFare.getText(), "Enter your Bkash number",
                    "Enter your Bkash PIN", (int) (fare * 0.2), (fare - (int) (fare * 0.2)));

        } else{

            createPaymentDialog("Discount Fare:", "Payable Fare: ", "Bkash Purchase Payment",
                    totalFare.getText(), "Enter your Bkash number",
                    "Enter your Bkash PIN", (int) (fare * 0.1), (fare - (int) (fare * 0.1)));
        }

    }

    /**
     * this method is called when the user chooses Rocket as a payment method
     */
    public void onClickingRocketPane(){

        int fare = Integer.parseInt(totalFare.getText().substring(4));

        paymentMethod = "rocket";

        if(bookOrPurchase.equals("book")){

            createPaymentDialog("Payable Fare:", "Due Fare: ", "Rocket Booking Payment",
                    totalFare.getText(), "Enter your Rocket number",
                    "Enter your Rocket PIN", (int) (fare * 0.2), (fare - (int) (fare * 0.2)));

        } else{

            createPaymentDialog("Discount Fare:", "Payable Fare: ", "Rocket Purchase Payment",
                    totalFare.getText(), "Enter your Rocket number",
                    "Enter your Rocket PIN", (int) (fare * 0.1), (fare - (int) (fare * 0.1)));
        }

    }

    /**
     * this method is called when the user chooses Nagad as a payment method
     */
    public void onClickingNagadPane(){

        int fare = Integer.parseInt(totalFare.getText().substring(4));

        paymentMethod = "nagad";

        if(bookOrPurchase.equals("book")){

            createPaymentDialog("Payable Fare:", "Due Fare: ", "Nagad Booking Payment",
                    totalFare.getText(), "Enter your Nagad number",
                    "Enter your Nagad PIN", (int) (fare * 0.2), (fare - (int) (fare * 0.2)));

        } else{

            createPaymentDialog("Discount Fare:", "Payable Fare: ", "Nagad Purchase Payment",
                    totalFare.getText(), "Enter your Nagad number",
                    "Enter your Nagad PIN", (int) (fare * 0.1), (fare - (int) (fare * 0.1)));
        }

    }

    /**
     * this method generates the booking or purchasable payment dialog
     */
    private void createPaymentDialog(String discountOrPayable, String payableOrDue, String dialogTitle, String total, String textFieldPromptText, String passwordFieldPromptText, int discountOrPayableFare, int payableOrDueFare){

        BoxBlur blur = new BoxBlur(6, 6, 6);
        FXMLLoader paymentFXML = new FXMLLoader(getClass().getResource("/resources/paymentDialog.fxml"));

        // payment dialog box is generated here

        try {

            Region conLoader = paymentFXML.load();

            PaymentDialogController pdc = paymentFXML.getController();
            pdc.setDialogBodyText(discountOrPayable, payableOrDue);
            pdc.setDialogTitle(dialogTitle);
            pdc.setTotalFare(total);
            pdc.setTextFieldPromptText(textFieldPromptText);
            pdc.setPasswordFieldFieldPromptText(passwordFieldPromptText);

            pdc.setDialogBodyFare("BDT " + discountOrPayableFare, "BDT " + payableOrDueFare);

            JFXDialog paymentDialog = new JFXDialog(dialogStack, conLoader, JFXDialog.DialogTransition.TOP);

            pdc.setDialog(paymentDialog);
            paymentDialog.show();
            paymentDialog.setOnDialogClosed((JFXDialogEvent event) -> {

                blurPane.setEffect(null);

                if(pdc.isConfirmButtonPressed()){

                    //this task object is letting us to get the time for pushing the data to database
                    Task<Void> task = new Task<>() {
                        @Override
                        public Void call() {

                            scaleIt(200, fetchProg, 1, 2);

                            //TODO generate UTK number and push the data of booking ticket to database here...

                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            scaleIt(200, fetchProg, 0, 2);

                            return null;
                        }
                    };
                    task.setOnSucceeded(e -> {

                        if(bookOrPurchase.equals("book")){

                            switch (paymentMethod) {
                                case "bkash":

                                    createInfoDialog("Ticket booking using Bkash was successful!!!");
                                    break;
                                case "rocket":

                                    createInfoDialog("Ticket booking using Rocket was successful!!!");
                                    break;
                                case "nagad":

                                    createInfoDialog("Ticket booking using Nagad was successful!!!");
                                    break;
                            }


                        } else{

                            switch (paymentMethod) {
                                case "bkash":

                                    createInfoDialog("Ticket Purchasing using Bkash was successful!!!");
                                    break;
                                case "rocket":

                                    createInfoDialog("Ticket Purchasing using Rocket was successful!!!");
                                    break;
                                case "nagad":

                                    createInfoDialog("Ticket Purchasing using Nagad was successful!!!");
                                    break;
                            }

                        }

                    });
                    new Thread(task).start();
                }
            });
            blurPane.setEffect(blur);

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * this method generates the booking or purchase successful dialog
     */
    private void createInfoDialog(String dialogTitle){

        // success dialog box is generated here

        BoxBlur blur = new BoxBlur(6, 6, 6);
        FXMLLoader success = new FXMLLoader(getClass().getResource("/resources/infoDialog.fxml"));

        try {

            Region successLoader = success.load();

            InfoDialogController idc = success.getController();
            idc.setDialogBody("A PDF copy of the ticket has been sent to your email. " +
                    "You must bring the PDF ticket to the counter on the journey date. " +
                    "Thanks for choosing SUPLUX paribahan. Have a nice journey.");

            idc.setDialogButtonText("Okay, Thank you");
            idc.setRootPaneHeightAndWidth(300, 700);
            idc.setDialogBodyHeightAndWidth(146, 581);
            idc.setDialogButtonPosition(263, 209);
            idc.setDialogBodyPosition(60, 50);
            idc.setDialogTitle(dialogTitle);
            idc.setDialogTitlePosition(60, 14);

            JFXDialog successDialog = new JFXDialog(dialogStack, successLoader, JFXDialog.DialogTransition.TOP);

            idc.setDialog(successDialog);
            successDialog.show();
            successDialog.setOnDialogClosed((JFXDialogEvent ev) -> {

                blurPane.setEffect(null);

                //TODO fetch user data from the database here...

                try {
                    Parent dashboard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resources/dashboard.fxml")));

                    Scene dashboardScene = new Scene(dashboard);

                    Stage window = (Stage) ((Node) ev.getSource()).getScene().getWindow();
                    window.setScene(dashboardScene);
                    window.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
            blurPane.setEffect(blur);

        } catch (IOException ignored) {

        }
    }

    /**
     * initializes the scene
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        translateIt(500, page, -197, 1);
        translateIt(500, confirmation, -393, 1);
        translateIt(500, backToReserveBtn, 180, 1);
        translateIt(500, containerPane, 1300, 1);

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
