package com.example.alex.controller;

import com.example.alex.MainApp;
import com.example.alex.SettingsClass;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Created by teacher on 08.11.16.
 */
public class LoginController {
    public Button quitBtn;
    public javafx.scene.layout.AnchorPane AnchorPane;
    public Button enterBtn;
    public PasswordField pass;
    public Button showSettingsBtn;

    @FXML
    public void initialize() {
        quitBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                Stage stage = (Stage) quitBtn.getScene().getWindow();
                stage.close();
            }
        });
        enterBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                if (pass.getText().equals(SettingsClass.pass)) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        AnchorPane p = fxmlLoader.load(getClass().getResource("/fxml/settings.fxml").openStream());
                        SettingsController fooController = (SettingsController) fxmlLoader.getController();
                        fooController.setEditDisable(false);
                        Stage stage = new Stage();
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initStyle(StageStyle.UNDECORATED);
                        Scene scene = new Scene(p);
                        scene.getStylesheets().add("css/mainPage.css");
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        System.out.println(e + " ");
                    }
                    Stage stage = (Stage) enterBtn.getScene().getWindow();
                    stage.close();
                } else {
                    pass.setText("");
                }
            }
        });

        showSettingsBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    AnchorPane p = fxmlLoader.load(getClass().getResource("/fxml/settings.fxml").openStream());
                    SettingsController fooController = (SettingsController) fxmlLoader.getController();
                    fooController.setEditDisable(true);
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initStyle(StageStyle.UNDECORATED);
                    Scene scene = new Scene(p);
                    scene.getStylesheets().add("css/mainPage.css");
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    System.out.println(e + " ");
                }

            }
        });
    }

}