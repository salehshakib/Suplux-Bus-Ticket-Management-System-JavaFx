package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.net.URL;
import java.nio.charset.StandardCharsets;
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


            rpc.showSeatMap(typeText.getText(), coachNoText.getText(), fromText.getText(), toText.getText(), fareText.getText(), "LOCAL", reportingText.getText(), departureText.getText());
            isViewSeatButtonPressed = true;
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    /**
     * TODO update this method's parameters and get values from the database
     */
    public void updateInfo(String from, String to, String coachNo, String reportingTime, String boarding, String departureTime, String destination,String coachType, String availSeats, String fare){


        fromText.setText(from);
        fromShortText.setText(from.substring(0,3).toUpperCase());
        toText.setText(to);
        toShortText.setText(to.substring(0,3).toUpperCase());
        coachNoText.setText(coachNo);
        reportingText.setText(reportingTime);
        boardingText.setText(boarding);
        departureText.setText(departureTime);
        destinationText.setText(destination);
        typeText.setText(coachType);
        seatsText.setText(availSeats);
        fareText.setText("BDT " + fare);

    }

    public void buttonPressAvailable(boolean isViewSeatButtonPressed){

        this.isViewSeatButtonPressed =isViewSeatButtonPressed;
    }
}
