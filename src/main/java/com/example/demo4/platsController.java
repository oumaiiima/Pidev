/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo4;

import com.example.demo4.entities.plats;
import com.example.demo4.entities.reservation;
import com.example.demo4.entities.Table;

import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import com.example.demo4.services.platsService;
import com.example.demo4.services.reservationService;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author asus
 */
public class platsController implements Initializable {

    int idevenn;
    @FXML
    private Label nomevennLabel;
    @FXML
    private Label typeevennLabel;
    @FXML
    private Label descriptionevennLabel;
    @FXML
    private Label dateevennLabel;
    @FXML
    private Button participerevennButton;
    @FXML
    private Label nb_reservationsLabel;
    
    Table u=new Table();
    reservationService Ps=new reservationService();
    @FXML
    private TextField idevennF;
    @FXML
    private TextField iduserF;
    
    platsService Ev=new platsService();
    @FXML
    private ImageView imageview;
    @FXML
    private Label reservationComplet;
    @FXML
    private TextField idPartField;
    @FXML
    private Button annulerButton;
    @FXML
    private Button likeButton;
     @FXML
    private Button deslikeButton;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        idevennF.setVisible(false);
        deslikeButton.setVisible(false);
        likeButton.setVisible(true);
                iduserF.setVisible(false);
                reservationComplet.setVisible(false);
                annulerButton.setVisible(false);
        likeButton.setOnAction(event -> {
    likeButton.setStyle("-fx-background-color: green;");
    deslikeButton.setVisible(false);
    PauseTransition delay = new PauseTransition(Duration.seconds(5));
});
deslikeButton.setVisible(true);
likeButton.setVisible(true);
deslikeButton.setOnAction(event -> {
    PauseTransition delay = new PauseTransition(Duration.seconds(5));
    delay.setOnFinished(evenn -> {
        likeButton.setStyle("-fx-background-color: green;");
        
    });
    delay.play();
    deslikeButton.setStyle("-fx-background-color: red;");
    likeButton.setVisible(false);
});



                

    }    
    private plats eve=new plats();
    
    public void setplats(plats e) {
        this.eve=e;
        nomevennLabel.setText(e.getNom_plat());
        typeevennLabel.setText(e.getType_plat());
        descriptionevennLabel.setText(e.getDescription_plat());
        dateevennLabel.setText(String.valueOf(e.getDate()));
        nb_reservationsLabel.setText(String.valueOf(e.getNombre_places()));
        idevennF.setText(String.valueOf(e.getId_plat()));
        iduserF.setText(String.valueOf(1));
         String path = e.getImage_plat();
         File file=new File(path);
         Image img = new Image(file.toURI().toString());
         imageview.setImage(img);

    }
    public void setIdevenn(int idevenn){
        this.idevenn=idevenn;
    }


    @FXML
    private void participerevenn(MouseEvent evenn) throws SQLException {

        LocalDate dateActuelle = LocalDate.now();
        Date dateSQL = Date.valueOf(dateActuelle);
        reservation p=new reservation(dateSQL,Integer.parseInt(iduserF.getText()),Integer.parseInt(idevennF.getText()));
        
        Ps.ajouterreservation(p);

        idPartField.setText(String.valueOf(27));
        annulerButton.setVisible(true);
       
        
        participerevennButton.setVisible(false);
        
 
        }
    
    public void arreterevenn()
    {
        participerevennButton.setVisible(false);
        reservationComplet.setVisible(true);
    }

    @FXML
    private void annulerreservation(ActionEvent evenn) throws SQLException {
        reservation p=new reservation();
        p.setId_reservation(Integer.parseInt(idPartField.getText()));
        Ps.Deletereservation(p);
        participerevennButton.setVisible(true);
        annulerButton.setVisible(false);
        
    }
    
    
}
