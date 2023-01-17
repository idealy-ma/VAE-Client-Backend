package com.example.demo.dbmanager;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionDB {
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

    public Connection getC() throws Exception{
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+this.getDatabaseName(),this.getUser(),this.getMdp());
    }
}
