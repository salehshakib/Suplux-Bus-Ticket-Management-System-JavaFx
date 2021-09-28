package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;

import java.util.ArrayList;

public class SleeperSeatRowController {

    /**
     * variable initialization
     */
    @FXML
    public Pane seatPane1, seatPane2, seatPane3;
    public SVGPath seatSVG1, seatSVG2, seatSVG3;
    public Label seatLabel1, seatLabel2, seatLabel3;
    public boolean isSeatSelected1 = false, isSeatSelected2 = false, isSeatSelected3 = false;

    private ReservationPageController rpc;

    /**
     * this setter gets controller from ReservationPageController
     */
    public void setRpc(ReservationPageController rpc) {
        this.rpc = rpc;
    }

    public void setSeatText(String seat1, String seat2, String seat3){

        seatLabel1.setText(seat1);
        seatLabel2.setText(seat2);
        seatLabel3.setText(seat3);
    }

    public void soldSeats(ArrayList<String> soldSeatsList){

        for(String seat : soldSeatsList){

            if(seatLabel1.getText().equals(seat.substring(1))){

                seatPane1.getStyleClass().clear();
                seatPane1.getStyleClass().add("sleeper_seat_sold");

            } else if(seatLabel2.getText().equals(seat.substring(1))){

                seatPane2.getStyleClass().clear();
                seatPane2.getStyleClass().add("sleeper_seat_sold");

            } else if(seatLabel3.getText().equals(seat.substring(1))){

                seatPane3.getStyleClass().clear();
                seatPane3.getStyleClass().add("sleeper_seat_sold");
            }
        }
    }

    public void bookedSeats(ArrayList<String> bookedSeatsList){

        for(String seat : bookedSeatsList){

            if(seatLabel1.getText().equals(seat.substring(1))){

                seatPane1.getStyleClass().clear();
                seatPane1.getStyleClass().add("sleeper_seat_booked");

            } else if(seatLabel2.getText().equals(seat.substring(1))){

                seatPane2.getStyleClass().clear();
                seatPane2.getStyleClass().add("sleeper_seat_booked");

            } else if(seatLabel3.getText().equals(seat.substring(1))){

                seatPane3.getStyleClass().clear();
                seatPane3.getStyleClass().add("sleeper_seat_booked");
            }
        }
    }

    public void onClickSeatPane1(){

        if(!seatPane1.getStyleClass().get(0).equals("sleeper_seat_sold")
                && !seatPane1.getStyleClass().get(0).equals("sleeper_seat_booked")){

            if(!isSeatSelected1){

                if(rpc.isFourSeatsSelected()){

                    seatPane1.getStyleClass().clear();
                    seatPane1.getStyleClass().add("sleeper_seat_selected");
                    isSeatSelected1 = true;
                }

            } else{

                seatPane1.getStyleClass().clear();
                seatPane1.getStyleClass().add("sleeper_seat");
                isSeatSelected1 = false;
            }

            rpc.updateSelectedSeatBox(seatLabel1.getText(), isSeatSelected1);

        }

    }

    public void onClickSeatPane2(){

        if(!seatPane2.getStyleClass().get(0).equals("sleeper_seat_sold")
                && !seatPane2.getStyleClass().get(0).equals("sleeper_seat_booked")){

            if(!isSeatSelected2){

                if(rpc.isFourSeatsSelected()){

                    seatPane2.getStyleClass().clear();
                    seatPane2.getStyleClass().add("sleeper_seat_selected");
                    isSeatSelected2 = true;
                }

            } else{

                seatPane2.getStyleClass().clear();
                seatPane2.getStyleClass().add("sleeper_seat");
                isSeatSelected2 = false;
            }

            rpc.updateSelectedSeatBox(seatLabel2.getText(), isSeatSelected2);

        }

    }

    public void onClickSeatPane3(){

        if(!seatPane3.getStyleClass().get(0).equals("sleeper_seat_sold")
                && !seatPane3.getStyleClass().get(0).equals("sleeper_seat_booked")){

            if(!isSeatSelected3){

                if(rpc.isFourSeatsSelected()){

                    seatPane3.getStyleClass().clear();
                    seatPane3.getStyleClass().add("sleeper_seat_selected");
                    isSeatSelected3 = true;
                }

            } else{

                seatPane3.getStyleClass().clear();
                seatPane3.getStyleClass().add("sleeper_seat");
                isSeatSelected3 = false;
            }

            rpc.updateSelectedSeatBox(seatLabel3.getText(), isSeatSelected3);

        }
    }
}
