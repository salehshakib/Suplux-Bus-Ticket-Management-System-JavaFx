package controllers;

import com.gluonhq.charm.glisten.control.ProgressBar;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.codejava.sql.ConnectorDB;

import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class CancellationPageController implements Initializable {

    /**
     * variable initialization
     */
    @FXML
    public Button crossButton, homeBtn, searchBtn, cancelTicketBtn;
    public Pane logOutBtn, transBtn, reserveBtn;
    public Label logOutInfo, tranLogInfo, cancelInfo, reserveInfo, searchErrorMsg, cancelWarningMsg;
    public Text cancellation, page, UTKNumber, tripDate, coachNumber, boardingPoint, reportingTime, seats, reservationStatus, totalFare, coachType, destination, departureTime;
    public TextField UTKSearchField;
    public HBox ticketDetails;
    public ProgressBar fetchProg;
    public boolean eligible = false;

    //todo my variables
    public String bookedSeat = "";
    //public String utkNo,  status,  bookedSeats, journeyDate, cNo, cType, busFare, boarding, dest, reportTime, departTime;
    public int i = 0;

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

            Parent homePage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resources/home.fxml")));
            Scene homePageScene = new Scene(homePage);

            Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(homePageScene);
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

    /**
     * this method is used to search the ticket details in the database
     */
    public void onClickSearchButton(){
        bookedSeat = "";

        //this task object is letting us get the time for fetching the data from database
        Task<Void> task = new Task<>() {
            @Override
            public Void call() {


                if(cancelWarningMsg.getScaleY() == 1){

                    scaleIt(300, cancelWarningMsg, 0, 2);
                }

                scaleIt(200, fetchProg, 1, 2);

                //TODO search the database for ticket details here
                try {
                    i=0;
                    ConnectorDB connectorDB = new ConnectorDB();
                    String sqlQuery = "Select * from Reservation where UTKNo = '"+ UTKSearchField.getText() + "' ";
                    Statement statement = connectorDB.getConnection().createStatement();
                    ResultSet resultSet = statement.executeQuery(sqlQuery);

                    while (resultSet.next()){
                        i++;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

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

            if(i != 0){
                ScaleTransition detailsTransition = new ScaleTransition(Duration.millis(300), ticketDetails);

                try {
                    ConnectorDB connectorDB = new ConnectorDB();
                    String sqlQuery = "select * from Reservation join tripData on tripData.coachNo = Reservation.coachNo join transactionInformation on Reservation.UTKNo = transactionInformation.transactionId where UTKNo = '"+ UTKSearchField.getText() + "' ";
                    Statement statement = connectorDB.getConnection().createStatement();
                    ResultSet resultSet = statement.executeQuery(sqlQuery);
                    while (resultSet.next()){
                        UTKNumber.setText(resultSet.getString("UTKNo"));
                        //System.out.println(UTKNumber.getText());
                        reservationStatus.setText(resultSet.getString("statusInfo"));
                        //System.out.println(reservationStatus.getText());
                        if (UTKNumber.getText().contains("R")){
                            tripDate.setText(resultSet.getString("dateOfReturn"));
                            boardingPoint.setText(resultSet.getString("destination") );
                            destination.setText(resultSet.getString("startingFrom"));
                        } else {
                            tripDate.setText(resultSet.getString("dateOfJourney"));
                            boardingPoint.setText(resultSet.getString("boardingPoint") + ", "+ resultSet.getString("startingFrom") );
                            destination.setText(resultSet.getString("destination"));
                        }
                        departureTime.setText(resultSet.getString("departureTime"));
                        coachNumber.setText(resultSet.getString("coachNo"));
                        coachType.setText(resultSet.getString("coachType"));
                        bookedSeat += resultSet.getString("bookedSeat") + "  ";
                        totalFare.setText(resultSet.getString("farePerSeat"));


                    }

                    seats.setText(bookedSeat);

                    int hour = Integer.parseInt(departureTime.getText().substring(0,2));
                    int min = Integer.parseInt(departureTime.getText().substring(3,5));
                    String amPM = departureTime.getText().substring(5,7);
                    if (min<15){
                        hour--;
                        min = 60 + min - 15;
                    } else {
                        min = min -15;
                    }
                    reportingTime.setText(new DecimalFormat("00").format(hour) + ":" + new DecimalFormat("00").format(min) + amPM);


                    //System.out.println(seats.getText());

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }


                if(ticketDetails.getScaleX() == 1){

                    detailsTransition.setFromY(0);
                    detailsTransition.setFromX(0);

                }

                detailsTransition.setToX(1);
               detailsTransition.setToY(1);
               detailsTransition.play();
            } else{

                ScaleTransition errorTransition = new ScaleTransition(Duration.millis(200), searchErrorMsg);

                if(searchErrorMsg.getScaleX() == 1){

                    errorTransition.setFromY(0);
                    errorTransition.setFromX(0);

                }

                errorTransition.setToY(1);
                errorTransition.setToX(1);
                errorTransition.play();
            }

        });
        new Thread(task).start();

    }

    /**
     * this method will erase the error message when a key is pressed in the text field
     */
    public void onKeyPressedSearchField(){


        if(searchErrorMsg.getScaleX() == 1){

            scaleIt(200, searchErrorMsg, 0, 3);
        } else if(ticketDetails.getScaleX() == 1){

            if(cancelWarningMsg.getScaleY() == 1){

                scaleIt(300, cancelWarningMsg, 0, 2);
            }

            scaleIt(300, ticketDetails, 0, 3);
        }
    }

    /**
     * this method will execute the cancellation process when cancel ticket button is clicked
     */
    public void onClickCancelTicketButton(){

        //this task object is letting us get the time for fetching the data from database
        Task<Void> task = new Task<>() {
            @Override
            public Void call() throws ParseException {

                scaleIt(200, fetchProg, 1, 2);

                //TODO here the database must be checked if the ticket is eligible for cancellation or not
                LocalDateTime myDateObj = LocalDateTime.now();
                DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyyMMdd");

                DateFormat givenFormat = new SimpleDateFormat("yyyy-MM-dd");
                DateFormat targetFormat = new SimpleDateFormat("yyyyMMdd");

                Date date = givenFormat.parse(tripDate.getText());
                String tripDate = targetFormat.format(date);

                String todayDate = myDateObj.format(myFormatObj);
                System.out.println(todayDate);
                System.out.println(tripDate);

                if (Integer.parseInt(todayDate) < Integer.parseInt(tripDate)-1 ){
                    System.out.println("cancelable");
                    eligible = true;
                    try {
                        ConnectorDB connectorDB = new ConnectorDB();

                        String sqlQuery1 = "Update transactionInformation set statusInfo = 'Cancelled' where transactionId = '"+ UTKNumber.getText() + "' ";
                        System.out.println(sqlQuery1);

                        Statement statement;

                        statement = connectorDB.getConnection().createStatement();
                        statement.execute(sqlQuery1);


                        String sqlQuery = "Delete from Reservation where UTKNo =  '" + UTKNumber.getText()+ "' ";
                        Statement statement1;

                        statement1 = connectorDB.getConnection().createStatement();
                        statement1.execute(sqlQuery);


                        //resultSet = statement.executeQuery(sqlQuery);


//                    PreparedStatement preparedStatement = connectorDB.getConnection().prepareStatement(sqlQuery);
//                    preparedStatement.setString(1, UTKNumber.getText());
//                    preparedStatement.executeUpdate();


                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                } else {
                    System.out.println("NOT ELIGIBLE");
                }







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

            if(eligible){


                //todo show dialouge box  and take user home


            } else {
                ScaleTransition warningTransition = new ScaleTransition(Duration.millis(200), cancelWarningMsg);

                if(cancelWarningMsg.getScaleY() == 1){

                    warningTransition.setFromY(0);

                }
                warningTransition.setToY(1);
                warningTransition.play();
            }

        });
        new Thread(task).start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        translateIt(500, cancellation, -384, 1);
        translateIt(500, page, -199, 1);
        translateIt(500, homeBtn, 175, 1);
        translateIt(500, UTKSearchField, 820, 1);
        translateIt(500, searchBtn, 695, 1);

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
