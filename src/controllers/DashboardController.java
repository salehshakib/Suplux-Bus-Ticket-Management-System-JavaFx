package controllers;

import com.gluonhq.charm.glisten.control.ProgressBar;
import com.gluonhq.charm.glisten.control.ProgressIndicator;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import miscellaneous.java.UserData;
import net.codejava.sql.ConnectorDB;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    /**
     * variable initialization
     */
    @FXML
    public Button crossButton;

    public Pane rootPane, logOutBtn, transBtn, cancelBtn, reserveBtn, reservePane, cancelPane, firstNamePane, lastNamePane, updateFirstNameBtn, updateLastNameBtn, updateGenderBtn, updatePhoneNoBtn, updateNIDBtn, updateBirthRegBtn;
    public Label logOutInfo, tranLogInfo, cancelInfo, reserveInfo, reserveLabel, cancelLabel, tripLabel, firstNameLabel, lastNameLabel, emailLabel, genderLabel, phoneNoLabel, NIDLabel, birthRegLabel;
    public ProgressIndicator reserveProg, cancelProg;
    public ScrollPane dashScroll;
    public StackPane rootStack;
    public Text passenger, dashboard, userName;
    public Circle innerCircle;
    public ProgressBar fetchProg;





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
     * this method updates the first name section
     */
    public void onClickUpdateFirstNameBtn(){

        updateUserData("Update First Name", "Enter a first name", "First Name");
        UpdateUserInfoDialogController.checkData(firstNameLabel.getText());


    }

    /**
     * this method updates the last name section
     */
    public void onClickUpdateLastNameBtn(){

        updateUserData("Update Last Name", "Enter a last name", "Last Name");
        UpdateUserInfoDialogController.checkData(lastNameLabel.getText());
    }

    /**
     * this method updates the gender section
     */
    public void onClickUpdateGenderBtn(){

        UpdateGenderDialogController.checkData(genderLabel.getText());
        updateUserData("Update Gender", "Enter a gender", "Gender");
    }

    /**
     * this method updates the phone number section
     */
    public void onClickUpdatePhoneNoBtn(){

        updateUserData("Update Phone Number", "Enter a phone number", "Phone No");
        UpdateUserInfoDialogController.checkData(phoneNoLabel.getText());
    }

    /**
     * this method updates the NID number section
     */
    public void onClickUpdateNIDBtn(){

        updateUserData("Update NID Number", "Enter a NID Number", "NID No");
        UpdateUserInfoDialogController.checkData(NIDLabel.getText());
    }

    /**
     * this method updates the birth registration number section
     */
    public void onClickUpdateBirthRegBtn(){

        updateUserData("Update Birth Reg No", "Enter a birth reg number", "Birth Reg No");
        UpdateUserInfoDialogController.checkData(birthRegLabel.getText());
    }

    /**
     * this method updates the user data onto the database section
     */
    private void updateUserData(String title, String promptText, String field){

        BoxBlur blur = new BoxBlur(6, 6, 6);

        if(field.equals("Gender")){

            try {

                FXMLLoader updateGender = new FXMLLoader(getClass().getResource("/resources/updateGenderDialog.fxml"));
                Region updateGenderLoader = updateGender.load();

                UpdateGenderDialogController ugidc = updateGender.getController();

                JFXDialog updateGenderDialog = new JFXDialog(rootStack, updateGenderLoader, JFXDialog.DialogTransition.TOP);

                ugidc.setDialog(updateGenderDialog);

                updateGenderDialog.show();
                updateGenderDialog.setOnDialogClosed((JFXDialogEvent eve) -> {

                    rootPane.setEffect(null);

                    //this task object is letting us get the time to push the data to the database
                    Task<Void> task = new Task<>() {
                        @Override
                        public Void call() {

                            if(ugidc.isConfirmButtonPressed()){

                                scaleIt(200, fetchProg, 1, 2);

                                //here update the database with new gender data here...
                                UserData userData = new UserData();
                                try {
                                    ConnectorDB connectorDB = new ConnectorDB();
                                    String sqlQuery;
                                    Statement statement;
                                    sqlQuery = "Update userInformation Set userGender = '" + ugidc.getData() +"'"+ "where userEmail = '" + userData.getUserEmail() + "'";
                                    statement = connectorDB.getConnection().createStatement();
                                    statement.execute(sqlQuery);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }

                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                scaleIt(200, fetchProg, 0, 2);
                            }

                            return null;
                        }
                    };
                    task.setOnSucceeded(e -> {

                        if(ugidc.isConfirmButtonPressed()){

                            successDialog(ugidc.getData(), field);
                        }

                    });
                    new Thread(task).start();

                });
                rootPane.setEffect(blur);

            } catch (IOException ignored) {

            }
        } else{

            FXMLLoader updateInfo = new FXMLLoader(getClass().getResource("/resources/updateUserInfoDialog.fxml"));

            try {

                Region updateInfoLoaderLoader = updateInfo.load();

                UpdateUserInfoDialogController uuidc = updateInfo.getController();

                JFXDialog updateDialog = new JFXDialog(rootStack, updateInfoLoaderLoader, JFXDialog.DialogTransition.TOP);

                uuidc.setDialog(updateDialog);
                uuidc.setDialogTitle(title);
                uuidc.setTextFieldPromptText(promptText);
                updateDialog.show();
                updateDialog.setOnDialogClosed((JFXDialogEvent event) -> {

                    rootPane.setEffect(null);

                    //this task object is letting us get the time to push the data to the database
                    Task<Void> task = new Task<>() {
                        @Override
                        public Void call() {

                            if(uuidc.isConfirmButtonPressed()){

                                scaleIt(200, fetchProg, 1, 2);

                                //here update the database with new user data here...
                                UserData userData = new UserData();
                                try {
                                    ConnectorDB connectorDB = new ConnectorDB();
                                    String sqlQuery;
                                    Statement statement;
                                    switch (field) {
                                        case "First Name" -> {
                                            sqlQuery = "Update userInformation Set userFirstName = '" + uuidc.getData() + "'" + "where userEmail = '" + userData.getUserEmail() + "'";
                                            statement = connectorDB.getConnection().createStatement();
                                            statement.execute(sqlQuery);
                                        }
                                        case "Last Name" -> {
                                            sqlQuery = "Update userInformation Set userLastName = '" + uuidc.getData() + "'" + "where userEmail = '" + userData.getUserEmail() + "'";
                                            statement = connectorDB.getConnection().createStatement();
                                            statement.execute(sqlQuery);
                                        }
                                        case "Phone No" -> {
                                            sqlQuery = "Update userInformation Set userPhoneNumber = '" + uuidc.getData() + "'" + "where userEmail = '" + userData.getUserEmail() + "'";
                                            statement = connectorDB.getConnection().createStatement();
                                            statement.execute(sqlQuery);
                                        }
                                        case "NID No" -> {
                                            sqlQuery = "Update userInformation Set userNID = '" + uuidc.getData() + "'" + "where userEmail = '" + userData.getUserEmail() + "'";
                                            statement = connectorDB.getConnection().createStatement();
                                            statement.execute(sqlQuery);
                                        }
                                        case "Birth Reg No" -> {
                                            sqlQuery = "Update userInformation Set userBReg = '" + uuidc.getData() + "'" + "where userEmail = '" + userData.getUserEmail() + "'";
                                            statement = connectorDB.getConnection().createStatement();
                                            statement.execute(sqlQuery);
                                        }
                                        case "Passport No" -> {
                                            sqlQuery = "Update userInformation Set userPassport = '" + uuidc.getData() + "'" + "where userEmail = '" + userData.getUserEmail() + "'";
                                            statement = connectorDB.getConnection().createStatement();
                                            statement.execute(sqlQuery);
                                        }
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
                            }

                            return null;
                        }
                    };
                    task.setOnSucceeded(e -> {

                        if(uuidc.isConfirmButtonPressed()){

                            successDialog(uuidc.getData(), field);
                        }

                    });
                    new Thread(task).start();

                });
                rootPane.setEffect(blur);

            } catch (IOException ignored) {

            }
        }

    }

    /**
     * this method shows the update is successful dialog
     */
    private void successDialog(String data, String field){

        BoxBlur blur = new BoxBlur(6, 6, 6);

        // success dialog box is generated here

        FXMLLoader successMsg = new FXMLLoader(getClass().getResource("/resources/infoDialog.fxml"));
        try {

            Region sucLoader = successMsg.load();

            InfoDialogController idc = successMsg.getController();
            idc.setDialogBody(field + " " + "Updated Successfully!!!");
            idc.setDialogButtonText("Okay, Thank you");

            JFXDialog contDialog = new JFXDialog(rootStack, sucLoader, JFXDialog.DialogTransition.TOP);

            idc.setDialog(contDialog);
            contDialog.show();
            contDialog.setOnDialogClosed((JFXDialogEvent ev) -> {

                try {
                    updateDashboardField(data, field);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                rootPane.setEffect(null);

            });
            rootPane.setEffect(blur);

        } catch (IOException ignored) {

        }
    }

    /**
     * this method updates the sections in the dashboard page
     */
    private void updateDashboardField(String data, String field) throws SQLException {

        switch (field) {
            case "First Name" -> {
                firstNameLabel.setText(data);
                userName.setText(firstNameLabel.getText() + " " + lastNameLabel.getText());
            }
            case "Last Name" -> {
                lastNameLabel.setText(data);
                userName.setText(firstNameLabel.getText() + " " + lastNameLabel.getText());
            }
            case "Gender" -> genderLabel.setText(data);
            case "Phone No" -> phoneNoLabel.setText(data);
            case "NID No" -> NIDLabel.setText(data);
            case "Birth Reg No" -> birthRegLabel.setText(data);
        }
    }

    /**
     * this method initializes every component in this scene
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /*TODO reservation, cancellation and rating data should be retrieved here and pass them
         * TODO into the progValue of the below constructors
         */

        UserData userData = new UserData();

        try {
            ConnectorDB connectorDB = new ConnectorDB();

            String sqlQuery = "Select * from userInformation where userEmail = '" + userData.getUserEmail() +"'";
            Statement statement = connectorDB.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while(resultSet.next()){

                firstNameLabel.setText(resultSet.getString("userFirstName"));
                lastNameLabel.setText(resultSet.getString("userLastName"));
                userName.setText(firstNameLabel.getText() + " " + lastNameLabel.getText());
                emailLabel.setText(resultSet.getString("userEmail"));
                genderLabel.setText(resultSet.getString("userGender"));
                phoneNoLabel.setText(resultSet.getString("userPhoneNumber"));
                if (resultSet.getString("userNID") != null){
                    NIDLabel.setText(resultSet.getString("userNID"));
                }
                if (resultSet.getString("userBReg") != null){
                    birthRegLabel.setText(resultSet.getString("userBReg"));
                }

                if (resultSet.getString("userImage") != null){
                    System.out.println(resultSet.getString("userImage"));

                    Image img = new Image("/upload/"+resultSet.getString("userImage"), false);
                    innerCircle.setFill(new ImagePattern(img));


                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }



        new ProgressThread(reserveProg, reserveLabel, 0.7, 1).start();
        new ProgressThread(cancelProg, cancelLabel, 0.3, 1).start();

        translateIt(500, passenger, -329, 1);
        translateIt(500, dashboard, -461, 1);
        translateIt(500, userName, 651, 1);
        translateIt(500, innerCircle, 520, 1);


        TranslateTransition scrollTransition = new TranslateTransition(Duration.millis(500), dashScroll);
        scrollTransition.setToX(1300);
        scrollTransition.play();

        scrollTransition.setOnFinished((er)->{

            dashScroll.setVisible(true);

            ScaleTransition reserveTransition = new ScaleTransition(Duration.millis(500), reservePane);
            reserveTransition.setToX(1);
            reserveTransition.setToY(1);
            reserveTransition.play();

            reserveTransition.setOnFinished((ec)->{

                ScaleTransition cancelTransition = new ScaleTransition(Duration.millis(500), cancelPane);
                cancelTransition.setToX(1);
                cancelTransition.setToY(1);
                cancelTransition.play();

                cancelTransition.setOnFinished((et)->{

                    scaleIt(500, firstNamePane, 1, 3);
                    scaleIt(500, lastNamePane, 1, 3);
                });
            });
        });

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

    /**this inner class will show retrieved data of reservation, cancellation and rating
     * from the database in progressbar and label with percentage value
     */
    static class ProgressThread extends Thread{

        public ProgressIndicator tempProg;
        public Label tempLabel;
        public double progValue;
        public double initialValue = 0;
        public int type;

        public ProgressThread(ProgressIndicator tempProg, Label tempLabel, double progValue, int type){

            this.tempProg = tempProg;
            this.tempLabel = tempLabel;
            this.progValue = progValue;
            this.type = type;
        }

        @Override
        public void run() {

            while (progValue - initialValue > 0.00000001){

                try{

                    if(initialValue == 0.0){

                        Thread.sleep(2000);
                    } else{

                        Thread.sleep(30);
                    }
                }catch (InterruptedException ignored){

                }

                Platform.runLater(()-> {
                    tempProg.setProgress(initialValue);

                    if (type == 1){

                        tempLabel.setText(Double.toString(Math.round(tempProg.getProgress() * 100)) + "%");
                    } else{

                        double value = Math.round(((tempProg.getProgress() * 10) / 2) * 10);
                        tempLabel.setText(value/ 10 + "/5");
                    }

                });

                initialValue +=0.02;

            }
        }
    }
}
