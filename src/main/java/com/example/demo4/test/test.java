/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo4.test;

import com.example.demo4.entities.plats;
import com.example.demo4.entities.reservation;

import java.sql.Date;
import java.sql.SQLException;

import com.example.demo4.services.platsService;
import com.example.demo4.services.reservationService;


/**
 *
 * @author asus
 */
public class test {
    
      public static void main(String[] args) {   
          
          Date d=Date.valueOf("2022-06-11");
          Date d1=Date.valueOf("2020-04-12");
        try {
            //kifeh ya9ra el orde fel base de donnée , kifeh 3raf nom evenn bch n3amarha f nom 
            plats e = new plats(2,10,"nom21", "type21","image21.png","description21",d1);
            plats e1 = new plats(5,"nom3", "type3","image3.png","description3",d);
            plats e2 = new plats(6,"nom4", "type4","image4.png","description4",d);
            plats e3 = new plats(5,8,"nom5", "type5","image5.png","description5",d);
            
            
            reservation p=new reservation(d,1,2);
            reservation p1=new reservation(d1,3,4);
            reservation p2=new reservation(27,d1,55,45);

            reservationService ps=new reservationService();
            //ps.reservation(p);
          //  ps.reservation(p1);
           // ps.reservation(p2);
            ps.modifierreservation(p2);
            //ps.reservation(p2);
            System.out.println("");
            platsService ab = new platsService();
            //ab.ajouterplats(e1);
            //ab.ajouterplats(e2);
           // ab.ajouterplats(e3);
            //ab.ajouter(p);
            //ab.modifierplats(e);
            //ab.supprimerplats(e3);
            System.out.println(ab.recupererplats());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
