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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dbmanager.connection.BDD;
import com.example.demo.model.Client;
import com.example.demo.model.RechargementCompte;
import com.example.demo.util.exception.JSONException;
import com.example.demo.util.security.TokenUserModel;

@CrossOrigin
@RestController
public class CompteController {
    HashMap<String, Object> returnValue;
    BDD bdd = null;
    
    public CompteController() {
        try {
            bdd = new BDD("postgres", "HY6NINF73nbTN5zYpzsk", "railway", "postgresql");
        } catch (Exception e) {
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
    public HashMap<String, Object> findSolde(@PathVariable int idClient,@RequestHeader("userId") int userId,@RequestHeader("hash") String hash) throws Exception{
        Connection c = bdd.getConnection();
        TokenUserModel tokenUserModel = new TokenUserModel();
        tokenUserModel.setUserId(userId);
        tokenUserModel.setHash(hash);
        ArrayList<Object> arrayList = tokenUserModel.findAll(c);
        if( arrayList.size() > 0 ){
            returnValue.clear();
            ArrayList<Client> listeClient = new ArrayList<>();
            try {
                
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
        } else {
            returnValue.clear();
            returnValue.put("denied", "token expirer");
        }
        
        return returnValue;
    }

    @PostMapping("/soldes")
    public HashMap<String, Object> addRechargement(@RequestBody RechargementCompte rechargementCompte,@RequestHeader("userId") int userId,@RequestHeader("hash") String hash) throws Exception{
        Connection c = bdd.getConnection();
        TokenUserModel tokenUserModel = new TokenUserModel();
        tokenUserModel.setUserId(userId);
        tokenUserModel.setHash(hash);
        ArrayList<Object> arrayList = tokenUserModel.findAll(c);
        if( arrayList.size() > 0 ){
            try {
                returnValue.clear();
                rechargementCompte.setEtat(1);
                rechargementCompte.create(c);
                returnValue.put("data", new JSONException("200", "Insertion OK"));
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                returnValue.put("error", new JSONException("500", ex.getMessage()));
                return returnValue;
            } 
        } else {
            returnValue.clear();
            returnValue.put("denied", "token expirer");
        }
        return returnValue;
    }
}
