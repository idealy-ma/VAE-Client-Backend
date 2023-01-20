/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controller;

import com.example.demo.dbmanager.connection.BDD;
import com.example.demo.model.Client;
import com.example.demo.model.Enchere;
import com.example.demo.model.Mise;
import com.example.demo.util.exception.JSONException;
import com.example.demo.util.security.TokenUserModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    BDD bdd;
    public MiseController() {
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

    @PostMapping("/mises")
<<<<<<< Updated upstream
    public HashMap<String, Object> addMise(@RequestBody Mise mise,@RequestHeader("userId") int userId,@RequestHeader("hash") String hash) throws Exception{
        Connection c = bdd.getConnection();
        TokenUserModel tokenUserModel = new TokenUserModel();
        tokenUserModel.setUserId(userId);
        tokenUserModel.setHash(hash);
        ArrayList<Object> arrayList = tokenUserModel.findAll(c);
        if( arrayList.size() > 0 ){
            try {
                returnValue.clear();
                c.setAutoCommit(false);
                Client client = mise.getClient();
                Mise lastMise = mise.findLast(c);
                if(lastMise == null){
                    Enchere enchere = mise.getEnchere();
                    if(client.verifySolde(enchere.getPrixMin())){
                        client.debiter(enchere.getPrixMin());
                        client.updateSolde(c);
                    } else {
                        c.rollback();
                        c.close();
                        returnValue.put("error", new JSONException("500", "Solde insuffisant"));
                        return returnValue;
                    }
                } else {
                    if ((lastMise.getSoldeMise()<mise.getSoldeMise()) && client.verifySolde(mise.getSoldeMise())) {
                        lastMise.getClient().crediter(lastMise.getSoldeMise());
                        client.debiter(lastMise.getSoldeMise());
=======
    public HashMap<String, Object> addMise() throws Exception{
        System.out.println("tesssssss");
        try {
            returnValue.clear();
            BDD bdd = new BDD("vae", "vae", "vae", "postgresql");
            Connection c = bdd.getConnection();
            Mise mise = new Mise();
            mise.setIdEnchere(2);
            mise.setSoldeMise(300);
            mise.setIdClient(3);
>>>>>>> Stashed changes

                        lastMise.getClient().updateSolde(c);
                        client.updateSolde(c);
                    } else {
                        c.rollback();
                        c.close();
                        returnValue.put("error", new JSONException("500", "Solde insuffisant ou mise inferieur au dernier mise"));
                        return returnValue;
                    }
                }
                mise.create(c);

                c.commit();
                c.close();
                returnValue.put("data", new JSONException("200", "insertion ok"));
            } catch (SQLException ex) {
                Logger.getLogger(MiseController.class.getName()).log(Level.SEVERE, null, ex);
                returnValue.put("error", new JSONException("500", ex.getMessage()));
                return returnValue; 
            }
        } else {
            returnValue.put("denied", "token expirer");
        }
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
