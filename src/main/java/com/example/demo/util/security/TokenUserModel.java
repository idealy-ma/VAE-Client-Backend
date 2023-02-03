package com.example.demo.util.security;

import com.example.demo.dbmanager.bdd.object.BddObject;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;

public class TokenUserModel extends BddObject{
    private int userId;
    private String hash;
    private Timestamp expirationDate;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Timestamp getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public void create(Connection c) throws Exception {
        String sql = "INSERT INTO TokenUserModel(userid,hash,expirationdate) VALUES (?,?,?) ";
        ArrayList<Object> arrayList = new ArrayList<>();
        arrayList.add(this.userId);
        arrayList.add(this.hash);
        arrayList.add(this.expirationDate);
        executeQuery(c, sql, arrayList);
    }
    
}
 