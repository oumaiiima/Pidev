/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo4;

import com.example.demo4.entities.plats;
import com.example.demo4.entities.reservation;

import java.io.IOException;

import com.example.demo4.services.reservationService;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
    import com.example.demo4.services.platsService;



/**
 * FXML Controller class
 *
 * @author asus
 */
public class AfficherreservationController implements Initializable {

    @FXML
    private TableView<reservation> tablereservation;
     platsService ab=new platsService();
    @FXML
    private TableColumn<reservation, Integer> iduserTv;
    @FXML
    private TableColumn<reservation, Integer> idevennTv;
    @FXML
    private TableColumn<reservation, Date> datePartTv;
    @FXML
    private Button modifierPartBtn;
    @FXML
    private Button supprimerPartBtn;
     @FXML
    private Button ajouter;
    @FXML
    private TextField idread;
    @FXML
    private TextField iduserField;
    @FXML
    private TextField idevennField;
    @FXML
    private DatePicker datepartField;
    @FXML
    private TextField chercherevennField;
    
    reservationService Ps=new reservationService();
    @FXML
    private TextField datepartField1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        getReservation();
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
    @FXML
    private void rechercherplats(KeyEvent evenn) {
        try {
            List<plats> plats = ab.chercherevenn(chercherevennField.getText());
            
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
    private void modifierreservation(ActionEvent evenn) throws SQLException {
        
         reservation pa = new reservation();
        pa.setId_reservation(Integer.valueOf(idread.getText()));
        pa.setId_plats(Integer.valueOf(idevennField.getText()));
        pa.setId_user(Integer.valueOf(iduserField.getText()));
            Date d=Date.valueOf(datepartField.getValue());
        pa.setDate_part(d);
        //pa.setDate_part(datepartField.getText());
       
        Ps.modifierreservation(pa);
        resetPart();
        getReservation();
           
        
    }

    @FXML
    private void supprimerreservation(ActionEvent evenn) {
         reservation p = tablereservation.getItems().get(tablereservation.getSelectionModel().getSelectedIndex());
      
        try {
            Ps.Deletereservation(p);
        } catch (SQLException ex) {
            Logger.getLogger(AjouterplatsController.class.getName()).log(Level.SEVERE, null, ex);
        }   
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information ");
        alert.setHeaderText("reservation delete");
        alert.setContentText("reservation deleted successfully!");
        alert.showAndWait();
        getReservation();
     
    }

    @FXML
    private void choisirreservation(ActionEvent evenn) {
        reservation part = tablereservation.getItems().get(tablereservation.getSelectionModel().getSelectedIndex());
        
        idread.setText(String.valueOf(part.getId_reservation()));
        idevennField.setText(String.valueOf(part.getId_plats()));
        iduserField.setText(String.valueOf(part.getId_user())); 
        datepartField1.setText(String.valueOf(part.getDate_part()));
        //datepartField.setValue((part.getDate_part()));
        
    }
    
    
    public void getReservation(){
        try {
       

           // TODO
            List<reservation> part = Ps.recupererReservation();
            ObservableList<reservation> olp = FXCollections.observableArrayList(part);
            tablereservation.setItems(olp);
            iduserTv.setCellValueFactory(new PropertyValueFactory("id_user"));
            idevennTv.setCellValueFactory(new PropertyValueFactory("id_plats"));
            datePartTv.setCellValueFactory(new PropertyValueFactory("date_part"));
            // this.delete();
        } catch (SQLException ex) {
            System.out.println("error" + ex.getMessage());
        }
    }
    
    public void resetPart() {
        idread.setText("");
        idevennField.setText("");
        iduserField.setText("");
        datepartField.setValue(null);
        
    }
   
    
}


 