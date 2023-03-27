/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projetdragon;

import java.io.IOException;
import java.net.MalformedURLException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 *
 * @author matia
 */
public class CodageView extends Application {

    @Override
    public void start(Stage primaryStage) throws MalformedURLException, IOException {

        WebView browser = new WebView();
        WebEngine webEngine = browser.getEngine();
        String url = "https://play.thingz.co/galaxia";
        // Load a page from remote url.
        webEngine.load(url);

        VBox root = new VBox();
        root.getChildren().add(browser);
        
        //Button buttonTeleverser = (Button) browser.lookup("ui orange medium icon right floated right labeled button HeaderFreeCreationControlsButton");
        
        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("CODAGE");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /*
      @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
