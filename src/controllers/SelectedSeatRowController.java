package controllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class SelectedSeatRowController {

    /**
     * variable initialization
     */
    @FXML
    Text seatText, fareText;

    public void setSeatText(String seat) {

        seatText.setText(seat);
    }

    public void setFareText(String fare) {

        fareText.setText(fare);
    }

    public String getSeatText(){

        return seatText.getText();
    }
}
