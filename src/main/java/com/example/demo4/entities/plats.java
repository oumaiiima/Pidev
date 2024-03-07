/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo4.entities;

import java.sql.Date;

/**
 *
 * @author asus
 */
public class plats {

   
        private int id_plat;
        private int nombre_places;
    private String nom_plat,type_plat,image_plat,description_plat;
    private Date date;

    public plats() {
    }

    public plats(int id_plat, int nombre_places, String nom_plat, String type_plat, String image_plat, String description_plat, Date date) {
        this.id_plat = id_plat;
        this.nombre_places=nombre_places;
        this.nom_plat = nom_plat;
        this.type_plat = type_plat;
        this.image_plat = image_plat;
        this.description_plat = description_plat;
        this.date = date;
    }
    public plats(int nombre_places, String nom_plat, String type_plat, String image_plat, String description_plat, Date date) {
        this.nombre_places=nombre_places;
        this.nom_plat = nom_plat;
        this.type_plat = type_plat;
        this.image_plat = image_plat;
        this.description_plat = description_plat;
        this.date = date;
    }
    
    
     public plats(int id_plat, int nombre_places, String nom_plat, String type_plat, String image_plat, String description_plat) {
        this.id_plat = id_plat;
        this.nombre_places=nombre_places;
        this.nom_plat = nom_plat;
        this.type_plat = type_plat;
        this.image_plat = image_plat;
        this.description_plat = description_plat;
        
    }
    
    
     //****************** getters ****************

    public int getId_plat() {
        return id_plat;
    }

    public String getNom_plat() {
        return nom_plat;
    }

    public String getType_plat() {
        return type_plat;
    }

    public String getImage_plat() {
        return image_plat;
    }

    public String getDescription_plat() {
        return description_plat;
    }

    public Date getDate() {
        return date;
    }
    
    
    //****************** setters ****************

    public void setId_plat(int id_plat) {
        this.id_plat = id_plat;
    }

    public void setNom_plat(String nom_plat) {
        this.nom_plat = nom_plat;
    }

    public void setType_plat(String type_plat) {
        this.type_plat = type_plat;
    }

    public void setImage_plat(String image_plat) {
        this.image_plat = image_plat;
    }

    public void setDescription_plat(String description_plat) {
        this.description_plat = description_plat;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNombre_places() {
        return nombre_places;
    }

    public void setNombre_places(int nombre_places) {
        this.nombre_places = nombre_places;
    }
    
    

    @Override
    public String toString() {
        return "plats{" + "id_plat=" + id_plat+ ", nombre_places=" + nombre_places  + ", nom_plat=" + nom_plat + ", type_plat=" + type_plat + ", image_plat=" + image_plat + ", description_plat=" + description_plat + ", date=" + date + '}';
    }
    
    
    
    
    
    
}
