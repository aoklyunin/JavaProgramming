package com.example.alex.controller;

import com.example.alex.MainApp;
import com.example.alex.SettingsClass;
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
import java.text.SimpleDateFormat;
import java.util.Date;

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
    public ImageView ballon1Img;
    public Slider s1;
    public TextField tf1;
    public Slider s2;
    public TextField tf2;
    public Slider s3;
    public TextField tf3;
    public Slider s4;
    public TextField tf5;
    public Slider s5;
    public TextField tf4;
    public javafx.scene.layout.AnchorPane rBar;
    public Button btnGraph;
    public Button btnErrLog;
    public Button btnSettings;


    @FXML
    public void initialize() {

        btnQuit.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                System.out.println("ressed");
                Stage stage = (Stage) btnQuit.getScene().getWindow();
                stage.close();
            }
        });
        //AnchorPane.getStyleClass().add("pane");
        tabPane.getSelectionModel().select(0);
        Image img = new Image("img/баллон.png", true);
        ballon1Img.setImage(img);

        logoImg.setImage(new Image("img/logo.png", true));
        btnErrLog.setText("Журнал \n ошибок");

        btnErrLog.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                try {
                    AnchorPane root1 = (AnchorPane) FXMLLoader.load(MainApp.class.getResource("/fxml/errLog.fxml"));
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initStyle(StageStyle.UNDECORATED);
                    Scene scene = new Scene(root1);
                    scene.getStylesheets().add("css/mainPage.css");
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    System.out.println(e + " ");
                }
            }
        });


        btnGraph.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                try {
                    AnchorPane root1 = (AnchorPane) FXMLLoader.load(MainApp.class.getResource("/fxml/graph.fxml"));
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initStyle(StageStyle.UNDECORATED);
                    Scene scene = new Scene(root1);
                    scene.getStylesheets().add("css/mainPage.css");
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    System.out.println(e + " ");
                }
            }
        });

        btnSettings.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                try {
                    AnchorPane root1 = (AnchorPane) FXMLLoader.load(MainApp.class.getResource("/fxml/login.fxml"));
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initStyle(StageStyle.UNDECORATED);
                    Scene scene = new Scene(root1);
                    scene.getStylesheets().add("css/mainPage.css");
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    System.out.println(e + " ");
                }
            }
        });
        shortText.setText(shortTextVal);
        longText.setText(longTextVal);

        SettingsClass settingsClass = new SettingsClass("settings");
        settingsClass.setTempVals();
        setStatsFromSettings(settingsClass);


    }

    void setStatsFromSettings(SettingsClass settingsClass){

        long difference =new Date().getTime()- settingsClass.d.getTime();
        System.out.println(new Date()+" "+settingsClass.d);
        int days =(int)( difference/1000/60/60/24);
        double months = (double)days/365*12;
        useTimeText.setText((int)months+"");
        nCicleText.setText(settingsClass.nCicle+"");
        tempText.setText(settingsClass.t+"");
        pAccessText.setText(settingsClass.maxPress+"");
        pText.setText(settingsClass.p+"");
        tf1.setText(settingsClass.d1+"");
        s1.setMax(settingsClass.maxD1);
        s1.setMin(settingsClass.minD1);
        s1.setValue(settingsClass.d1);

        tf2.setText(settingsClass.d2+"");
        s2.setMax(settingsClass.maxD2);
        s2.setMin(settingsClass.minD2);
        s2.setValue(settingsClass.d2);

        tf3.setText(settingsClass.d3+"");
        s3.setMax(settingsClass.maxD3);
        s3.setMin(settingsClass.minD3);
        s3.setValue(settingsClass.d3);

        tf4.setText(settingsClass.l1+"");
        s4.setMax(settingsClass.maxL1);
        s4.setMin(settingsClass.minL1);
        s4.setValue(settingsClass.l1);

        tf5.setText(settingsClass.l2+"");
        s5.setMax(settingsClass.maxL2);
        s5.setMin(settingsClass.minL2);
        s5.setValue(settingsClass.l2);
    }

    private static final String shortTextVal = "Oxygen\nequipment";
    private static final String longTextVal = "Общество с ограниченной ответственностью \"НПО Поиск\""+"\n"+
                                              "(ООО \"НПО \"Поиск\")"+"\n"+
                                              "Моховая ул.,д.18, пом. 11-Н, Санкт-Петербург, 191028"+"\n"+
                                              "Тел./факс (812)309-10-11, e-mail poisk-ltd@bk.ru";

}
