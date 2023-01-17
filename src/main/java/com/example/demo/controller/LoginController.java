/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controller;

import com.example.demo.dbmanager.connection.BDD;
import com.example.demo.model.Client;
import com.example.demo.util.exception.JSONException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author P14A_30_Ando
 */
@RestController
@CrossOrigin
public class LoginController {
    HashMap<String, Object> returnValue;
    
    public LoginController() {
        returnValue = new HashMap<>();
    }  
    
    @PostMapping("/clients")
    public HashMap<String, Object> login(@RequestHeader(name="email") String email, @RequestHeader(name="mdp") String mdp) throws Exception{
        try {
            returnValue.clear();
            BDD bdd = new BDD("postgres", "root", "Enchere");
            try (Connection c = bdd.getConnection()) {
                Client client = new Client();
                client.setEmail(email);
                client.setMdp(mdp);

                client.find(c);
                if(client.getIdClient()>0){
                    returnValue.put("data", client.getMyToken());
                } else {
                    returnValue.put("error", new JSONException("403", "User not found"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            returnValue.put("error", new JSONException("500", ex.getMessage()));
            return returnValue;
        }
        returnValue.put("response", new JSONException("200", "Insertion OK"));
        return returnValue;
    }

    public HashMap<String, Object> getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(HashMap<String, Object> returnValue) {
        this.returnValue = returnValue;
    }
}
