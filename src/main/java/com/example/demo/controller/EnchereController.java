/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controller;

import com.example.demo.dbmanager.connection.BDD;
import com.example.demo.model.Enchere;
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
        try {
            
            Connection c = bdd.getConnection();
            Enchere enchere = new Enchere();
            ArrayList<Object> listeObjectEnchere = enchere.findAll(c);
            
            
            for (Object ench : listeObjectEnchere) {
                listeEnchere.add((Enchere)ench);
            }
            
            
            returnValue.put("data", listeEnchere);
            
        } catch (SQLException ex) {
            Logger.getLogger(EnchereController.class.getName()).log(Level.SEVERE, null, ex);
            returnValue.put("error", new JSONException("500", ex.getMessage()));
            return returnValue; 
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
        if( !arrayList.isEmpty() ){
            ArrayList<Enchere> listeEnchere = new ArrayList<>();
            try {
                Enchere enchere = new Enchere();
                enchere.setIdClient(idClient);
                ArrayList<Object> listeObjectEnchere = enchere.findAll(c);
                
                for (Object m : listeObjectEnchere) {
                    listeEnchere.add((Enchere)m);
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
        return returnValue;
    }
    @PostMapping("/encheres")
    public HashMap<String, Object> addEnchere(@RequestBody Enchere enchere,@RequestHeader("userId") int userId,@RequestHeader("hash") String hash) throws Exception{
        Connection c = bdd.getConnection();
        TokenUserModel tokenUserModel = new TokenUserModel();
        tokenUserModel.setUserId(userId);
        tokenUserModel.setHash(hash);
        ArrayList<Object> arrayList = tokenUserModel.findAll(c);
        if( !arrayList.isEmpty() ){
            try {
                returnValue.clear();
                enchere.create(c);
                
                c.close();
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
        return returnValue;
    } 
    
    @GetMapping("/encheres/{idEnchere}")
    public HashMap<String, Object> findEnchereByIdEnchere(@PathVariable int idEnchere,@RequestHeader("userId") int userId,@RequestHeader("hash") String hash) throws Exception{
        returnValue.clear();
        Connection c = bdd.getConnection();
        TokenUserModel tokenUserModel = new TokenUserModel();
        tokenUserModel.setUserId(userId);
        tokenUserModel.setHash(hash);
        ArrayList<Object> arrayList = tokenUserModel.findAll(c);
        if( !arrayList.isEmpty() ){
            
            ArrayList<Enchere> listeEnchere = new ArrayList<>();
            try {
                Enchere enchere = new Enchere();
                enchere.setIdEnchere(idEnchere);

                ArrayList<Object> listeObjectEnchere = enchere.findAll(c);
                
                for (Object encher : listeObjectEnchere) {
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
        return returnValue;
    }


    @GetMapping("/historiques")
    public HashMap<String , Object> getHistorique(@RequestParam("idClient") int idClient) throws Exception {
        return new HashMap<>();
    }

    @GetMapping("/recherche:mobile")
    public HashMap<String , Object> recherche(@RequestParam String motCle) throws Exception {
        HashMap<String , Object> hashMap = new HashMap<>();
        Connection c = bdd.getConnection();
        ArrayList<Enchere> encheres = new ArrayList<>();
        try {
            ArrayList<Object> listeEnchere = new Enchere().recherche(c,motCle);
                
                for (Object object : listeEnchere) {
                    encheres.add((Enchere) object);
                }

                if (!encheres.isEmpty()) {
                    hashMap.put("data", encheres);
                }
                
        } catch (Exception ex) {
            Logger.getLogger(EnchereController.class.getName()).log(Level.SEVERE, null, ex);
            hashMap.put("error", new JSONException("500", ex.getMessage()));
            return hashMap; 
        }
        return hashMap;
    }
}
