package com.example.demo.model;

import java.sql.Connection;
import java.util.ArrayList;

import com.example.demo.dbmanager.bdd.object.BddObject;

public class Commission extends BddObject {
    private int idCommission;
    private double pourcentage;
    public int getIdCommission() {
        return idCommission;
    }
    public void setIdCommission(int idCommission) {
        this.idCommission = idCommission;
    }
    public double getPourcentage() {
        return pourcentage;
    }
    public void setPourcentage(double pourcentage) {
        this.pourcentage = pourcentage;
    }

    @Override
    public void create(Connection c) throws Exception {
        String sql = "INSERT INTO commission(pourcentage) VALUES(?)";
        ArrayList<Object> arrayList = new ArrayList<>();
        arrayList.add(this.pourcentage);
        executeQuery(c, sql, arrayList);
    }
    
    @Override
    public void update(Connection c) throws Exception {
        String sql = "UPDATE commission SET pourcentage = ?";
        ArrayList<Object> arrayList = new ArrayList<>();
        arrayList.add(this.pourcentage);
        executeQuery(c, sql, arrayList);
    }
}
