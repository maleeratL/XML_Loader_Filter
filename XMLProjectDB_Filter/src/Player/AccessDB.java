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
	
}
