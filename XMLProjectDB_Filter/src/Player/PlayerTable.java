package Player;
import java.io.File;
import java.util.ArrayList;

import javafx.application.*;
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.*;
public class PlayerTable extends Application {
   private TableView<Player> table;
   private TableView<Player> tableUpdate;
   private TableView<Player> tableFilter;
   private ObservableList<Player> data;
   private ObservableList<Player> dataUpdate;
   private ObservableList<Player> dataFilter;
   private Text txtStatus;
   private TextField filterField;
   private TextField filterUpdateField;
   private TableColumn<Player, String> nameColumn;
   private TableColumn<Player, String> clubColumn;
   private TableColumn<Player, String> positionColumn;
   private TableColumn<Player, String> marketvalueColumn;
   private TableColumn<Player, String> nationalityColumn;
   private VBox filechooser;
   private VBox vbox;
   private FileChooser fil_chooser;
   private int count;
   private Label labelFile;
   private Button delbtn;
   
   private String dbUser = "userExample";
   private String usrPass = "passExample";
  
   @Override
   public void start(Stage primaryStage) {
      primaryStage.setTitle("Player Info Table View");
      //============================= Tab Heading =================================
      //Tab 1
      Label label = new Label("Player List");
      label.setTextFill(Color.DARKBLUE);
      label.setFont(Font.font("Calibri", FontWeight.BOLD, 20));
      HBox labelHb = new HBox();
      labelHb.setAlignment(Pos.CENTER);
      labelHb.getChildren().add(label);
      
      //Tab 2
      Label label2 = new Label("Filter Search Player List");
      label2.setTextFill(Color.DARKBLUE);
      label2.setFont(Font.font("Calibri", FontWeight.BOLD, 20));
      HBox labelHb2 = new HBox();
      labelHb2.setAlignment(Pos.CENTER);
      labelHb2.getChildren().add(label2);
      
      //Tab menu
      TabPane tabpane = new TabPane(); 
      tabpane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
     
   	  // create Tab 
   	  Tab tab = new Tab("Load/Save"); 
   	  Tab tab2 = new Tab("Filter Search"); 
   	  
   	//============================= Tab Load/Save (Tab 1)================================= 
      // Add and delete buttons
   	  Button savebtn = new Button("Save");
   	  savebtn.setOnAction(new SaveButtonListener());
   	  Button addbtn = new Button("Add");
   	  addbtn.setOnAction(new AddButtonListener());
   	  delbtn = new Button("Delete");
   	  delbtn.setOnAction(new DeleteButtonListener());
   	  HBox buttonHb = new HBox(10);
   	  buttonHb.setAlignment(Pos.CENTER);
   	  buttonHb.setPadding(new Insets(0, 0, 20, 0));
   	  buttonHb.getChildren().addAll(savebtn,addbtn, delbtn);
   	  
   	  
      //============================= Tab listener update data =================================
   	  // Tab Filter
   			tab2.setOnSelectionChanged(event -> {
   		        if (tab2.isSelected()) {
   		        	labelFile.setText("Please select file");
   		        	delbtn.setVisible(false);
   		        	table.getItems().clear();
   		        	fileChooser(primaryStage,table,labelHb,buttonHb);
   		            dataUpdate = dataUpdateList();
   		            FilteredList<Player> filteredData = new FilteredList<>(dataUpdate, p -> true);
   		      
	   		       filterUpdateField.textProperty().addListener((observable, oldValue, newValue) -> {
	   		          filteredData.setPredicate(player -> {
	   		              if (newValue == null || newValue.isEmpty()) {
	   		                  return true;
	   		              }
	   		              
	   		              String lowerCaseFilter = newValue.toLowerCase();
	   		              
	   		              if (player.getName().toLowerCase().contains(lowerCaseFilter)) {
	   		                  return true; // Filter matches name.
	   		              } else if (player.getClub().toLowerCase().contains(lowerCaseFilter)) {
	   		                  return true; // Filter matches club.
	   		              } else if (player.getPosition().toLowerCase().contains(lowerCaseFilter)) {
	   		                  return true; // Filter matches position.
	   		              } else if (player.getMarketvalue().toLowerCase().contains(lowerCaseFilter)) {
	   		                  return true; // Filter matches market value.
	   		              } else if (player.getNationality().toLowerCase().contains(lowerCaseFilter)) {
	   		                  return true; // Filter matches nationality.
	   		              }
	   		              
	   		              return false; // Does not match.
	   		          });
	   		      });
	   		      
	   		      SortedList<Player> sortedData = new SortedList<>(filteredData);
	   		      sortedData.comparatorProperty().bind(tableUpdate.comparatorProperty());
	   		      
	   		      tableUpdate.setItems(sortedData);
	   		      try {
	   		    	tableUpdate.getSelectionModel().getSelectedItem();
	   		      }
	   		      catch(IndexOutOfBoundsException ex){
	   		    	  System.out.println("cannot find");
	   		      }
   		        }
   		    });
   			

   	// add tab 
   	tabpane.getTabs().add(tab); 
   	tabpane.getTabs().add(tab2); 
   
   
      
      
     //============================= Call File Chooser =================================
    loadTable();  
   	fileChooser(primaryStage,table,labelHb,buttonHb);
      
      txtStatus = new Text();

     //============================= Add element into scene =================================
     // Tab Load/Save
      vbox = new VBox(20);
      vbox.setPadding(new Insets(20, 20, 20, 20));
      vbox.getChildren().addAll(filechooser);
      
      // Tab Update
      updateFilterTable();
      
      VBox vboxUpdate = new VBox(20);
      vboxUpdate.getChildren().addAll(filterUpdateField,tableUpdate);
      
      VBox vbox2 = new VBox(20);
      vbox2.setPadding(new Insets(20, 20, 20, 20));
      vbox2.getChildren().addAll(labelHb2,vboxUpdate);
      
      
      tab.setContent(vbox);
      tab2.setContent(vbox2);
     
      
      // W x H
      Scene scene = new Scene(tabpane, 650, 550);
      primaryStage.setScene(scene);
      primaryStage.show();
      
   }
   
   
   
  //================================================================================================
   //============================= Tab Load/Save (Tab 1) =================================
   public void loadTable() {
	   //Table (Load/Save) 
	      table = new TableView<>();
	      data = dummyData("path");
	      table.setItems(data);
	       
	      
	      TableColumn<Player,String> nameCol = new TableColumn<>("Name");
	      nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

	      TableColumn<Player,String> clubCol = new TableColumn("Club");
	      clubCol.setCellValueFactory(new PropertyValueFactory<>("club"));

	      TableColumn<Player,String> positionCol = new TableColumn("Position");
	      positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));

	      TableColumn<Player,String> marketvalueCol = new TableColumn("Market value");
	      marketvalueCol.setCellValueFactory(new PropertyValueFactory<>("marketvalue"));    

	      TableColumn<Player,String> nationalityCol = new TableColumn("Nationality");
	      nationalityCol.setCellValueFactory(new PropertyValueFactory<>("nationality"));
	      
	      
	      table.getColumns().setAll(nameCol, clubCol,positionCol,marketvalueCol,nationalityCol);
	      table.setPrefWidth(400);
	      table.setPrefHeight(650);
	      table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	      table.getSelectionModel().selectedIndexProperty().addListener(new RowChangeHandler());
	      table.setEditable(true);
	      
	      // Listener to adjust data (Tab 1)
	      nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
	      nameCol.setOnEditCommit(event -> (event.getTableView().
	    		  getItems().get(event.getTablePosition().getRow())).
	    		  setName(event.getNewValue()));

	      clubCol.setCellFactory(TextFieldTableCell.forTableColumn());
	      clubCol.setOnEditCommit(event -> (event.getTableView().
	    		  getItems().get(event.getTablePosition().getRow())).
	    		  setClub(event.getNewValue()));

	      positionCol.setCellFactory(TextFieldTableCell.forTableColumn());
	      positionCol.setOnEditCommit(event -> (event.getTableView().
	    		  getItems().get(event.getTablePosition().getRow())).
	    		  setPosition(event.getNewValue()));

	      marketvalueCol.setCellFactory(TextFieldTableCell.forTableColumn());
	      marketvalueCol.setOnEditCommit(event -> (event.getTableView().
	    		  getItems().get(event.getTablePosition().getRow())).
	    		  setMarketvalue(event.getNewValue()));

	      nationalityCol.setCellFactory(TextFieldTableCell.forTableColumn());
	      nationalityCol.setOnEditCommit(event -> (event.getTableView().
	    		  getItems().get(event.getTablePosition().getRow())).
	    		  setNationality(event.getNewValue()));
   }
   
   
   //============================= Tab Filter (Tab 2) =================================
   public void updateFilterTable() {
	      tableUpdate = new TableView<>();
	      dataUpdate = dataUpdateList();
	      
	      filterUpdateField = new TextField();
	  
	      
	      TableColumn<Player,String> nameColUpdate = new TableColumn<>("Name");
	      nameColUpdate.setCellValueFactory(new PropertyValueFactory<>("name"));

	      TableColumn<Player,String> clubColUpdate = new TableColumn("Club");
	      clubColUpdate.setCellValueFactory(new PropertyValueFactory<>("club"));

	      TableColumn<Player,String> positionColUpdate = new TableColumn("Position");
	      positionColUpdate.setCellValueFactory(new PropertyValueFactory<>("position"));

	      TableColumn<Player,String> marketvalueColUpdate= new TableColumn("Market value");
	      marketvalueColUpdate.setCellValueFactory(new PropertyValueFactory<>("marketvalue"));    

	      TableColumn<Player,String> nationalityColUpdate = new TableColumn("Nationality");
	      nationalityColUpdate.setCellValueFactory(new PropertyValueFactory<>("nationality"));
	      
	      
	      nameColUpdate.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
	      clubColUpdate.setCellValueFactory(cellData -> cellData.getValue().clubProperty());
	      positionColUpdate.setCellValueFactory(cellData -> cellData.getValue().positionProperty());
	      marketvalueColUpdate.setCellValueFactory(cellData -> cellData.getValue().marketvalueProperty());
	      nationalityColUpdate.setCellValueFactory(cellData -> cellData.getValue().nationalityProperty());
	      
	      
	      FilteredList<Player> filteredData = new FilteredList<>(dataUpdate, p -> true);
	      filterUpdateField.textProperty().addListener((observable, oldValue, newValue) -> {
	    	  String lowerCaseFilter = newValue.toLowerCase();
	    	  AccessDB db = new AccessDB();
	    	  dataUpdate.clear();
	    	  for(Player p: db.filterCountItem(lowerCaseFilter,dbUser,usrPass)) {
	    		  dataUpdate.add(new Player(p.getName(), p.getClub(),p.getPosition(),p.getMarketvalue(),p.getNationality()));
		      }
	    	  
	    	  tableUpdate.setItems(dataUpdate);
	      });
	      
	      SortedList<Player> sortedData = new SortedList<>(filteredData);
	      sortedData.comparatorProperty().bind(tableUpdate.comparatorProperty());
	      
	      tableUpdate.setItems(sortedData);
	      
	      tableUpdate.getColumns().setAll(nameColUpdate, clubColUpdate,positionColUpdate,marketvalueColUpdate,nationalityColUpdate);
	      tableUpdate.setPrefWidth(400);
	      tableUpdate.setPrefHeight(650);
	      tableUpdate.setColumnResizePolicy(TableView.
	         CONSTRAINED_RESIZE_POLICY);
	      tableUpdate.getSelectionModel().selectedIndexProperty().addListener(new RowChangeHandler());
	      tableUpdate.setEditable(true);
	      
   }
   
   
   
   private class RowChangeHandler implements
         ChangeListener {
      @Override
      public void changed(ObservableValue ov, Object oldVal,
            Object newVal) {
         int val = ((Number)newVal).intValue();
         if (data.size()<=0) {
            return;
         }
        Player pb= (Player) data.get(val);
        txtStatus.setText(pb.toString());
      }
   }
   
   public ObservableList<Player> dummyData(String path) {
      ObservableList<Player> records =
         FXCollections.observableArrayList();
      DataLoader loader = new DataLoader();
      loader.load(path);
      for(Player p: loader.getPlayerList()) {
    	  records.add(new Player(p.getName(), p.getClub(),p.getPosition(),p.getMarketvalue(),p.getNationality()));
      }
      return records;
   }
   
   public ObservableList<Player> dataUpdateList() {
	   ObservableList<Player> recordsFilter =
		         FXCollections.observableArrayList();
	   recordsFilter.clear();
	   AccessDB db = new AccessDB();
	      db.accessSQL(dbUser,usrPass);
	      for(Player p: db.playerListDB) {
	    	  recordsFilter.add(new Player(p.getName(), p.getClub(),p.getPosition(),p.getMarketvalue(),p.getNationality()));
	      }
	  return recordsFilter;   
   }
   
   
   public void fileChooser(Stage stage,TableView<Player> table,HBox labelHb,HBox buttonHb) {
	   try { 
		   
		   DataLoader db = new DataLoader();

	        fil_chooser = new FileChooser(); 
	        fil_chooser.setTitle("Select File"); 
	  
	        // set initial File 
	        fil_chooser.setInitialDirectory(new File("c:\\")); 
	  
	        labelFile = new Label("no files selected"); 
	        Button button = new Button("Open file..."); 
	 
	        EventHandler<ActionEvent> event =  
	        new EventHandler<ActionEvent>() { 
	  
	            public void handle(ActionEvent e) 
	            { 
	  
	                // get the file selected 
	                File file = fil_chooser.showOpenDialog(stage); 
	  
	                if (file != null) { 
	                	labelFile.setText(file.getAbsolutePath()  
	                                        + "  :selected"); 
	                    
	                   
	                    data = dummyData(file.getAbsolutePath());
	                    table.setItems(data);
	                    delbtn.setVisible(true);
	                    if(count<1) {
	                    	vbox.getChildren().addAll(labelHb,table, txtStatus,buttonHb);
	                    }
	                    count++;
	                   
	                } 
	            } 
	        }; 
	  
	        button.setOnAction(event); 

	        filechooser = new VBox(30, labelFile, button); 
	        filechooser.setAlignment(Pos.CENTER); 
	    } 
	  
	    catch (Exception e) { 
	  
//	        System.out.println(e.getMessage()); 
	    } 
   }
   
   
   public static boolean isDouble(String s) {
	    try { 
	        Double.parseDouble(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    // only got here if we didn't return false
	    return true;
	}
   
   
   private class SaveButtonListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {
			AccessDB db = new AccessDB();
			int count=0;
			for(Player p: data){
				if(!db.checkDuplicate(p)) {
					db.savePlayerInSQL(p);
					
					
					if(p.getName() instanceof String&&
							p.getClub() instanceof String&&
							p.getPosition() instanceof String&&
							p.getNationality() instanceof String) {
						if(isDouble(p.getMarketvalue())) {
							txtStatus.setText("Saved successfully.");
						}
						else{
							txtStatus.setText("Cannot save. Your marketvalue should be number.");
						}
					}
					count++;
				}
			}
			if(count==0) {
				txtStatus.setText("All data is already saved");
			}
		}
	}
   
   private class AddButtonListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {

			// Create a new row after last row
			Player Player = new Player("...", "...", "...", "...", "...");
			data.add(Player);
			int row = data.size() - 1;

			// Select the new row
			table.requestFocus();
			table.getSelectionModel().select(row);
			table.getFocusModel().focus(row);

			txtStatus.setText("New Player: Enter infomation. Press <Enter>. **MarketValue have to be number**");
			delbtn.setVisible(true);
		}
	}

	private class DeleteButtonListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {

			// Get selected row and delete
			int ix = table.getSelectionModel().getSelectedIndex();
			Player Player = (Player) table.getSelectionModel().getSelectedItem();
			if(table.getItems().size()!=0) {
				data.remove(ix);
			}
			
			txtStatus.setText("Deleted: " + Player.toString());

			// Select a row
			if (table.getItems().size() == 0) {

				txtStatus.setText("No data in table !");
				delbtn.setVisible(false);
				return;
			}

			if (ix != 0) {

				ix = ix -1;
			}

			table.requestFocus();
			table.getSelectionModel().select(ix);
			table.getFocusModel().focus(ix);
		}
	}
   
//   public static void main(String [] args) {
//      Application.launch(args);
//   }
//   
//   
}