/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import com.example.demo.dbmanager.annotation.PrimaryKey;
import com.example.demo.dbmanager.bdd.object.BddObject;
import java.sql.Timestamp;

/**
 *
 * @author P14A_30_Ando
 */
public class Enchere extends BddObject{
    @PrimaryKey
    private int idEnchere;
    private String nomProduit;
    private Timestamp dateDebut;
    private Timestamp dateFin;
    private double prixMin;
    private String description;
    private Categorie categorie;
    private int idCategorie;  
    private Client client;
    private int idClient;



    public int getIdEnchere() {
        return idEnchere;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setIdEnchere(int idEnchere) {
        this.idEnchere = idEnchere;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public Timestamp getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Timestamp dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Timestamp getDateFin() {
        return dateFin;
    }

    public void setDateFin(Timestamp dateFin) {
        this.dateFin = dateFin;
    }

    public double getPrixMin() {
        return prixMin;
    }

    public void setPrixMin(double prixMin) {
        this.prixMin = prixMin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }
    
    
}