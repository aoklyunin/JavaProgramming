package com.example.alex.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Created by teacher on 08.11.16.
 */
public class ErrLogController {
    public Button closeBtn;
    public Button exportBtn;

    @FXML
    public void initialize() {
        closeBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                Stage stage = (Stage) closeBtn.getScene().getWindow();
                stage.close();
            }
        });

    }
}
