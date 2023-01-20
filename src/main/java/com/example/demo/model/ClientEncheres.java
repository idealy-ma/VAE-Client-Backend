package com.example.demo.model;

import java.sql.Connection;
import java.util.ArrayList;

import com.example.demo.dbmanager.connection.BDD;

public class ClientEncheres {
    private int idClient;
    private Client client;
    private ArrayList<Enchere> arrayListEncheres;
    private BDD bdd;

    public ClientEncheres(){
        try {
            bdd = new BDD("postgres", "HY6NINF73nbTN5zYpzsk", "railway", "postgresql");
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    public int getIdClient() {
        return idClient;
    }
    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }
    public Client getClient() throws Exception {
        if( client == null ){
            client = new Client();
            client.setIdClient(getIdClient());
            Connection connection = null;
            try {
                connection = bdd.getConnection();
                ArrayList<Object> arrayList = client.findAll(connection);
                for (Object object : arrayList) {
                    client = (Client) object;
                }
            } catch (Exception e) {
                throw e;
            } finally {
                if( connection != null ) connection.close();
            }
        }
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public ArrayList<Enchere> getArrayListEncheres() throws Exception {
        if( arrayListEncheres == null ){
            arrayListEncheres = new ArrayList<>();
            Enchere enchere = new Enchere();
            enchere.setIdClient(getIdClient());
            Connection connection = null;
            try {
                connection = bdd.getConnection();
                ArrayList<Object> arrayList = enchere.findAll(connection);
                for (Object object : arrayList) {
                    arrayListEncheres.add((Enchere) object);
                }
            } catch (Exception e) {
                throw e;
            } finally {
                if( connection != null ) connection.close();
            }
        }
        return arrayListEncheres;
    }
    public void setArrayListEncheres(ArrayList<Enchere> arrayListEncheres) {
        this.arrayListEncheres = arrayListEncheres;
    }
}
