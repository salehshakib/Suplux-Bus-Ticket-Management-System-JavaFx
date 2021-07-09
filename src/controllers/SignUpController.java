package controllers;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    /*
    * variable initialization
    */
    @FXML
    public Button crossButton, profileImageUploadBtn, proceedBtn;
    @FXML
    public RadioButton radioMale, radioFemale, radioOthers;
    @FXML
    public javafx.scene.image.ImageView profileImageView;
    public TextField nidField, bRegField;
    public ScrollPane signUpForm;
    public SVGPath rightArrowSvg;
    public Text signUp, form;

    //this method is to close the application
    public void onClickCrossButton(){

        System.exit(0);
    }

    /*
    * this method opens the fileChooser window when "Upload a Profile Image" button
    * is clicked so the user can select an image for profile picture
    */
    public void onClickUploadAProfileImage() throws FileNotFoundException {

        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));

        File proPic = fc.showOpenDialog(null);

        if(proPic != null){

            showImageInImageView(proPic.getAbsolutePath());
        }
    }

    //this method shows the selected image in the image view
    public void showImageInImageView(String imagePath) throws FileNotFoundException {

        InputStream s = new FileInputStream(imagePath);
        Image pic = new Image(s);

        centerTheImage(pic);
        profileImageView.setImage(pic);
    }

    //this method centers the image in the frame
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

    /*
    * this method is to disable the birth registration number field as user
    * just give the information only for their NID or registration number
    */
    public void isNidFieldEmptyOrNot(){

        bRegField.setDisable(!nidField.getText().isEmpty());
    }

    /*
    * this method is to disable the NID number field as user
    * just give the information only for their NID or registration number
    */
    public void isBRegFieldEmptyOrNot(){

        nidField.setDisable(!bRegField.getText().isEmpty());
    }

    //this method changes color of the right arrow on hovering on the "Proceed" button
    public void onHoverProceedButton(){

        rightArrowSvg.setFill(Color.rgb(0, 126, 252));
    }

    //this method changes color of the right arrow on exiting on the "Proceed" button
    public void onExitProceedButton(){

        rightArrowSvg.setFill(Color.WHITE);
    }

    //this method is called on clicking the "Proceed" button
    public void onClickProceedButton(){

        //TODO database sign up uploading credentials should be done here...

        rightArrowSvg.setFill(Color.WHITE);
    }

    //this method is to set the initial stage
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //this toggle group is to toggle the gender radio buttons

        ToggleGroup genderGroup = new ToggleGroup();

        radioMale.setToggleGroup(genderGroup);
        radioFemale.setToggleGroup(genderGroup);
        radioOthers.setToggleGroup(genderGroup);

        //to animate the form

        TranslateTransition sTransition = new TranslateTransition(Duration.millis(500), signUp);
        TranslateTransition fTransition = new TranslateTransition(Duration.millis(500), form);
        TranslateTransition scrollTransition = new TranslateTransition(Duration.millis(500), signUpForm);

        sTransition.setToX(-207);
        fTransition.setToX(-223);
        scrollTransition.setToX(1300);

        sTransition.play();
        fTransition.play();
        scrollTransition.play();
    }
}
