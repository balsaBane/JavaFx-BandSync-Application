

package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class SugData {
   public SimpleIntegerProperty SongId = new SimpleIntegerProperty(); 
   public SimpleStringProperty SongName = new SimpleStringProperty(); 
   public SimpleStringProperty Artist = new SimpleStringProperty();
   public SimpleStringProperty Youtube = new SimpleStringProperty(); 

   public Integer getSongId() {
      return SongId.get();
   } 
   
   public String getSongName() {
      return SongName.get();
   }

   public String getArtist() {
      return Artist.get();
   }
   public String getYoutube() {
      return Youtube.get();
   }
}
