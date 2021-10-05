package controllers;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import listeners.AutoCompleteComboBoxListener;
import miscellaneous.java.UserData;
import net.codejava.sql.ConnectorDB;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.*;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;


public class ReservationPageController implements Initializable {

    public static int selectedSeatCount = 0;
    private static ReservationPageController rpc;
    public final String[] destinations = {"Dhaka", "Khulna", "Chittagong", "Cox's Bazar", "Kolkata", "Benapole", "Satkhira",
                                            "Barisal", "Sylhet", "Rajshahi", "Rangpur", "Saint Martin", "Jessore", "Bogura",
                                            "Khagrachari", "Bandarban", "Rangamati", "Panchagarh", "Chapai Nawabganj",
                                            "Natore", "Pabna", "Kuakata", "Dinajpur", "Siliguri", "Kathmandu"};
    public final String[] timePeriod = {"Time Period", "Morning(5:00 AM - 11:59 AM)", "Afternoon(12:00 PM - 5:59 PM)", "Night(6:00 PM - 11:59 PM)"};
    public final String[] boardingPoints = {"Select A Boarding Point", "Aarambag, Dhaka (10:00 PM)", "Panthapath, Dhaka (10:30 PM)", "Gabtoli, Dhaka (11:00 PM)", "Savar, Dhaka (11:30 PM)"};
    public final String[] droppingPoints = {"Select A Dropping Point", "Aarambag, Khagrachari (10:00 PM)", "Panthapath, Khagrachari (10:30 PM)", "Gabtoli, Khagrachari (11:00 PM)", "Savar, Khagrachari (11:30 PM)"};
    /**
     * variable initialization
     */
    @FXML
    public Button crossButton, homeButton, resetButton, searchButton, backToReserveBtn, proceedBtn, backToCoachInfoButton;
    public Pane logOutBtn, transBtn, cancelBtn, revPane, seatMapDetailsPane, viewSeatPane;
    public ScrollPane coachInfoPane;
    public AnchorPane rootAnchor;
    public StackPane dialogStack, rootStack;
    public Label logOutInfo, tranLogInfo, cancelInfo, reserveInfo;
    public Text reservation, page, coachType, totalFare, seatMapDetailsFare, coachTypeSeatMapText, coachStartingSeatMapText, coachNoSeatMapText, DestinationSeatMapText, tripTypeSeatMapText, reportingTimeSeatMapText, departureTimeSeatMapText;
    public ComboBox<String> fromComboBox, toComboBox, timePeriodComboBox, droppingPointComboBox, boardingPointComboBox;
    public CheckBox checkBoxNonAC, checkBoxBi, checkBoxMulti, checkBoxSleeper, checkBoxDD;
    public DatePicker dateOfJourney, dateOfReturn;
    public VBox coachInfoBox, coachTypeVBox, seatMapVBox, selectedSeatBox, radioDeckVBox;
    public com.gluonhq.charm.glisten.control.ProgressBar fetchProg;
    public RadioButton radioUpper, radioLower;
    public Node[] selectedSeats = new Node[4];
    public ArrayList<FXMLLoader> selectedSeatsFXML = new ArrayList<>();
    public ArrayList<FXMLLoader> coachInfoFXML = new ArrayList<>();
    public ArrayList<Node> lowerDeckDD = new ArrayList<>();
    public ArrayList<Node> upperDeckDD = new ArrayList<>();
    public ArrayList<Node> lowerDeckSleeper = new ArrayList<>();
    public ArrayList<Node> upperDeckSleeper = new ArrayList<>();
    public ArrayList<String> destination;
    public boolean isToTrimDone = false;
    public boolean isFromTrimDone = false;
    public ArrayList<String> selectedSeatString = new ArrayList<>();
    public ArrayList<String> soldSeatsList = new ArrayList<>();
    public ArrayList<String> bookedSeatsList = new ArrayList<>();
    public ArrayList<String> soldSeatsListDDLower = new ArrayList<>();
    public ArrayList<String> bookedSeatsListDDLower = new ArrayList<>();
    public ArrayList<String> soldSeatsListDDUpper = new ArrayList<>();
    public ArrayList<String> bookedSeatsListDDUpper = new ArrayList<>();
    public ArrayList<String> soldSeatsListSleeperLower = new ArrayList<>();
    public ArrayList<String> bookedSeatsListSleeperLower = new ArrayList<>();
    public ArrayList<String> soldSeatsListSleeperUpper = new ArrayList<>();
    public ArrayList<String> bookedSeatsListSleeperUpper = new ArrayList<>();
    public ArrayList<String> selectedCoachType = new ArrayList<>();
    public Boolean isNonAcSelected = false;
    public Boolean isAcBiSelected = false;
    public Boolean isACMultiSelected = false;
    public Boolean isDDSelected = false;
    public Boolean isSleeperSelected = false;
    public static String isPassengerReturn = "0";
    public static boolean downTripSelected = false;
    public String formatDate, formatDate2, formatDate1;
    public String finalSqlQuery, finalSqlQuery1;
    public DateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    public DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
    public DateFormat targetFormat2 = new SimpleDateFormat("E, dd MMM yyyy");


    public String startingFrom, dest,  coachNo, reportingTime, boarding, departureTime, cType, busFare, journeyDate, returnDate;
    public String userName, userGender;
    public  int total =  0;




    /**
     * this setter invokes ReservationPageController for after uses
     */
    public static void setRpc(ReservationPageController rpc) {
        ReservationPageController.rpc = rpc;
    }

    /**
     * this method is to close the application
     */
    public void onClickCrossButton() {

        System.exit(0);
    }

    /**
     * this method is used for user logging out
     */
    public void onClickLogOutButton(javafx.event.Event actionEvent) {

        try {

            Parent homePage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resources/home.fxml")));
            Scene homePageScene = new Scene(homePage);

            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(homePageScene);
            window.show();

        } catch (IOException ignored) {

        }
    }

    /**
     * this method is to animate in the log out menu information
     */
    public void onHoverLogOutButton() {

        translateIt(100, logOutInfo, 220, 1);
    }

    /**
     * this method is to animate out the log out menu information
     */
    public void onExitLogOutButton() {

        translateIt(100, logOutInfo, 0, 1);
    }

    /**
     * this method is used to switch to transaction log page
     */
    public void onClickTransactionLogButton(javafx.event.Event actionEvent) {

        try {

            Parent page = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resources/transactionLogPage.fxml")));
            Scene scene = new Scene(page);

            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();

        } catch (IOException ignored) {

        }
    }

    /**
     * this method is to animate in the transaction log menu information
     */
    public void onHoverTransactionLogButton() {

        translateIt(100, tranLogInfo, 220, 1);
    }

    /**
     * this method is to animate out the transaction log menu information
     */
    public void onExitTransactionLogButton() {

        translateIt(100, tranLogInfo, 0, 1);
    }

    /**
     * this method is used to switch to cancellation page
     */
    public void onClickCancellationButton(javafx.event.Event actionEvent) {

        try {

            Parent page = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resources/cancellationPage.fxml")));
            Scene scene = new Scene(page);

            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();

        } catch (IOException ignored) {

        }
    }

    /**
     * this method is to animate in the cancellation menu information
     */
    public void onHoverCancellationButton() {

        translateIt(100, cancelInfo, 220, 1);
    }

    /**
     * this method is to animate out the cancellation menu information
     */
    public void onExitCancellationButton() {

        translateIt(100, cancelInfo, 0, 1);
    }

    /**
     * this method allows user to return to the dashboard
     */
    public void onClickHomeButton(javafx.event.Event actionEvent) {

        try {

            Parent homePage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resources/dashboard.fxml")));
            Scene homePageScene = new Scene(homePage);

            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(homePageScene);
            window.show();

        } catch (IOException ignored) {

        }
    }

    /**
     * this method allows user to reset the value of "Date of Return" date picker
     */
    public void onClickResetButton() {

        dateOfReturn.getEditor().clear();

    }

    public void onClickDateOfJourney() {

        dateOfJourney.getStyleClass().remove("date-picker-error");
    }

    public void onClickTimePeriodComboBox() {

        timePeriodComboBox.getStyleClass().remove("combo-box-base-error");
    }

    /**
     * this method searches the database for available trips
     */
    public void onClickSearchButton() {
        selectedCoachType.clear();


        if (!fromComboBox.getEditor().getText().isEmpty() && !fromComboBox.getEditor().getText().isBlank()
                && !toComboBox.getEditor().getText().isEmpty() && !toComboBox.getEditor().getText().isBlank()
                && !dateOfJourney.getEditor().getText().isEmpty() && !timePeriodComboBox.getSelectionModel().isSelected(0)) {


            //this task object is letting us get the time for fetching the data from database
            Task<Void> task = new Task<>() {
                @Override
                public Void call() throws ParseException {

                    scaleIt(200, fetchProg, 1, 2);
                    String from = fromComboBox.getEditor().getText();
                    String to = toComboBox.getEditor().getText();
                    if(to.equals("Cox's Bazar")){
                        to = "Cox\"s Bazar";
                    } if (from.equals("Cox's Bazar")){
                        from = "Cox\"s Bazar";
                    }
                    if (!from.equals("Dhaka")){
                        downTripSelected = true;
                    }


                    journeyDate = dateOfJourney.getEditor().getText();
                    if (!dateOfReturn.getEditor().getText().equals("")){
                        isPassengerReturn = "1";
                    }




                    Date date = simpleDateFormat.parse(journeyDate);
                    formatDate = targetFormat.format(date);
                    formatDate2 = targetFormat2.format(date);




                    String time = timePeriodComboBox.getSelectionModel().getSelectedItem();
                    String time1, time2, amOrPm;


                    if (time.equals("Morning(5:00 AM - 11:59 AM)")) {
                        time1 = "08";
                        time2 = "12";
                        amOrPm = "%AM";
                    } else if (time.equals("Afternoon(12:00 PM - 5:59 PM)")) {
                        time1 = "04";
                        time2 = "06";
                        amOrPm = "%PM";
                    } else {
                        time1 = "08";
                        time2 = "12";
                        amOrPm = "%PM";
                    }


                    String sqlQuery = "Select * from tripData Where startingFrom = '" +
                            from + "' and destination = '" +
                            to + "' and departureTime like '" +
                            amOrPm + "' and departureTime between '" +
                            time1 + "' and '" +
                            time2 + "' ";

                    String sqlQuery1 = "Select * from tripData Where startingFrom = '" +
                            to + "' and destination = '" +
                            from + "' and departureTime like '" +
                            amOrPm + "' and departureTime between '" +
                            time1 + "' and '" +
                            time2 + "' ";



                    if (checkBoxNonAC.isSelected()) {
                        selectedCoachType.add("NON-AC");
                    }
                    if (checkBoxBi.isSelected()) {
                        selectedCoachType.add("AC (Bi)");
                    }
                    if (checkBoxMulti.isSelected()) {
                        selectedCoachType.add("AC (Multi)");
                    }
                    if (checkBoxSleeper.isSelected()) {
                        selectedCoachType.add("Sleeper");
                    }
                    if (checkBoxDD.isSelected()) {
                        selectedCoachType.add("DD");
                    }

                    if (selectedCoachType.size() == 0){
                        sqlQuery += " and ( coachType like 'AC (Bi)'" + " or coachType like 'AC (Multi)'" + " or coachType like 'AC (Bi)'" + " or coachType like 'Sleeper'" + " or coachType like 'DD'" + " or coachType like 'NON-AC'";
                        sqlQuery += ")";
                        sqlQuery1 += " and ( coachType like 'AC (Bi)'" + " or coachType like 'AC (Multi)'" + " or coachType like 'AC (Bi)'" + " or coachType like 'Sleeper'" + " or coachType like 'DD'" + " or coachType like 'NON-AC'";
                        sqlQuery1 += ")";
                    } else {
                        sqlQuery += " and ( ";
                        sqlQuery1 += " and ( ";
                        for (int i = 0; i < selectedCoachType.size(); i++){
                            if(i < selectedCoachType.size()-1){
                                sqlQuery = sqlQuery + "coachType like '" + selectedCoachType.get(i) + "' or ";
                                sqlQuery1 = sqlQuery1 + "coachType like '" + selectedCoachType.get(i) + "' or ";
                            } else {
                                sqlQuery += "coachType like '" + selectedCoachType.get(i) + "')";
                                sqlQuery1 += "coachType like '" + selectedCoachType.get(i) + "')";
                            }
                        }
                        System.out.println(sqlQuery);
                    }

                    finalSqlQuery = sqlQuery;

                    finalSqlQuery1 = sqlQuery1;
                    Platform.runLater(() -> {
                        System.out.println(finalSqlQuery);






                        if (isPassengerReturn.equals("1") || isPassengerReturn.equals("0") && !downTripSelected) {
                            try {
                                ConnectorDB connectorDB = new ConnectorDB();
                                Statement statement = connectorDB.getConnection().createStatement();
                                ResultSet resultSet = statement.executeQuery(finalSqlQuery);
                                Node[] info = new Node[20];
                                int i =0;
                                while (resultSet.next()){
                                    try {

                                        FXMLLoader coachInfo = new FXMLLoader(getClass().getResource("/resources/coachInfo.fxml"));
                                        info[i] = coachInfo.load();

                                        coachInfoFXML.add(coachInfo);

                                        CoachInfoController cic = coachInfo.getController();
                                        cic.setRpc(rpc);

                                        startingFrom =  resultSet.getString("startingFrom");
                                        dest = resultSet.getString("destination");
                                        coachNo = resultSet.getString("coachNo");
                                        reportingTime = resultSet.getString("departureTime") ;

                                        boarding = resultSet.getString("boardingPoint") + ", " + startingFrom;
                                        departureTime = resultSet.getString("departureTime") + ", " + formatDate2;
                                        cType = resultSet.getString("coachType");
                                        int hour = Integer.parseInt(departureTime.substring(0,2));
                                        int min = Integer.parseInt(departureTime.substring(3,5));
                                        String amPM = reportingTime.substring(5,7);
                                        if (min<15){
                                            hour--;
                                            min = 60 + min - 15;
                                        } else {
                                            min = min -15;
                                        }
                                        reportingTime = new DecimalFormat("00").format(hour) + ":" + new DecimalFormat("00").format(min) + amPM +", "+ formatDate2;


                                        busFare = resultSet.getString("farePerSeat");
                                        int availableSeats = 0;

                                        try {
                                            Statement statement1 = connectorDB.getConnection().createStatement();
                                            String queryForAvailableSeats = "select * from Reservation join tripData on tripData.coachNo = Reservation.coachNo where tripData.coachNo = '"+ coachNo +"' and Reservation.dateOfJourney = '" + formatDate+ "'";
                                            System.out.println(queryForAvailableSeats);
                                            ResultSet resultSet1 = statement1.executeQuery(queryForAvailableSeats);
                                            int ir=0;
                                            while (resultSet1.next()){
                                                ir++;
                                            }
                                            if ("NON-AC".equals(cType)) {
                                                availableSeats = 41 - ir;
                                                System.out.println(availableSeats);
                                            } else if ("AC (Bi)".equals(cType)) {
                                                availableSeats = 28 - ir;
                                                System.out.println(availableSeats);
                                            } else if ("AC (Multi)".equals(cType)) {
                                                availableSeats = 31 - ir;
                                                System.out.println(availableSeats);
                                            } else if ("DD".equals(cType)) {
                                                availableSeats = 43 - ir;
                                                System.out.println(availableSeats);
                                            } else {
                                                availableSeats = 30 - ir;
                                                System.out.println(availableSeats);
                                            }


                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }



                                        cic.updateInfo(startingFrom, dest, coachNo, reportingTime, boarding, departureTime, dest, cType, Integer.toString(availableSeats), busFare);

                                        coachInfoBox.getChildren().add(info[i]);

                                    } catch (IOException ignored) {
                                    }

                                }

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (downTripSelected){

                            returnDate = dateOfJourney.getEditor().getText();
                            Date date1 = null;
                            try {
                                date1 = simpleDateFormat.parse(returnDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            formatDate1 = targetFormat.format(date1);
                            String formatDate3 = targetFormat2.format(date1);
                            try {
                                ConnectorDB connectorDB = new ConnectorDB();
                                Statement statement = connectorDB.getConnection().createStatement();
                                ResultSet resultSet = statement.executeQuery(finalSqlQuery1);
                                Node[] info = new Node[20];
                                int i =0;
                                while (resultSet.next()){
                                    try {

                                        FXMLLoader coachInfo = new FXMLLoader(getClass().getResource("/resources/coachInfo.fxml"));
                                        info[i] = coachInfo.load();

                                        coachInfoFXML.add(coachInfo);

                                        CoachInfoController cic = coachInfo.getController();
                                        cic.setRpc(rpc);

                                        startingFrom =  resultSet.getString("startingFrom");
                                        dest = resultSet.getString("destination");
                                        coachNo = resultSet.getString("coachNo");
                                        reportingTime = resultSet.getString("departureTime") ;

                                        boarding = resultSet.getString("boardingPoint") + ", " + startingFrom;
                                        departureTime = resultSet.getString("departureTime") + ", " + formatDate3;
                                        cType = resultSet.getString("coachType");
                                        int hour = Integer.parseInt(departureTime.substring(0,2));
                                        int min = Integer.parseInt(departureTime.substring(3,5));
                                        String amPM = reportingTime.substring(5,7);
                                        System.out.println(hour+", "+min);
                                        if (min<15){
                                            hour--;
                                            min = 60 + min - 15;
                                        } else {
                                            min = min -15;
                                        }
                                        reportingTime = new DecimalFormat("00").format(hour) + ":" + new DecimalFormat("00").format(min) + amPM +", "+ formatDate3;


                                        busFare = resultSet.getString("farePerSeat");
                                        int availableSeats = 0;

                                        try {
                                            Statement statement1 = connectorDB.getConnection().createStatement();
                                            String queryForAvailableSeats = "select * from Reservation join tripData on tripData.coachNo = Reservation.coachNo where tripData.coachNo = '"+ coachNo +"' and Reservation.dateOfReturn = '" + formatDate1+ "'";
                                            System.out.println(queryForAvailableSeats);
                                            ResultSet resultSet1 = statement1.executeQuery(queryForAvailableSeats);
                                            int ir=0;
                                            while (resultSet1.next()){
                                                ir++;
                                            }
                                            if ("NON-AC".equals(cType)) {
                                                availableSeats = 41 - ir;
                                                System.out.println(availableSeats);
                                            } else if ("AC (Bi)".equals(cType)) {
                                                availableSeats = 28 - ir;
                                                System.out.println(availableSeats);
                                            } else if ("AC (Multi)".equals(cType)) {
                                                availableSeats = 31 - ir;
                                                System.out.println(availableSeats);
                                            } else if ("DD".equals(cType)) {
                                                availableSeats = 43 - ir;
                                                System.out.println(availableSeats);
                                            } else {
                                                availableSeats = 30 - ir;
                                                System.out.println(availableSeats);
                                            }


                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }


                                        reportingTime = new DecimalFormat("00").format(hour) + ":" + new DecimalFormat("00").format(min) + amPM +", "+ formatDate3;
                                        departureTime = resultSet.getString("departureTime") + ", " + formatDate3;

                                        String temp = startingFrom;
                                        startingFrom = dest;
                                        dest = temp;
                                        coachNo +=  "-R";


                                        cic.updateInfo(startingFrom, dest, coachNo, reportingTime, startingFrom, departureTime, dest, cType, Integer.toString(availableSeats), busFare);
                                        //cic.updateInfo(dest, startingFrom, coachNo + "-R", reportingTime, dest, departureTime, startingFrom, cType, Integer.toString(availableSeats), busFare);

                                        coachInfoBox.getChildren().add(info[i]);

                                    } catch (IOException ignored) {
                                    }

                                }

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                        }

                    });



                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    scaleIt(200, fetchProg, 0, 2);

                    return null;
                }
            };
            task.setOnSucceeded(e -> {


                translateIt(300, fromComboBox, -80, 2);
                translateIt(300, toComboBox, -80, 2);
                translateIt(300, dateOfReturn, -80, 2);
                translateIt(300, dateOfJourney, -80, 2);
                translateIt(300, resetButton, -80, 2);

                translateIt(300, timePeriodComboBox, 1300, 1);
                translateIt(300, coachType, 1300, 1);
                translateIt(300, coachTypeVBox, 1300, 1);

                TranslateTransition searchTransition = new TranslateTransition(Duration.millis(300), searchButton);
                searchTransition.setToX(1300);
                searchTransition.play();

                searchTransition.setOnFinished((et) -> {

                    scaleIt(200, coachInfoPane, 1, 3);
                    translateIt(200, backToReserveBtn, 80, 1);

                });

            });
            new Thread(task).start();
        } else {

            if (fromComboBox.getEditor().getText().isEmpty() || fromComboBox.getEditor().getText().isBlank()) {

                fromComboBox.setStyle("-fx-border-color: red");
            }

            if (toComboBox.getEditor().getText().isEmpty() || toComboBox.getEditor().getText().isBlank()) {

                toComboBox.setStyle("-fx-border-color: red");
            }

            if (dateOfJourney.getEditor().getText().isEmpty()) {

                dateOfJourney.getStyleClass().add("date-picker-error");
            }

            if (timePeriodComboBox.getSelectionModel().isSelected(0)) {

                timePeriodComboBox.getStyleClass().add("combo-box-base-error");
            }

            // error dialog box is generated here
            BoxBlur blur = new BoxBlur(6, 6, 6);

            FXMLLoader error = new FXMLLoader(getClass().getResource("/resources/infoDialog.fxml"));
            try {

                Region errorLoader = error.load();

                InfoDialogController idc = error.getController();
                idc.setDialogBody("You must fill up \"From\", \"To\", \"Date Of Journey\" and \"Time Period\" field.");

                JFXDialog errorDialog = new JFXDialog(rootStack, errorLoader, JFXDialog.DialogTransition.TOP);

                idc.setDialog(errorDialog);
                errorDialog.show();
                errorDialog.setOnDialogClosed((JFXDialogEvent event) -> rootAnchor.setEffect(null));
                rootAnchor.setEffect(blur);

            } catch (IOException ignored) {

            }

        }

    }

    /**
     * this method hides the coach information details
     */
    public void onClickBackToReserveButton() {

        scaleIt(200, coachInfoPane, 0, 3);

        TranslateTransition backTransition = new TranslateTransition(Duration.millis(200), backToReserveBtn);
        backTransition.setToX(0);
        backTransition.play();

        backTransition.setOnFinished((e) -> {

            translateIt(300, fromComboBox, 0, 2);
            translateIt(300, toComboBox, 0, 2);
            translateIt(300, dateOfReturn, 0, 2);
            translateIt(300, dateOfJourney, 0, 2);
            translateIt(300, resetButton, 0, 2);

            translateIt(300, timePeriodComboBox, 0, 1);
            translateIt(300, coachType, 0, 1);
            translateIt(300, coachTypeVBox, 0, 1);
            translateIt(300, searchButton, 0, 1);

            coachInfoBox.getChildren().clear();
            coachInfoFXML.clear();

        });
    }

    /**
     * this method shows the seat map to the user
     */
    public void showSeatMap(String coachType, String coachNo, String from, String to, String fare, String tripType, String reportingTime, String departureTime) {

        try {
            ConnectorDB connectorDB =  new ConnectorDB();
            Statement statement = connectorDB.getConnection().createStatement();


            if (coachNo.contains("-R")){
                String newCoachNo = coachNo.substring(0, coachNo.length()-2);
                String sqlQuery = "select bookedSeat, statusInfo from Reservation join transactionInformation on transactionInformation.transactionId = Reservation.UTKNo where coachNo = '" + newCoachNo + "' and dateOfReturn = '"+ formatDate1 + "'";
                System.out.println(sqlQuery);

                ResultSet resultSet = statement.executeQuery(sqlQuery);
                while (resultSet.next()){
                    if (resultSet.getString("statusInfo").equals("Paid")){
                        soldSeatsList.add(resultSet.getString("bookedSeat"));
                    } else if (resultSet.getString("statusInfo").equals("Booked")){
                        bookedSeatsList.add(resultSet.getString("bookedSeat"));
                    }
                }

            } else {
                String sqlQuery = "select bookedSeat, statusInfo from Reservation join transactionInformation on transactionInformation.transactionId = Reservation.UTKNo where coachNo = '" + coachNo + "' and dateOfJourney = '"+ formatDate + "'";
                System.out.println(sqlQuery);

                ResultSet resultSet = statement.executeQuery(sqlQuery);
                while (resultSet.next()){
                    if (resultSet.getString("statusInfo").equals("Paid")){
                        soldSeatsList.add(resultSet.getString("bookedSeat"));
                    } else {
                        bookedSeatsList.add(resultSet.getString("bookedSeat"));
                    }
                }

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


        coachTypeSeatMapText.setText(coachType);
        coachNoSeatMapText.setText(coachNo);
        coachStartingSeatMapText.setText(from);
        DestinationSeatMapText.setText(to);
        seatMapDetailsFare.setText(fare);
        tripTypeSeatMapText.setText(tripType);
        reportingTimeSeatMapText.setText(reportingTime);
        departureTimeSeatMapText.setText(departureTime);
        proceedBtn.setDisable(true);

        Task<Void> task = new Task<>() {
            @Override
            public Void call() {

                scaleIt(200, fetchProg, 1, 2);

                AtomicReference<Character> c = new AtomicReference<>('A');

                Platform.runLater(() -> {

                    switch (coachType) {
                        case "NON-AC" -> {

                            Node[] seatMap = new Node[11];

                            try {

                                FXMLLoader frontRow = new FXMLLoader(getClass().getResource("/resources/driverAndDoor.fxml"));
                                seatMap[0] = frontRow.load();
                                seatMapVBox.getChildren().add(seatMap[0]);

                            } catch (IOException ignored) {

                            }

                            for (int i = 1; i < seatMap.length - 1; i++) {

                                try {

                                    FXMLLoader seatRow = new FXMLLoader(getClass().getResource("/resources/economyClassSeatRow.fxml"));
                                    seatMap[i] = seatRow.load();

                                    EconomyClassSeatRowController ecsrc = seatRow.getController();
                                    ecsrc.setRpc(rpc);
                                    ecsrc.setSeatText(c + "-" + 1, c + "-" + 2, c + "-" + 3, c + "-" + 4);
                                    ecsrc.soldSeats(soldSeatsList);
                                    ecsrc.bookedSeats(bookedSeatsList);

                                    seatMapVBox.getChildren().add(seatMap[i]);

                                    c.getAndSet((char) (c.get() + 1));
                                } catch (IOException ignored) {

                                }
                            }

                            try {

                                FXMLLoader seatRow = new FXMLLoader(getClass().getResource("/resources/economyClassSeatLastRow.fxml"));
                                seatMap[10] = seatRow.load();

                                EconomyClassSeatLastRowController ecslrc = seatRow.getController();
                                ecslrc.setRpc(rpc);
                                ecslrc.setSeatText(c + "-" + 1, c + "-" + 2, c + "-" + 3, c + "-" + 4, c + "-" + 5);
                                ecslrc.soldSeats(soldSeatsList);
                                ecslrc.bookedSeats(bookedSeatsList);

                                seatMapVBox.getChildren().add(seatMap[10]);

                            } catch (IOException ignored) {

                            }
                            break;
                        }
                        case "AC (Multi)" -> {

                            Node[] seatMap = new Node[11];

                            try {

                                FXMLLoader frontRow = new FXMLLoader(getClass().getResource("/resources/driverAndDoor.fxml"));
                                seatMap[0] = frontRow.load();
                                seatMapVBox.getChildren().add(seatMap[0]);

                            } catch (IOException ignored) {

                            }

                            for (int i = 1; i < seatMap.length - 1; i++) {

                                try {

                                    FXMLLoader seatRow = new FXMLLoader(getClass().getResource("/resources/businessClassSeatRow.fxml"));
                                    seatMap[i] = seatRow.load();

                                    BusinessClassSeatRowController bcsrc = seatRow.getController();
                                    bcsrc.setRpc(rpc);
                                    bcsrc.setSeatText(c + "-" + 1, c + "-" + 2, c + "-" + 3);
                                    bcsrc.soldSeats(soldSeatsList);
                                    bcsrc.bookedSeats(bookedSeatsList);

                                    seatMapVBox.getChildren().add(seatMap[i]);

                                    c.getAndSet((char) (c.get() + 1));
                                } catch (IOException ignored) {

                                }
                            }

                            try {

                                FXMLLoader seatRow = new FXMLLoader(getClass().getResource("/resources/businessClassSeatLastRow.fxml"));
                                seatMap[10] = seatRow.load();

                                BusinessClassSeatLastRowController bcslrc = seatRow.getController();
                                bcslrc.setRpc(rpc);
                                bcslrc.setSeatText(c + "-" + 1, c + "-" + 2, c + "-" + 3, c + "-" + 4);
                                bcslrc.soldSeats(soldSeatsList);
                                bcslrc.bookedSeats(bookedSeatsList);

                                seatMapVBox.getChildren().add(seatMap[10]);

                            } catch (IOException ignored) {

                            }
                            break;
                        }
                        case "AC (Bi)" -> {

                            Node[] seatMap = new Node[10];

                            try {

                                FXMLLoader frontRow = new FXMLLoader(getClass().getResource("/resources/driverAndDoor.fxml"));
                                seatMap[0] = frontRow.load();
                                seatMapVBox.getChildren().add(seatMap[0]);

                            } catch (IOException ignored) {

                            }

                            for (int i = 1; i < seatMap.length - 1; i++) {

                                try {

                                    FXMLLoader seatRow = new FXMLLoader(getClass().getResource("/resources/businessClassSeatRow.fxml"));
                                    seatMap[i] = seatRow.load();

                                    BusinessClassSeatRowController bcsrc = seatRow.getController();
                                    bcsrc.setRpc(rpc);
                                    bcsrc.setSeatText(c + "-" + 1, c + "-" + 2, c + "-" + 3);
                                    bcsrc.soldSeats(soldSeatsList);
                                    bcsrc.bookedSeats(bookedSeatsList);

                                    seatMapVBox.getChildren().add(seatMap[i]);

                                    c.getAndSet((char) (c.get() + 1));
                                } catch (IOException ignored) {

                                }
                            }

                            try {

                                FXMLLoader seatRow = new FXMLLoader(getClass().getResource("/resources/businessClassSeatLastRow.fxml"));
                                seatMap[9] = seatRow.load();

                                BusinessClassSeatLastRowController bcslrc = seatRow.getController();
                                bcslrc.setRpc(rpc);
                                bcslrc.setSeatText(c + "-" + 1, c + "-" + 2, c + "-" + 3, c + "-" + 4);
                                bcslrc.soldSeats(soldSeatsList);
                                bcslrc.bookedSeats(bookedSeatsList);

                                seatMapVBox.getChildren().add(seatMap[9]);

                                seatMapVBox.setLayoutY(44);

                            } catch (IOException ignored) {

                            }
                            break;
                        }
                        case "DD" -> {

                            radioDeckVBox.setVisible(true);

                            updateTheDeck("Lower Deck", c, "DD");

                            break;
                        }
                        case "Sleeper" -> {

                            radioDeckVBox.setVisible(true);

                            updateTheDeck("Lower Deck", c, "Sleeper");

                            break;
                        }
                    }

                });

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                scaleIt(200, fetchProg, 0, 2);

                return null;
            }
        };
        task.setOnSucceeded(e -> {

            ScaleTransition scalePane = new ScaleTransition(Duration.millis(300), dialogStack);
            scalePane.setToY(1);
            scalePane.play();

            scalePane.setOnFinished((ep) -> {

                translateIt(200, seatMapDetailsPane, 880, 1);
                translateIt(200, seatMapVBox, -404, 1);

            });

        });
        new Thread(task).start();

    }

    /**
     * this method calculates the total fare and updates the total fare table
     */
    public void updateSelectedSeatBox(String seatNo, boolean isSelected) {

        try {

            if (isSelected) {

                FXMLLoader seatRow = new FXMLLoader(getClass().getResource("/resources/selectedSeatRow.fxml"));
                selectedSeats[selectedSeatCount] = seatRow.load();
                selectedSeatsFXML.add(seatRow);

                SelectedSeatRowController ssrc = seatRow.getController();

                if (radioDeckVBox.isVisible()) {

                    if (radioLower.isSelected()) {

                        ssrc.setSeatText("L" + seatNo);
                        selectedSeatString.add("L" + seatNo);
                    } else {

                        ssrc.setSeatText("U" + seatNo);
                        selectedSeatString.add("U" + seatNo);
                    }
                } else {

                    ssrc.setSeatText(seatNo);
                    selectedSeatString.add(seatNo);
                }

                ssrc.setFareText(seatMapDetailsFare.getText());

                selectedSeatBox.getChildren().add(selectedSeats[selectedSeatCount]);

                String fareParSeat = seatMapDetailsFare.getText().substring(4);
                String totalFareCount = totalFare.getText().substring(4);

               total = Integer.parseInt(totalFareCount) + Integer.parseInt(fareParSeat);

                totalFare.setText("BDT " + total);

                selectedSeatCount++;
                proceedBtn.setDisable(selectedSeatCount == 0);
            } else {

                Platform.runLater(() -> {

                    for (int i = 0; i <= selectedSeatCount; i++) {

                        try {

                            FXMLLoader deleteSeatRow;

                            deleteSeatRow = selectedSeatsFXML.get(i);
                            SelectedSeatRowController ssrc = deleteSeatRow.getController();

                            if (ssrc.getSeatText().equals(seatNo) || (ssrc.getSeatText().equals("U" + seatNo) && radioUpper.isSelected()) || (ssrc.getSeatText().equals("L" + seatNo) && radioLower.isSelected())) {

                                String fareParSeat = seatMapDetailsFare.getText().substring(4);
                                String totalFareCount = totalFare.getText().substring(4);

                                int total = Integer.parseInt(totalFareCount) - Integer.parseInt(fareParSeat);

                                totalFare.setText("BDT " + total);

                                selectedSeatBox.getChildren().remove(i);
                                selectedSeatsFXML.remove(i);
                                selectedSeatCount--;
                                selectedSeatString.remove(i);
                                proceedBtn.setDisable(selectedSeatCount == 0);
                                break;
                            }

                        } catch (IndexOutOfBoundsException e) {

                            // error dialog box is generated here
                            BoxBlur blur = new BoxBlur(6, 6, 6);

                            FXMLLoader error = new FXMLLoader(getClass().getResource("/resources/infoDialog.fxml"));
                            try {

                                Region errorLoader = error.load();

                                InfoDialogController idc = error.getController();
                                idc.setDialogBody("Sorry, you cannot select more than 4 seats at a time.");

                                JFXDialog errorDialog = new JFXDialog(dialogStack, errorLoader, JFXDialog.DialogTransition.TOP);

                                idc.setDialog(errorDialog);
                                errorDialog.show();
                                errorDialog.setOnDialogClosed((JFXDialogEvent event) -> viewSeatPane.setEffect(null));
                                viewSeatPane.setEffect(blur);

                            } catch (IOException ignored) {

                            }

                        }

                    }
                });
            }

        } catch (IOException ignored) {

        }

    }

    /**
     * this method takes user back to coach info page when the button is clicked
     */
    public void onClickBackToCoachInfoButton() {

        selectedSeatCount = 0;
        lowerDeckDD.clear();
        upperDeckDD.clear();
        lowerDeckSleeper.clear();
        upperDeckSleeper.clear();
        selectedSeatsFXML.clear();
        selectedSeatString.clear();
        radioDeckVBox.setVisible(false);
        radioLower.setSelected(true);
        boardingPointComboBox.getSelectionModel().selectFirst();
        droppingPointComboBox.getSelectionModel().selectFirst();
        soldSeatsList.clear();
        bookedSeatsList.clear();
        bookedSeatsListDDUpper.clear();
        bookedSeatsListDDLower.clear();
        bookedSeatsListSleeperUpper.clear();
        bookedSeatsListSleeperLower.clear();
        soldSeatsListDDUpper.clear();
        soldSeatsListDDLower.clear();
        soldSeatsListSleeperLower.clear();
        soldSeatsListSleeperUpper.clear();
        coachInfoBox.getChildren().clear();

        translateIt(300, seatMapDetailsPane, 0, 1);

        TranslateTransition mapTransition = new TranslateTransition(Duration.millis(300), seatMapVBox);

        mapTransition.setToX(0);
        mapTransition.play();

        mapTransition.setOnFinished((e) -> {

            scaleIt(200, dialogStack, 0, 2);
            seatMapVBox.getChildren().clear();
            selectedSeatBox.getChildren().clear();
            seatMapVBox.setLayoutY(5);

            for (FXMLLoader update : coachInfoFXML) {

                CoachInfoController cic = update.getController();
                cic.buttonPressAvailable(false);
            }
        });


        if(ConfirmationPageController.goingToDhaka){

            try {
                ConnectorDB connectorDB = new ConnectorDB();
                Statement statement = connectorDB.getConnection().createStatement();
                System.out.println(finalSqlQuery1);
                ResultSet resultSet = statement.executeQuery(finalSqlQuery1);
                Node[] info = new Node[20];
                int i =0;
                while (resultSet.next()){
                    try {

                        FXMLLoader coachInfo = new FXMLLoader(getClass().getResource("/resources/coachInfo.fxml"));
                        info[i] = coachInfo.load();

                        coachInfoFXML.add(coachInfo);

                        CoachInfoController cic = coachInfo.getController();
                        cic.setRpc(rpc);

                        startingFrom =  resultSet.getString("startingFrom");
                        dest = resultSet.getString("destination");
                        coachNo = resultSet.getString("coachNo");
                        reportingTime = resultSet.getString("departureTime") ;

                        boarding = resultSet.getString("boardingPoint") + ", " + startingFrom;
                        departureTime = resultSet.getString("departureTime") + ", " + formatDate2;
                        cType = resultSet.getString("coachType");
                        int hour = Integer.parseInt(departureTime.substring(0,2));
                        int min = Integer.parseInt(departureTime.substring(3,5));
                        String amPM = reportingTime.substring(5,7);
                        if (min<15){
                            hour--;
                            min = 60 + min - 15;
                        } else {
                            min = min -15;
                        }
                        reportingTime = new DecimalFormat("00").format(hour) + ":" + new DecimalFormat("00").format(min) + amPM +", "+ formatDate2;


                        busFare = resultSet.getString("farePerSeat");
                        int availableSeats = 0;

                        try {
                            Statement statement1 = connectorDB.getConnection().createStatement();
                            String queryForAvailableSeats = "select * from Reservation join tripData on tripData.coachNo = Reservation.coachNo where tripData.coachNo = '"+ coachNo +"' and Reservation.dateOfJourney = '" + formatDate+ "'";
                            System.out.println(queryForAvailableSeats);
                            ResultSet resultSet1 = statement1.executeQuery(queryForAvailableSeats);
                            int ir=0;
                            while (resultSet1.next()){
                                ir++;
                            }
                            if ("NON-AC".equals(cType)) {
                                availableSeats = 41 - ir;
                                System.out.println(availableSeats);
                            } else if ("AC (Bi)".equals(cType)) {
                                availableSeats = 28 - ir;
                                System.out.println(availableSeats);
                            } else if ("AC (Multi)".equals(cType)) {
                                availableSeats = 31 - ir;
                                System.out.println(availableSeats);
                            } else if ("DD".equals(cType)) {
                                availableSeats = 43 - ir;
                                System.out.println(availableSeats);
                            } else {
                                availableSeats = 30 - ir;
                                System.out.println(availableSeats);
                            }


                        } catch (SQLException e) {
                            e.printStackTrace();
                        }



                        cic.updateInfo(startingFrom, dest, coachNo, reportingTime, boarding, departureTime, dest, cType, Integer.toString(availableSeats), busFare);

                        coachInfoBox.getChildren().add(info[i]);

                    } catch (IOException ignored) {
                    }

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else if (isPassengerReturn.equals("2")){
            total = 0;
            returnDate = dateOfReturn.getEditor().getText();
            Date date1 = null;
            try {
                date1 = simpleDateFormat.parse(returnDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            formatDate1 = targetFormat.format(date1);
            String formatDate3 = targetFormat2.format(date1);
            try {
                ConnectorDB connectorDB = new ConnectorDB();
                Statement statement = connectorDB.getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery(finalSqlQuery);
                Node[] info = new Node[20];
                int i =0;
                while (resultSet.next()){
                    try {

                        FXMLLoader coachInfo = new FXMLLoader(getClass().getResource("/resources/coachInfo.fxml"));
                        info[i] = coachInfo.load();

                        coachInfoFXML.add(coachInfo);

                        CoachInfoController cic = coachInfo.getController();
                        cic.setRpc(rpc);


                        startingFrom =  resultSet.getString("startingFrom");
                        dest = resultSet.getString("destination");
                        coachNo = resultSet.getString("coachNo");
                        reportingTime = resultSet.getString("departureTime") ;

                        boarding = resultSet.getString("boardingPoint") + ", " + startingFrom;
                        departureTime = resultSet.getString("departureTime") + ", " + formatDate3;
                        cType = resultSet.getString("coachType");
                        int hour = Integer.parseInt(departureTime.substring(0,2));
                        int min = Integer.parseInt(departureTime.substring(3,5));
                        String amPM = reportingTime.substring(5,7);
                        System.out.println(hour+", "+min);
                        if (min<15){
                            hour--;
                            min = 60 + min - 15;
                        } else {
                            min = min -15;
                        }
                        reportingTime = new DecimalFormat("00").format(hour) + ":" + new DecimalFormat("00").format(min) + amPM +", "+ formatDate3;
                        busFare = resultSet.getString("farePerSeat");
                        int availableSeats = 0;

                        try {
                            Statement statement1 = connectorDB.getConnection().createStatement();
                            String queryForAvailableSeats = "select * from Reservation join tripData on tripData.coachNo = Reservation.coachNo where tripData.coachNo = '"+ coachNo +"' and Reservation.dateOfReturn = '" + formatDate1+ "'";
                            System.out.println(queryForAvailableSeats);
                            ResultSet resultSet1 = statement1.executeQuery(queryForAvailableSeats);
                            int ir=0;
                            while (resultSet1.next()){
                                ir++;
                            }
                            if ("NON-AC".equals(cType)) {
                                availableSeats = 41 - ir;
                                System.out.println(availableSeats);
                            } else if ("AC (Bi)".equals(cType)) {
                                availableSeats = 28 - ir;
                                System.out.println(availableSeats);
                            } else if ("AC (Multi)".equals(cType)) {
                                availableSeats = 31 - ir;
                                System.out.println(availableSeats);
                            } else if ("DD".equals(cType)) {
                                availableSeats = 43 - ir;
                                System.out.println(availableSeats);
                            } else {
                                availableSeats = 30 - ir;
                                System.out.println(availableSeats);
                            }


                        } catch (SQLException e) {
                            e.printStackTrace();
                        }


                        reportingTime = new DecimalFormat("00").format(hour) + ":" + new DecimalFormat("00").format(min) + amPM +", "+ formatDate3;
                        departureTime = resultSet.getString("departureTime") + ", " + formatDate3;

                        String temp = startingFrom;
                        startingFrom = dest;
                        dest = temp;
                        coachNo +=  "-R";


                        cic.updateInfo(startingFrom, dest, coachNo, reportingTime, startingFrom, departureTime, dest, cType, Integer.toString(availableSeats), busFare);
                        //cic.updateInfo(dest, startingFrom, coachNo + "-R", reportingTime, dest, departureTime, startingFrom, cType, Integer.toString(availableSeats), busFare);

                        coachInfoBox.getChildren().add(info[i]);

                    } catch (IOException ignored) {
                    }

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * this method changes the deck in the seat map
     */
    public void updateTheDeck(String deckType, AtomicReference<Character> c, String coachType) {

        if (deckType.equals("Lower Deck")) {

            seatMapVBox.getChildren().clear();

            Node[] seatMap;

            if (coachType.equals("Sleeper")) {

                seatMap = new Node[6];

                if (lowerDeckSleeper.size() != 0) {

                    for (int i = 0; i < lowerDeckSleeper.size(); i++) {

                        seatMap[i] = lowerDeckSleeper.get(i);
                        seatMapVBox.getChildren().add(seatMap[i]);
                    }
                } else {

                    try {

                        FXMLLoader frontRow = new FXMLLoader(getClass().getResource("/resources/driverAndDoor.fxml"));
                        seatMap[0] = frontRow.load();
                        lowerDeckSleeper.add(seatMap[0]);
                        seatMapVBox.getChildren().add(seatMap[0]);

                    } catch (IOException ignored) {

                    }

                    for (int i = 1; i < seatMap.length; i++) {

                        try {

                            FXMLLoader seatRow = new FXMLLoader(getClass().getResource("/resources/sleeperSeatRow.fxml"));
                            seatMap[i] = seatRow.load();
                            lowerDeckSleeper.add(seatMap[i]);

                            SleeperSeatRowController ssrc = seatRow.getController();
                            ssrc.setRpc(rpc);
                            ssrc.setSeatText(c + "-" + 1, c + "-" + 2, c + "-" + 3);
                            ssrc.soldSeats(soldSeatsListSleeperLower);
                            ssrc.bookedSeats(bookedSeatsListSleeperLower);

                            seatMapVBox.getChildren().add(seatMap[i]);

                            c.getAndSet((char) (c.get() + 1));
                        } catch (IOException ignored) {

                        }
                    }

                }

                seatMapVBox.setLayoutY(42.5);

            } else {

                seatMap = new Node[8];

                if (lowerDeckDD.size() != 0) {

                    for (int i = 0; i < lowerDeckDD.size(); i++) {

                        seatMap[i] = lowerDeckDD.get(i);
                        seatMapVBox.getChildren().add(seatMap[i]);
                    }
                } else {

                    try {

                        FXMLLoader frontRow = new FXMLLoader(getClass().getResource("/resources/driverAndDoor.fxml"));
                        seatMap[0] = frontRow.load();
                        lowerDeckDD.add(seatMap[0]);
                        seatMapVBox.getChildren().add(seatMap[0]);

                    } catch (IOException ignored) {

                    }

                    for (int i = 1; i < 3; i++) {

                        try {

                            FXMLLoader blankRow = new FXMLLoader(getClass().getResource("/resources/blankSeatRow.fxml"));
                            seatMap[i] = blankRow.load();
                            lowerDeckDD.add(seatMap[i]);
                            seatMapVBox.getChildren().add(seatMap[i]);

                        } catch (IOException ignored) {

                        }
                    }

                    try {

                        FXMLLoader seatRow = new FXMLLoader(getClass().getResource("/resources/firstRowDD.fxml"));
                        seatMap[3] = seatRow.load();
                        lowerDeckDD.add(seatMap[3]);

                        FirstRowDDController bcsrc = seatRow.getController();
                        bcsrc.setRpc(rpc);
                        bcsrc.setSeatText(c + "-" + 1, c + "-" + 2);
                        bcsrc.soldSeats(soldSeatsListDDLower);
                        bcsrc.bookedSeats(bookedSeatsListDDLower);

                        seatMapVBox.getChildren().add(seatMap[3]);

                        c.getAndSet((char) (c.get() + 1));
                    } catch (IOException ignored) {

                    }


                    for (int i = 4; i < seatMap.length; i++) {

                        try {

                            FXMLLoader seatRow = new FXMLLoader(getClass().getResource("/resources/businessClassSeatRow.fxml"));
                            seatMap[i] = seatRow.load();
                            lowerDeckDD.add(seatMap[i]);

                            BusinessClassSeatRowController bcsrc = seatRow.getController();
                            bcsrc.setRpc(rpc);
                            bcsrc.setSeatText(c + "-" + 1, c + "-" + 2, c + "-" + 3);
                            bcsrc.soldSeats(soldSeatsListDDLower);
                            bcsrc.bookedSeats(bookedSeatsListDDLower);

                            seatMapVBox.getChildren().add(seatMap[i]);

                            c.getAndSet((char) (c.get() + 1));
                        } catch (IOException ignored) {

                        }
                    }
                }

                seatMapVBox.setLayoutY(77.5);
            }

        } else {

            seatMapVBox.getChildren().clear();

            Node[] seatMap;

            if (coachType.equals("Sleeper")) {

                seatMap = new Node[6];

                if (upperDeckSleeper.size() != 0) {

                    for (int i = 0; i < upperDeckSleeper.size(); i++) {

                        seatMap[i] = upperDeckSleeper.get(i);
                        seatMapVBox.getChildren().add(seatMap[i]);
                    }
                } else {

                    try {

                        FXMLLoader blankRow = new FXMLLoader(getClass().getResource("/resources/blankSeatRow.fxml"));
                        seatMap[0] = blankRow.load();
                        upperDeckSleeper.add(seatMap[0]);
                        seatMapVBox.getChildren().add(seatMap[0]);

                    } catch (IOException ignored) {

                    }

                    for (int i = 1; i < seatMap.length; i++) {

                        try {

                            FXMLLoader seatRow = new FXMLLoader(getClass().getResource("/resources/sleeperSeatRow.fxml"));
                            seatMap[i] = seatRow.load();
                            upperDeckSleeper.add(seatMap[i]);

                            SleeperSeatRowController ssrc = seatRow.getController();
                            ssrc.setRpc(rpc);
                            ssrc.setSeatText(c + "-" + 1, c + "-" + 2, c + "-" + 3);
                            ssrc.soldSeats(soldSeatsListSleeperUpper);
                            ssrc.bookedSeats(bookedSeatsListSleeperUpper);

                            seatMapVBox.getChildren().add(seatMap[i]);

                            c.getAndSet((char) (c.get() + 1));
                        } catch (IOException ignored) {

                        }
                    }
                }

                seatMapVBox.setLayoutY(42.5);

            } else {

                seatMap = new Node[10];

                if (upperDeckDD.size() != 0) {

                    for (int i = 0; i < upperDeckDD.size(); i++) {

                        seatMap[i] = upperDeckDD.get(i);
                        seatMapVBox.getChildren().add(seatMap[i]);
                    }
                } else {

                    for (int i = 0; i < 2; i++) {

                        try {

                            FXMLLoader seatRow = new FXMLLoader(getClass().getResource("/resources/firstRowDD.fxml"));
                            seatMap[i] = seatRow.load();
                            upperDeckDD.add(seatMap[i]);

                            FirstRowDDController bcsrc = seatRow.getController();
                            bcsrc.setRpc(rpc);
                            bcsrc.setSeatText(c + "-" + 1, c + "-" + 2);
                            bcsrc.soldSeats(soldSeatsListDDUpper);
                            bcsrc.bookedSeats(bookedSeatsListDDUpper);

                            seatMapVBox.getChildren().add(seatMap[i]);

                            c.getAndSet((char) (c.get() + 1));
                        } catch (IOException ignored) {

                        }
                    }

                    for (int i = 2; i < seatMap.length - 1; i++) {

                        try {

                            FXMLLoader seatRow = new FXMLLoader(getClass().getResource("/resources/businessClassSeatRow.fxml"));
                            seatMap[i] = seatRow.load();
                            upperDeckDD.add(seatMap[i]);

                            BusinessClassSeatRowController bcsrc = seatRow.getController();
                            bcsrc.setRpc(rpc);
                            bcsrc.setSeatText(c + "-" + 1, c + "-" + 2, c + "-" + 3);
                            bcsrc.soldSeats(soldSeatsListDDUpper);
                            bcsrc.bookedSeats(bookedSeatsListDDUpper);

                            seatMapVBox.getChildren().add(seatMap[i]);

                            c.getAndSet((char) (c.get() + 1));
                        } catch (IOException ignored) {

                        }
                    }

                    try {

                        FXMLLoader seatRow = new FXMLLoader(getClass().getResource("/resources/businessClassSeatLastRow.fxml"));
                        seatMap[9] = seatRow.load();
                        upperDeckDD.add(seatMap[9]);

                        BusinessClassSeatLastRowController bcslrc = seatRow.getController();
                        bcslrc.setRpc(rpc);
                        bcslrc.setSeatText(c + "-" + 1, c + "-" + 2, c + "-" + 3, c + "-" + 4);
                        bcslrc.soldSeats(soldSeatsListDDUpper);
                        bcslrc.bookedSeats(bookedSeatsListDDUpper);

                        seatMapVBox.getChildren().add(seatMap[9]);

                    } catch (IOException ignored) {

                    }
                }

                seatMapVBox.setLayoutY(62.5);
            }


        }
    }

    /**
     * this method is called when the proceed button is clicked
     */
    public void onClickProceedButton(javafx.event.Event actionEvent) {

        if (boardingPointComboBox.getSelectionModel().isSelected(0)
                || droppingPointComboBox.getSelectionModel().isSelected(0)) {

            if (boardingPointComboBox.getSelectionModel().isSelected(0)) {

                boardingPointComboBox.getStyleClass().add("combo-box-base-error");
            }

            if (droppingPointComboBox.getSelectionModel().isSelected(0)) {

                droppingPointComboBox.getStyleClass().add("combo-box-base-error");
            }

            // error dialog box is generated here
            BoxBlur blur = new BoxBlur(6, 6, 6);

            FXMLLoader error = new FXMLLoader(getClass().getResource("/resources/infoDialog.fxml"));
            try {

                Region errorLoader = error.load();

                InfoDialogController idc = error.getController();
                idc.setDialogBody("Please, select a boarding and a dropping point.");

                JFXDialog errorDialog = new JFXDialog(dialogStack, errorLoader, JFXDialog.DialogTransition.TOP);

                idc.setDialog(errorDialog);
                errorDialog.show();
                errorDialog.setOnDialogClosed((JFXDialogEvent event) -> viewSeatPane.setEffect(null));
                viewSeatPane.setEffect(blur);

            } catch (IOException ignored) {

            }

        } else {

            try {

                FXMLLoader confirmationPage = new FXMLLoader(getClass().getResource("/resources/confirmationPage.fxml"));
                Parent page = confirmationPage.load();
                Scene pageScene = new Scene(page);

                ConfirmationPageController cpc = confirmationPage.getController();

                cpc.setRpc(rpc);

                StringBuilder seat = null;

                for (int i = 0; i < selectedSeatString.size(); i++) {
                    if (i == 0) {
                        seat = new StringBuilder(selectedSeatString.get(i));
                    } else {
                        seat.append(", ").append(selectedSeatString.get(i));
                    }
                }




                assert seat != null;
                UserData userData = new UserData();

                cpc.updateTripData(userData.getUserLastName(), userData.getUserGender(), coachNoSeatMapText.getText(), reportingTimeSeatMapText.getText(), coachStartingSeatMapText.getText(), departureTimeSeatMapText.getText(), dest, coachTypeSeatMapText.getText(), seat.toString(), totalFare.getText());
                totalFare.setText("BDT 0");
                total = 0;
                Stage window = new Stage();
                window.initStyle(StageStyle.UNDECORATED);
                window.initOwner(proceedBtn.getScene().getWindow());
                window.setScene(pageScene);
                window.showAndWait();

            } catch (IOException ignored) {

            }
        }

    }

    /**
     * this method change the color of boarding combo box if it left empty and clicked again
     */
    public void onClickBoardingPointComboBox() {

        boardingPointComboBox.getStyleClass().remove("combo-box-base-error");
    }

    /**
     * this method change the color of dropping combobox if it left empty and clicked again
     */
    public void onClickDroppingPointComboBox() {

        droppingPointComboBox.getStyleClass().remove("combo-box-base-error");
    }

    /**
     * initializes the scene
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        checkBoxNonAC.selectedProperty().addListener((o, oldValue, newValue) -> {

            isNonAcSelected = checkBoxNonAC.isSelected();
        });

        checkBoxBi.selectedProperty().addListener((o, oldValue, newValue) -> {
            isAcBiSelected = checkBoxBi.isSelected();
        });

        checkBoxMulti.selectedProperty().addListener((o, oldValue, newValue) -> {
            isACMultiSelected = checkBoxMulti.isSelected();
        });

        checkBoxDD.selectedProperty().addListener((o, oldValue, newValue) -> {
            isDDSelected = checkBoxDD.isSelected();
        });

        checkBoxSleeper.selectedProperty().addListener((o, oldValue, newValue) -> {
            isSleeperSelected = checkBoxSleeper.isSelected();
        });


        boardingPointComboBox.getItems().addAll(boardingPoints);
        droppingPointComboBox.getItems().addAll(droppingPoints);

        boardingPointComboBox.getSelectionModel().selectFirst();
        droppingPointComboBox.getSelectionModel().selectFirst();

        selectedSeatCount = 0;

        proceedBtn.setDisable(true);

        //this toggle group is to toggle the deck radio buttons

        ToggleGroup deckGroup = new ToggleGroup();

        radioUpper.setToggleGroup(deckGroup);
        radioLower.setToggleGroup(deckGroup);

        radioLower.setSelected(true);

        deckGroup.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (deckGroup.getSelectedToggle() != null) {

                if (radioLower.isSelected()) {

                    updateTheDeck("Lower Deck", new AtomicReference<>('A'), coachTypeSeatMapText.getText());
                }

                if (radioUpper.isSelected()) {

                    updateTheDeck("Upper Deck", new AtomicReference<>('A'), coachTypeSeatMapText.getText());
                }
            }
        });

        fromComboBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> fromComboBox.setStyle("-fx-border-color: #007EfC"));

        toComboBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> toComboBox.setStyle("-fx-border-color: #007EfC"));

        radioDeckVBox.setVisible(false);

        translateIt(500, reservation, -343, 1);
        translateIt(500, page, -199, 1);
        translateIt(500, revPane, 1300, 1);
        translateIt(500, homeButton, 165, 1);

        destination = new ArrayList<>(Arrays.asList(destinations));

        fromComboBox.getItems().addAll(destination);
        toComboBox.getItems().addAll(destination);
        timePeriodComboBox.getItems().addAll(timePeriod);

        timePeriodComboBox.getSelectionModel().selectFirst();

        dateOfJourney.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0);
            }
        });

        dateOfReturn.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0);
            }
        });

        new AutoCompleteComboBoxListener<>(fromComboBox);
        new AutoCompleteComboBoxListener<>(toComboBox);

        toComboBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {

            String tempFrom = fromComboBox.getEditor().getText();
            fromComboBox.getItems().clear();
            fromComboBox.getEditor().setText(tempFrom);
            fromComboBox.getItems().addAll(destination);
            new AutoCompleteComboBoxListener<>(fromComboBox);

            if (!isToTrimDone) {

                String d = null;

                for (String s : destination) {

                    if (fromComboBox.getEditor().getText().equals(s)) {

                        d = s;
                        break;
                    }
                }

                destination.remove(d);
                toComboBox.getItems().clear();
                toComboBox.getItems().addAll(destination);
                new AutoCompleteComboBoxListener<>(toComboBox);
                destination = new ArrayList<>(Arrays.asList(destinations));
                isToTrimDone = true;
                isFromTrimDone = false;
            }

        });

        fromComboBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {

            String tempTo = toComboBox.getEditor().getText();
            toComboBox.getItems().clear();
            toComboBox.getEditor().setText(tempTo);
            toComboBox.getItems().addAll(destination);
            new AutoCompleteComboBoxListener<>(toComboBox);

            if (!isFromTrimDone) {

                System.out.println();

                String d = null;

                for (String s : destination) {

                    if (toComboBox.getEditor().getText().equals(s)) {

                        d = s;
                        break;
                    }
                }

                destination.remove(d);
                fromComboBox.getItems().clear();
                fromComboBox.getItems().addAll(destination);
                new AutoCompleteComboBoxListener<>(fromComboBox);
                destination = new ArrayList<>(Arrays.asList(destinations));
                isFromTrimDone = true;
                isToTrimDone = false;
            }

        });
    }

    public void translateIt(double duration, Node node, double translateTo, int type) {

        TranslateTransition transition = new TranslateTransition(Duration.millis(duration), node);

        if (type == 1) {

            transition.setToX(translateTo);
        } else if (type == 2) {

            transition.setToY(translateTo);
        }

        transition.play();

    }

    public void scaleIt(double duration, Node node, double scaleTo, int type) {

        ScaleTransition transition = new ScaleTransition(Duration.millis(duration), node);

        if (type == 1) {

            transition.setToX(scaleTo);
        } else if (type == 2) {

            transition.setToY(scaleTo);
        } else if (type == 3) {

            transition.setToX(scaleTo);
            transition.setToY(scaleTo);
        }

        transition.play();
    }

    public boolean isFourSeatsSelected() {

        return selectedSeatCount != 4;
    }

}
