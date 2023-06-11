
package com.example.logineregistro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.*;

import java.io.IOException;

public class DBUTILS {

    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username) {
        Parent root = null;

        if (username != null) {
            try {
                FXMLLoader loader = new FXMLLoader(DBUTILS.class.getResource(fxmlFile));
                root = loader.load();
                loginControler loginControler = loader.getController();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try{
                root = FXMLLoader.load (DBUTILS.class.getResource(fxmlFile));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    public static void SignUpUser(ActionEvent event, String username, String Password){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckuserexist = null;
        ResultSet resultsSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafxloguin", "root", "qwerty1234");
            psCheckuserexist = connection.prepareStatement("Select * from users where username = ?");
            psCheckuserexist.setString(1,username);
            resultsSet = psCheckuserexist.executeQuery();

            if(resultsSet.isBeforeFirst()){
                System.out.println("User already exist !!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("U cannot use this username");
                alert.show();
            }else {
                psInsert = connection.prepareStatement("Insert into users(username, password ) Values ( ?, ?)");
                psInsert.setString(1,username);
                psInsert.setString(2,Password);
                psInsert.executeUpdate();

                changeScene(event, "logged_in.fxml", "PedradaDigitalWeb", username);
            }
        }catch (SQLException e){
            e.printStackTrace();

        }finally {
            if (resultsSet != null){
                try {
                    resultsSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }

            }
            if (psCheckuserexist !=null){
                try{
                    psCheckuserexist.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(psInsert != null){
                try {
                    psInsert.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }

            }
            if(connection != null){
                try{
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void loginUser(ActionEvent event, String username,String password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafxloguin", "root", "qwerty1234");
            preparedStatement  = connection.prepareStatement("Select password from users where username = ?");
            preparedStatement.setString(1,username);
            resultSet = preparedStatement.executeQuery();


            if(!resultSet.isBeforeFirst()) {
                System.out.println("User not found in database!!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provide credentials are incorrect");
                alert.show();
            }else{
                while(resultSet.next()) {
                    String retrievedPassword = resultSet.getString("password");
                    if(retrievedPassword.equals(password)){
                        changeScene(event, "logged_in.fxml", "Welcome!!", username);
                    }else {
                        System.out.println("Password did not match!!");
                        Alert alert  = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("The provided credentials are incorrect");
                        alert.show();
                    }
                }
            }


        }catch (SQLException e ){
            e.printStackTrace();
        }finally {
            if(resultSet != null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(preparedStatement != null){
                try{
                    preparedStatement.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try{
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }

            }
        }
    }

}
























