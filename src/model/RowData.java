

package model;
 
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class RowData {    

   public SimpleIntegerProperty songId = new SimpleIntegerProperty(); 
   public SimpleStringProperty songName = new SimpleStringProperty(); 
   public SimpleStringProperty artist = new SimpleStringProperty();
   public SimpleIntegerProperty tempo = new SimpleIntegerProperty();
   public SimpleStringProperty category = new SimpleStringProperty();
   public SimpleStringProperty dateCreated = new SimpleStringProperty();
   public SimpleStringProperty dateModified = new SimpleStringProperty();
   public SimpleStringProperty domFor = new SimpleStringProperty();
   public SimpleStringProperty youtube = new SimpleStringProperty();
   public SimpleStringProperty song = new SimpleStringProperty();

   public Integer getSongId() {
      return songId.get();
   } 
   
   public String getSongName() {
      return songName.get();
   }

   public String getArtist() {
      return artist.get();
   }

   public Integer getTempo() {
      return tempo.get();
   }

   public String getCategory() {
      return category.get();
   }

   public String getDateCreated() {
      return dateCreated.get();
   }

   public String getDateModified() {
      return dateModified.get();
   }
   public String getDomFor() {
      return domFor.get();
   }

    public String getYoutube() {
        return youtube.get();
    }

    public String getSong() {
        return song.get();
    }
     
}
