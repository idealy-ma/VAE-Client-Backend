/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controller;

import com.example.demo.dbmanager.connection.BDD;
import com.example.demo.model.Mise;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author P14A_30_Ando
 */
@RestController
@CrossOrigin
public class MiseController {
    HashMap<String, Object> returnValue;
    
    public MiseController() {
        returnValue = new HashMap<>();
    }

    public HashMap<String, Object> getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(HashMap<String, Object> returnValue) {
        this.returnValue = returnValue;
    }
    @PostMapping("/mises")
    public HashMap<String, Object> addMise(@RequestHeader(name="idEnchere") int idEnchere,@RequestHeader(name="soldeMise") double soldeMise,@RequestHeader(name="idClient") int idClient) throws Exception{
        System.out.println("tesssssss");
        try {
            returnValue.clear();
            BDD bdd = new BDD("vae", "vae", "vae", "postgresql");
            Connection c = bdd.getConnection();
            Mise mise = new Mise();
            mise.setIdEnchere(idEnchere);
            mise.setSoldeMise(soldeMise);
            mise.setIdClient(idClient);

            mise.create(c);
            
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(MiseController.class.getName()).log(Level.SEVERE, null, ex);
            returnValue.put("error", new JSONException("500", ex.getMessage()));
            return returnValue; 
        }
        returnValue.put("response", new JSONException("200", "Insertion OK"));
        return returnValue;
    } 
    
   /* @GetMapping("/mises/{idEnchere}")
    public HashMap<String, Object> findMiseByIdEnchere(@PathVariable int idEnchere) throws Exception{
        returnValue.clear();
        ArrayList<Mise> listeMise = new ArrayList<>();
        try {
            BDD bdd = new BDD("postgres", "root", "Enchere");
            Connection c = bdd.getConnection();
            Mise mise = new Mise();
            mise.setIdEnchere(idEnchere);
            ArrayList<Object> listeObjectMise = mise.findAll(c);
            
            for (Object m : listeObjectMise) {
                listeMise.add((Mise)m);
            }
            
            if (!listeMise.isEmpty()) {
                returnValue.put("data", listeMise);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(MiseController.class.getName()).log(Level.SEVERE, null, ex);
            returnValue.put("error", new JSONException("500", ex.getMessage()));
            return returnValue; 
        }
        return returnValue;
    }*/
}
