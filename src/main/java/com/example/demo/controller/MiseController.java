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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public HashMap<String, Object> addMise(@RequestBody Mise mise) throws Exception{
        try {
            returnValue.clear();
            BDD bdd = new BDD("postgres", "HY6NINF73nbTN5zYpzsk", "railway", "postgresql");
            Connection c = bdd.getConnection();
            c.setAutoCommit(false);
            /**
             * Get last mise
             * if last mise null
             *  verify prix min enchere
             *  verify solde
             *  update solde
             * else
             *  verify solde
             *  update solde
             *  return solde
             */
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
