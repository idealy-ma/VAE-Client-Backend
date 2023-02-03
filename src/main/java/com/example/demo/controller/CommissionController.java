package com.example.demo.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dbmanager.connection.BDD;
import com.example.demo.model.Commission;
import com.example.demo.util.exception.JSONException;
import com.example.demo.util.security.TokenUserModel;

@CrossOrigin
@RestController
public class CommissionController {
    BDD bdd;
    
    public CommissionController() {
        try {
            bdd = new BDD("postgres", "HY6NINF73nbTN5zYpzsk", "railway", "postgresql");
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    @GetMapping("/commissions")
    public HashMap<String ,Object> getCommission(@RequestHeader("userId") int userId,@RequestHeader("hash") String hash) throws Exception {
        HashMap<String ,Object> hashMap = new HashMap<>();
        Connection c = bdd.getConnection();
        TokenUserModel tokenUserModel = new TokenUserModel();
        tokenUserModel.setUserId(userId);
        tokenUserModel.setHash(hash);
        ArrayList<Object> list = tokenUserModel.findAll(c);
        try {
            if( list.size() > 0 ){    
                try {
                    Commission commission = new Commission();
                    ArrayList<Object> arrayList = commission.findAll(c);
                    hashMap.put("data", arrayList);
    
                } catch (Exception e) {
                    Logger.getLogger(CommissionController.class.getName()).log(Level.SEVERE, null, e);
                    hashMap.put("error", new JSONException("500", e.getMessage()));
                    return hashMap; 
                } 
            } else {
                hashMap.clear();
                hashMap.put("denied", "token expirer");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if( c != null ) c.close();
        }
        return hashMap;
    }

    @PostMapping("/commissions")
    public HashMap<String ,Object> saveCommission(@RequestBody Commission commission,@RequestHeader("userId") int userId,@RequestHeader("hash") String hash) throws Exception {
        HashMap<String ,Object> hashMap = new HashMap<>();
        Connection c = bdd.getConnection();
        TokenUserModel tokenUserModel = new TokenUserModel();
        tokenUserModel.setUserId(userId);
        tokenUserModel.setHash(hash);
        ArrayList<Object> list = tokenUserModel.findAll(c);
        try {
            if( list.size() > 0 ){  
                try {
                    commission.create(c);
                } catch (Exception e) {
                    Logger.getLogger(CommissionController.class.getName()).log(Level.SEVERE, null, e);
                    hashMap.put("error", new JSONException("500", e.getMessage()));
                    return hashMap; 
                } 
                hashMap.put("response", new JSONException("200", "Insertion OK"));
            } else {
                hashMap.clear();
                hashMap.put("denied", "token expirer");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if( c != null ) c.close();
        }
        return hashMap;
    }

    @PutMapping("/commissions")
    public HashMap<String ,Object> updateCommission(@RequestBody Commission commission,@RequestHeader("userId") int userId,@RequestHeader("hash") String hash) throws Exception {
        HashMap<String ,Object> hashMap = new HashMap<>();
        Connection c = bdd.getConnection();
        TokenUserModel tokenUserModel = new TokenUserModel();
        tokenUserModel.setUserId(userId);
        tokenUserModel.setHash(hash);
        ArrayList<Object> list = tokenUserModel.findAll(c);
        try {
            if( list.size() > 0 ){ 
                try {
                    commission.update(c);
                } catch (Exception e) {
                    Logger.getLogger(CommissionController.class.getName()).log(Level.SEVERE, null, e);
                    hashMap.put("error", new JSONException("500", e.getMessage()));
                    return hashMap; 
                } 
                hashMap.put("response", new JSONException("200", "Update OK"));
            } else {
                hashMap.clear();
                hashMap.put("denied", "token expirer");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if( c != null ) c.close();
        }
        return hashMap;
    }
}
