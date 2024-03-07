/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo4;

import com.example.demo4.entities.plats;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import com.example.demo4.services.platsService;

import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
/**
 * FXML Controller class
 *
 * @author asus
 */
public class AfficherplatsController implements Initializable {

    @FXML
    private GridPane gridevenn;

    platsService ab=new platsService();
    @FXML
    private TextField chercherevennField;
    @FXML
    private Button ajouter;
    @FXML
    private Button mailButton;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        afficherplats();
               
    }    


    @FXML
    private void ajouterplats(ActionEvent evenn) {
      try {
            //navigation
            Parent loader = FXMLLoader.load(getClass().getResource("ajouterplats.fxml"));
            chercherevennField.getScene().setRoot(loader);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void afficherplats(){
         try {
            List<plats> plats = ab.recupererplats();
            gridevenn.getChildren().clear();
            int row = 0;
            int column = 0;
            for (int i = 0; i < plats.size(); i++) {
                //chargement dynamique d'une interface
                FXMLLoader loader = new FXMLLoader(getClass().getResource("plats.fxml"));
                AnchorPane pane = loader.load();
               
                //passage de parametres
                platsController controller = loader.getController();
                controller.setplats(plats.get(i));
                controller.setIdevenn(plats.get(i).getId_plat());
                gridevenn.add(pane, column, row);
                column++;
                if (column > 1) {
                    column = 0;
                    row++;
                }
                if(plats.get(i).getNombre_places()<=0)
                {
                 // ab.supprimerplats(plats.get(i));
                controller.arreterevenn();
                }
            }
        } catch (SQLException | IOException ex) {
            System.out.println(ex.getMessage());
        }   
    }

    @FXML
    private void rechercherplats(KeyEvent evenn) {
        try {
            List<plats> plats = ab.chercherevenn(chercherevennField.getText());
            gridevenn.getChildren().clear();
            int row = 0;
            int column = 0;
            for (int i = 0; i < plats.size(); i++) {
                //chargement dynamique d'une interface
                FXMLLoader loader = new FXMLLoader(getClass().getResource("plats.fxml"));
                AnchorPane pane = loader.load();         
                //passage de parametres
                platsController controller = loader.getController();
                controller.setplats(plats.get(i));
                controller.setIdevenn(plats.get(i).getId_plat());
                gridevenn.add(pane, column, row);
                column++;
                if (column > 1) {
                    column = 0;
                    row++;
                }
                if(plats.get(i).getNombre_places()<=0)
                {
                 // ab.supprimerplats(plats.get(i));
                controller.arreterevenn();
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }   
    }

    @FXML
    private void mailingg(ActionEvent evenn) throws MessagingException, AddressException, IOException, URISyntaxException {
        
        String link = "https://mail.google.com/mail/u/0/?tab=cm&zx=6vpa7piztdtn#chat/space/AAAACkhBi5Q";
         Desktop.getDesktop().browse(new URI(link));
    }

    @FXML
    private void trierplats(ActionEvent evenn) throws SQLException {
        try {
            List<plats> plats = ab.trierevenn();
            gridevenn.getChildren().clear();
            int row = 0;
            int column = 0;
            for (int i = 0; i < plats.size(); i++) {
                //chargement dynamique d'une interface
                FXMLLoader loader = new FXMLLoader(getClass().getResource("plats.fxml"));
                AnchorPane pane = loader.load();      
                //passage de parametres
                platsController controller = loader.getController();
                controller.setplats(plats.get(i));
                controller.setIdevenn(plats.get(i).getId_plat());
                gridevenn.add(pane, column, row);
                column++;
                if (column > 1) {
                    column = 0;
                    row++;
                }
                if(plats.get(i).getNombre_places()<=0)
                {
                 // ab.supprimerPlats(plats.get(i));
                controller.arreterevenn();
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
 
    }
    
}
