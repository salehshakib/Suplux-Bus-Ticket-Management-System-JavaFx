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

        translateIt(500, passenger, -329, 1);
        translateIt(500, dashboard, -461, 1);
        translateIt(500, userName, 651, 1);
        translateIt(500, innerCircle, 689, 1);
        translateIt(500, outerCircle, 689, 1);


        TranslateTransition scrollTransition = new TranslateTransition(Duration.millis(500), dashScroll);
        scrollTransition.setToX(1300);
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

                        scaleIt(500, firstNamePane, 1, 3);
                        scaleIt(500, lastNamePane, 1, 3);

                    });
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
