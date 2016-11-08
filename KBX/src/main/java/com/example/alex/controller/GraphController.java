package com.example.alex.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by teacher on 08.11.16.
 */
public class GraphController {
    public Canvas myCanvas;
    public Button quitBtn;
    public TabPane tabPane2;
    GraphicsContext gc;

    @FXML
    public void initialize() {
        quitBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                Stage stage = (Stage) quitBtn.getScene().getWindow();
                stage.close();
            }
        });
        gc = myCanvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 700, 500);
        paintTest();
    }

    int myfunc1(int i){
        double val = (double)i/700*10;
        return (int)((val*val)*2)+150;
    }

    int myfunc2(int i){
        double val = (double)i/700*10-5;
        System.out.println(val);
        return (int)((val*val*val)/2)+250;
    }

    void paintTest(){
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        for (int i = 0; i < 699; i+=5) {
            gc.strokeLine(i,myfunc1(i),
                          i+5,myfunc1(i+5));
        }
        gc.setStroke(Color.RED);
        for (int i = 0; i < 699; i+=5) {
            gc.strokeLine(i,myfunc2(i),
                    i+5,myfunc2(i+5));
        }
    }
}
