package com.alexey.samsung.controller;

import com.alexey.samsung.*;
import com.vk.api.sdk.client.VkApiClient;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.Array;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

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
    public AnchorPane ap;
    public SplitPane sp;
    public AnchorPane apsp;
    public AnchorPane apsp2;

    @FXML
    private Button button;
    @FXML
    public Button vkButton;


    double clientWidth = 1360;
    double clientHeight = 710;

    @FXML
    public void initialize() {


        ap.setPrefWidth(clientWidth);
        ap.setPrefHeight(clientHeight);
        sp.setPrefWidth(clientWidth);
        sp.setPrefHeight(clientHeight);
/*
        apsp.setPrefWidth(clientWidth);
        apsp.setPrefHeight(clientHeight*0.8);

        apsp2.setPrefWidth(clientWidth);
        apsp2.setPrefHeight(clientHeight*0.2);
        //attemptTable.setPrefHeight(clientHeight*0.8);
*/
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
         /*   for (Attempt l : att) {
                System.out.println(l);
            }*/
            System.out.println("Building table");
            buildTable(att, lstNames, tasks);
            //addColumn(attemptTable);
            //addColumn(attemptTable);
            //addColumn(attemptTable);
            //addRow(attemptTable,tasks);
            //      start(att,lstNames,tasks);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void buildTable(ArrayList<Attempt> att, ArrayList<String> lstNames, ArrayList<Task> tasks) {
        attemptTable.getColumns().clear();
        // add columns
        HashMap<String, Integer> posByTask = new HashMap<>();
        List<String> columnNames = new ArrayList<>();
        int N_COLS = tasks.size();
        int ln = 180;
        columnNames.add("Имя Фамилия");
        for (int i = 0; i < N_COLS; i++) {
            columnNames.add(tasks.get(i).name);
            posByTask.put(tasks.get(i).name, i + 1);
        }
        //attemptTable.setPrefWidth(ln * (N_COLS + 1));
        for (int i = 0; i < columnNames.size(); i++) {
            final int finalIdx = i;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(
                    columnNames.get(i)
            );
            column.setCellValueFactory(param ->
                    new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx))
            );
            attemptTable.getColumns().add(column);
        }

        // заполняем таблицу
        att.sort(new Comparator<Attempt>() {
            @Override
            public int compare(Attempt o1, Attempt o2) {
                int n = CustomOperations.reverseName(o1.name).compareTo(
                        CustomOperations.reverseName(o2.name));
                if (n != 0) return n;
                return o1.addDate.compareTo(o2.addDate);
            }
        });

        int N_ROWS = 5;

        // add data

        HashMap<String, Integer> posByName = new HashMap<>();
        for (int i = 0; i < lstNames.size(); i++) {
            posByName.put(lstNames.get(i), i);
        }


        String data[][] = new String[lstNames.size()][tasks.size() + 1];
        for (int i = 0; i < lstNames.size(); i++) {
            data[i][0] = CustomOperations.reverseName(lstNames.get(i));
            for (int j = 1; j <= tasks.size(); j++) {
                data[i][j] = "0";
            }
        }
        for (Attempt at : att) {
            int i = posByName.get(at.name);
            int j = posByTask.get(at.testName);
            String s = (at.evaluation == 0 ? at.sum : at.evaluation) + "";
            data[i][j] = s.length() > 5 ? s.substring(0, 5) : s;
            // System.out.printf("%d %10s %3.1f\n",j,at.testName, (at.evaluation==0?at.sum:at.evaluation));
        }

        for (String[] ad : data
                ) {
            List<String> assetList = Arrays.asList(ad);
            attemptTable.getItems().add(FXCollections.observableArrayList(assetList));
        }

        attemptTable.setPrefWidth(clientWidth);
        attemptTable.setPrefHeight(apsp.getHeight());
        System.out.println("table builded");
    }

}


