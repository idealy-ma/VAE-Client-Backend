/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import com.example.demo.dbmanager.annotation.PrimaryKey;

/**
 *
 * @author P14A_30_Ando
 */
public class PhotoEnchere {
    @PrimaryKey
    private int idPhotoEnchere;
    private String photo;
    private Enchere enchere;
    private int idEnchere;

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
    
    
}
