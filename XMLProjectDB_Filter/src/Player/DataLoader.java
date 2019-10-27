package Player;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
public class DataLoader {
	private ArrayList<Player> playerList = new ArrayList<Player>();
	AccessDB db = new AccessDB();
		
	public int load(String path) {
		int size =0;
		getPlayerList().clear();
		try {
			String xmlFileName = path;
			File xmlFile = new File(xmlFileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBulider = dbFactory.newDocumentBuilder();
			Document doc = dBulider.parse(xmlFile);
			
			//find all element which have <row>
			NodeList nList = doc.getElementsByTagName("row");
			size = nList.getLength();
			for(int i=0; i<nList.getLength(); i++) {
				
				Node n = nList.item(i);
				Element e = (Element)n;
				String name;
				String club;
				String position;
				String market_value;
				String nationality;
				
				try {
					
					name = e.getElementsByTagName("name").item(0).getTextContent();	
				}
				catch(Exception ex){
					name = "unknown";
				}
				
				try {
					club = e.getElementsByTagName("club").item(0).getTextContent();	
				}
				catch(Exception ex){
					club = "unknown";
				}
				
				try {
					position = e.getElementsByTagName("position").item(0).getTextContent();	
				}
				catch(Exception ex){
					position = "unknown";
				}
				
				try {
					market_value = e.getElementsByTagName("market_value").item(0).getTextContent();	
				}
				catch(Exception ex){
					market_value = "unknown";
				}
				
				try {
					nationality = e.getElementsByTagName("nationality").item(0).getTextContent();	
				}
				catch(Exception ex){
					nationality = "unknown";
				}
				
				
				Player p = new Player(name,club,position,market_value,nationality);				
				getPlayerList().add(p);

			}
			
		}
		catch(Exception e){
			System.out.println("File path cannot be loaded.");
		}
		return size;
	}
	

	public ArrayList<Player> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(ArrayList<Player> playerList) {
		this.playerList = playerList;
	}
	
	

}
