/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo4;

import com.example.demo4.entities.plats;
import com.example.demo4.entities.Table;
import com.example.demo4.entities.reservation;
import com.example.demo4.services.Pdf2;
import com.example.demo4.services.platsService;
import com.example.demo4.services.reservationService;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.*;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;















/**
 * FXML Controller class
 *
 * @author asus
 */
public class AjouterplatsController implements Initializable {

    @FXML
    private TextField descriptionevennField;
    @FXML
    private DatePicker dateevennField;
    @FXML
    private TextField typeevennField;
    @FXML
    private TextField imageevennField;
    @FXML
    private TextField nom_platField;
    @FXML
    private Button supprimerBoutton;
    @FXML
    private Button ajouterButton;
    @FXML
    private Button afficherBoutton;
    @FXML
    private Button goToPong;
  
    @FXML
    private TableView<plats> platsTv;
    @FXML
    private TableColumn<plats, String> nomevennTv;
    @FXML
    private TableColumn<plats, String> typeevennTv;
    @FXML
    private TableColumn<plats, String> imageevennTv;
    @FXML
    private TableColumn<plats, String> dateevennTv;
    @FXML
    private TableColumn<plats, String> descriptionevennTv;
     @FXML
    private TableColumn<plats, Integer> nombre_placesTv;
    @FXML
    private TextField nombre_placesField;
    
    private Date date1;
    @FXML
    private Label partError;
    @FXML
    private Label idLabel;
 
    ObservableList<plats> evenns;
    platsService Ev=new platsService();
    reservationService Pservice =new reservationService();
    Pdf2 oo=new Pdf2();
    
    @FXML
    private TextField idmodifierField;
    @FXML
    private Button participerbutton;
    @FXML
    private ImageView imageview;
    @FXML
    private TextField rechercher;
    @FXML
    private ImageView QrCode;
    @FXML
    private ImageView GoBackBtn;
    @FXML
    private Canvas myCanvas;

    
    /**
     * Initializes the controller class.
     */
@Override
public void initialize(URL url, ResourceBundle rb) {


    partError.setVisible(false);
    //idLabel.setText("");
    getevenns(); 
}




    
  
     private boolean NoDate() {
         LocalDate currentDate = LocalDate.now();     
         LocalDate myDate = dateevennField.getValue(); 
         int comparisonResult = myDate.compareTo(currentDate);      
         boolean test = true;
        if (comparisonResult < 0) {
        // myDate est antérieure à currentDate
        test = true;
        } else if (comparisonResult > 0) {
         // myDate est postérieure à currentDate
         test = false;
        }
        return test;
    }
    @FXML
    private void ajouterplats(ActionEvent evenn) {

        int part = 0;
        if ((nom_platField.getText().length() == 0) || (typeevennField.getText().length() == 0) || (imageevennField.getText().length() == 0) || (nombre_placesField.getText().length() == 0) || (descriptionevennField.getText().length() == 0)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setHeaderText("Error!");
            alert.setContentText("Fields cannot be empty");
            alert.showAndWait();
        } else if (NoDate() == true) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setHeaderText("Error!");
            alert.setContentText("la date date doit être aprés la date d'aujourd'hui");
            alert.showAndWait();
        } else {
            try {
                part = Integer.parseInt(nombre_placesField.getText());
                partError.setVisible(false);
            } catch (Exception exc) {
                System.out.println("Number of nombre_places int");
                partError.setVisible(true);
                return;
            }
            if (part < 10) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error ");
                alert.setHeaderText("Error!");
                alert.setContentText("le code reservation doit être suupp ou egale  à 10");
                alert.showAndWait();
                partError.setVisible(true);
            } else {
                plats e = new plats();


                e.setNom_plat(nom_platField.getText());
                e.setType_plat(typeevennField.getText());
                e.setDescription_plat(descriptionevennField.getText());
                java.util.Date date_debut = java.util.Date.from(dateevennField.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date sqlDate = new Date(date_debut.getTime());
                e.setDate(sqlDate);
                e.setNombre_places(Integer.valueOf(nombre_placesField.getText()));

                //lel image
                e.setImage_plat(imageevennField.getText());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information ");
                alert.setHeaderText("plats add");
                alert.setContentText("plats added successfully!");
                alert.showAndWait();
                try {
                    Ev.ajouterplats(e);
                    reset();

                    // Envoi de l'e-mail de notification
                    try {
                        EmailSender.sendEmail("alimaloul534@gmail.com", "Nouvel événement ajouté", "Bonjour,\n\nUn nouvel événement a été ajouté avec succès.");
                    } catch (MessagingException ex) {
                        System.err.println("Erreur lors de l'envoi de l'e-mail : " + ex.getMessage());
                    }

                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
                getevenns();

            }
        }
    }


    //fin d ajout d'un plats
    private void reset() {
        nom_platField.setText("");
        typeevennField.setText("");
        descriptionevennField.setText("");
        imageevennField.setText("");
        nombre_placesField.setText("");
        dateevennField.setValue(null);    
    }
    
   public void getevenns() {  
         try {
            // TODO
            List<plats> plats = Ev.recupererplats();
            ObservableList<plats> olp = FXCollections.observableArrayList(plats);
            platsTv.setItems(olp);
            nomevennTv.setCellValueFactory(new PropertyValueFactory<>("nom_plat"));
            typeevennTv.setCellValueFactory(new PropertyValueFactory<>("type_plat"));
            imageevennTv.setCellValueFactory(new PropertyValueFactory<>("image_plat"));
            dateevennTv.setCellValueFactory(new PropertyValueFactory<>("date"));
            descriptionevennTv.setCellValueFactory(new PropertyValueFactory<>("description_plat"));
            nombre_placesTv.setCellValueFactory(new PropertyValueFactory<>("nombre_places"));
            
            
           // this.delete();
        } catch (SQLException ex) {
            System.out.println("error" + ex.getMessage());
        }
    }//get evenns

     
     @FXML
   private void modifierplats(ActionEvent evenn) throws SQLException {
        plats e = new plats();
        e.setId_plat(Integer.parseInt(idmodifierField.getText()));
        e.setNom_plat(nom_platField.getText());
        e.setType_plat(typeevennField.getText());
        e.setDescription_plat(descriptionevennField.getText()); 
        Date d=Date.valueOf(dateevennField.getValue());
        e.setDate(d);
        e.setImage_plat(imageevennField.getText());
        e.setNombre_places(Integer.parseInt(nombre_placesField.getText()));
        Ev.modifierplats(e);
        reset();
        getevenns();         
    }

    @FXML
    private void supprimerplats(ActionEvent evenn) {
           plats e = platsTv.getItems().get(platsTv.getSelectionModel().getSelectedIndex());
        try {
            Ev.supprimerplats(e);
        } catch (SQLException ex) {
            Logger.getLogger(AjouterplatsController.class.getName()).log(Level.SEVERE, null, ex);
        }   
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information ");
        alert.setHeaderText("plats delete");
        alert.setContentText("plats deleted successfully!");
        alert.showAndWait();        
        getevenns();    
    }

    @FXML
    private void afficherplats(ActionEvent evenn) {
         try {
            //navigation
            Parent loader = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("afficherplats.fxml")));
            typeevennField.getScene().setRoot(loader);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

  
    @FXML
    //ta3 tablee bch nenzel 3ala wehed ya5tarou w yet3abew textfield
    private void choisirevenn(MouseEvent evenn) throws IOException {
        plats e = platsTv.getItems().get(platsTv.getSelectionModel().getSelectedIndex());
        //idLabel.setText(String.valueOf(e.getid_plat()));
        idmodifierField.setText(String.valueOf(e.getId_plat()));
        nom_platField.setText(e.getNom_plat());
        typeevennField.setText(e.getType_plat());
        imageevennField.setText(e.getImage_plat());
        descriptionevennField.setText(e.getDescription_plat());
        //dateevennField.setValue((e.getDate()));
        nombre_placesField.setText(String.valueOf(e.getNombre_places()));       
        //lel image
        String path = e.getImage_plat();
               File file=new File(path);
              Image img = new Image(file.toURI().toString());
                imageview.setImage(img);

        String filename = Ev.GenerateQrabonn(e);
        System.out.println("filename lenaaa " + filename);
        String path1="C:\\xampp\\htdocs\\xchangex\\imgQr\\qrcode"+filename;
        File file1=new File(path1);
        Image img1 = new Image(file1.toURI().toString());
        //Image image = new Image(getClass().getResourceAsStream("src/utils/img/" + filename));
        QrCode.setImage(img1);


    }

    private void participer(ActionEvent evenn) {

        Table u=new Table();
        LocalDate dateActuelle = LocalDate.now();
        Date dateSQL = Date.valueOf(dateActuelle);
        reservation p=new reservation();
        p.setDate_part(dateSQL);
        //p.setplats();
        p.setId_plats(Integer.parseInt(idmodifierField.getText()));
        p.setId_user(u.getId());
        Pservice.ajouterreservation(p);
    }

    @FXML
    private void afficherreservations(ActionEvent evenn) {     
         try {
            //navigation
            Parent loader = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("afficherreservation.fxml")));
            typeevennField.getScene().setRoot(loader);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }    
    }

    @FXML
    private void uploadImage(ActionEvent evenn)throws FileNotFoundException, IOException  {

        Random rand = new Random();
        int x = rand.nextInt(1000);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload File Path");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File file = fileChooser.showOpenDialog(null);
        String DBPath = "C:\\\\xampp\\\\htdocs\\\\imageP\\\\"  + x + ".jpg";
        if (file != null) {
            FileInputStream Fsource = new FileInputStream(file.getAbsolutePath());
            FileOutputStream Fdestination = new FileOutputStream(DBPath);
            BufferedInputStream bin = new BufferedInputStream(Fsource);
            BufferedOutputStream bou = new BufferedOutputStream(Fdestination);
            System.out.println(file.getAbsoluteFile());
            String path=file.getAbsolutePath();
            Image img = new Image(file.toURI().toString());
            imageview.setImage(img);    
            imageevennField.setText(DBPath);
            int b = 0;
            while (b != -1) {
                b = bin.read();
                bou.write(b);
            }
            bin.close();
            bou.close();          
        } else {
            System.out.println("error");
        }
    }

    @FXML
    private void excelevenn(ActionEvent evenn) {
           
try{
String filename="C:\\xampp\\htdocs\\fichierExcelJava\\dataevenn.xls" ;
    HSSFWorkbook hwb=new HSSFWorkbook();
    HSSFSheet sheet =  hwb.createSheet("new sheet");
    HSSFRow rowhead=   sheet.createRow((short)0);
rowhead.createCell((short) 0).setCellValue("nom Plats");
rowhead.createCell((short) 1).setCellValue("type d'plats");
rowhead.createCell((short) 2).setCellValue("description ");
List<plats> plats = Ev.recupererplats();
  for (int i = 0; i < plats.size(); i++) {
HSSFRow row=   sheet.createRow((short)i);
row.createCell((short) 0).setCellValue(plats.get(i).getNom_plat());
row.createCell((short) 1).setCellValue(plats.get(i).getType_plat());
row.createCell((short) 2).setCellValue(plats.get(i).getDescription_plat());
//row.createCell((short) 3).setCellValue((plats.get(i).getDate()));
i++;
            }
int i=1;
    FileOutputStream fileOut =  new FileOutputStream(filename);
hwb.write(fileOut);
fileOut.close();
System.out.println("Your excel file has been generated!");
 File file = new File(filename);
        if (file.exists()){ 
        if(Desktop.isDesktopSupported()){
            Desktop.getDesktop().open(file);
        }}       
} catch ( Exception ex ) {
    System.out.println(ex);
}
    }
    
    @FXML
    private void pdfevenn(ActionEvent evenn) throws FileNotFoundException, SQLException, IOException {        
       // plats tab_Recselected = platsTv.getSelectionModel().getSelectedItem();
               long millis = System.currentTimeMillis();
        java.sql.Date DateRapport = new java.sql.Date(millis);

        String DateLyoum = new SimpleDateFormat("yyyyMMddHHmmss", Locale.ENGLISH).format(DateRapport);//yyyyMMddHHmmss
        System.out.println("Date d'aujourdhui : " + DateLyoum);

        com.itextpdf.text.Document document = new com.itextpdf.text.Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(String.valueOf(DateLyoum + ".pdf")));//yyyy-MM-dd
            document.open();
            Paragraph ph1 = new Paragraph("Voici un rapport détaillé de notre application qui contient tous les Platss . Pour chaque Plats, nous fournissons des informations telles que la date d'Aujourd'hui :" + DateRapport );
            Paragraph ph2 = new Paragraph(".");
            PdfPTable table = new PdfPTable(4);
            //On créer l'objet cellule.
            PdfPCell cell;
            //contenu du tableau.
            table.addCell("nom_plat");
            table.addCell("type_plat");
            table.addCell("description_plat");
            table.addCell("image_plat");
             
            plats r = new plats();
            Ev.recupererplats().forEach(new Consumer<plats>() {
                @Override
                public void accept(plats e) {
                    table.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(String.valueOf(e.getNom_plat()));
                    table.addCell(String.valueOf(e.getType_plat()));
                    table.addCell(String.valueOf(e.getDescription_plat()));
                    try {
    // Créer un objet Image à partir de l'image
    String path = e.getImage_plat();
    com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance(path);
    
    // Définir la taille de l'image dans le tableau
    img.scaleToFit(100, 100); // Définir la largeur et la hauteur de l'image
    
    // Ajouter l'image à la cellule du tableau
    PdfPCell cell = new PdfPCell(img);
    table.addCell(cell);
} catch (Exception ex) {
    table.addCell("Erreur lors du chargement de l'image");
}
         }
            });
            document.add(ph1);
            document.add(ph2);
            document.add(table);
             } catch (Exception e) {
            System.out.println(e);
        }
        document.close();

        ///Open FilePdf
        File file = new File(DateLyoum + ".pdf");
        Desktop desktop = Desktop.getDesktop();
        if (file.exists()) //checks file exists or not  
        {
            desktop.open(file); //opens the specified file   
        }
    }
    
    @FXML
    private void rechercherevenn(KeyEvent evenn) {
        
        platsService bs=new platsService();
        plats b= new plats();
        ObservableList<plats>filter= bs.chercherevenn(rechercher.getText());
        populateTable(filter);
    }
     private void populateTable(ObservableList<plats> branlist){
       platsTv.setItems(branlist);
   
       }

  



    }


    





    

