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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author P14A_30_Ando
 */
@RestController
@CrossOrigin
public class LoginController {
    HashMap<String, Object> returnValue;
    BDD bdd;
    
    public LoginController() {
        returnValue = new HashMap<>();
        try {
            bdd = new BDD("postgres", "HY6NINF73nbTN5zYpzsk", "railway", "postgresql");
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }  
    
    @PostMapping("/login")
    public HashMap<String, Object> login(@RequestBody Client client) throws Exception{
        Connection c = null;
        try {
            c = bdd.getConnection();
            returnValue.clear();

                client.find(c);
                if(client.getIdClient()>0){
                    returnValue.put("data", client.getMyToken());
                } else {
                    returnValue.put("error", new JSONException("403", "User not found"));
                }
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            returnValue.put("error", new JSONException("500", ex.getMessage()));
            return returnValue;
        } finally {
            if( c!=null )c.close();
        }
        return returnValue;
    }

    @PostMapping("/clients")
    public HashMap<String, Object> inscription(@RequestBody Client client) throws Exception{
        Connection c = null;
        try {
            c = bdd.getConnection();
                client.create(c);
                returnValue.put("data", true);
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            returnValue.put("error", new JSONException("500", ex.getMessage()));
            return returnValue;
        } finally {
            if( c!=null )c.close();
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
