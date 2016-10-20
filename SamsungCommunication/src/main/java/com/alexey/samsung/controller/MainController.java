package com.alexey.samsung.controller;

import com.alexey.samsung.DBHelper;
import com.alexey.samsung.Main;
import com.alexey.samsung.VkApi;
import com.vk.api.sdk.client.VkApiClient;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Created by aokly on 24.09.2016.
 */
public class MainController {

    @FXML
    private Button button;
    @FXML
    public Button vkButton;


    @FXML
    public void initialize(){
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try{
                    AnchorPane root1 = (AnchorPane) FXMLLoader.load(Main.class.getResource("/fxml/taskDialog.fxml"));
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.setTitle("ABC");
                    stage.setScene(new Scene(root1));
                    stage.show();
                } catch (IOException e) {
                    System.out.println(e+" ");
                }
            }
        });
        vkButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
               try(VkApi vk = new VkApi()){
                   System.out.println(vk.getDialogs());
               } catch (Exception e) {
                   System.out.println("Ошибка подключения к VK");
               }
            }
        });

    }
}


