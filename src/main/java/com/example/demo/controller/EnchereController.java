/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controller;

import com.example.demo.dbmanager.connection.BDD;
import com.example.demo.model.Enchere;
import com.example.demo.util.exception.JSONException;
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
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author P14A_30_Ando
 */

@RestController
@CrossOrigin
public class EnchereController {
    HashMap<String, Object> returnValue;
    
    public EnchereController() {
        returnValue = new HashMap<>();
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
            new BDD("postgres", "HY6NINF73nbTN5zYpzsk", "railway", "postgresql");
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
        public HashMap<String, Object> findEnchereByIdClient(@PathVariable int idClient) throws Exception{
        returnValue.clear();
        ArrayList<Enchere> listeEnchere = new ArrayList<>();
        try {
            new BDD("postgres", "HY6NINF73nbTN5zYpzsk", "railway", "postgresql");
            Connection c = bdd.getConnection();
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
            Logger.getLogger(MiseController.class.getName()).log(Level.SEVERE, null, ex);
            returnValue.put("error", new JSONException("500", ex.getMessage()));
            return returnValue; 
        }
        return returnValue;
    }
    @PostMapping("/encheres")
    public HashMap<String, Object> addEnchere(@RequestBody Enchere enchere) throws Exception{
        try {
            returnValue.clear();
            new BDD("postgres", "HY6NINF73nbTN5zYpzsk", "railway", "postgresql");
            Connection c = bdd.getConnection();
            enchere.create(c);
            
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(MiseController.class.getName()).log(Level.SEVERE, null, ex);
            returnValue.put("error", new JSONException("500", ex.getMessage()));
            return returnValue; 
        }
        returnValue.put("response", new JSONException("200", "Insertion OK"));
        return returnValue;
    } 
    
    @GetMapping("/encheres/{idEnchere}")
    public HashMap<String, Object> findEnchereByIdEnchere(@PathVariable int idEnchere) throws Exception{
        System.out.println(idEnchere);
        returnValue.clear();
        ArrayList<Enchere> listeEnchere = new ArrayList<>();
        try {
            new BDD("postgres", "HY6NINF73nbTN5zYpzsk", "railway", "postgresql");
            Connection c = bdd.getConnection();
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
            Logger.getLogger(Enchere.class.getName()).log(Level.SEVERE, null, ex);
            returnValue.put("error", new JSONException("500", ex.getMessage()));
            return returnValue; 
        }
        return returnValue;
    }
}
