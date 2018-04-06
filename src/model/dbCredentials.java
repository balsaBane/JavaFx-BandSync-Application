package model;

import java.io.Serializable;

public class dbCredentials implements Serializable { //name of the class: database credentials

    private String bandName;
    private String host;
    private String databaseName;
    private String databaseUser;
    private String databasePass;
    private int port;

    public dbCredentials(String bandName, String databaseName, String databasePass, String databaseUser, String host, int port) {
        this.bandName = bandName;
        this.databaseName = databaseName;
        this.databasePass = databasePass;
        this.databaseUser = databaseUser;
        this.host = host;
        this.port = port;
    } 
    public dbCredentials(){
        
    }  
    
    public void setBandName(String bandName) {
        this.bandName = bandName;
    }

    public String getBandName() {
        return bandName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public void setDatabasePass(String databasePass) {
        this.databasePass = databasePass;
    }

    public void setDatabaseUser(String databaseUser) {
        this.databaseUser = databaseUser;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getDatabasePass() {
        return databasePass;
    }

    public String getDatabaseUser() {
        return databaseUser;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
