package com.example.demo.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dbmanager.connection.BDD;
import com.example.demo.model.Categorie;
import com.example.demo.util.exception.JSONException;

@CrossOrigin
@RestController
public class GlobalController {
    HashMap<String, Object> returnValue;
    BDD bdd = null;
    
    public GlobalController() {
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
    @GetMapping("/categories")
    public HashMap<String, Object> findCategorie() throws Exception{
        Connection c = null;
        returnValue.clear();
        ArrayList<Categorie> listeCategorie = new ArrayList<>();
        try {
            c = bdd.getConnection();
                ArrayList<Object> arrayList = new Categorie().findAll(c);
                for (Object enchere : arrayList) {
                    listeCategorie.add((Categorie)enchere);
                }
                
                if (!listeCategorie.isEmpty()) {
                    returnValue.put("data", listeCategorie);
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(CompteController.class.getName()).log(Level.SEVERE, null, ex);
                returnValue.put("error", new JSONException("500", ex.getMessage()));
                return returnValue; 
            } finally {
                if( c!=null )c.close();
            }
        return returnValue;
    }
}
