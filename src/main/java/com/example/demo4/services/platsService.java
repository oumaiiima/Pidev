/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo4.services;

//import com.sun.javafx.iio.ImageStorage.ImageType;
import com.example.demo4.entities.plats;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.IOException;
import java.io.File;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import com.example.demo4.utils.MyDB;
import javafx.collections.ObservableList;

//**************//
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import static org.apache.poi.hssf.usermodel.HeaderFooter.date;

import java.io.ByteArrayOutputStream;


import java.util.ArrayList;

import com.example.demo4.utils.MyDB;
import javafx.collections.ObservableList;

//**************//
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import java.io.FileNotFoundException;

import static org.apache.poi.hssf.usermodel.HeaderFooter.date;

/**
 *
 * @author asus
 */
public class platsService implements IplatsService<plats> {

    Connection cnx;
    public Statement ste;
    public PreparedStatement pst;

    public platsService() {
        cnx = MyDB.getInstance().getCnx();

    }

    @Override
    public void ajouterplats(plats e) throws SQLException {

        String requete = "INSERT INTO `plats` (`nom_plat`,`type_plat`,`image_plat`,`description_plat`,`date`,`nombre_places`) "
                + "VALUES (?,?,?,?,?,?);";
        try {
            pst = (PreparedStatement) cnx.prepareStatement(requete);
            pst.setString(1, e.getNom_plat());
            pst.setString(2, e.getType_plat());
            pst.setString(3, e.getImage_plat());
            pst.setString(4, e.getDescription_plat());
            pst.setDate(5, e.getDate());
            pst.setInt(6, e.getNombre_places());
            pst.executeUpdate();
            System.out.println("evenn " + e.getNom_plat() + " added successfully");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void modifierplats(plats e) throws SQLException {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String req = "UPDATE plats SET nom_plat = ?,type_plat = ?,image_plat=?,description_plat = ?,date=?,nombre_places=? where id_plat = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, e.getNom_plat());
        ps.setString(2, e.getType_plat());
        ps.setString(3, e.getImage_plat());
        ps.setString(4, e.getDescription_plat());
        ps.setDate(5, e.getDate());
        ps.setInt(6, e.getNombre_places());
        ps.setInt(7, e.getId_plat());
        ps.executeUpdate();
    }

    @Override
    public void supprimerplats(plats e) throws SQLException {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String req = "delete from plats where id_plat = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, e.getId_plat());
        ps.executeUpdate();
        System.out.println("evenn with id= " + e.getId_plat() + "  is deleted successfully");
    }

    @Override
    public List<plats> recupererplats() throws SQLException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        List<plats> plats = new ArrayList<>();
        String s = "select * from plats";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(s);
        while (rs.next()) {
            plats e = new plats();
            e.setNom_plat(rs.getString("nom_plat"));
            e.setType_plat(rs.getString("type_plat"));
            e.setImage_plat(rs.getString("Image_plat"));
            e.setDescription_plat(rs.getString("description_plat"));
            e.setDate(rs.getDate("date"));
            e.setNombre_places(rs.getInt("nombre_places"));
            e.setId_plat(rs.getInt("id_plat"));

            plats.add(e);

        }
        return plats;
    }

    public plats FetchOneevenn(int id) {
        plats evenn = new plats();
        String requete = "SELECT * FROM `plats` where id_plat = " + id;

        try {
            ste = (Statement) cnx.createStatement();
            ResultSet rs = ste.executeQuery(requete);

            while (rs.next()) {

                evenn = new plats(rs.getInt("id_plat"), rs.getInt("nombre_places"), rs.getString("nom_plat"), rs.getString("type_plat"), rs.getString("image_plat"), rs.getString("description_plat"), rs.getDate("date"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(platsService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return evenn;
    }

    public ObservableList<plats> Fetchevenns() {
        ObservableList<plats> evenns = FXCollections.observableArrayList();
        String requete = "SELECT * FROM `plats`";
        try {
            ste = (Statement) cnx.createStatement();
            ResultSet rs = ste.executeQuery(requete);

            while (rs.next()) {
                evenns.add(new plats(rs.getInt("id_plat"), rs.getInt("nombre_places"), rs.getString("nom_plat"), rs.getString("type_plat"), rs.getString("image_plat"), rs.getString("description_plat"), rs.getDate("date")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(platsService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return evenns;
    }

    public String GenerateQrabonn(plats abonn) throws FileNotFoundException, IOException {
        String abonnName = "abonn name: " + abonn.getNom_plat() + "\n" + "abonn date: " + abonn.getDate() + "\n" + "abonn description: " + abonn.getDescription_plat() + "\n";
        ByteArrayOutputStream out = QRCode.from(abonnName).to(ImageType.JPG).stream();
        String filename = abonn.getNom_plat() + "_QrCode.jpg";
        //File f = new File("src\\utils\\img\\" + filename);
        File f = new File("C:\\xampp\\htdocs\\oumaima\\imgQr\\qrcode" + filename);
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(out.toByteArray());
        fos.flush();

        System.out.println("qr yemshi");
        return filename;
    }




    public ObservableList<plats> chercherevenn(String chaine) {
        String sql = "SELECT * FROM plats WHERE (nom_plat LIKE ? or type_plat LIKE ?  ) order by nom_plat ";
        //Connection cnx= Maconnexion.getInstance().getCnx();
        String ch = "%" + chaine + "%";
        ObservableList<plats> myList = FXCollections.observableArrayList();
        try {

            Statement ste = cnx.createStatement();
            // PreparedStatement pst = myCNX.getCnx().prepareStatement(requete6);
            PreparedStatement stee = cnx.prepareStatement(sql);
            stee.setString(1, ch);
            stee.setString(2, ch);

            ResultSet rs = stee.executeQuery();
            while (rs.next()) {
                plats e = new plats();

                e.setNom_plat(rs.getString("nom_plat"));
                e.setType_plat(rs.getString("type_plat"));
                e.setImage_plat(rs.getString("Image_plat"));
                e.setDescription_plat(rs.getString("description_plat"));
                e.setDate(rs.getDate("date"));
                e.setNombre_places(rs.getInt("nombre_places"));
                e.setId_plat(rs.getInt("id_plat"));

                myList.add(e);
                System.out.println("evenn trouvé! ");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    public List<plats> trierevenn()throws SQLException {
        List<plats> plats = new ArrayList<>();
        String s = "select * from plats order by nom_plat ";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(s);
        while (rs.next()) {
            plats e = new plats();
            e.setNom_plat(rs.getString("nom_plat"));
            e.setType_plat(rs.getString("type_plat"));
            e.setImage_plat(rs.getString("Image_plat"));
            e.setDescription_plat(rs.getString("description_plat"));
            e.setDate(rs.getDate("date"));
            e.setNombre_places(rs.getInt("nombre_places"));
            e.setId_plat(rs.getInt("id_plat"));
            plats.add(e);
        }
        return plats;
    }
   

}
