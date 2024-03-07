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
public class reservation extends plats {
    private int id_reservation;
private Date date_part;
private int id_user;

public int id_plats;
public plats plats;

    public reservation() {
    }

    public reservation(int id_reservation, Date date_reservation, int id_user, int id_plats) {
        this.id_reservation = id_reservation;
        this.date_part = date_reservation;
        this.id_user = id_user;
        this.id_plats = id_plats;
    }
    public reservation(Date date_reservation, int id_user, int id_plats) {
        this.date_part = date_reservation;
        this.id_user = id_user;
        this.id_plats = id_plats;
    }

    public reservation(int id_reservation, Date date_reservation, int id_user, int id_plats, plats plats) {
        this.id_reservation = id_reservation;
        this.date_part = date_reservation;
        this.id_user = id_user;
        this.id_plats = id_plats;
        this.plats = plats;
    }

    public int getId_reservation() {
        return id_reservation;
    }

    public Date getDate_part() {
        return date_part;
    }

    public int getId_user() {
        return id_user;
    }

    public int getId_plats() {
        return id_plats;
    }

    public plats getplats() {
        return plats;
    }

    public void setId_reservation(int id_reservation) {
        this.id_reservation = id_reservation;
    }

    public void setDate_part(Date date_reservation) {
        this.date_part = date_reservation;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void setId_plats(int id_plats) {
        this.id_plats = id_plats;
    }

    public void setPlats(plats plats) {
        this.plats = plats;
    }

    @Override
    public String toString() {
        return "reservation{" + "id_reservation=" + id_reservation + ", date_reservation=" + date_part + ", id_user=" + id_user + ", id_plats=" + id_plats +  '}';
    }







}
