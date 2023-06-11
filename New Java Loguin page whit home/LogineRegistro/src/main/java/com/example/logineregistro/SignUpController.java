package com.example.logineregistro;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private TextField tf_username;
    @FXML
    private PasswordField tf_password;

    @FXML
    private Button button_signup2;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        button_signup2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!tf_username.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty()){
                    DBUTILS.SignUpUser(event, tf_username.getText(), tf_password.getText());
                }else {
                    System.out.println("Please fill all the information");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill all the information to sign up!!");
                    alert.show();
                }
            }
        });

    }
}
