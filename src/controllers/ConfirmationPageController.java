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
import miscellaneous.createPdf.CreatePDF;
import miscellaneous.java.UserData;
import net.codejava.sql.ConnectorDB;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.CacheRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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

    private ReservationPageController rpc;
    public static boolean goingToDhaka = false;
    public String utkNo = "";

    /**
     * this setter gets controller from ReservationPageController
     */
    public void setRpc(ReservationPageController rpc) {
        this.rpc = rpc;
    }

    /**
     * this method is updating the trip data of confirmation page
     */



    public void updateTripData(String userName, String userGender, String cNo, String rTime, String boarding, String departTime, String dest, String cType, String seat, String fare){

        if(userGender.equals("Female")){
            passName.setText("Ms. " + userName);
        } else {
            passName.setText("Mr. "+ userName);
        }

        //todo if dhaka not selected swap from and to



        String[] part1 = rTime.split(",");
        String[] part2 = departTime.split(",");
        reportingTime.setText(part1[0]);
        tripDate.setText(part1[2]);
        boardingPoint.setText(boarding);
        departureTime.setText(part2[0]);
        destination.setText(dest);
        coachNo.setText(cNo);
        coachType.setText(cType);
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

                    //this task object is letting us get the time for pushing the data to database
                    Task<Void> task = new Task<>() {
                        @Override
                        public Void call() throws ParseException, SQLException {

                            scaleIt(200, fetchProg, 1, 2);

                            //TODO generate UTK number and push the data of booking ticket to database here...

                            String seat[] = seats.getText().split(", ");
                            String seatNo[] = seat[0].split("-");

                            LocalDateTime myDateObj = LocalDateTime.now();
                            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("ddMMyyyyHHmm");
                            DateTimeFormatter myFormatObj1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                            utkNo = myDateObj.format(myFormatObj);
                            String reservationDate = myDateObj.format(myFormatObj1);

                            DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
                            DateFormat givenFormat = new SimpleDateFormat("dd MMM yyyy");

                            Date date = givenFormat.parse(tripDate.getText());
                            String newTripDate = targetFormat.format(date);


                            utkNo += "-";
                            utkNo += coachNo.getText();
                            utkNo += seatNo[0] + seatNo[1];
                            utkNo +="-";


                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
                            SimpleDateFormat newTargetFormat = new SimpleDateFormat("ddMMyyyy");
                            date = simpleDateFormat.parse(tripDate.getText());
                            String formatDate = newTargetFormat.format(date);
                            if (departureTime.getText().substring(5, 7).equals("PM")){
                                String hour[] = departureTime.getText().split(":");
                                hour[1] = departureTime.getText().substring(3,5);
                                int time = Integer.parseInt(hour[0]);
                                time += 12;
                                formatDate += Integer.toString(time)+hour[1];

                            } else {
                                String hour[] = departureTime.getText().split(":");
                                hour[1] = departureTime.getText().substring(3,5);
                                formatDate += hour[0] + hour[1];
                            }
                            utkNo += formatDate;


                            try {
                                ConnectorDB connectorDB = new ConnectorDB();
                                UserData userData = new UserData();
                                PreparedStatement preparedStatement;

                                for(String arr: seat){
                                    String newCoachNo = coachNo.getText();



                                    if (coachNo.getText().contains("-R")) {

                                        newCoachNo = newCoachNo.substring(0, newCoachNo.length()-2);
                                        String sqlQuery1 = "INSERT INTO Reservation (UTKNo," +
                                                "userEmail, " +
                                                "coachNo, " +
                                                "bookedSeat, " +
                                                "reservationDate, " +
                                                "dateOfReturn, " +
                                                "paymentMethod, " +
                                                "duePayment) Values(?,?,?,?,?,?,?,?)";
                                        preparedStatement = connectorDB.getConnection().prepareStatement(sqlQuery1);
                                    } else{
                                        String sqlQuery2 = "INSERT INTO Reservation (UTKNo," +
                                                "userEmail, " +
                                                "coachNo, " +
                                                "bookedSeat, " +
                                                "reservationDate, " +
                                                "dateOfJourney, " +
                                                "paymentMethod, " +
                                                "duePayment) Values(?,?,?,?,?,?,?,?)";
                                        preparedStatement = connectorDB.getConnection().prepareStatement(sqlQuery2);

                                    }

                                    preparedStatement.setString(1,utkNo);
                                    preparedStatement.setString(2,userData.getUserEmail());

                                    preparedStatement.setString(3,newCoachNo);
                                    preparedStatement.setString(4,arr);
                                    preparedStatement.setString(5,reservationDate);
                                    preparedStatement.setString(6,newTripDate);
                                    preparedStatement.setString(7,paymentMethod);
                                    if(payableOrDue.contains("Payable")){
                                        preparedStatement.setString(8,"0");
                                    }else {
                                        preparedStatement.setString(8,Integer.toString(payableOrDueFare));

                                    }
                                    preparedStatement.executeUpdate();
                                }

                                String sqlQuery3 = "INSERT INTO transactionInformation (transactionId," +
                                        "userEmail, " +
                                        "statusInfo) Values(?,?,?)";

                                preparedStatement = connectorDB.getConnection().prepareStatement(sqlQuery3);
                                preparedStatement.setString(1, utkNo);
                                preparedStatement.setString(2, userData.getUserEmail());
                                if (payableOrDue.contains("Payable")){
                                    preparedStatement.setString(3, "Paid");
                                } else {
                                    preparedStatement.setString(3, "Booked");
                                }

                                preparedStatement.executeUpdate();



//                                createPDF.makePDF();













                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
//                            } catch (MalformedURLException e) {
//                                e.printStackTrace();
//                            } catch (FileNotFoundException e) {
//                                e.printStackTrace();
//                            }


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
                        CreatePDF createPDF = new CreatePDF(passName.getText(), seats.getText(), boardingPoint.getText(), reportingTime.getText(), coachNo.getText(), tripDate.getText(), totalFare.getText(), destination.getText(), departureTime.getText(), coachType.getText(), utkNo);



                        if(bookOrPurchase.equals("book")){

                            switch (paymentMethod) {
                                case "bkash" -> createInfoDialog("Ticket booking using Bkash was successful!!!");
                                case "rocket" -> createInfoDialog("Ticket booking using Rocket was successful!!!");
                                case "nagad" -> createInfoDialog("Ticket booking using Nagad was successful!!!");
                            }

                        } else{

                            switch (paymentMethod) {
                                case "bkash" -> createInfoDialog("Ticket Purchasing using Bkash was successful!!!");
                                case "rocket" -> createInfoDialog("Ticket Purchasing using Rocket was successful!!!");
                                case "nagad" -> createInfoDialog("Ticket Purchasing using Nagad was successful!!!");
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



                if(ReservationPageController.isPassengerReturn.equals("1")){

                    ReservationPageController.isPassengerReturn = "2";

                    if (ReservationPageController.downTripSelected){
                        goingToDhaka = true;
                        System.out.println("halum");
                    }


                    rpc.onClickBackToCoachInfoButton();
                    bookTicketBtn.getScene().getWindow().hide();

                } else{

                    try {
                        Parent dashboard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resources/dashboard.fxml")));

                        Scene dashboardScene = new Scene(dashboard);

                        Stage window = (Stage) ((Node) ev.getSource()).getScene().getWindow();
                        window.setScene(dashboardScene);
                        window.show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

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
