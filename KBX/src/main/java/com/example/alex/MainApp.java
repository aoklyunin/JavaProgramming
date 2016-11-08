package com.example.alex;

/**
 * Created by teacher on 07.11.16.
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;

// TODO: 08.11.16
// Окно графиков добавить границы выборки при помощи пикеров, а также сделать нормальное хранение/ запись
// неправильно выводится/вводится дата(вроде починил, надо потестить)
// мб стоит добавить в окно настроек текущие значения кол-ва циклов и дату старта эксплуатации
// экспорт ошибок в файл
// запись ошибок в файл и отображение из файла

 public class  MainApp extends Application {
     public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        String fxmlFile = "/fxml/hello.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));
        stage.setTitle("JavaFX and Maven");
        Scene scene = new Scene(root);
        // окно без заголовка
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
        scene.getStylesheets().add("css/mainPage.css");

    }
}
