/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo4.services;

import com.example.demo4.entities.plats;
import com.example.demo4.entities.reservation;
import com.example.demo4.entities.Table;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.demo4.utils.MyDB;

/**
 *
 * @author asus
 */
public class reservationService {

    Connection cnx;
    public Statement ste;
    public PreparedStatement pst;

    public reservationService() {

        cnx = MyDB.getInstance().getCnx();
    }

    public void ajouterreservation(reservation p) {
        Table U = new Table();
        platsService es = new platsService();
        String requete = "INSERT INTO `reservation` (`date_part`,`id_plats` ,`id_user`) VALUES(? ,?,?) ;";

        try {
            plats tempevenn = es.FetchOneevenn(p.getId_plats());
            System.out.println("before" + tempevenn);

            tempevenn.setNombre_places(Math.max(tempevenn.getNombre_places() + 1, 0));
            es.modifierplats(tempevenn);
            int new_id = tempevenn.getId_plat();
            p.setPlats(tempevenn);
            System.out.println("after" + tempevenn);

            pst = (PreparedStatement) cnx.prepareStatement(requete);
            pst.setDate(1, p.getDate_part());
            pst.setInt(2, p.getId_plats());
            pst.setInt(3, p.getId_user());
            pst.executeUpdate();
          

            System.out.println("reservation with id evenn = " + p.getId_plats() + " is added successfully");

        } catch (SQLException ex) {
            System.out.println("error in adding reservation");
            Logger.getLogger(reservationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<reservation> recupererReservation() throws SQLException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        List<reservation> particip = new ArrayList<>();
        String s = "select * from reservation";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(s);
        while (rs.next()) {
            reservation pa = new reservation();
            pa.setId_reservation(rs.getInt("id_reservation"));
            pa.setId_user(rs.getInt("id_user"));
            pa.setId_plats(rs.getInt("id_plats"));
            pa.setDate_part(rs.getDate("date_part"));

            particip.add(pa);

        }
        return particip;
    }

    public void supprimerreservation(reservation p) throws SQLException {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String req = "delete from reservation where id_reservation  = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, p.getId_reservation());
        ps.executeUpdate();
        System.out.println("reservation with id= " + p.getId_reservation() + "  is deleted successfully");
    }

    public reservation FetchOneRes(int id) throws SQLException {
        reservation r = new reservation();
        String requete = "SELECT * FROM `reservation` where id_reservation=" + id;

        try {
            ste = (Statement) cnx.createStatement();
            ResultSet rs = ste.executeQuery(requete);

            while (rs.next()) {

                r = new reservation(rs.getInt("id_reservation"), rs.getDate("date_part"), rs.getInt("id_user"), rs.getInt("id_plats"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(platsService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    public void Deletereservation(reservation p) throws SQLException {
        platsService es = new platsService();
        reservationService rs = new reservationService();

        reservation r = rs.FetchOneRes(p.getId_reservation());

        String requete = "delete from reservation where id_reservation=" + p.getId_reservation();
        try {
            plats tempevenn = es.FetchOneevenn(r.getId_plats());
            System.out.println("before" + tempevenn);
            tempevenn.setNombre_places(tempevenn.getNombre_places() + 1);
            es.modifierplats(tempevenn);
            System.out.println("after" + tempevenn);
            pst = (PreparedStatement) cnx.prepareStatement(requete);
            //pst.setInt(1, id);

            pst.executeUpdate();
            System.out.println("reservation with id=" + p.getId_reservation() + " is deleted successfully");
        } catch (SQLException ex) {
            System.out.println("error in delete reservation " + ex.getMessage());
        }
    }
    
    public void modifierreservation(reservation p) throws SQLException {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String req = "UPDATE reservation SET id_user = ?,id_plats = ?,date_part=? where id_reservation = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, p.getId_user());
        ps.setInt(2, p.getId_plats());
        ps.setDate(3, p.getDate_part());
        ps.setInt(4, p.getId_reservation());

        ps.executeUpdate();
    }

}
