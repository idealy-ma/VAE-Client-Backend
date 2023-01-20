package com.example.demo.model;

import java.sql.Connection;
import java.util.ArrayList;

import com.example.demo.dbmanager.connection.BDD;

public class EnchereMises {
    private int idEnchere;
    private Enchere enchere;
    private ArrayList<Mise> arrayListMises;
    private BDD bdd;

    public EnchereMises(){
        try {
            bdd = new BDD("postgres", "HY6NINF73nbTN5zYpzsk", "railway", "postgresql");
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    public int getIdEnchere() {
        return idEnchere;
    }
    public void setIdEnchere(int idEnchere) {
        this.idEnchere = idEnchere;
    }
    public Enchere getEnchere() throws Exception {
        if( enchere == null ){
            enchere = new Enchere();
            enchere.setIdEnchere(getIdEnchere());
            Connection connection = null;
            try {
                connection = bdd.getConnection();
                ArrayList<Object> arrayList = enchere.findAll(connection);
                for (Object object : arrayList) {
                    enchere = (Enchere) object;
                } 
            } catch (Exception e) {
                throw e;
            } finally {
                if( connection != null ) connection.close();
            }
        }
        return enchere;
    }
    public void setEnchere(Enchere enchere) {
        this.enchere = enchere;
    }
    public ArrayList<Mise> getArrayListMises() throws Exception {
        if( arrayListMises == null ){
            arrayListMises = new ArrayList<>();
            Mise mise = new Mise();
            mise.setIdEnchere(getIdEnchere());
            Connection connection = null;
            try {
                connection = bdd.getConnection();
                ArrayList<Object> arrayList = mise.findAll(connection);
                for (Object object : arrayList) {
                    arrayListMises.add((Mise) object);
                } 
            } catch (Exception e) {
                throw e;
            } finally {
                if( connection != null ) connection.close();
            }
        }
        return arrayListMises;
    }
    public void setArrayListMises(ArrayList<Mise> arrayListMises) {
        this.arrayListMises = arrayListMises;
    }
}
