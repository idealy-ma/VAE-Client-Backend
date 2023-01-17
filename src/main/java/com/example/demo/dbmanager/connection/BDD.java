/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.dbmanager.connection;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author i.m.a
 */
public class BDD {
    String user;
    String databaseName;
    String mdp;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }
    
    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public BDD(String user, String mdp, String databaseName) {
        this.user = user;
        this.databaseName = databaseName;
        this.mdp = mdp;
    }

    public Connection getConnection() throws SQLException{
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+this.getDatabaseName(),this.getUser(),this.getMdp());
    }
}
