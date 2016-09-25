package com.alexey.samsung.controller;

import com.alexey.samsung.DBHelper;
import com.alexey.samsung.Main;
import com.alexey.samsung.VkApi;
import com.vk.api.sdk.client.VkApiClient;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

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


