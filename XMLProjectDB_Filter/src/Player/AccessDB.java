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


	public boolean checkDuplicateArray(ArrayList<Player> data, Player p) {

		for(Player player: data) {
			if(p.getName().equalsIgnoreCase(player.getName())&&
					p.getClub().equalsIgnoreCase(player.getClub())&&
					p.getPosition().equalsIgnoreCase(player.getPosition())&&
					p.getMarketvalue().equalsIgnoreCase(player.getMarketvalue())&&
					p.getNationality().equalsIgnoreCase(player.getNationality())) {
				return true;
			}
		}

		return false;
	}

	public ArrayList<Player> filterCountItem(String input,String dbUser,String usrPass) {
		ArrayList<Player> data = new ArrayList<Player>();
		data.clear();
		int count =0;
		try {

			if(input.equalsIgnoreCase("'")) {
				input = "''";
			}

			if(input.equalsIgnoreCase("'''")||
					input.equalsIgnoreCase("''''")||
					input.equalsIgnoreCase("'''''")||
					input.equalsIgnoreCase("''''''")||
					input.equalsIgnoreCase("'''''''")) {
				input = "error.......@$@$@$@^%^%#%%";
			}


			Class.forName("org.mariadb.jdbc.Driver");

			Connection con = null;

			String url = "jdbc:mariadb://localhost/mydbxml";

			con = DriverManager.getConnection(url, dbUser, usrPass);

			Statement stmt = con.createStatement();

			String sql = "select * from players where name like '%"+input+"%'";

			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next())
			{
				Player p = new Player(rs.getString("name"),rs.getString("club"),rs.getString("position"),rs.getString("market_value"),rs.getString("nationality"));
				if(!checkDuplicateArray(data,p)) {
					data.add(p);
					count++;
				}

			}
			rs.close();


			sql = "select * from players where club like '%"+input+"%'";

			rs = stmt.executeQuery(sql);

			while(rs.next())
			{
				Player p = new Player(rs.getString("name"),rs.getString("club"),rs.getString("position"),rs.getString("market_value"),rs.getString("nationality"));
				if(!checkDuplicateArray(data,p)) {
					data.add(p);
					count++;
				}

			}
			rs.close();

			sql = "select * from players where position like '%"+input+"%'";

			rs = stmt.executeQuery(sql);

			while(rs.next())
			{
				Player p = new Player(rs.getString("name"),rs.getString("club"),rs.getString("position"),rs.getString("market_value"),rs.getString("nationality"));
				if(!checkDuplicateArray(data,p)) {
					data.add(p);
					count++;
				}

			}
			rs.close();

			sql = "select * from players where market_value like '%"+input+"%'";

			rs = stmt.executeQuery(sql);

			while(rs.next())
			{
				Player p = new Player(rs.getString("name"),rs.getString("club"),rs.getString("position"),rs.getString("market_value"),rs.getString("nationality"));
				if(!checkDuplicateArray(data,p)) {
					data.add(p);
					count++;
				}

			}
			rs.close();

			sql = "select * from players where nationality like '%"+input+"%'";

			rs = stmt.executeQuery(sql);

			while(rs.next())
			{
				Player p = new Player(rs.getString("name"),rs.getString("club"),rs.getString("position"),rs.getString("market_value"),rs.getString("nationality"));
				if(!checkDuplicateArray(data,p)) {
					data.add(p);
					count++;
				}

			}
			rs.close();

			stmt.close();
			con.close();
			//        System.out.println("Count: "+count);

		}
		catch(Exception ex) {
			//		ex.printStackTrace();
		}
		return data;
	}

}
