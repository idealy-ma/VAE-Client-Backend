/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import java.sql.Connection;

import com.example.demo.dbmanager.annotation.PrimaryKey;
import com.example.demo.dbmanager.bdd.object.BddObject;
import java.util.ArrayList;
import com.example.demo.dbmanager.connection.BDD;

/**
 *
 * @author P14A_30_Ando
 */
public class PhotoEnchere extends BddObject{
    @PrimaryKey
    private int idPhotoEnchere;
    private String photo;
    private Enchere enchere;
    private int idEnchere;
    private String [] lesSary;

    public int getIdPhotoEnchere() {
        return idPhotoEnchere;
    }

    public void setIdPhotoEnchere(int idPhotoEnchere) {
        this.idPhotoEnchere = idPhotoEnchere;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Enchere getEnchere() {
        return enchere;
    }

    public void setEnchere(Enchere enchere) {
        this.enchere = enchere;
    }

    public int getIdEnchere() {
        return idEnchere;
    }

    public void setIdEnchere(int idEnchere) {
        this.idEnchere = idEnchere;
    }

    public String[] getLesSary() {
        try {
            Connection c = new BDD("postgres", "HY6NINF73nbTN5zYpzsk", "railway", "postgresql").getConnection();
            String sql = "select * from photoenchere where idenchere = "+this.getIdEnchere();
            ArrayList<Object> listes = executeResultedQuery(c, sql, null);
            c.close();
            String [] sary = new String[listes.size()];
            for (int i = 0; i < sary.length; i++) {
                sary[i] = ((PhotoEnchere)listes.get(i)).getPhoto();
            }
            setLesSary(sary);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lesSary;
    }

    public void setLesSary(String[] lesSary) {
        this.lesSary = lesSary;
    }
    
    @Override
    public void create(Connection c) throws Exception {
        String sql="insert into PhotoEnchere(photo,idenchere) values (?,?)";
        ArrayList<Object> objects=new ArrayList<>();
        objects.add(this.photo);
        objects.add(this.idEnchere);
        executeQuery(c, sql, objects);
    }
}
