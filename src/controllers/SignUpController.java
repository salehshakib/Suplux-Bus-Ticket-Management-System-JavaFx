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
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.codejava.sql.ConnectorDB;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    /**
    * variable initialization
    */
    @FXML
    public Button crossButton, profileImageUploadBtn, proceedBtn, backBtn;
    public RadioButton radioMale, radioFemale, radioOthers;
    public javafx.scene.image.ImageView profileImageView;
    public TextField nidField, bRegField, formFirstName, formLastName, formMobile, formOTP;
    public ScrollPane signUpForm;
    public SVGPath rightArrowSvg;
    public Text signUp, form;
    public StackPane rootStack;
    public Pane rootPane, imagePane;
    public com.gluonhq.charm.glisten.control.ProgressBar fetchProg;

    private String formEmail, formPassword, otp;
    private File proPic;
    private String imageFile;
    private String fileName;

    public SignUpController() throws SQLException {
    }


    /**
     * this method is to close the application
     */

    public void onClickCrossButton(){

        System.exit(0);
    }

    public void userData(String formEmail, String formPassword, String otp){
        this.formEmail = formEmail;
        this.formPassword = formPassword;
        this.otp = otp;

    }






    /**
    * this method opens the fileChooser window when "Upload a Profile Image" button
    * is clicked so the user can select an image for profile picture
    */
    public void onClickUploadAProfileImage() throws IOException {

        imagePane.setStyle("-fx-border-color:  #007EfC");

        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*jpeg"));

        proPic = fc.showOpenDialog(null);

        if(proPic != null){

            try {
                String newPath = "src/upload/";
                File directory = new File(newPath);
                if(!directory.exists()){
                    directory.mkdirs();
                }

                LocalDate userIdCreatedDate = LocalDate.now();
                Random random = new Random();
                String randomNumber = Integer.toString(1123 + random.nextInt(9475)) + "-" + Integer.toString(1123 + random.nextInt(9475));


                String profilePictureName =  userIdCreatedDate + "-" + randomNumber;
                String extension =  proPic.getAbsolutePath().substring(proPic.getAbsolutePath().lastIndexOf("\\")+1);
                extension = extension.substring(extension.lastIndexOf('.')+1);

                File sourceFile = new File(proPic.getAbsolutePath());
                fileName =profilePictureName + "."+ extension;
                File destinationFile = new File(newPath+ profilePictureName + "."+ extension);
                System.out.println(fileName);


                imageFile = destinationFile.getAbsolutePath();

                System.out.println(imageFile);

                Files.copy(sourceFile.toPath(), destinationFile.toPath());
                System.out.println(destinationFile.getAbsolutePath());


            } catch (IOException e) {
                System.out.println(imageFile);

                e.printStackTrace();
            }
            showImageInImageView(proPic.getAbsolutePath());
        }
    }

    /**
     * this method shows the selected image in the image view
     */
    public void showImageInImageView(String imagePath) throws FileNotFoundException {

        InputStream s = new FileInputStream(imagePath);
        Image pic = new Image(s);

        centerTheImage(pic);
        profileImageView.setImage(pic);
    }

    /**
     * this method centers the image in the frame
     */
    public void centerTheImage(Image img) {

        if (img != null) {
            double w;
            double h;

            double ratioX = profileImageView.getFitWidth() / img.getWidth();
            double ratioY = profileImageView.getFitHeight() / img.getHeight();

            double reduceCoEff;
            reduceCoEff = Math.min(ratioX, ratioY);

            w = img.getWidth() * reduceCoEff;
            h = img.getHeight() * reduceCoEff;

            profileImageView.setX((profileImageView.getFitWidth() - w) / 2);
            profileImageView.setY((profileImageView.getFitHeight() - h) / 2);

        }
    }

    /**
    * this method is to disable the birth registration number field as user
    * just give the information only for their NID or registration number
    */
    public void isNidFieldEmptyOrNot(){

        bRegField.setDisable(!nidField.getText().isEmpty());
    }

    /**
    * this method is to disable the NID number field as user
    * just give the information only for their NID or registration number
    */
    public void isBRegFieldEmptyOrNot(){

        nidField.setDisable(!bRegField.getText().isEmpty());
    }

    /**
     * this method is called on clicking the "Proceed" button
     */
    public void onClickProceedButton(javafx.event.ActionEvent actionEvent){

        if(((!nidField.getText().isEmpty() && !nidField.getText().isBlank())
                || (!bRegField.getText().isEmpty() && !bRegField.getText().isBlank()))
                && !formFirstName.getText().isEmpty() && !formFirstName.getText().isBlank()
                && !formLastName.getText().isEmpty() && !formLastName.getText().isBlank()
                && !formMobile.getText().isEmpty() && !formMobile.getText().isBlank()
                && !formOTP.getText().isEmpty() && !formOTP.getText().isBlank()
                && proPic != null && formOTP.getText().equals(otp)){

            //this task object is letting us get the time for fetching the data from database
            Task<Void> task = new Task<>() {
                @Override
                public Void call() {

                    scaleIt(200, fetchProg, 1, 2);

                    Platform.runLater(() ->{

                        try {
                            ConnectorDB connectorDB = new ConnectorDB();


                            String gender = null;



                            String firstName = formFirstName.getText();
                            String lastName = formLastName.getText();
                            String mobileNo = formMobile.getText();
                            String email = formEmail;
                            String password = formPassword;
                            String otp = formOTP.getText();
                            String passport = null;
                            if (radioMale.isSelected()){
                                gender = "Male";
                            }
                            else if (radioFemale.isSelected()){
                                gender = "female";
                            }
                            else {
                                gender = "Others";
                            }

                            String nid = nidField.getText();
                            String bReg = bRegField.getText();

                            System.out.println(email + "\n" + gender + "\n" + firstName + "\n"+ lastName + "\n" + mobileNo + "\n"+ email + "\n"+ password + "\n" + otp + "\n" + fileName);

                            if (bReg.equals("")){
                                bReg = "NULL";
                            } else {
                                nid = "NULL";
                            }

                            String sqlQuery = "INSERT INTO userInformation (userEmail," +
                                    "userPassword, " +
                                    "userFirstName, " +
                                    "userLastName, " +
                                    "userGender, " +
                                    "userPhoneNumber, " +
                                    "userNID, " +
                                    "userBReg, " +
                                    "userPassport, " +
                                    "userImage) " +
                                    "Values(?,?,?,?,?,?,?,?,?,?)";

                            PreparedStatement preparedStatement = connectorDB.getConnection().prepareStatement(sqlQuery);
                            preparedStatement.setString(1,email);
                            preparedStatement.setString(2,password);
                            preparedStatement.setString(3,firstName);
                            preparedStatement.setString(4,lastName);
                            preparedStatement.setString(5,gender);
                            preparedStatement.setString(6,mobileNo);
                            preparedStatement.setString(7,nid);
                            preparedStatement.setString(8,bReg);
                            preparedStatement.setString(9, null);
                            preparedStatement.setString(10,fileName);

                            preparedStatement.executeUpdate();



                        } catch (SQLException e) {
                            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
                            e.printStackTrace();
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

                // account created successfully dialog box is generated here
                BoxBlur blur = new BoxBlur(6, 6, 6);

                FXMLLoader success = new FXMLLoader(getClass().getResource("/resources/infoDialog.fxml"));
                try {

                    Region errorLoader = success.load();

                    InfoDialogController idc = success.getController();
                    idc.setDialogBody("Account created successfully!!! Please, log in to your account.");
                    idc.setDialogButtonText("Okay, Thank you");

                    JFXDialog errorDialog = new JFXDialog(rootStack, errorLoader, JFXDialog.DialogTransition.TOP);

                    idc.setDialog(errorDialog);
                    errorDialog.show();
                    errorDialog.setOnDialogClosed((JFXDialogEvent event) -> {

                        rootPane.setEffect(null);

                        try {

                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/home.fxml"));
                            Parent home = loader.load();

                            Scene homeScene = new Scene(home);

                            Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                            window.setScene(homeScene);
                            window.show();

                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    });
                    rootPane.setEffect(blur);

                } catch (IOException ignored) {

                }

            });
            new Thread(task).start();

        } else{

            if(!formOTP.getText().isBlank() && !formOTP.getText().isEmpty() && !formOTP.getText().equals(otp)) {

                formOTP.setStyle("-fx-border-color: red");

                // error dialog box is generated here when a text field is vacant
                BoxBlur blur = new BoxBlur(6, 6, 6);

                FXMLLoader error = new FXMLLoader(getClass().getResource("/resources/infoDialog.fxml"));
                try {

                    Region errorLoader = error.load();

                    InfoDialogController idc = error.getController();
                    idc.setDialogBody("OTP mismatched!!! Please, try again.");
                    idc.setDialogButtonText("Okay, I understand");

                    JFXDialog errorDialog = new JFXDialog(rootStack, errorLoader, JFXDialog.DialogTransition.TOP);

                    idc.setDialog(errorDialog);
                    errorDialog.show();
                    errorDialog.setOnDialogClosed((JFXDialogEvent event) -> rootPane.setEffect(null));
                    rootPane.setEffect(blur);

                } catch (IOException ignored) {

                }

            } else{

                // error dialog box is generated here when a text field is vacant
                BoxBlur blur = new BoxBlur(6, 6, 6);

                FXMLLoader error = new FXMLLoader(getClass().getResource("/resources/infoDialog.fxml"));
                try {

                    Region errorLoader = error.load();

                    InfoDialogController idc = error.getController();
                    idc.setDialogBody("Please fill up the form properly.");
                    idc.setDialogButtonText("Okay, I understand");

                    JFXDialog errorDialog = new JFXDialog(rootStack, errorLoader, JFXDialog.DialogTransition.TOP);

                    idc.setDialog(errorDialog);
                    errorDialog.show();
                    errorDialog.setOnDialogClosed((JFXDialogEvent event) -> rootPane.setEffect(null));
                    rootPane.setEffect(blur);

                } catch (IOException ignored) {

                }
            }

            if((nidField.getText().isBlank() || nidField.getText().isEmpty()) && !nidField.isDisabled()){

                nidField.setStyle("-fx-border-color: red");
            }

            if(!radioOthers.isSelected() && !radioMale.isSelected() && !radioFemale.isSelected()){

                radioFemale.getStyleClass().add("radio_button_error");
                radioMale.getStyleClass().add("radio_button_error");
                radioOthers.getStyleClass().add("radio_button_error");
            }

            if((bRegField.getText().isBlank() || bRegField.getText().isEmpty()) && !bRegField.isDisabled()){

                bRegField.setStyle("-fx-border-color: red");
            }

            if(formFirstName.getText().isBlank() || formFirstName.getText().isEmpty()){

                formFirstName.setStyle("-fx-border-color: red");
            }

            if(formLastName.getText().isBlank() || formLastName.getText().isEmpty()){

                formLastName.setStyle("-fx-border-color: red");
            }

            if(formMobile.getText().isBlank() || formMobile.getText().isEmpty()){

                formMobile.setStyle("-fx-border-color: red");
            }

            if(formOTP.getText().isBlank() || formOTP.getText().isEmpty()){

                formOTP.setStyle("-fx-border-color: red");
            }

            if(proPic == null){

                imagePane.setStyle("-fx-border-color: red");
            }
        }

    }

    /**
     * to change radio button design
     */
    public void onClickARadioButton(){

        radioFemale.getStyleClass().remove("radio_button_error");
        radioMale.getStyleClass().remove("radio_button_error");
        radioOthers.getStyleClass().remove("radio_button_error");
    }

    /**
     * to change nidField design
     */
    public void onClickNidField(){

        nidField.setStyle("-fx-border-color:  #007EfC");
        bRegField.setStyle("-fx-border-color:  #007EfC");
    }

    /**
     * to change first name field design
     */
    public void onClickFormFirstName(){

        formFirstName.setStyle("-fx-border-color:  #007EfC");
    }

    /**
     * to change last name field design
     */
    public void onClickFormLastName(){

        formLastName.setStyle("-fx-border-color:  #007EfC");
    }

    /**
     * to change mobile number field design
     */
    public void onClickFormMobile(){

        formMobile.setStyle("-fx-border-color:  #007EfC");
    }

    /**
     * to change OTP field design
     */
    public void onClickFormOTP(){

        formOTP.setStyle("-fx-border-color:  #007EfC");
    }

    /**
     * this method executes return from the page option
     */
    public void onClickBackButton(javafx.event.Event actionEvent){

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
     * this method is to set the initial stage
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //this toggle group is to toggle the gender radio buttons

        ToggleGroup genderGroup = new ToggleGroup();

        radioMale.setToggleGroup(genderGroup);
        radioFemale.setToggleGroup(genderGroup);
        radioOthers.setToggleGroup(genderGroup);

        //to animate the form

        translateIt(500, signUp, -207, 1);
        translateIt(500, form, -223, 1);
        translateIt(500, signUpForm, 1300, 1);
        translateIt(500, backBtn, 120, 1);

    }

    public void getEmailAndPassword(String email, String password, String otp){

        formEmail = email;
        formPassword = password;
        this.otp = otp;
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
}
