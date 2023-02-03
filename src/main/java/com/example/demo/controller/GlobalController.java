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
import com.example.demo.util.Notifier;
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
        /*Notifier notifier = new Notifier();
        notifier.setRegistrationToken("eg4AdieSTGSWlOWCRWIODb:APA91bHrhi5j9Xv4clqYmW9XMPngyT_cMjGlAF7fN93ZzHYHo7nlZgLKe_ns_LXfbQmmAIZBuqlkmCJVXwwGI6xA6wgVyMaT1S69OuAruYNOq9bb4OIE-mkbLK5rIZIEL0Ll2ZQnlzia");
        notifier.sendMessage("cetegories", "Maka liste Categorie");*/
        Connection c = bdd.getConnection();
        returnValue.clear();
        ArrayList<Categorie> listeCategorie = new ArrayList<>();
        try {
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
            }
        return returnValue;
    }
}
