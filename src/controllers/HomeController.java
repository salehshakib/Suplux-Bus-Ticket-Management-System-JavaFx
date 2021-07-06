package controllers;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    public Button crossButton;
    public ToggleButton logInAnimBtn, signUpAnimBtn;
    public Pane logInPane, signUpPane;
    public Text welcome, to, sup, dash;

    public void onClickCrossButton(){

        System.exit(0);
    }

    public void logInPaneAnimation(){

        TranslateTransition logTransition = new TranslateTransition(Duration.millis(500), logInPane);

        TranslateTransition wTransition = new TranslateTransition(Duration.millis(500), welcome);
        TranslateTransition tTransition = new TranslateTransition(Duration.millis(500), to);
        TranslateTransition sTransition = new TranslateTransition(Duration.millis(500), sup);
        TranslateTransition dTransition = new TranslateTransition(Duration.millis(500), dash);

        wTransition.setToX(473);
        tTransition.setToX(596);
        sTransition.setToX(312);
        dTransition.setToX(423);

        wTransition.play();
        tTransition.play();
        sTransition.play();
        dTransition.play();

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

    public void signUpPaneAnimation(){

        TranslateTransition signTransition = new TranslateTransition(Duration.millis(500), signUpPane);
        TranslateTransition wTransition = new TranslateTransition(Duration.millis(500), welcome);
        TranslateTransition tTransition = new TranslateTransition(Duration.millis(500), to);
        TranslateTransition sTransition = new TranslateTransition(Duration.millis(500), sup);
        TranslateTransition dTransition = new TranslateTransition(Duration.millis(500), dash);

        wTransition.setToX(-467);
        tTransition.setToX(-591);
        sTransition.setToX(-308);
        dTransition.setToX(-416);

        wTransition.play();
        tTransition.play();
        sTransition.play();
        dTransition.play();

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        FadeTransition welcomeTransition = new FadeTransition(Duration.millis(1000), welcome);
        FadeTransition toTransition = new FadeTransition(Duration.millis(1000), to);
        FadeTransition supTransition = new FadeTransition(Duration.millis(1000), sup);
        FadeTransition dashTransition = new FadeTransition(Duration.millis(1000), dash);

        welcomeTransition.setFromValue(0f);
        toTransition.setFromValue(0f);
        supTransition.setFromValue(0f);
        dashTransition.setFromValue(0f);

        welcomeTransition.setToValue(1.0f);
        toTransition.setToValue(1.0f);
        supTransition.setToValue(1.0f);
        dashTransition.setToValue(1.0f);

        welcomeTransition.setCycleCount(5);
        toTransition.setCycleCount(5);
        supTransition.setCycleCount(5);
        dashTransition.setCycleCount(5);

        welcomeTransition.setAutoReverse(true);
        toTransition.setAutoReverse(true);
        supTransition.setAutoReverse(true);
        dashTransition.setAutoReverse(true);

        welcomeTransition.play();
        toTransition.play();
        supTransition.play();
        dashTransition.play();

        dashTransition.setOnFinished((e)->{

            TranslateTransition wTransition = new TranslateTransition(Duration.millis(1000), welcome);
            TranslateTransition tTransition = new TranslateTransition(Duration.millis(1000), to);
            TranslateTransition sTransition = new TranslateTransition(Duration.millis(1000), sup);
            TranslateTransition dTransition = new TranslateTransition(Duration.millis(1000), dash);

            wTransition.setToX(-467);
            tTransition.setToX(-591);
            sTransition.setToX(-308);
            dTransition.setToX(-416);

            logInAnimBtn.setSelected(true);
            logInAnimBtn.setDisable(true);
            TranslateTransition logTransition = new TranslateTransition(Duration.millis(1000), logInPane);
            logTransition.setToX(-455);

            TranslateTransition logBtnTransition = new TranslateTransition(Duration.millis(1000), logInAnimBtn);
            logBtnTransition.setToY(93);

            TranslateTransition signBtnTransition = new TranslateTransition(Duration.millis(1000), signUpAnimBtn);
            signBtnTransition.setToY(93);

            wTransition.play();
            tTransition.play();
            sTransition.play();
            dTransition.play();
            logTransition.play();
            logBtnTransition.play();
            signBtnTransition.play();
        });

    }
}
