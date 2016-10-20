package com.alexey.samsung.controller;

import com.alexey.samsung.*;
import com.vk.api.sdk.client.VkApiClient;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

/**
 * Created by aokly on 24.09.2016.
 */
public class MainController {

    public DatePicker startDP;
    public DatePicker endDP;
    public CheckBox group1enable;
    public CheckBox group2enable;
    public Button showBtn;
    public TableView attemptTable;

    @FXML
    private Button button;
    @FXML
    public Button vkButton;


    @FXML
    public void initialize() {
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
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
        vkButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try (VkApi vk = new VkApi()) {
                    System.out.println(vk.getDialogs());
                } catch (Exception e) {
                    System.out.println("Ошибка подключения к VK");
                }
            }
        });
        showBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                showAttempts();
            }
        });
        startDP.setValue(LocalDate.of(2016, Month.SEPTEMBER, 1));
        endDP.setValue(LocalDate.now());


    }

    public void showAttempts() {
        try (DBHelper dbHelper = new DBHelper()) {
            dbHelper.connect();
            ArrayList<Task> tasks = dbHelper.getTasks(
                    Date.valueOf(startDP.getValue()),
                    Date.valueOf(endDP.getValue())
            );
            int columnCnt = tasks.size();

            int group = 0;
            group += group1enable.isSelected() ? 1 : 0;
            group += group2enable.isSelected() ? 2 : 0;
           // System.out.println(group);
            ArrayList<String> lstNames = dbHelper.getSchoolers(group);

            int rowCnt = lstNames.size();

            ArrayList<Attempt> att = dbHelper.getAttempts(tasks, lstNames);
            for (Attempt l : att) {
                System.out.println(l);
            }
            buildTable(att,lstNames,tasks);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void buildTable(ArrayList<Attempt> att, ArrayList<String> lstNames,  ArrayList<Task> tasks){
        attemptTable.getColumns().clear();
        int cnt = 0;
        int ln = 180;
        for (Task task:tasks
             ) {
            cnt++;
            TableColumn col = new TableColumn(task.name+"\n"+task.date);
            //col.setMinWidth(10);
            col.setPrefWidth(ln);
            attemptTable.getColumns().add(col);

        }
        attemptTable.setPrefWidth(ln*cnt);
        //attemptTable.setItems();
    }
}


