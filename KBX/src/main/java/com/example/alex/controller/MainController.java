package com.example.alex.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Created by teacher on 07.11.16.
 */
public class MainController {
    public Button btnQuit;
    public javafx.scene.layout.AnchorPane AnchorPane;
    public TabPane tabPane;
    public ImageView logoImg;
    public TextField pText;
    public TextField pAccessText;
    public TextField nCicleText;
    public TextField useTimeText;
    public TextField tempText;
    public Label shortText;
    public Label longText;
    public Button emergencyBtn;


    @FXML
    public void initialize() {
        btnQuit.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                Stage stage = (Stage) btnQuit.getScene().getWindow();
                stage.close();
            }
        });
        //AnchorPane.getStyleClass().add("pane");
        tabPane.getSelectionModel().select(0);
        Image img = new Image("img/logo.png", true);

        System.out.println(img.getWidth()+" "+img.getHeight());
        logoImg.setImage(img);
       // logoImg
        /* новое окно

        btnQuit.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    AnchorPane root1 = (AnchorPane) FXMLLoader.load(Main.class.getResource("/fxml/taskDialog.fxml"));
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.setTitle("ABC");
                    stage.setScene(new Scene(root1));
                    stage.show();
                } catch (IOException e) {
                    System.out.println(e + " ");
                }
            }
        });

         */
        shortText.setText(shortTextVal);
        longText.setText(longTextVal);
    }
    private static final String shortTextVal = "Oxygen\nequipment";
    private static final String longTextVal = "Общество с ограниченной ответственностью \"НПО Поиск\""+"\n"+
                                              "(ООО \"НПО \"Поиск\")"+"\n"+
                                              "Моховая ул.,д.18, пом. 11-Н, Санкт-Петербург, 191028"+"\n"+
                                              "Тел./факс (812)309-10-11, e-mail poisk-ltd@bk.ru";

}
