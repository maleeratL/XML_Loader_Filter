package Player;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
public class Player {
   private StringProperty name;
   private StringProperty club;
   private StringProperty position;
   private StringProperty marketvalue;
   private StringProperty nationality;
   private StringProperty phone;
   public Player(String name, String club, String position, String marketvalue, String nationality) {
      setName(name);
      setClub(club);
      setPosition(position);
      setMarketvalue(marketvalue);
      setNationality(nationality);
   }
   
   
   public StringProperty nameProperty(){
      if(name == null)
         name = new SimpleStringProperty();
      return name;
   }
   
   public StringProperty clubProperty(){
      if(club == null)
    	  club = new SimpleStringProperty();
      return club;
   }
   public StringProperty positionProperty(){
      if(position == null)
    	  position = new SimpleStringProperty();
      return position;
   }

   public StringProperty marketvalueProperty(){
      if(marketvalue == null)
    	  marketvalue = new SimpleStringProperty();
      return marketvalue;
   }
   public StringProperty nationalityProperty(){
      if(nationality == null)
    	  nationality = new SimpleStringProperty();
      return nationality;
   }
   
   
   public void setName(String name){ 
	   nameProperty().setValue(name);  
   }
   public String getName(){ 
	   return nameProperty().get();  
   }
     
   public void setClub(String club){
	   clubProperty().setValue(club);  
   }
   
	public String getClub(){
		return clubProperty().get();  
   }
	
	public void setPosition(String position) {
		positionProperty().setValue(position);
	}
	
	public String getPosition(){
		return positionProperty().get(); 
	}
	
	public void setMarketvalue(String marketvalue)	{
		marketvalueProperty().setValue(marketvalue);  
	}
	public String getMarketvalue(){
		return marketvalueProperty().get();  
	}
	
	public void setNationality(String nationality){
		nationalityProperty().setValue(nationality);
	}
	public String getNationality(){
		return nationalityProperty().get(); 
	}
   
   @Override
   public String toString() {
      return "Player Info: "+getName()+", "+getClub()+", "+getPosition()+","+getMarketvalue()+", "+getNationality();
   }
}
