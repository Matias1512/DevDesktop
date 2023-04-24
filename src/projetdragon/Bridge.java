/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projetdragon;

import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;

/**
 *
 * @author matia
 */
public class Bridge {
    Label l;
    WebEngine webEngine;
    
    public Bridge(Label l, WebEngine we) {
        this.l = l;
        this.webEngine = we;
    }
    
    public void functionClick(){
        System.out.println("IL A CLIQUE");
    }
}
