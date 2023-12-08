package com.example.dtalearning;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StartApplication extends Application {

    Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{

        this.stage=primaryStage;

        MainWindow();

    }

    private void MainWindow() {

        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("SplashScreen.fxml"));
            AnchorPane pane=loader.load();
            SplashScreenController controller=loader.getController();
            controller.main(stage,this);
            Scene scene=new Scene(pane);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void closeStage(){
        stage.close();
    }


    public static void main(String[] args) {
        launch(args);
    }
}