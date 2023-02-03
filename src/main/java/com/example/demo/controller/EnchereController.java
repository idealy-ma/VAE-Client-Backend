/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controller;

import com.example.demo.dbmanager.connection.BDD;
import com.example.demo.model.Enchere;
import com.example.demo.model.PhotoEnchere;
import com.example.demo.util.exception.JSONException;
import com.example.demo.util.security.TokenUserModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author P14A_30_Ando
 */

@RestController
@CrossOrigin
public class EnchereController {
    HashMap<String, Object> returnValue;
    BDD bdd;
    
    public EnchereController() {
        returnValue = new HashMap<>();
        try {
            bdd = new BDD("postgres", "HY6NINF73nbTN5zYpzsk", "railway", "postgresql");
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
    public HashMap<String, Object> getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(HashMap<String, Object> returnValue) {
        this.returnValue = returnValue;
    }
   @GetMapping("/encheres")
    public HashMap<String, Object> findEnchere() throws Exception{
        returnValue.clear();
        ArrayList<Enchere> listeEnchere = new ArrayList<>();
        Connection c = null;
        try {
            c = bdd.getConnection();
            Enchere enchere = new Enchere();
            ArrayList<Object> listeObjectEnchere = enchere.findAll(c);
            for (Object ench : listeObjectEnchere) {
                Enchere enchere1 = ((Enchere)ench);
                PhotoEnchere photoEnchere = new PhotoEnchere();
                photoEnchere.setIdEnchere(enchere1.getIdEnchere());
                enchere1.setLesSary(photoEnchere.getLesSary());
                listeEnchere.add((Enchere)ench);
            }
            
            
            returnValue.put("data", listeEnchere);
            
        } catch (SQLException ex) {
            Logger.getLogger(EnchereController.class.getName()).log(Level.SEVERE, null, ex);
            returnValue.put("error", new JSONException("500", ex.getMessage()));
            return returnValue; 
        } finally {
            if(c!=null)c.close();
        }
        return returnValue;
    }
    
    @GetMapping("/encheres?idClient={idClient}")
    public HashMap<String, Object> findEnchereByIdClient(@PathVariable int idClient,@RequestHeader("userId") int userId,@RequestHeader("hash") String hash) throws Exception{
        returnValue.clear();
        Connection c = bdd.getConnection();
        TokenUserModel tokenUserModel = new TokenUserModel();
        tokenUserModel.setUserId(userId);
        tokenUserModel.setHash(hash);
        ArrayList<Object> arrayList = tokenUserModel.findAll(c);
        try {
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(c!=null)c.close();
        }
        return returnValue; 
    }
    @PostMapping("/encheres")
    public HashMap<String, Object> addEnchere(@RequestBody Enchere enchere,@RequestHeader("userId") int userId,@RequestHeader("hash") String hash) throws Exception{
        Connection c = null;
        TokenUserModel tokenUserModel = new TokenUserModel();
        tokenUserModel.setUserId(userId);
        tokenUserModel.setHash(hash);
        ArrayList<Object> arrayList = tokenUserModel.findAll(c);
        try {
            c = bdd.getConnection();
            if( !arrayList.isEmpty() ){
                try {
                    returnValue.clear();
                    if( enchere.getEtat() == 1 ){
                        enchere.create(c);
                        Enchere enchere1 = enchere.getEnd(c);
                        PhotoEnchere photoEnchere = new PhotoEnchere();
                        photoEnchere.setIdEnchere(enchere1.getIdEnchere());
                        photoEnchere.setPhoto(enchere.getPhotos());
                        photoEnchere.create(c);
                    } else {
                        Enchere enchere1 = enchere.getEnd(c);
                        PhotoEnchere photoEnchere = new PhotoEnchere();
                        photoEnchere.setIdEnchere(enchere1.getIdEnchere());
                        photoEnchere.setPhoto(enchere.getPhotos());
                        photoEnchere.create(c);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(EnchereController.class.getName()).log(Level.SEVERE, null, ex);
                    returnValue.put("error", new JSONException("500", ex.getMessage()));
                    return returnValue; 
                }
                returnValue.put("response", new JSONException("200", "Insertion OK"));
            } else {
                returnValue.clear();
                returnValue.put("denied", "token expirer");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(c!=null)c.close();
        }
        return returnValue;
    } 
    
    @GetMapping("/encheres/{idEnchere}")
    public HashMap<String, Object> findEnchereByIdEnchere(@PathVariable int idEnchere,@RequestHeader("userId") int userId,@RequestHeader("hash") String hash) throws Exception{
        returnValue.clear();
        Connection c = null;
        TokenUserModel tokenUserModel = new TokenUserModel();
        tokenUserModel.setUserId(userId);
        tokenUserModel.setHash(hash);
        ArrayList<Object> arrayList = tokenUserModel.findAll(c);
        try {
            c = bdd.getConnection();
            if( !arrayList.isEmpty() ){
            
                ArrayList<Enchere> listeEnchere = new ArrayList<>();
                try {
                    Enchere enchere = new Enchere();
                    enchere.setIdEnchere(idEnchere);
    
                    ArrayList<Object> listeObjectEnchere = enchere.findAll(c);
                    
                    for (Object encher : listeObjectEnchere) {
                        Enchere enchere1 = ((Enchere)encher);
                        PhotoEnchere photoEnchere = new PhotoEnchere();
                        photoEnchere.setIdEnchere(enchere1.getIdEnchere());
                        enchere1.setLesSary(photoEnchere.getLesSary());
                        listeEnchere.add((Enchere)encher);
                    }
                    
                    if (!listeEnchere.isEmpty()) {
                        returnValue.put("data", listeEnchere);
                    }
                    
                } catch (SQLException ex) {
                    Logger.getLogger(EnchereController.class.getName()).log(Level.SEVERE, null, ex);
                    returnValue.put("error", new JSONException("500", ex.getMessage()));
                    return returnValue; 
                }
            } else {
                returnValue.clear();
                returnValue.put("denied", "token expirer");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(c!=null)c.close();
        }
        return returnValue;
    }


    @GetMapping("/historiques")
    public HashMap<String , Object> getHistorique(@RequestParam("idClient") int idClient) throws Exception {
        HashMap<String , Object> hashMap = new HashMap<>();
        try {
        } catch (Exception ex) {
            Logger.getLogger(EnchereController.class.getName()).log(Level.SEVERE, null, ex);
            hashMap.put("error", new JSONException("500", ex.getMessage()));
            return hashMap; 
        }
        return hashMap;
    }
}
