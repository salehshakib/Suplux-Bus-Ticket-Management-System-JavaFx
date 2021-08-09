package controllers;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    /**
    * variable initialization
    */
    @FXML
    public Button crossButton, logInBtn, signUpBtn;
    public ToggleButton logInAnimBtn, signUpAnimBtn;
    public Pane logInPane, signUpPane;
    public Text welcome, to, sup, dash;

    private static boolean stage = false;

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
     * this method is to animate the sign up pane form left to right
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
    public void onClickLogInButton(javafx.event.ActionEvent actionEvent) throws IOException {

        //TODO database log in credentials should be checked here...

        Parent dashboard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resources/dashboard.fxml")));
        Scene dashboardScene = new Scene(dashboard);

        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(dashboardScene);
        window.show();
    }

    /**
     * this method is to switch from home page to sign up page
     */
    public void onClickSignUpButton(ActionEvent actionEvent) throws IOException {

        Parent dashboard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resources/signUpForm.fxml")));
        Scene dashboardScene = new Scene(dashboard);

        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(dashboardScene);
        window.show();
    }


    /**
     * this method is to show the initial animation
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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

}
