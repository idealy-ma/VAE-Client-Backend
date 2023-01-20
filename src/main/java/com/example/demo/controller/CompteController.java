package com.example.demo.controller;

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

import com.example.demo.dbmanager.connection.BDD;
import com.example.demo.model.Client;
import com.example.demo.model.RechargementCompte;
import com.example.demo.util.exception.JSONException;

@CrossOrigin
@RestController
public class CompteController {
    HashMap<String, Object> returnValue;
    BDD bdd = null;
    
    public CompteController() {
        try {
            bdd = new BDD("postgres", "HY6NINF73nbTN5zYpzsk", "railway", "postgresql");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        returnValue = new HashMap<>();
    }
    
    public HashMap<String, Object> getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(HashMap<String, Object> returnValue) {
        this.returnValue = returnValue;
    }
    @GetMapping("/soldes/{idClient}")
    public HashMap<String, Object> findSolde(@PathVariable int idClient) throws Exception{
        returnValue.clear();
        ArrayList<Client> listeClient = new ArrayList<>();
        try {
            Connection c = bdd.getConnection();
            Client client = new Client();
            client.setIdClient(idClient);
            ArrayList<Object> listeObjectClient = client.findAll(c);
            
            
            for (Object ench : listeObjectClient) {
                listeClient.add((Client)ench);
            }
            
            if (!listeClient.isEmpty()) {
                returnValue.put("data", listeClient);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CompteController.class.getName()).log(Level.SEVERE, null, ex);
            returnValue.put("error", new JSONException("500", ex.getMessage()));
            return returnValue; 
        }
        return returnValue;
    }

    @PostMapping("/soldes")
    public HashMap<String, Object> addRechargement(@RequestBody RechargementCompte rechargementCompte) throws Exception{
        try {
            returnValue.clear();
            Connection c = bdd.getConnection();
            rechargementCompte.setEtat(1);
            rechargementCompte.create(c);
            returnValue.put("data", new JSONException("200", "Insertion OK"));
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            returnValue.put("error", new JSONException("500", ex.getMessage()));
            return returnValue;
        } 
        return returnValue;
    }
}
