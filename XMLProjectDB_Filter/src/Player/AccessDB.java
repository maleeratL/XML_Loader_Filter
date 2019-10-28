package Player;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class AccessDB {
	
	ArrayList<Player> playerListDB = new ArrayList<Player>();
	String dbUser = "userExample";
    String usrPass = "passExample";
    
	public boolean accessSQL(String dbUser,String usrPass) {
		playerListDB.clear();
		boolean result= false;
		try {
            Class.forName("org.mariadb.jdbc.Driver");

            Connection con = null;

            String url = "jdbc:mariadb://localhost/mydbxml";

            con = DriverManager.getConnection(url, dbUser, usrPass);

            Statement stmt = con.createStatement();

            String sql = "select * from players";

            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next())
            {
            	Player p = new Player(rs.getString("name"),rs.getString("club"),rs.getString("position"),rs.getString("market_value"),rs.getString("nationality"));
                playerListDB.add(p);
            }
            rs.close();
            stmt.close();
            con.close();
            
            result =true;
		
		}
		catch(Exception ex) {
//			ex.printStackTrace();
			result = false;
		}
		return result;
	}
	
	
	public boolean savePlayerInSQL(Player p) {
		boolean result= false;
		try {
			String dbUser = "userExample";
            String usrPass = "passExample";
            Class.forName("org.mariadb.jdbc.Driver");

            Connection con = null;
            
         

            String url = "jdbc:mariadb://localhost/mydbxml";

            con = DriverManager.getConnection(url, dbUser, usrPass);

            Statement stmt = con.createStatement();     
           
                      
            String query = " insert into players (name, club, position, market_value, nationality)"
                    + " values (?, ?, ?, ?, ?)";

                  // create the mysql insert preparedstatement
                  PreparedStatement preparedStmt = con.prepareStatement(query);
                  preparedStmt.setString (1, p.getName());
                  preparedStmt.setString (2, p.getClub());
                  preparedStmt.setString (3, p.getPosition());
                  preparedStmt.setString (4, p.getMarketvalue());
                  preparedStmt.setString (5, p.getNationality());
                  preparedStmt.execute();

            stmt.close();
            con.close();
            result =true;
		}
		catch(Exception ex) {
//			System.out.println("Cannot Save");
			result =false;
		}
		return result;
	}
	
	
public boolean checkDuplicate(Player p){
		
		boolean res = true;
		try {
			String dbUser = "userExample";
            String usrPass = "passExample";
            Class.forName("org.mariadb.jdbc.Driver");

            Connection con = null;

            String url = "jdbc:mariadb://localhost/mydbxml";

            con = DriverManager.getConnection(url, dbUser, usrPass);

            Statement stmt = con.createStatement();
            
            String name = p.getName();
            String club = p.getClub();
            String position = p.getPosition();
            String market_value = p.getMarketvalue();
            String nationality = p.getNationality();
    
            String sql = "select * from players where name = \""+name +"\" and club = \""+club+"\" and position = \""+position+"\""
            		+ "and market_value = \""+market_value+"\" and nationality = \""+nationality+"\"";

            ResultSet rs = stmt.executeQuery(sql);
            
			
			if(! rs.next()) {
				res = false;
				
			}
			rs.close();
            stmt.close();
            con.close();
            
		
		}
		catch(Exception ex) {
//			System.out.println("No Duplicate");
		}
		
		return res;
	}
	
}
