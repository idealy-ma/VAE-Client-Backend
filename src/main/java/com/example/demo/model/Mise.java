/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import com.example.demo.dbmanager.annotation.PrimaryKey;
import com.example.demo.dbmanager.bdd.object.BddObject;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author P14A_30_Ando
 */
public class Mise extends BddObject{
    @PrimaryKey
    private int idMise;
    private Timestamp dateMise;
    private double soldeMise;
    private int idEnchere;
    private int idClient;

    public int getIdMise() {
        return idMise;
    }

    public void setIdMise(int idMise) {
        this.idMise = idMise;
    }

    public Timestamp getDateMise() {
        return dateMise;
    }

    public void setDateMise(Timestamp dateMise) {
        this.dateMise = dateMise;
    }

    public double getSoldeMise() {
        return soldeMise;
    }

    public void setSoldeMise(double soldeMise) {
        this.soldeMise = soldeMise;
    }


    public int getIdEnchere() {
        return idEnchere;
    }

    public void setIdEnchere(int idEnchere) {
        this.idEnchere = idEnchere;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    @Override
    public void create(Connection c) throws Exception {
        String sql="insert into Mise(soldeMise,idClient,idEnchere) values (?,?,?)";
        ArrayList<Object> objects=new ArrayList<>();
        objects.add(this.soldeMise);
        objects.add(this.idClient);
        objects.add(this.idEnchere);
        executeQuery(c, sql, objects);
    }
    
    
}
