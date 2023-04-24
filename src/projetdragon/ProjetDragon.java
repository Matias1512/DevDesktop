/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package projetdragon;

import com.fazecast.jSerialComm.SerialPort;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Compiler.command;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.filechooser.FileSystemView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 *
 * @author matia
 */
public class ProjetDragon extends Application {
    
    @Override
    public void start(Stage primaryStage) throws MalformedURLException, IOException, JSONException {

        String pwd = System.getProperty("user.dir");
        
        ArrayList<Version> versionsArray = new ArrayList<Version>();
        try {
            // Read the JSON file into a string
            File file = new File(pwd+"\\src\\projetdragon\\firmware_list.json");
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
        btn.setText("Download");
        btn.setOnAction((ActionEvent event) -> {
            Object vobject = combo_box.getValue();
            Version v = (Version)vobject;
            try (BufferedInputStream bis = new BufferedInputStream(new URL(v.getValue()).openStream());  
            FileOutputStream fos = new FileOutputStream(pwd+"\\src\\projetdragon\\Firmwares\\"+v.getKey()+".bin")) {
            byte data[] = new byte[1024];
            int byteContent;
            while ((byteContent = bis.read(data, 0, 1024)) != -1) {
                fos.write(data, 0, byteContent);
            }
            } catch (IOException e) {
               e.printStackTrace(System.out);
            }
                
            /*
            C:\Users\matia\AppData\Local\Microsoft\WindowsApps\python.exe -m esptool --port COM5 write_flash 0x0000 C:\Users\matia\OneDrive\Bureau\ynov\DevDesktop\PROJET\DevDesktop\src\projetdragon\Firmwares\1.0-beta-27-pcb-1.0.3.bin
            */
            
            File[] drives = File.listRoots();
            SerialPort[] ports = SerialPort.getCommPorts();
            FileSystemView fsv = FileSystemView.getFileSystemView();
            for (File d : drives) {
                System.out.println(fsv.getSystemDisplayName(d));
            }
            String portUse = null;
            for(SerialPort port : ports){
                portUse = port.getSystemPortName();
                System.out.println(port.getSystemPortName());
                
            }
            System.out.println();          
            String[] cmd = {"C:\\Users\\matia\\AppData\\Local\\Microsoft\\WindowsApps\\python.exe","-m","esptool","--port",portUse,"write_flash","0x0000",pwd+"\\src\\projetdragon\\Firmwares\\"+v.getKey()+".bin"};
            System.out.println(Arrays.toString(cmd)); 
            Process p;
            try {
                p = Runtime.getRuntime().exec(cmd);
                String line;
                BufferedReader is =  new BufferedReader(new InputStreamReader(p.getInputStream()));
                while ((line = is.readLine()) != null){
                    System.out.println(line);
                }
            } catch (IOException ex) {
                Logger.getLogger(ProjetDragon.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        Button btnCodage = new Button();
        btnCodage.setText("Codage");
        btnCodage.setOnAction((ActionEvent event) -> {
            CodageView codageView = new CodageView();
            try {
                codageView.start(new Stage());
            } catch (IOException ex) {
                Logger.getLogger(ProjetDragon.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        // Charger l'image
        Image image = new Image("https://ucarecdn.com/13bbbf2d-ccfd-4f7d-b195-048ca0f6c46b/-/format/auto/-/preview/3000x3000/-/quality/lighter/renvoie%20rcto-vrso.png");

        // Créer une instance de ImageView pour afficher l'image
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(300);
        imageView.setPreserveRatio(true);
        
        StackPane root = new StackPane();
        VBox vbox = new VBox();
        HBox hbox = new HBox();
        hbox.getChildren().add(vbox);
        hbox.getChildren().add(imageView);
        
        vbox.getChildren().add(combo_box);
        vbox.getChildren().add(btn);
        vbox.getChildren().add(btnCodage);
        root.getChildren().add(hbox);
        
        Scene scene = new Scene(root, 500, 250);
        
        primaryStage.setTitle("Firmwares");
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
