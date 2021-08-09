package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class CoachInfoController implements Initializable {

    /**
     * variable initialization
     */
    @FXML
    public Button viewSeatButton;
    public Text fromText, fromShortText, toText, toShortText, coachNoText, reportingText, boardingText, departureText, destinationText, typeText, seatsText, fareText;

    private ReservationPageController rpc;

    private boolean isViewSeatButtonPressed = false;

    /**
     * this setter gets controller from ReservationPageController
     */
    public void setRpc(ReservationPageController rpc) {
        this.rpc = rpc;
    }

    /**
     * this method invokes when the view seat button is pressed
     */
    public void onClickViewSeatButton(){

        if(!isViewSeatButtonPressed){

            rpc.showSeatMap(typeText.getText());
            isViewSeatButtonPressed = true;
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    /**
     * TODO update this method's parameters and get values from the database
     */
    public void updateInfo(String fare, String coachType){

        fareText.setText(fare);
        typeText.setText(coachType);
    }

    public void buttonPressAvailable(boolean isViewSeatButtonPressed){

        this.isViewSeatButtonPressed =isViewSeatButtonPressed;
    }
}