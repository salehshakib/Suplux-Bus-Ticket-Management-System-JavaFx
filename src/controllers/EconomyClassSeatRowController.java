package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;

public class EconomyClassSeatRowController {

    /**
     * variable initialization
     */
    @FXML
    public Pane seatPane1, seatPane2, seatPane3, seatPane4;
    public SVGPath seatSVG1, seatSVG2, seatSVG3, seatSVG4;
    public Label seatLabel1, seatLabel2, seatLabel3, seatLabel4;
    public boolean isSeatSelected1 = false, isSeatSelected2 = false, isSeatSelected3 = false, isSeatSelected4 = false;

    private ReservationPageController rpc;

    /**
     * this setter gets controller from ReservationPageController
     */
    public void setRpc(ReservationPageController rpc) {
        this.rpc = rpc;
    }

    public void setSeatText(String seat1, String seat2, String seat3, String seat4){

        seatLabel1.setText(seat1);
        seatLabel2.setText(seat2);
        seatLabel3.setText(seat3);
        seatLabel4.setText(seat4);
    }

    public void onClickSeatPane1(){

        if(!isSeatSelected1){

            if (rpc.isFourSeatsSelected()){

                seatPane1.getStyleClass().clear();
                seatPane1.getStyleClass().add("seat_selected");
                isSeatSelected1 = true;
            }

        } else{

            seatPane1.getStyleClass().clear();
            seatPane1.getStyleClass().add("seat");
            isSeatSelected1 = false;
        }

        rpc.updateSelectedSeatBox(seatLabel1.getText(), isSeatSelected1);

    }

    public void onClickSeatPane2(){

        if(!isSeatSelected2){

            if (rpc.isFourSeatsSelected()){

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

    public void onClickSeatPane3(){


        if(!isSeatSelected3){

            if (rpc.isFourSeatsSelected()){

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

    public void onClickSeatPane4(){

        if(!isSeatSelected4){

            if (rpc.isFourSeatsSelected()){

                seatPane4.getStyleClass().clear();
                seatPane4.getStyleClass().add("seat_selected");
                isSeatSelected4 = true;
            }

        } else{

            seatPane4.getStyleClass().clear();
            seatPane4.getStyleClass().add("seat");
            isSeatSelected4 = false;
        }

        rpc.updateSelectedSeatBox(seatLabel4.getText(), isSeatSelected4);

    }
}
