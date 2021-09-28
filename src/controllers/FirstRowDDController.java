package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;

import java.util.ArrayList;

public class FirstRowDDController {

    /**
     * variable initialization
     */
    @FXML
    public Pane seatPane2, seatPane3;
    public SVGPath seatSVG2, seatSVG3;
    public Label seatLabel2, seatLabel3;
    public boolean isSeatSelected2 = false, isSeatSelected3 = false;

    private ReservationPageController rpc;

    /**
     * this setter gets controller from ReservationPageController
     */
    public void setRpc(ReservationPageController rpc) {
        this.rpc = rpc;
    }

    public void setSeatText(String seat2, String seat3){

        seatLabel2.setText(seat2);
        seatLabel3.setText(seat3);
    }

    public void soldSeats(ArrayList<String> soldSeatsList){

        for(String seat : soldSeatsList){

            if(seatLabel2.getText().equals(seat)){

                seatPane2.getStyleClass().clear();
                seatPane2.getStyleClass().add("seat_sold");

            } else if(seatLabel3.getText().equals(seat)){

                seatPane3.getStyleClass().clear();
                seatPane3.getStyleClass().add("seat_sold");
            }
        }
    }

    public void bookedSeats(ArrayList<String> bookedSeatsList){

        for(String seat : bookedSeatsList){

            if(seatLabel2.getText().equals(seat)){

                seatPane2.getStyleClass().clear();
                seatPane2.getStyleClass().add("seat_booked");

            } else if(seatLabel3.getText().equals(seat)){

                seatPane3.getStyleClass().clear();
                seatPane3.getStyleClass().add("seat_booked");
            }
        }
    }

    public void onClickSeatPane2(){

        if(!seatPane2.getStyleClass().get(0).equals("seat_sold")
                || !seatPane2.getStyleClass().get(0).equals("seat_booked")){

            if(!isSeatSelected2){

                if(rpc.isFourSeatsSelected()){

                    seatPane2.getStyleClass().clear();
                    seatPane2.getStyleClass().add("seat_selected");
                    isSeatSelected2 = true;
                }

            } else{

                seatPane2.getStyleClass().clear();
                seatPane2.getStyleClass().add("seat");
                isSeatSelected2 = false;
            }

            rpc.updateSelectedSeatBox(seatLabel2.getText(), isSeatSelected2);
        }

    }

    public void onClickSeatPane3(){

        if(!seatPane3.getStyleClass().get(0).equals("seat_sold")
                || !seatPane3.getStyleClass().get(0).equals("seat_booked")){

            if(!isSeatSelected3){

                if(rpc.isFourSeatsSelected()){

                    seatPane3.getStyleClass().clear();
                    seatPane3.getStyleClass().add("seat_selected");
                    isSeatSelected3 = true;
                }

            } else{

                seatPane3.getStyleClass().clear();
                seatPane3.getStyleClass().add("seat");
                isSeatSelected3 = false;
            }

            rpc.updateSelectedSeatBox(seatLabel3.getText(), isSeatSelected3);
        }

    }
}
