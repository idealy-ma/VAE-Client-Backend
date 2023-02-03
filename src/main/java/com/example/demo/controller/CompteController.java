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
        Connection c = null;
        TokenUserModel tokenUserModel = new TokenUserModel();
        tokenUserModel.setUserId(userId);
        tokenUserModel.setHash(hash);
        ArrayList<Object> arrayList = tokenUserModel.findAll(c);
        try {
            c = bdd.getConnection();
            if( !arrayList.isEmpty() ){
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(c!=null)c.close();
        }
        
        return returnValue;
    }

    @PostMapping("/soldes")
    public HashMap<String, Object> addRechargement(@RequestBody RechargementCompte rechargementCompte,@RequestHeader("userId") int userId,@RequestHeader("hash") String hash) throws Exception{
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
                    rechargementCompte.setEtat(1);
                    rechargementCompte.create(c);
                    returnValue.put("data", new JSONException("200", "Insertion OK"));
                } catch (SQLException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
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

    @GetMapping("/rechargements/{idClient}")
    public HashMap<String, Object> findRechargementByIdClient(@PathVariable int idClient) throws Exception{
        returnValue.clear();
        ArrayList<RechargementCompte> listeRechargement = new ArrayList<>();
        Connection c = null;
        try {
            c = bdd.getConnection();
            RechargementCompte rechargementCompte = new RechargementCompte();
            rechargementCompte.setIdClient(idClient);
            ArrayList<Object> listeObjectRechargement = rechargementCompte.findAll(c);
            
            
            for (Object ench : listeObjectRechargement) {
                listeRechargement.add((RechargementCompte)ench);
            }
            
            if (!listeRechargement.isEmpty()) {
                returnValue.put("data", listeRechargement);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CompteController.class.getName()).log(Level.SEVERE, null, ex);
            returnValue.put("error", new JSONException("500", ex.getMessage()));
            return returnValue; 
        } finally {
            if(c!=null)c.close();
        }
        return returnValue;
    }
}
