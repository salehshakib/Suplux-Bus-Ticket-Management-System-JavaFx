package controllers;

import com.gluonhq.charm.glisten.control.ProgressIndicator;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    /**
     * variable initialization
     */
    @FXML
    public Button crossButton;
    public Pane logOutBtn, transBtn, cancelBtn, reserveBtn, reservePane, cancelPane, ratingsPane, firstNamePane, lastNamePane;
    public Label logOutInfo, tranLogInfo, cancelInfo, reserveInfo, reserveLabel, cancelLabel, tripLabel;
    public ProgressIndicator reserveProg, cancelProg, tripProg;
    public ScrollPane dashScroll;
    public Text passenger, dashboard, userName;
    public Circle innerCircle, outerCircle;
    public SVGPath userPic;


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
     * this method is used to switch to reservation page
     */
    public void onClickReservationButton(javafx.event.Event actionEvent) {

        try{

            Parent page = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resources/reservationPage.fxml")));
            Scene scene = new Scene(page);

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

        TranslateTransition transition = new TranslateTransition(Duration.millis(100), reserveInfo);
        transition.setToX(220);
        transition.play();

    }

    /**
     * this method is to animate out the reservation menu information
     */
    public void onExitReservationButton(){

        TranslateTransition transition = new TranslateTransition(Duration.millis(100), reserveInfo);
        transition.setToX(0);
        transition.play();

    }

    /**
     * this method initializes every components in this scene
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /*TODO reservation, cancellation and rating data should be retrieved here and pass them
         * TODO into the progValue of the below constructors
         */

        new ProgressThread(reserveProg, reserveLabel, 0.7, 1).start();
        new ProgressThread(cancelProg, cancelLabel, 0.3, 1).start();
        new ProgressThread(tripProg, tripLabel, 0.85, 2).start();

        TranslateTransition scrollTransition = new TranslateTransition(Duration.millis(500), dashScroll);
        TranslateTransition passTransition = new TranslateTransition(Duration.millis(500), passenger);
        TranslateTransition dashTransition = new TranslateTransition(Duration.millis(500), dashboard);
        TranslateTransition nameTransition = new TranslateTransition(Duration.millis(500), userName);
        TranslateTransition innerTransition = new TranslateTransition(Duration.millis(500), innerCircle);
        TranslateTransition outerTransition = new TranslateTransition(Duration.millis(500), outerCircle);

        innerTransition.setToX(689);
        outerTransition.setToX(689);
        nameTransition.setToX(651);
        scrollTransition.setToX(1300);
        passTransition.setToX(-329);
        dashTransition.setToX(-461);

        innerTransition.play();
        outerTransition.play();
        nameTransition.play();
        dashTransition.play();
        passTransition.play();
        scrollTransition.play();

        scrollTransition.setOnFinished((er)->{

            userPic.setVisible(true);
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

                    ScaleTransition ratingTransition = new ScaleTransition(Duration.millis(500), ratingsPane);
                    ratingTransition.setToX(1);
                    ratingTransition.setToY(1);
                    ratingTransition.play();

                    ratingTransition.setOnFinished((ef)->{

                        ScaleTransition firstTransition = new ScaleTransition(Duration.millis(500), firstNamePane);
                        ScaleTransition lastTransition = new ScaleTransition(Duration.millis(500), lastNamePane);

                        firstTransition.setToX(1);
                        firstTransition.setToY(1);
                        lastTransition.setToX(1);
                        lastTransition.setToY(1);

                        firstTransition.play();
                        lastTransition.play();

                    });
                });
            });
        });

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
