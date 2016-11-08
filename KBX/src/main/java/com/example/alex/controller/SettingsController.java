package com.example.alex.controller;

import com.example.alex.SettingsClass;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Date;

/**
 * Created by teacher on 08.11.16.
 */
public class SettingsController {
    public Button cancelBtn;
    public Button saveBtn;
    public TextField tfl1_min;
    public TextField tfl1_max;
    public TextField tfl2_min;
    public TextField tfl2_max;
    public TextField tfd3_max;
    public TextField tfd3_min;
    public TextField tfd2_max;
    public TextField tfd2_min;
    public TextField tfd1_min;
    public TextField tfd1_max;
    public javafx.scene.layout.AnchorPane AnchorPane;
    public Button nullCiclCnt;
    public Button nullTm;
    public TextField maxPress;

    private boolean editDisable = true;

    public void setEditDisable(boolean editDisable) {
        this.editDisable = editDisable;
        saveBtn.setDisable(editDisable);
        maxPress.setDisable(editDisable);
        tfl1_min.setDisable(editDisable);
        tfl1_max.setDisable(editDisable);
        tfl2_min.setDisable(editDisable);
        tfl2_max.setDisable(editDisable);
        tfd3_max.setDisable(editDisable);
        tfd3_min.setDisable(editDisable);
        tfd2_max.setDisable(editDisable);
        tfd2_min.setDisable(editDisable);
        tfd1_min.setDisable(editDisable);
        tfd1_max.setDisable(editDisable);
        nullCiclCnt.setDisable(editDisable);
        nullTm.setDisable(editDisable);
    }
    SettingsClass settings;


    public void initialize() {
        cancelBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                Stage stage = (Stage) cancelBtn.getScene().getWindow();
                stage.close();
            }
        });
        settings = new SettingsClass("settings");

        maxPress.setText(settings.maxPress+"");
        tfl1_min.setText(settings.minL1+"");
        tfl1_max.setText(settings.maxL1+"");
        tfl2_min.setText(settings.minL2+"");
        tfl2_max.setText(settings.maxL2+"");
        tfd1_min.setText(settings.minD1+"");
        tfd1_max.setText(settings.maxD1+"");
        tfd2_min.setText(settings.minD2+"");
        tfd2_max.setText(settings.maxD2+"");
        tfd3_min.setText(settings.minD3+"");
        tfd3_max.setText(settings.maxD3+"");

        saveBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                Stage stage = (Stage) cancelBtn.getScene().getWindow();
                stage.close();
                settings.save("settings");
            }
        });
        nullCiclCnt.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                settings.nCicle = 0;
            }
        });

        nullTm.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                settings.d = new Date();
            }
        });
    }
}
