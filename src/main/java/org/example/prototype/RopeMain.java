package org.example.prototype;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RopeMain extends Application {
    //where stage modeling and stuff go

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        try{
            Parent root=FXMLLoader.load(getClass().getResource("rope-view.fxml"));
            Scene scene=new Scene(root,800,600);
            stage.setScene(scene);
            stage.setTitle("R.O.P.E");
            stage.show();
        } catch(Exception e){
            e.printStackTrace();
        }

    }
}
