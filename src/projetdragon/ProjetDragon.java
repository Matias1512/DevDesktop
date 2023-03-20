/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package projetdragon;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jdk.nashorn.internal.runtime.regexp.joni.Config;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author matia
 */
public class ProjetDragon extends Application {
    
    @Override
    public void start(Stage primaryStage) throws MalformedURLException, IOException, JSONException {

        ArrayList<Version> versionsArray = new ArrayList<Version>();
        try {
            // Read the JSON file into a string
            File file = new File("C:\\Users\\matia\\OneDrive\\Bureau\\ynov\\DevDesktop\\DevDesktop\\src\\projetdragon\\firmware_list.json");
            String jsonString = new String(Files.readAllBytes(file.toPath()));
            
            // Parse the JSON string into a JSONObject
            JSONObject jsonObject = new JSONObject(jsonString);

            // Access values in the JSON object
            JSONArray versions = jsonObject.getJSONArray("versions");
            // ...
            for(int i = 0; i < versions.length(); i++){
                JSONObject versionJson = versions.getJSONObject(i);
                String version = versionJson.getString("version");
                String url = versionJson.getString("url");
                versionsArray.add(new Version(version, url));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        ComboBox combo_box =
                    new ComboBox(FXCollections
                              .observableArrayList(versionsArray));
        
        Button btn = new Button();
        btn.setText("Show");
        btn.setOnAction((ActionEvent event) -> {
            Object vobject = combo_box.getValue();
            Version v = (Version)vobject;
            System.out.println(v.getValue());
        });

        StackPane root = new StackPane();
        VBox vbox = new VBox();
        vbox.getChildren().add(combo_box);
        vbox.getChildren().add(btn);
        root.getChildren().add(vbox);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Projet");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }   
}
//https://www.tabnine.com/code/java/methods/java.net.URL/openStream
