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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private String photos;
    private int etat;
    private String [] lesSary;

    public String[] getLesSary() {
        return lesSary;
    }

    public void setLesSary(String[] lesSary) {
        this.lesSary = lesSary;
    }

    public int getEtat() {
        return etat;
    }
    public void setEtat(int etat) {
        this.etat = etat;
    }

    public String getPhotos() {
        return photos;
    }
    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public int getIdEnchere() {
        return idEnchere;
    }

    public Client getClient() {
        if(this.client == null) {
            this.client = new Client();
            this.client.setIdClient(this.getIdClient());
            try {
                Connection c = new BDD("postgres", "HY6NINF73nbTN5zYpzsk", "railway", "postgresql").getConnection();
                this.client.find(c);
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(Enchere.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(Enchere.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
        if(this.categorie == null) {
            this.categorie = new Categorie();
            this.categorie.setIdCategorie(this.getIdCategorie());
            try {
                Connection c = new BDD("postgres", "HY6NINF73nbTN5zYpzsk", "railway", "postgresql").getConnection();
                this.categorie.find(c);
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(Enchere.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(Enchere.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
    
    @Override
    public void create(Connection c) throws Exception {
        String sql="insert into Enchere(nomproduit,dateFin,prixMin,description,idCategorie,idClient) values (?,?,?,?,?,?)";
        ArrayList<Object> objects=new ArrayList<>();
        objects.add(this.nomProduit);
        objects.add(this.dateFin);
        objects.add(this.prixMin);
        objects.add(this.description);
        objects.add(this.idCategorie);
        objects.add(this.idClient); 
        executeQuery(c, sql, objects);
    }

    public Enchere getEnd(Connection connection)throws Exception {
        String sql = "select * from enchere order by idenchere desc limit 1";
        return (Enchere)(executeResultedQuery(connection, sql, null).get(0));
    }
}
