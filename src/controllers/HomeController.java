package controllers;

import com.gluonhq.charm.glisten.control.ProgressBar;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import miscellaneous.java.UserData;
import miscellaneous.mail.MailOTP;
import net.codejava.sql.ConnectorDB;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;



public class HomeController implements Initializable {

    /**
    * variable initialization
    */
    @FXML
    public Button crossButton, logInBtn, signUpBtn;
    public ToggleButton logInAnimBtn, signUpAnimBtn;
    public TextField logInEmail, signUpEmail;
    public PasswordField logInPassword, signUpPassword, signUpConfirm;
    public Pane logInPane, signUpPane, rootPane;
    public Text welcome, to, sup, dash;
    public ProgressBar fetchProg;
    public StackPane rootStack;
    public Label logInErrorMsg, signUpErrorMsg;
    private String verificationOTP;

    private static boolean stage = false;
    private static boolean isLoggedIn = false;

//    public static String justEmail, justUserName, justUserGender;


    /**
     * this method is to close the application
     */
    public void onClickCrossButton(){

        System.exit(0);
    }

    /**
     * this method is to animate the log in pane left to right
     */
    public void logInPaneAnimation(){

        translateIt(500, welcome, 473, 1);
        translateIt(500, to, 596, 1);
        translateIt(500, sup, 312, 1);
        translateIt(500, dash, 423, 1);

        TranslateTransition logTransition = new TranslateTransition(Duration.millis(500), logInPane);

        if(logInAnimBtn.isSelected()){

            logInAnimBtn.setDisable(true);
            signUpAnimBtn.setSelected(false);
            signUpPaneAnimation();
            logTransition.setToX(-455);
        } else{

            logInAnimBtn.setDisable(false);
            logTransition.setToX(0);
        }

        logTransition.play();
    }

    /**
     * this method is to animate the sign-up pane form left to right
     */
    public void signUpPaneAnimation(){

        translateIt(500, welcome, -467, 1);
        translateIt(500, to, -591, 1);
        translateIt(500, sup, -308, 1);
        translateIt(500, dash, -416, 1);

        TranslateTransition signTransition = new TranslateTransition(Duration.millis(500), signUpPane);

        if(signUpAnimBtn.isSelected()){

            signUpAnimBtn.setDisable(true);
            logInAnimBtn.setSelected(false);
            logInPaneAnimation();
            signTransition.setToX(455);
        } else{

            signUpAnimBtn.setDisable(false);
            signTransition.setToX(0);
        }

        signTransition.play();
    }

    /**
     * this method is to switch from home page to dashboard page
     */


    public void onClickLogInButton(javafx.event.ActionEvent actionEvent) throws SQLException {

        if(!logInEmail.getText().isBlank() && !logInEmail.getText().isEmpty()
                && !logInPassword.getText().isBlank() && !logInPassword.getText().isEmpty()){

            //this task object is letting us get the time for fetching the data from database
            Task<Void> task = new Task<>() {
                @Override
                public Void call() {

                    scaleIt(200, fetchProg, 1, 2);

                    Platform.runLater(() ->{

                        try {
                            ConnectorDB connectorDB = new ConnectorDB();
                            String email = logInEmail.getText();
                            System.out.println(email);
                            String sqlQuery = "select * from userInformation where userEmail = '" + email +"'";
                            Statement statement = connectorDB.getConnection().createStatement();
                            ResultSet resultSet = statement.executeQuery(sqlQuery);
                            while (resultSet.next()){
                                if(resultSet.getString("userPassword").equals(logInPassword.getText())){
                                    System.out.println(resultSet.getString("userPassword"));
                                    System.out.println("log IN success");
                                    UserData userData = new UserData();
                                    userData.setUserEmail(email);
                                    String name = resultSet.getString("userLastName");
                                    String gender = resultSet.getString("userGender");
                                    userData.setUserLastName(name);
                                    userData.setUserGender(gender);
                                    isLoggedIn = true;
                                }
                                else {
                                    System.out.println("Log IN Failed");
                                    isLoggedIn = false;
                                }
                            }





                        } catch (SQLException e) {
                            e.printStackTrace();
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

                if(isLoggedIn){
                    try {



                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/dashboard.fxml"));
                        Parent dashboard = loader.load();

                        Scene dashboardScene = new Scene(dashboard);

                        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                        window.setScene(dashboardScene);
                        window.show();

                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                } else {
                    scaleIt(200, logInErrorMsg, 1, 2);
                    logInEmail.setStyle("-fx-border-color: red");
                    logInPassword.setStyle("-fx-border-color: red");

                }

            });
            new Thread(task).start();

        } else{
            scaleIt(200, logInErrorMsg, 1, 2);
            logInEmail.setStyle("-fx-border-color: red");
            logInPassword.setStyle("-fx-border-color: red");
        }

    }

    /**
     * this method is to switch from home page to sign up page
     */
    public void onClickSignUpButton(ActionEvent actionEvent) {

        if(!signUpEmail.getText().isBlank() && !signUpEmail.getText().isEmpty()
                && !signUpPassword.getText().isBlank() && !signUpPassword.getText().isEmpty()
                && !signUpConfirm.getText().isBlank() && !signUpConfirm.getText().isEmpty()
                && isValidEmailAddress(signUpEmail.getText()) && signUpPassword.getText().equals(signUpConfirm.getText())){

            //this task object is letting us get the time for fetching the data from database
            Task<Void> task = new Task<>() {
                @Override
                public Void call() {

                    scaleIt(200, fetchProg, 1, 2);

                    Platform.runLater(() ->{

                        //OTP is sent here
                        try {
                            ConnectorDB connectorDB = new ConnectorDB();
                            //String email = signUpEmail.getText();
                            String sqlQuery = "select userEmail from userInformation where userEmail = '" + signUpEmail.getText() +"'";
                            Statement statement = connectorDB.getConnection().createStatement();
                            ResultSet resultSet = statement.executeQuery(sqlQuery);
                            if (!resultSet.next()){
                                MailOTP mailOTP = new MailOTP();
                                System.out.println("preparing to send message ...");
                                verificationOTP = mailOTP.getRandomNumberString();
                                String message = "<html>Hello there, <br> Your OTP is : " + "<b>" + verificationOTP + "</b>" + "<br> Use this OTP to verify your account. <br></html>";
                                String subject = "User Verification OTP";
                                String from = "sam404.iums@gmail.com";

                                mailOTP.sendMail(message, subject, signUpEmail.getText(), from);

                                SignUpController signUpController = new SignUpController();


                                signUpController.userData(signUpEmail.getText(), signUpPassword.getText(), verificationOTP);
                                System.out.println(verificationOTP);



                            }else {
                                //todo if user already exist so fix this shit $_chudirvi("aumi')

                                System.out.println("user already exist");
                            }


                        } catch (SQLException e) {
                            e.printStackTrace();
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

                // OTP sent dialog box is generated here
                BoxBlur blur = new BoxBlur(6, 6, 6);

                FXMLLoader otp = new FXMLLoader(getClass().getResource("/resources/infoDialog.fxml"));
                try {

                    Region otpLoader = otp.load();

                    InfoDialogController idc = otp.getController();
                    idc.setDialogBody("An E mail with OTP has been sent to your given E mail ID. Please, check it.");
                    idc.setDialogButtonText("Okay, Thank you");

                    JFXDialog otpDialog = new JFXDialog(rootStack, otpLoader, JFXDialog.DialogTransition.TOP);

                    idc.setDialog(otpDialog);
                    otpDialog.show();
                    otpDialog.setOnDialogClosed((JFXDialogEvent event) -> {

                        rootPane.setEffect(null);

                        try {

                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/signUpForm.fxml"));
                            Parent signUpPage = loader.load();
                            Scene scene = new Scene(signUpPage);

                            SignUpController suc = loader.getController();

                            suc.getEmailAndPassword(signUpEmail.getText(), signUpPassword.getText(), verificationOTP);

                            Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                            window.setScene(scene);
                            window.show();

                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }

                    });
                    rootPane.setEffect(blur);

                } catch (IOException ignored) {

                }

            });
            new Thread(task).start();

        } else{

            if(signUpEmail.getText().isBlank() && signUpEmail.getText().isEmpty()
                    && signUpPassword.getText().isBlank() && signUpPassword.getText().isEmpty()
                    && signUpConfirm.getText().isBlank() && signUpConfirm.getText().isEmpty()){

                signUpErrorMsg.setText("Invalid E mail or password");
                scaleIt(200, signUpErrorMsg, 1, 2);
                signUpEmail.setStyle("-fx-border-color: red");
                signUpPassword.setStyle("-fx-border-color: red");
                signUpConfirm.setStyle("-fx-border-color: red");

            } else if(!isValidEmailAddress(signUpEmail.getText())){

                signUpErrorMsg.setText("Invalid E mail");
                scaleIt(200, signUpErrorMsg, 1, 2);
                signUpEmail.setStyle("-fx-border-color: red");

            } else if(!signUpPassword.getText().equals(signUpConfirm.getText())){

                signUpErrorMsg.setText("Password mismatched");
                scaleIt(200, signUpErrorMsg, 1, 2);
                signUpPassword.setStyle("-fx-border-color: red");
                signUpConfirm.setStyle("-fx-border-color: red");
            }

        }

    }

    public void onClickLogInField(){

        scaleIt(200, logInErrorMsg, 0, 2);
        logInEmail.setStyle("-fx-border-color: #007EfC");
        logInPassword.setStyle("-fx-border-color: #007EfC");
    }

    public void onClickSignUpTextField(){

        scaleIt(200, signUpErrorMsg, 0, 2);
        signUpEmail.setStyle("-fx-border-color: #007EfC");
    }

    public void onClickSignUpPasswordField(){

        scaleIt(200, signUpErrorMsg, 0, 2);
        signUpPassword.setStyle("-fx-border-color: #007EfC");
        signUpConfirm.setStyle("-fx-border-color: #007EfC");
    }

    /**
     * this method is to show the initial animation
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        isLoggedIn = false;

        if(!stage){

            fadeIt(1000, welcome, 0f, 1f, 5, true);
            fadeIt(1000, to, 0f, 1f, 5, true);
            fadeIt(1000, sup, 0f, 1f, 5, true);

            FadeTransition dashTransition = new FadeTransition(Duration.millis(1000), dash);
            dashTransition.setFromValue(0f);
            dashTransition.setToValue(1.0f);
            dashTransition.setCycleCount(5);
            dashTransition.setAutoReverse(true);
            dashTransition.play();

            dashTransition.setOnFinished((e)->{

                translateIt(1000, welcome, -467, 1);
                translateIt(1000, to, -591, 1);
                translateIt(1000, sup, -308, 1);
                translateIt(1000, dash, -416, 1);

                logInAnimBtn.setSelected(true);
                logInAnimBtn.setDisable(true);

                translateIt(1000, logInPane, -455, 1);
                translateIt(1000, logInAnimBtn, 93, 2);
                translateIt(1000, signUpAnimBtn, 93, 2);

            });

            stage = true;
        } else{

            welcome.setOpacity(1.0f);
            to.setOpacity(1.0f);
            sup.setOpacity(1.0f);
            dash.setOpacity(1.0f);

            translateIt(1000, welcome, -467, 1);
            translateIt(1000, to, -591, 1);
            translateIt(1000, sup, -308, 1);
            translateIt(1000, dash, -416, 1);

            logInAnimBtn.setSelected(true);
            logInAnimBtn.setDisable(true);

            translateIt(1000, logInPane, -455, 1);
            translateIt(1000, logInAnimBtn, 93, 2);
            translateIt(1000, signUpAnimBtn, 93, 2);

        }
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
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

    public void fadeIt(double duration, Node node, double fromValue, double toValue, int cycleCount, boolean autoReverse){

        FadeTransition transition = new FadeTransition(Duration.millis(duration), node);

        transition.setFromValue(fromValue);
        transition.setToValue(toValue);
        transition.setCycleCount(cycleCount);
        transition.setAutoReverse(autoReverse);

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
