/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import com.example.demo.dbmanager.annotation.PrimaryKey;
import com.example.demo.dbmanager.bdd.object.BddObject;
import com.example.demo.dbmanager.connection.BDD;

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
    private Client client;
    private Enchere enchere;

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
    public Client getClient() throws Exception {
        if(this.client == null) {
            this.client = new Client();
            this.client.setIdClient(this.getIdClient());
            try {
                BDD bdd = new BDD("postgres", "HY6NINF73nbTN5zYpzsk", "railway", "postgresql");
                Connection c = bdd.getConnection();
                this.client.find(c);
                c.close();
            } catch (Exception ex) {
                throw ex;
            } 
        }
        return client;
    }

    public Enchere getEnchere() throws Exception {
        if (this.enchere == null) {
            this.enchere = new Enchere();
            this.enchere.setIdEnchere(this.idClient);

            try {
                BDD bdd = new BDD("postgres", "HY6NINF73nbTN5zYpzsk", "railway", "postgresql");
                Connection c = bdd.getConnection();
                this.enchere.find(c);
                c.close();
            } catch (Exception ex) {
                throw ex;
            } 
        }
        return enchere;
    }

    public void setEnchere(Enchere enchere) {
        this.enchere = enchere;
    }

    public void setClient(Client client) {
        this.client = client;
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

    public Mise findLast(Connection c) throws Exception {
        Mise lastMise = null;
        String sql="select * from v_lastmise where idEnchere=?";
        ArrayList<Object> objects=new ArrayList<>();
        objects.add(this.idEnchere);
        ArrayList<Object> l = executeResultedQuery(c, sql, objects);
        if (!l.isEmpty()) {
            lastMise = (Mise) l.get(0);
        }
        return lastMise;
    }
}
