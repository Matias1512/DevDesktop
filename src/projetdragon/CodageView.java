/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projetdragon;

import java.io.IOException;
import java.net.MalformedURLException;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import org.w3c.dom.Element;
import projetdragon.Bridge;

/**
 *
 * @author matia
 */
public class CodageView extends Application {

    @Override
    public void start(Stage primaryStage) throws MalformedURLException, IOException {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.setJavaScriptEnabled(true);
        
        String url = "https://play.thingz.co/galaxia";
        webEngine.load(url);

        webEngine.getLoadWorker().stateProperty().addListener((observable, oldState, newState) -> {
            if (newState == State.SUCCEEDED) {
                Label l = new Label("l");
                JSObject jsobj = (JSObject) webEngine.executeScript("window");
                jsobj.setMember("java", new Bridge(l , webEngine));
                
                webEngine.executeScript("// Créer un élément de bouton\n" +
                    "var button = document.createElement(\"button\");\n" +
                    "\n" +
                    "// Ajouter du texte au bouton\n" +
                    "var buttonText = document.createTextNode(\"Cliquez ici\");\n" +
                    "button.appendChild(buttonText);\n" +
                    "\n" +
                    "// Ajouter une classe CSS au bouton (optionnel)\n" +
                    "button.classList.add(\"mon-bouton\");\n" +
                    "\n" +
                    "// Ajouter un gestionnaire d'événement pour le clic sur le bouton\n" +
                    "button.addEventListener(\"click\", function() {\n" +
                    "  alert(\"Vous avez cliqué sur le bouton !\");\n" +
                    "});\n" +
                    "\n" +
                    "// Ajouter le bouton à la page\n" +
                    "document.body.appendChild(button);");
                
                // Obtenir la référence au bouton
                JSObject boutons = (JSObject) webEngine.executeScript("document.getElementsByTagName('button');");
                Element bouton = (Element) boutons.getSlot(5);
                bouton.setAttribute("onclick", "java.functionClick()");
            }
        });
        VBox root = new VBox();
        root.getChildren().add(webView);
        
        //Button buttonTeleverser = (Button) webView.lookup("ui orange medium icon right floated right labeled button HeaderFreeCreationControlsButton");
        
        Scene scene = new Scene(root, 800, 600);

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
