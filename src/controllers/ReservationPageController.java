package controllers;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ReservationPageController implements Initializable {

    /**
     * variable initialization
     */
    @FXML
    public Button crossButton;
    public Pane logOutBtn, transBtn, cancelBtn;
    public Label logOutInfo, tranLogInfo, cancelInfo, reserveInfo;
    public Text reservation, page;
    public Button homeBtn;

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

        TranslateTransition transition = new TranslateTransition(Duration.millis(100), logOutInfo);
        transition.setToX(220);
        transition.play();
    }

    /**
     * this method is to animate out the log out menu information
     */
    public void onExitLogOutButton(){

        TranslateTransition transition = new TranslateTransition(Duration.millis(100), logOutInfo);
        transition.setToX(0);
        transition.play();
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

        TranslateTransition transition = new TranslateTransition(Duration.millis(100), tranLogInfo);
        transition.setToX(220);
        transition.play();

    }

    /**
     * this method is to animate out the transaction log menu information
     */
    public void onExitTransactionLogButton(){

        TranslateTransition transition = new TranslateTransition(Duration.millis(100), tranLogInfo);
        transition.setToX(0);
        transition.play();

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

        TranslateTransition transition = new TranslateTransition(Duration.millis(100), cancelInfo);
        transition.setToX(220);
        transition.play();

    }

    /**
     *  this method is to animate out the cancellation menu information
     */
    public void onExitCancellationButton(){

        TranslateTransition transition = new TranslateTransition(Duration.millis(100), cancelInfo);
        transition.setToX(0);
        transition.play();

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

    }
}
