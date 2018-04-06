

package model;

import java.sql.Connection;
import java.sql.PreparedStatement; 
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TableView;


public class DatabaseSingleton {
    
    private static DatabaseSingleton singleton ;
    private Connection conn;
    
    private DatabaseSingleton(){
         conn = ConnectDb.connectDb();
    }
    
    public static  DatabaseSingleton getSingleton(){
        if (singleton == null) {
            singleton = new DatabaseSingleton();
        }
        return singleton;
    }
    
    public void connect(Connection con){
        con = ConnectDb.connectDb();
    } 
    
    public Connection getConnection() {
        return conn;
    }
    
    // method that adds the song to the database
    
    public void updateRepertoire ( TableView tabela ) {
         
    }
    
    public void add (String song, String songName, String Artist, String category, 
                     int tempo, String dateCreated, String dateModified,  String domFor, String youtube ) {
        
        PreparedStatement prep = null; 
        String sql = "insert into repertoire (song, songName,artist, category, tempo, dateCreated, "
                +    "dateModified, domFor, youtube ) values(?,?,?,?,?,?,?,? , ?)";
        try {
            prep = conn.prepareStatement(sql);
            prep.setString (1, song);  
            prep.setString (2, songName);
            prep.setString (3, Artist);
            prep.setString (4, category);
            prep.setInt    (5, tempo);
            prep.setString (6, dateCreated);
            prep.setString (7, dateModified);
            prep.setString (8, domFor);
            prep.setString(9, youtube);
            prep.execute();  
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally{ 
            try {
                prep.close();  
            } catch (SQLException ex) { 
                System.out.println(ex.getMessage());
            }
        }
    }
 
    
    public void update(String song, String songName, String Artist, String category, 
                     int tempo, String dateModified,  String domFor, String youtube, int rowid){
        PreparedStatement prep = null;
        try {
            prep = conn.prepareStatement("UPDATE repertoire SET song = ?, songName = ?, artist = ?, category = ?,"
                    + " tempo = ?, dateModified = ?, domFor = ?, youtube = ?   where rowid = " + rowid);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        String sql = "insert into repertoire (song, songName,artist, category, tempo, dateCreated, "
                +    "dateModified, domFor, youtube ) values(?,?,?,?,?,?,?,?,?)";
        try {
            
            prep.setString (1, song);  
            prep.setString (2, songName);
            prep.setString (3, Artist);
            prep.setString (4, category);
            prep.setInt    (5, tempo); 
            prep.setString (6, dateModified);
            prep.setString (7, domFor);
            prep.setString(8, youtube);
            prep.executeUpdate();  
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally{ 
            try {
                prep.close();  
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    public String getDateAndTime(){
        String timeStamp = new SimpleDateFormat("dd.MM.yyyy HH.mm.ss").format(Calendar.getInstance().getTime());
        return timeStamp;
    }
    
    public void delete (int broj, String table)  {
        
        PreparedStatement prep2 = null;
        String sql = "DELETE from " + table+ " where rowid = " + Integer.toString(broj) + " "  ; 
        try {
            prep2 =  conn.prepareStatement(sql);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        try {
            prep2.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }   finally{  
            try {
                prep2.close();
            } catch (SQLException ex) {
               System.out.println(ex.getMessage());
            }
        }
        
        fixTheIndexes(table);
        
        
    }
    
    public void fixTheIndexes(String table){
        
        PreparedStatement prep1 = null;
        PreparedStatement prep2 = null;
        String sql1 = "ALTER TABLE "+table+" DROP rowid";
        String sql2 = "ALTER TABLE "+table+" ADD rowid MEDIUMINT NOT NULL AUTO_INCREMENT PRIMARY KEY";
        
        try{
            prep1 = conn.prepareStatement(sql1);
            prep2 = conn.prepareStatement(sql2);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        try {
            prep1.execute();
            prep2.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                prep1.close();
                prep2.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        } 
    }
    
    
      
}
