package model;

import controllers.CredentialsController;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CredentialsManager {  
    
    private static ArrayList<dbCredentials> dbList;
    private static dbCredentials selectedDatabaseCredentials;
    
    public static ArrayList<dbCredentials> getTheList(){
        return dbList;
    }
    
    public static void update(dbCredentials db, int changeIndex) {

        try {
            dbList = readCreds(); 
            dbList.get(changeIndex).setBandName(db.getBandName());
            dbList.get(changeIndex).setHost(db.getHost());
            dbList.get(changeIndex).setDatabaseName(db.getDatabaseName());
            dbList.get(changeIndex).setDatabaseUser(db.getDatabaseUser());
            dbList.get(changeIndex).setDatabasePass(db.getDatabasePass());
            dbList.get(changeIndex).setPort(db.getPort());

            writeCreds(dbList);
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void addNew(dbCredentials a) {

        try {

            dbList.add(a);
            writeCreds(dbList);

        } catch (Exception e) {
 
            dbList = readCreds();
            dbList.add(a);
            try {
                writeCreds(dbList);
            } catch (IOException ex) {
                Logger.getLogger(CredentialsManager.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public static void delete(String bandName) {
        for (int i = 0; i < dbList.size(); i++) {
            if (dbList.get(i).getBandName().equals(bandName)) {
                dbList.remove(i);
            }
        }
        try {
            writeCreds(dbList);
        } catch (IOException ex) {
            Logger.getLogger(CredentialsManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ArrayList<dbCredentials> readCreds()   {

        ArrayList<dbCredentials> listAccount = new ArrayList();
        
        //Create new FileInputStream object to read file
        
        FileInputStream fis = null;
        ObjectInputStream obj = null;
        
        String home = System.getenv("APPDATA");
        System.out.println(home);
        try {
            fis = new FileInputStream(home + "\\cred.dat");
            obj = new ObjectInputStream(fis);
        } catch (FileNotFoundException ex) { 
            return new ArrayList<>();
        } catch (IOException ex) {
            Logger.getLogger(CredentialsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Create new ObjectInputStream object to read object from file  
        
        try {
            while (fis.available() != -1) {
                //Read object from file
                dbCredentials acc = (dbCredentials) obj.readObject();
                listAccount.add(acc);
            }
        } catch (EOFException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(CredentialsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CredentialsManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        dbList = listAccount;
        return listAccount;
    }

    public static void writeCreds(ArrayList<dbCredentials> lista) throws IOException {

        //Create FileOutputStream to write file
        FileOutputStream fos;
        ObjectOutputStream objOutputStream = null;
        String home = System.getenv("APPDATA");
        
        try {
            fos = new FileOutputStream(home+ "\\cred.dat");
            objOutputStream = new ObjectOutputStream(fos);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CredentialsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CredentialsController.class.getName()).log(Level.SEVERE, null, ex);
        } 

        //Write object to file
        for (dbCredentials baza : lista) {
            objOutputStream.writeObject(baza);
            objOutputStream.reset();
        }
        objOutputStream.close();

    }
    
    public static void setSelectedDatabase(dbCredentials a){
        selectedDatabaseCredentials = a;
    }
    
    public static dbCredentials getSelectedDatabase(){
        return selectedDatabaseCredentials;
    }

}
