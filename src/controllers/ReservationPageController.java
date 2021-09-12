package controllers;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import listeners.AutoCompleteComboBoxListener;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class ReservationPageController implements Initializable {

    /**
     * variable initialization
     */
    @FXML
    public Button crossButton, homeButton, resetButton, searchButton, backToReserveBtn, proceedBtn, backToCoachInfoButton;
    public Pane logOutBtn, transBtn, cancelBtn, revPane, seatMapDetailsPane, viewSeatPane;
    public ScrollPane coachInfoPane;
    public AnchorPane rootAnchor;
    public StackPane dialogStack, rootStack;
    public Label logOutInfo, tranLogInfo, cancelInfo, reserveInfo;
    public Text reservation, page, coachType, totalFare, seatMapDetailsFare, coachTypeSeatMapText;
    public ComboBox<String> fromComboBox, toComboBox, timePeriodComboBox, droppingPointComboBox, boardingPointComboBox;
    public CheckBox checkBoxNonAC, checkBoxBi, checkBoxMulti, checkBoxSleeper, checkBoxDD;
    public DatePicker dateOfJourney, dateOfReturn;
    public VBox coachInfoBox, coachTypeVBox, seatMapVBox, selectedSeatBox, radioDeckVBox;
    public com.gluonhq.charm.glisten.control.ProgressBar fetchProg;
    public RadioButton radioUpper, radioLower;

    public final String[] destinations = {"Dhaka", "Khulna", "Chittagong", "Cox's Bazar", "Kolkata", "Benapole", "Satkhira", "Chashara"};
    public final String[] timePeriod = {"Time Period","Morning(5:00 AM - 11:59 AM)", "Afternoon(12:00 PM - 5:59 PM)", "Night(6:00 PM - 11:59 PM)"};
    public final String[] boardingPoints = {"Select A Boarding Point","Aarambag, Dhaka (10:00 PM)", "Panthapath, Dhaka (10:30 PM)", "Gabtoli, Dhaka (11:00 PM)", "Savar, Dhaka (11:30 PM)"};
    public final String[] droppingPoints = {"Select A Dropping Point","Aarambag, Khagrachari (10:00 PM)", "Panthapath, Khagrachari (10:30 PM)", "Gabtoli, Khagrachari (11:00 PM)", "Savar, Khagrachari (11:30 PM)"};
    public Node[] selectedSeats = new Node[4];
    public ArrayList<FXMLLoader> selectedSeatsFXML = new ArrayList<>();
    public ArrayList<FXMLLoader> coachInfoFXML = new ArrayList<>();
    public ArrayList<Node> lowerDeckDD = new ArrayList<>();
    public ArrayList<Node> upperDeckDD = new ArrayList<>();
    public ArrayList<Node> lowerDeckSleeper = new ArrayList<>();
    public ArrayList<Node> upperDeckSleeper = new ArrayList<>();
    public static int selectedSeatCount = 0;
    public ArrayList<String> destination;
    public boolean isToTrimDone = false;
    public boolean isFromTrimDone = false;
    private static ReservationPageController rpc;
    public ArrayList<String> selectedSeatString = new ArrayList<>();


    /**
     * this setter invokes ReservationPageController for after uses
     */
    public static void setRpc(ReservationPageController rpc) {
        ReservationPageController.rpc = rpc;
    }

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

    /**
     * this method allows user to reset the value of "Date of Return" date picker
     */
    public void onClickResetButton(){

        dateOfReturn.getEditor().clear();

    }

    public void onClickDateOfJourney(){

        dateOfJourney.getStyleClass().remove("date-picker-error");
    }

    public void onClickTimePeriodComboBox(){

        timePeriodComboBox.getStyleClass().remove("combo-box-base-error");
    }

    /**
     * this method searches the database for available trips
     */
    public void onClickSearchButton(){

        if(!fromComboBox.getEditor().getText().isEmpty() && !fromComboBox.getEditor().getText().isBlank()
                && !toComboBox.getEditor().getText().isEmpty() && !toComboBox.getEditor().getText().isBlank()
                && !dateOfJourney.getEditor().getText().isEmpty() && !timePeriodComboBox.getSelectionModel().isSelected(0)){


            //this task object is letting us to get the time for fetching the data from database
            Task<Void> task = new Task<>() {
                @Override
                public Void call() {

                scaleIt(200, fetchProg, 1, 2);
                //TODO search the database for coach info here

                Platform.runLater(() ->{

                    Node[] info = new Node[10];

                    for(int i = 0; i < info.length; i++){

                        try{

                            FXMLLoader coachInfo = new FXMLLoader(getClass().getResource("/resources/coachInfo.fxml"));
                            info[i] = coachInfo.load();

                            coachInfoFXML.add(coachInfo);

                            CoachInfoController cic = coachInfo.getController();
                            cic.setRpc(rpc);

                            if(i == 0){

                                cic.updateInfo("BDT 50.00", "AC (Bi)");
                            }

                            if(i == 3){

                                cic.updateInfo("BDT 50.00", "Non AC");
                            }

                            if(i == 2){

                                cic.updateInfo("BDT 50.00", "DD");
                            }

                            if(i == 4){

                                cic.updateInfo("BDT 1500.00", "Sleeper");
                            }

                            coachInfoBox.getChildren().add(info[i]);

                        } catch (IOException ignored){

                        }
                    }
                });

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                scaleIt(200, fetchProg, 0, 2);

                return null;
                }
            };
            task.setOnSucceeded(e -> {

                translateIt(300, fromComboBox, -80, 2);
                translateIt(300, toComboBox, -80, 2);
                translateIt(300, dateOfReturn, -80, 2);
                translateIt(300, dateOfJourney, -80, 2);
                translateIt(300, resetButton, -80, 2);

                translateIt(300, timePeriodComboBox, 1300, 1);
                translateIt(300, coachType, 1300, 1);
                translateIt(300, coachTypeVBox, 1300, 1);

                TranslateTransition searchTransition = new TranslateTransition(Duration.millis(300), searchButton);
                searchTransition.setToX(1300);
                searchTransition.play();

                searchTransition.setOnFinished((et) ->{

                    scaleIt(200, coachInfoPane, 1, 3);
                    translateIt(200, backToReserveBtn, 80, 1);

                });

            });
            new Thread(task).start();
        } else{

            if(fromComboBox.getEditor().getText().isEmpty() || fromComboBox.getEditor().getText().isBlank()){

                fromComboBox.setStyle("-fx-border-color: red");
            }

            if(toComboBox.getEditor().getText().isEmpty() || toComboBox.getEditor().getText().isBlank()){

                toComboBox.setStyle("-fx-border-color: red");
            }

            if(dateOfJourney.getEditor().getText().isEmpty()){

                dateOfJourney.getStyleClass().add("date-picker-error");
            }

            if(timePeriodComboBox.getSelectionModel().isSelected(0)){

                timePeriodComboBox.getStyleClass().add("combo-box-base-error");
            }

            // error dialog box is generated here
            BoxBlur blur = new BoxBlur(6, 6, 6);

            FXMLLoader error = new FXMLLoader(getClass().getResource("/resources/infoDialog.fxml"));
            try {

                Region errorLoader = error.load();

                InfoDialogController idc = error.getController();
                idc.setDialogBody("You must fill up \"From\", \"To\", \"Date Of Journey\" and \"Time Period\" field.");

                JFXDialog errorDialog = new JFXDialog(rootStack, errorLoader, JFXDialog.DialogTransition.TOP);

                idc.setDialog(errorDialog);
                errorDialog.show();
                errorDialog.setOnDialogClosed((JFXDialogEvent event) -> rootAnchor.setEffect(null));
                rootAnchor.setEffect(blur);

            } catch (IOException ignored) {

            }

        }

    }

    /**
     * this method hides the coach information details
     */
    public void onClickBackToReserveButton() {

        scaleIt(200, coachInfoPane, 0, 3);

        TranslateTransition backTransition = new TranslateTransition(Duration.millis(200), backToReserveBtn);
        backTransition.setToX(0);
        backTransition.play();

        backTransition.setOnFinished((e) ->{

            translateIt(300, fromComboBox, 0, 2);
            translateIt(300, toComboBox, 0, 2);
            translateIt(300, dateOfReturn, 0, 2);
            translateIt(300, dateOfJourney, 0, 2);
            translateIt(300, resetButton, 0, 2);

            translateIt(300, timePeriodComboBox, 0, 1);
            translateIt(300, coachType, 0, 1);
            translateIt(300, coachTypeVBox, 0, 1);
            translateIt(300, searchButton, 0, 1);

            coachInfoBox.getChildren().clear();
            coachInfoFXML.clear();

        });
    }

    /**
     * this method shows the seat map to the user
     */
    public void showSeatMap(String coachType){

        coachTypeSeatMapText.setText(coachType);
        proceedBtn.setDisable(true);

        //this task object is letting us to get the time for fetching the data from database
        Task<Void> task = new Task<>() {
            @Override
            public Void call() {

                scaleIt(200, fetchProg, 1, 2);

                AtomicReference<Character> c = new AtomicReference<>('A');

                Platform.runLater(() ->{

                    switch (coachType) {
                        case "Non AC": {

                            Node[] seatMap = new Node[11];

                            try {

                                FXMLLoader frontRow = new FXMLLoader(getClass().getResource("/resources/driverAndDoor.fxml"));
                                seatMap[0] = frontRow.load();
                                seatMapVBox.getChildren().add(seatMap[0]);

                            } catch (IOException ignored) {

                            }

                            for (int i = 1; i < seatMap.length - 1; i++) {

                                try {

                                    FXMLLoader seatRow = new FXMLLoader(getClass().getResource("/resources/economyClassSeatRow.fxml"));
                                    seatMap[i] = seatRow.load();

                                    EconomyClassSeatRowController ecsrc = seatRow.getController();
                                    ecsrc.setRpc(rpc);
                                    ecsrc.setSeatText(c + "-" + 1, c + "-" + 2, c + "-" + 3, c + "-" + 4);

                                    seatMapVBox.getChildren().add(seatMap[i]);

                                    c.getAndSet((char) (c.get() + 1));
                                } catch (IOException ignored) {

                                }
                            }

                            try {

                                FXMLLoader seatRow = new FXMLLoader(getClass().getResource("/resources/economyClassSeatLastRow.fxml"));
                                seatMap[10] = seatRow.load();

                                EconomyClassSeatLastRowController ecslrc = seatRow.getController();
                                ecslrc.setRpc(rpc);
                                ecslrc.setSeatText(c + "-" + 1, c + "-" + 2, c + "-" + 3, c + "-" + 4, c + "-" + 5);

                                seatMapVBox.getChildren().add(seatMap[10]);

                            } catch (IOException ignored) {

                            }
                            break;
                        }
                        case "AC (Multi)": {

                            Node[] seatMap = new Node[11];

                            try {

                                FXMLLoader frontRow = new FXMLLoader(getClass().getResource("/resources/driverAndDoor.fxml"));
                                seatMap[0] = frontRow.load();
                                seatMapVBox.getChildren().add(seatMap[0]);

                            } catch (IOException ignored) {

                            }

                            for (int i = 1; i < seatMap.length - 1; i++) {

                                try {

                                    FXMLLoader seatRow = new FXMLLoader(getClass().getResource("/resources/businessClassSeatRow.fxml"));
                                    seatMap[i] = seatRow.load();

                                    BusinessClassSeatRowController bcsrc = seatRow.getController();
                                    bcsrc.setRpc(rpc);
                                    bcsrc.setSeatText(c + "-" + 1, c + "-" + 2, c + "-" + 3);

                                    seatMapVBox.getChildren().add(seatMap[i]);

                                    c.getAndSet((char) (c.get() + 1));
                                } catch (IOException ignored) {

                                }
                            }

                            try {

                                FXMLLoader seatRow = new FXMLLoader(getClass().getResource("/resources/businessClassSeatLastRow.fxml"));
                                seatMap[10] = seatRow.load();

                                BusinessClassSeatLastRowController bcslrc = seatRow.getController();
                                bcslrc.setRpc(rpc);
                                bcslrc.setSeatText(c + "-" + 1, c + "-" + 2, c + "-" + 3, c + "-" + 4);

                                seatMapVBox.getChildren().add(seatMap[10]);

                            } catch (IOException ignored) {

                            }
                            break;
                        }
                        case "AC (Bi)": {

                            Node[] seatMap = new Node[10];

                            try {

                                FXMLLoader frontRow = new FXMLLoader(getClass().getResource("/resources/driverAndDoor.fxml"));
                                seatMap[0] = frontRow.load();
                                seatMapVBox.getChildren().add(seatMap[0]);

                            } catch (IOException ignored) {

                            }

                            for (int i = 1; i < seatMap.length - 1; i++) {

                                try {

                                    FXMLLoader seatRow = new FXMLLoader(getClass().getResource("/resources/businessClassSeatRow.fxml"));
                                    seatMap[i] = seatRow.load();

                                    BusinessClassSeatRowController bcsrc = seatRow.getController();
                                    bcsrc.setRpc(rpc);
                                    bcsrc.setSeatText(c + "-" + 1, c + "-" + 2, c + "-" + 3);

                                    seatMapVBox.getChildren().add(seatMap[i]);

                                    c.getAndSet((char) (c.get() + 1));
                                } catch (IOException ignored) {

                                }
                            }

                            try {

                                FXMLLoader seatRow = new FXMLLoader(getClass().getResource("/resources/businessClassSeatLastRow.fxml"));
                                seatMap[9] = seatRow.load();

                                BusinessClassSeatLastRowController bcslrc = seatRow.getController();
                                bcslrc.setRpc(rpc);
                                bcslrc.setSeatText(c + "-" + 1, c + "-" + 2, c + "-" + 3, c + "-" + 4);

                                seatMapVBox.getChildren().add(seatMap[9]);

                                seatMapVBox.setLayoutY(44);

                            } catch (IOException ignored) {

                            }
                            break;
                        }
                        case "DD": {

                            radioDeckVBox.setVisible(true);

                            updateTheDeck("Lower Deck", c, "DD");

                            break;
                        }

                        case "Sleeper": {

                            radioDeckVBox.setVisible(true);

                            updateTheDeck("Lower Deck", c, "Sleeper");

                            break;
                        }
                    }

                });

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                scaleIt(200, fetchProg, 0, 2);

                return null;
            }
        };
        task.setOnSucceeded(e -> {

            ScaleTransition scalePane = new ScaleTransition(Duration.millis(300), dialogStack);
            scalePane.setToY(1);
            scalePane.play();

            scalePane.setOnFinished((ep) ->{

                translateIt(200, seatMapDetailsPane, 880, 1);
                translateIt(200, seatMapVBox, -404, 1);

            });

        });
        new Thread(task).start();

    }

    /**
     * this method calculates the total fare and updates the total fare table
     */
    public void updateSelectedSeatBox(String seatNo, boolean isSelected){

        try{

            if(isSelected){

                FXMLLoader seatRow = new FXMLLoader(getClass().getResource("/resources/selectedSeatRow.fxml"));
                selectedSeats[selectedSeatCount] = seatRow.load();
                selectedSeatsFXML.add(seatRow);

                SelectedSeatRowController ssrc = seatRow.getController();

                if(radioDeckVBox.isVisible()){

                    if(radioLower.isSelected()){

                        ssrc.setSeatText("L" + seatNo);
                        selectedSeatString.add("L" + seatNo);
                    } else{

                        ssrc.setSeatText("U" + seatNo);
                        selectedSeatString.add("U" + seatNo);
                    }
                } else{

                    ssrc.setSeatText(seatNo);
                    selectedSeatString.add(seatNo);
                }

                ssrc.setFareText(seatMapDetailsFare.getText());

                selectedSeatBox.getChildren().add(selectedSeats[selectedSeatCount]);

                String fareParSeat = seatMapDetailsFare.getText().substring(4);
                String totalFareCount = totalFare.getText().substring(4);

                int total = Integer.parseInt(totalFareCount) +  Integer.parseInt(fareParSeat);

                totalFare.setText("BDT " + total);

                selectedSeatCount++;
                proceedBtn.setDisable(selectedSeatCount == 0);
            } else{

                Platform.runLater(() ->{

                    for(int i = 0; i <= selectedSeatCount; i++){

                        try{

                            FXMLLoader deleteSeatRow;

                            deleteSeatRow = selectedSeatsFXML.get(i);
                            SelectedSeatRowController ssrc = deleteSeatRow.getController();

                            if(ssrc.getSeatText().equals(seatNo) || (ssrc.getSeatText().equals("U"+seatNo) && radioUpper.isSelected()) || (ssrc.getSeatText().equals("L"+seatNo) && radioLower.isSelected())){

                                String fareParSeat = seatMapDetailsFare.getText().substring(4);
                                String totalFareCount = totalFare.getText().substring(4);

                                int total = Integer.parseInt(totalFareCount) -  Integer.parseInt(fareParSeat);

                                totalFare.setText("BDT " + total);

                                selectedSeatBox.getChildren().remove(i);
                                selectedSeatsFXML.remove(i);
                                selectedSeatCount--;
                                selectedSeatString.remove(i);
                                proceedBtn.setDisable(selectedSeatCount == 0);
                                break;
                            }

                        } catch (IndexOutOfBoundsException e){

                            // error dialog box is generated here
                            BoxBlur blur = new BoxBlur(6, 6, 6);

                            FXMLLoader error = new FXMLLoader(getClass().getResource("/resources/infoDialog.fxml"));
                            try {

                                Region errorLoader = error.load();

                                InfoDialogController idc = error.getController();
                                idc.setDialogBody("Sorry, you cannot select more than 4 seats at a time.");

                                JFXDialog errorDialog = new JFXDialog(dialogStack, errorLoader, JFXDialog.DialogTransition.TOP);

                                idc.setDialog(errorDialog);
                                errorDialog.show();
                                errorDialog.setOnDialogClosed((JFXDialogEvent event) -> viewSeatPane.setEffect(null));
                                viewSeatPane.setEffect(blur);

                            } catch (IOException ignored) {

                            }

                        }

                    }
                });
            }

        } catch (IOException ignored){

        }

    }

    /**
     *  this method takes user back to coach info page when the button is clicked
     */
    public void onClickBackToCoachInfoButton(){

        selectedSeatCount = 0;
        lowerDeckDD.clear();
        upperDeckDD.clear();
        lowerDeckSleeper.clear();
        upperDeckSleeper.clear();
        selectedSeatsFXML.clear();
        selectedSeatString.clear();
        radioDeckVBox.setVisible(false);
        radioLower.setSelected(true);
        boardingPointComboBox.getSelectionModel().selectFirst();
        droppingPointComboBox.getSelectionModel().selectFirst();

        translateIt(300, seatMapDetailsPane, 0, 1);

        TranslateTransition mapTransition = new TranslateTransition(Duration.millis(300), seatMapVBox);

        mapTransition.setToX(0);
        mapTransition.play();

        mapTransition.setOnFinished((e) ->{

            scaleIt(200, dialogStack, 0, 2);
            seatMapVBox.getChildren().clear();
            selectedSeatBox.getChildren().clear();
            seatMapVBox.setLayoutY(5);

            for (FXMLLoader update : coachInfoFXML) {

                CoachInfoController cic = update.getController();
                cic.buttonPressAvailable(false);
            }
        });
    }

    /**
     * this method changes the deck in the seat map
     */
    public void updateTheDeck(String deckType,  AtomicReference<Character> c, String coachType){

        if(deckType.equals("Lower Deck")){

            seatMapVBox.getChildren().clear();

            Node[] seatMap;

            if(coachType.equals("Sleeper")){

                seatMap = new Node[6];

                if(lowerDeckSleeper.size() != 0){

                    for(int i = 0; i < lowerDeckSleeper.size(); i++){

                        seatMap[i] = lowerDeckSleeper.get(i);
                        seatMapVBox.getChildren().add(seatMap[i]);
                    }
                } else{

                    try {

                        FXMLLoader frontRow = new FXMLLoader(getClass().getResource("/resources/driverAndDoor.fxml"));
                        seatMap[0] = frontRow.load();
                        lowerDeckSleeper.add(seatMap[0]);
                        seatMapVBox.getChildren().add(seatMap[0]);

                    } catch (IOException ignored) {

                    }

                    for (int i = 1; i < seatMap.length; i++) {

                        try {

                            FXMLLoader seatRow = new FXMLLoader(getClass().getResource("/resources/sleeperSeatRow.fxml"));
                            seatMap[i] = seatRow.load();
                            lowerDeckSleeper.add(seatMap[i]);

                            SleeperSeatRowController ssrc = seatRow.getController();
                            ssrc.setRpc(rpc);
                            ssrc.setSeatText(c + "-" + 1, c + "-" + 2, c + "-" + 3);

                            seatMapVBox.getChildren().add(seatMap[i]);

                            c.getAndSet((char) (c.get() + 1));
                        } catch (IOException ignored) {

                        }
                    }

                }

                seatMapVBox.setLayoutY(42.5);

            } else {

                seatMap = new Node[8];

                if(lowerDeckDD.size() != 0){

                    for(int i = 0; i < lowerDeckDD.size(); i++){

                        seatMap[i] = lowerDeckDD.get(i);
                        seatMapVBox.getChildren().add(seatMap[i]);
                    }
                } else{

                    try {

                        FXMLLoader frontRow = new FXMLLoader(getClass().getResource("/resources/driverAndDoor.fxml"));
                        seatMap[0] = frontRow.load();
                        lowerDeckDD.add(seatMap[0]);
                        seatMapVBox.getChildren().add(seatMap[0]);

                    } catch (IOException ignored) {

                    }

                    for (int i = 1; i < 3; i++) {

                        try {

                            FXMLLoader blankRow = new FXMLLoader(getClass().getResource("/resources/blankSeatRow.fxml"));
                            seatMap[i] = blankRow.load();
                            lowerDeckDD.add(seatMap[i]);
                            seatMapVBox.getChildren().add(seatMap[i]);

                        } catch (IOException ignored) {

                        }
                    }

                    try {

                        FXMLLoader seatRow = new FXMLLoader(getClass().getResource("/resources/firstRowDD.fxml"));
                        seatMap[3] = seatRow.load();
                        lowerDeckDD.add(seatMap[3]);

                        FirstRowDDController bcsrc = seatRow.getController();
                        bcsrc.setRpc(rpc);
                        bcsrc.setSeatText(c + "-" + 1, c + "-" + 2);

                        seatMapVBox.getChildren().add(seatMap[3]);

                        c.getAndSet((char) (c.get() + 1));
                    } catch (IOException ignored) {

                    }


                    for (int i = 4; i < seatMap.length; i++) {

                        try {

                            FXMLLoader seatRow = new FXMLLoader(getClass().getResource("/resources/businessClassSeatRow.fxml"));
                            seatMap[i] = seatRow.load();
                            lowerDeckDD.add(seatMap[i]);

                            BusinessClassSeatRowController bcsrc = seatRow.getController();
                            bcsrc.setRpc(rpc);
                            bcsrc.setSeatText(c + "-" + 1, c + "-" + 2, c + "-" + 3);

                            seatMapVBox.getChildren().add(seatMap[i]);

                            c.getAndSet((char) (c.get() + 1));
                        } catch (IOException ignored) {

                        }
                    }
                }

                seatMapVBox.setLayoutY(77.5);
            }


        } else{

            seatMapVBox.getChildren().clear();

            Node[] seatMap;

            if(coachType.equals("Sleeper")){

                seatMap = new Node[6];

                if(upperDeckSleeper.size() != 0){

                    for(int i = 0; i < upperDeckSleeper.size(); i++){

                        seatMap[i] = upperDeckSleeper.get(i);
                        seatMapVBox.getChildren().add(seatMap[i]);
                    }
                } else{

                    try {

                        FXMLLoader blankRow = new FXMLLoader(getClass().getResource("/resources/blankSeatRow.fxml"));
                        seatMap[0] = blankRow.load();
                        upperDeckSleeper.add(seatMap[0]);
                        seatMapVBox.getChildren().add(seatMap[0]);

                    } catch (IOException ignored) {

                    }

                    for (int i = 1; i < seatMap.length; i++) {

                        try {

                            FXMLLoader seatRow = new FXMLLoader(getClass().getResource("/resources/sleeperSeatRow.fxml"));
                            seatMap[i] = seatRow.load();
                            upperDeckSleeper.add(seatMap[i]);

                            SleeperSeatRowController ssrc = seatRow.getController();
                            ssrc.setRpc(rpc);
                            ssrc.setSeatText(c + "-" + 1, c + "-" + 2, c + "-" + 3);

                            seatMapVBox.getChildren().add(seatMap[i]);

                            c.getAndSet((char) (c.get() + 1));
                        } catch (IOException ignored) {

                        }
                    }
                }

                seatMapVBox.setLayoutY(42.5);

            } else{

                seatMap = new Node[10];

                if(upperDeckDD.size() != 0){

                    for(int i = 0; i < upperDeckDD.size(); i++){

                        seatMap[i] = upperDeckDD.get(i);
                        seatMapVBox.getChildren().add(seatMap[i]);
                    }
                } else{

                    for (int i = 0; i < 2; i++) {

                        try {

                            FXMLLoader seatRow = new FXMLLoader(getClass().getResource("/resources/firstRowDD.fxml"));
                            seatMap[i] = seatRow.load();
                            upperDeckDD.add(seatMap[i]);

                            FirstRowDDController bcsrc = seatRow.getController();
                            bcsrc.setRpc(rpc);
                            bcsrc.setSeatText(c + "-" + 1, c + "-" + 2);

                            seatMapVBox.getChildren().add(seatMap[i]);

                            c.getAndSet((char) (c.get() + 1));
                        } catch (IOException ignored) {

                        }
                    }

                    for (int i = 2; i < seatMap.length-1; i++) {

                        try {

                            FXMLLoader seatRow = new FXMLLoader(getClass().getResource("/resources/businessClassSeatRow.fxml"));
                            seatMap[i] = seatRow.load();
                            upperDeckDD.add(seatMap[i]);

                            BusinessClassSeatRowController bcsrc = seatRow.getController();
                            bcsrc.setRpc(rpc);
                            bcsrc.setSeatText(c + "-" + 1, c + "-" + 2, c + "-" + 3);

                            seatMapVBox.getChildren().add(seatMap[i]);

                            c.getAndSet((char) (c.get() + 1));
                        } catch (IOException ignored) {

                        }
                    }

                    try {

                        FXMLLoader seatRow = new FXMLLoader(getClass().getResource("/resources/businessClassSeatLastRow.fxml"));
                        seatMap[9] = seatRow.load();
                        upperDeckDD.add(seatMap[9]);

                        BusinessClassSeatLastRowController bcslrc = seatRow.getController();
                        bcslrc.setRpc(rpc);
                        bcslrc.setSeatText(c + "-" + 1, c + "-" + 2, c + "-" + 3, c + "-" + 4);

                        seatMapVBox.getChildren().add(seatMap[9]);

                    } catch (IOException ignored) {

                    }
                }

                seatMapVBox.setLayoutY(62.5);
            }


        }
    }

    /**
     * this method is called when the proceed button is clicked
     */
    public void onClickProceedButton(javafx.event.Event actionEvent){

        if(boardingPointComboBox.getSelectionModel().isSelected(0)
                || droppingPointComboBox.getSelectionModel().isSelected(0)){

            if(boardingPointComboBox.getSelectionModel().isSelected(0)){

                boardingPointComboBox.getStyleClass().add("combo-box-base-error");
            }

            if( droppingPointComboBox.getSelectionModel().isSelected(0)){

                droppingPointComboBox.getStyleClass().add("combo-box-base-error");
            }

            // error dialog box is generated here
            BoxBlur blur = new BoxBlur(6, 6, 6);

            FXMLLoader error = new FXMLLoader(getClass().getResource("/resources/infoDialog.fxml"));
            try {

                Region errorLoader = error.load();

                InfoDialogController idc = error.getController();
                idc.setDialogBody("Please, select a boarding and a dropping point.");

                JFXDialog errorDialog = new JFXDialog(dialogStack, errorLoader, JFXDialog.DialogTransition.TOP);

                idc.setDialog(errorDialog);
                errorDialog.show();
                errorDialog.setOnDialogClosed((JFXDialogEvent event) -> viewSeatPane.setEffect(null));
                viewSeatPane.setEffect(blur);

            } catch (IOException ignored) {

            }

        } else{

            try{

                FXMLLoader confirmationPage = new FXMLLoader(getClass().getResource("/resources/confirmationPage.fxml"));
                Parent page = confirmationPage.load();
                Scene pageScene = new Scene(page);

                ConfirmationPageController cpc = confirmationPage.getController();

                StringBuilder seat = null;

                for(int i = 0; i < selectedSeatString.size(); i++){

                    if(i == 0){

                        seat = new StringBuilder(selectedSeatString.get(i));
                    } else {

                        seat.append(", ").append(selectedSeatString.get(i));
                    }
                }

                //TODO pass trip details here...

                assert seat != null;
                cpc.updateTripData(seat.toString(), totalFare.getText());

                Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                window.setScene(pageScene);
                window.show();

            } catch (IOException ignored){

            }
        }

    }

    /**
     * this method change the color of boarding combobox if it left empty and clicked again
     */
    public void onClickBoardingPointComboBox(){

        boardingPointComboBox.getStyleClass().remove("combo-box-base-error");
    }

    /**
     * this method change the color of dropping combobox if it left empty and clicked again
     */
    public void onClickDroppingPointComboBox(){

        droppingPointComboBox.getStyleClass().remove("combo-box-base-error");
    }

    /**
     * initializes the scene
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        boardingPointComboBox.getItems().addAll(boardingPoints);
        droppingPointComboBox.getItems().addAll(droppingPoints);

        boardingPointComboBox.getSelectionModel().selectFirst();
        droppingPointComboBox.getSelectionModel().selectFirst();

        selectedSeatCount = 0;

        proceedBtn.setDisable(true);

        //this toggle group is to toggle the deck radio buttons

        ToggleGroup deckGroup = new ToggleGroup();

        radioUpper.setToggleGroup(deckGroup);
        radioLower.setToggleGroup(deckGroup);

        radioLower.setSelected(true);

        deckGroup.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (deckGroup.getSelectedToggle() != null) {

                if(radioLower.isSelected()){

                    updateTheDeck("Lower Deck", new AtomicReference<>('A'), coachTypeSeatMapText.getText());
                }

                if(radioUpper.isSelected()){

                    updateTheDeck("Upper Deck", new AtomicReference<>('A'), coachTypeSeatMapText.getText());
                }
            }
        });

        fromComboBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> fromComboBox.setStyle("-fx-border-color: #007EfC"));

        toComboBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> toComboBox.setStyle("-fx-border-color: #007EfC"));

        radioDeckVBox.setVisible(false);

        translateIt(500, reservation, -343, 1);
        translateIt(500, page, -199, 1);
        translateIt(500, revPane, 1300, 1);
        translateIt(500, homeButton, 165, 1);

        destination = new ArrayList<>(Arrays.asList(destinations));

        fromComboBox.getItems().addAll(destination);
        toComboBox.getItems().addAll(destination);
        timePeriodComboBox.getItems().addAll(timePeriod);

        timePeriodComboBox.getSelectionModel().selectFirst();

        dateOfJourney.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0);
            }
        });

        dateOfReturn.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0);
            }
        });

        new AutoCompleteComboBoxListener<>(fromComboBox);
        new AutoCompleteComboBoxListener<>(toComboBox);

        toComboBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {

            String tempFrom = fromComboBox.getEditor().getText();
            fromComboBox.getItems().clear();
            fromComboBox.getEditor().setText(tempFrom);
            fromComboBox.getItems().addAll(destination);
            new AutoCompleteComboBoxListener<>(fromComboBox);

            if (!isToTrimDone) {

                String d = null;

                for (String s : destination) {

                    if (fromComboBox.getEditor().getText().equals(s)) {

                        d = s;
                        break;
                    }
                }

                destination.remove(d);
                toComboBox.getItems().clear();
                toComboBox.getItems().addAll(destination);
                new AutoCompleteComboBoxListener<>(toComboBox);
                destination = new ArrayList<>(Arrays.asList(destinations));
                isToTrimDone = true;
                isFromTrimDone = false;
            }

        });

        fromComboBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {

            String tempTo = toComboBox.getEditor().getText();
            toComboBox.getItems().clear();
            toComboBox.getEditor().setText(tempTo);
            toComboBox.getItems().addAll(destination);
            new AutoCompleteComboBoxListener<>(toComboBox);

            if (!isFromTrimDone) {

                System.out.println();

                String d = null;

                for (String s : destination) {

                    if (toComboBox.getEditor().getText().equals(s)) {

                        d = s;
                        break;
                    }
                }

                destination.remove(d);
                fromComboBox.getItems().clear();
                fromComboBox.getItems().addAll(destination);
                new AutoCompleteComboBoxListener<>(fromComboBox);
                destination = new ArrayList<>(Arrays.asList(destinations));
                isFromTrimDone = true;
                isToTrimDone = false;
            }

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

    public boolean isFourSeatsSelected(){

        return selectedSeatCount != 4;
    }

}
