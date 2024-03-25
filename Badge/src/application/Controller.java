package application;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.StringTokenizer;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
public class Controller implements Initializable {
	@FXML private TableView <Badge> mainTable;
	@FXML private TableColumn<Badge, String> badgeNumber;
	@FXML private TableColumn<Badge,BadgeType> badgeType;
	@FXML private TableColumn<Badge, String> badgeNote;
	@FXML private TableColumn <Badge,String> badgeStatus; 
	@FXML private TableColumn <Badge,Badge> number;
	@FXML private Button delete;
	@FXML private Button save;
	@FXML private Button addNewBadge;
	@FXML private Button edit;
	@FXML private Button activate;
	@FXML private Button deactivate;
	@FXML private Button missing;
	@FXML private Button extra;
	@FXML private TextField search;
	@FXML private TextField newBadgeNumber;
	@FXML private TextField newBadgeNote;
	@FXML private ComboBox<String> newBadgeType;
	@FXML private ComboBox<String> newBadgeStatus;
	@FXML private Label information;
	@FXML private Button issue;
	@FXML private Button returned;
	
	public ObservableList<Badge> searchList = FXCollections.observableArrayList();
	public ObservableList<Badge> mainList;	

	public void readDataFromFile() {
        this.mainList = FXCollections.observableArrayList();
        try{
			Scanner sc = new Scanner(new File("src/text/badge.txt"));
            while(sc.hasNext())
                this.mainList.add(newBadge(sc.nextLine()));             
        }catch(FileNotFoundException e){
              System.out.println("Connot read data from file");
        }    
    }
    private Badge newBadge(String line){
        //System.out.println(line);
        StringTokenizer tk = new StringTokenizer(line);
        String num = tk.nextToken();
        String type = tk.nextToken();
        String status = tk.nextToken();
        String notes = "";
        while(tk.hasMoreTokens())
            notes += tk.nextToken() + " ";
        return new Badge(num, type, status, notes);
  
    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		readDataFromFile();
		initColomns();
		
		autoNumberingColumn();
		initComboBoxes();
		autoSearch();
		this.mainTable.setItems(this.mainList);	
		this.mainTable.setEditable(true);
	}
	private void autoSearch() {
		this.search.textProperty().addListener((observable, oldValue, newValue) -> {
			
	 
			this.searchList.clear();
		    if(this.search.getText().matches(Regex.NUMBER.getRegex()))
		    	searchBadgeNumber(this.search.getText());
		    else if(this.search.getText().matches(Regex.LETTER.getRegex())) 
		    	searchBadgeType(this.search.getText()); 
		    
		    if(this.search.getText().isEmpty())
				this.mainTable.setItems(this.mainList);
		    else
		    	this.mainTable.setItems(this.searchList);
		});
	}
	private void searchBadgeNumber(String str) {
		for(Badge b: this.mainList) {
			if(b.getBadgeNumber().startsWith(str))
				this.searchList.add(b);
		}
	}
	
	private void searchBadgeType(String str) {
		for(Badge b: this.mainList) 
			if(b.getBadgeType().regionMatches(true, 0, str, 0, str.length())
					|| b.getBadgeStatus().toLowerCase().startsWith(str.toLowerCase())) 
				this.searchList.add(b);
	}
	
	@FXML
	private void changeBadgeStatus(ActionEvent event) {
		
		String str = ((Button)event.getSource()).getText();
		Badge badge = this.mainTable.getSelectionModel().getSelectedItem();
		if(badge ==null)
			promptUser("INFORMATION","Please Select a badge!");
		else {
			badge.setBadgeStatus(str);
			this.mainTable.refresh();
		}
		
	}		

	@FXML
	private void saveBadge(ActionEvent event) {
		try {
			File file= new File("src/text/badge.txt");
			PrintWriter pr = new PrintWriter(file);
			for(Badge badge: this.mainList)
				pr.println(badge.getBadgeNumber() +"\t"
					   + badge.getBadgeType()   +"\t"
					   + badge.getBadgeStatus() +"\t"
					   + badge.getBadgeNote());
			this.information.setText("Backup completed successfully.");
			pr.close();
		}
		catch(IOException e) {
			promptUser("ERROR", "Back up failed due accessing backup location or backup file.");
		}

		
	}
	@FXML
	private void deleteBadge(ActionEvent event) {
		if(this.mainTable.getSelectionModel().isEmpty())
			promptUser("INFORMATION", "Please select a badge!");
		else {
			Optional<ButtonType> result = promptUser("CONFIRMATION","Are you sure you want to delete badge "
					+ this.mainTable.getSelectionModel().getSelectedItem().getBadgeNumber()+"?");
			if (result.isPresent() && result.get() == ButtonType.OK){
				this.mainTable.getItems().remove(this.mainTable.getSelectionModel().getSelectedItem());
				
				if(!this.mainList.isEmpty())
					this.information.setText("Badge " 
						+ this.mainTable.getSelectionModel().getSelectedItem().getBadgeNumber()
						+ " was deleted.");
			}
		}
	}
	
	@FXML
	private void addBadge(ActionEvent event) {
		if(this.newBadgeNumber.getText().isEmpty()) 
			promptUser("INFORMATION","Please add a new badge number!");
		else {
			this.mainList.add(new Badge(
					this.newBadgeNumber.getText(),
					this.newBadgeType.getValue(),
					this.newBadgeStatus.getValue(),
					this.newBadgeNote.getText()));
			
			this.newBadgeNumber.setText("");
			this.newBadgeNote.setText("");
			saveBadge(new ActionEvent());
		}
	}
	
	@FXML
	private void editBadge(ActionEvent event) {
		Badge badge = this.mainTable.getSelectionModel().getSelectedItem();
		
		if(this.mainTable.getSelectionModel().isEmpty()) 
			promptUser("WARNING","Please select a badge!");		
		else{
			this.newBadgeNumber.setText(badge.getBadgeNumber());
			this.newBadgeType.setValue(badge.getBadgeType());
			this.newBadgeStatus.setValue(badge.getBadgeStatus());
			this.newBadgeNote.setText(badge.getBadgeNote());
		}
	}
	private Optional<ButtonType> promptUser(String title, String msg) {
		Alert alert = new Alert(AlertType.CONFIRMATION);		
		if(title.equalsIgnoreCase("CONFIRMATION")) 
			alert.setAlertType(AlertType.CONFIRMATION);
		else if(title.equalsIgnoreCase("WARNING")) 
			alert.setAlertType(AlertType.WARNING);
		else if(title.equalsIgnoreCase("INFORMATION")) 
			alert.setAlertType(AlertType.INFORMATION);
		else if(title.equalsIgnoreCase("ERROR")) 
			alert.setAlertType(AlertType.ERROR);
		else
			alert.setAlertType(AlertType.NONE);
		
		alert.setHeaderText(null);
		alert.setGraphic(null);
		alert.setContentText(msg);
		alert.setResizable(true);
		applyCSStoAlertPane(alert); 
		Optional<ButtonType> result = alert.showAndWait();
		
		
		return result;
	}
	private void applyCSStoAlertPane(Alert alert) {
		DialogPane dialogPane = alert.getDialogPane();
		
		dialogPane.getStylesheets().add(
		   getClass().getResource("application.css").toExternalForm());
		dialogPane.getStyleClass().add("myDialog");
	}
	public void initComboBoxes(){
		this.newBadgeType.getItems().addAll(
				BadgeType.EMPLYEE.getValue(),
                BadgeType.EMPLYEE.getValue(),
                BadgeType.STUDENT.getValue(),
                BadgeType.VISITOR.getValue(),
                BadgeType.CONTRACTORS.getValue(),
                BadgeType.IT.getValue(),
                BadgeType.WAREHOUSE.getValue(),
                BadgeType.SECURITS.getValue());
		
		this.newBadgeType.setValue(this.newBadgeType.getItems().get(0));
		
		this.newBadgeStatus.getItems().addAll(
				BadgeStatus.ACTIVE.getValue(),
				BadgeStatus.DEACTIVE.getValue(),
				BadgeStatus.IN_USE.getValue(),
				BadgeStatus.ON_HAND.getValue(),
				BadgeStatus.MISSING.getValue());
		
		this.newBadgeStatus.setValue(this.newBadgeStatus.getItems().get(0));
		
	}
	private void initColomns() {
		this.badgeNumber.setCellValueFactory(new PropertyValueFactory<Badge, String>("badgeNumber"));
		//this.badgeType.setCellValueFactory(new PropertyValueFactory<Badge, String>("badgeType"));
		this.badgeNote.setCellValueFactory(new PropertyValueFactory<Badge, String>("badgeNote"));
		this.badgeStatus.setCellValueFactory(new PropertyValueFactory<Badge, String>("badgeStatus"));
		
		ObservableList<BadgeType> type = FXCollections.observableArrayList(BadgeType.values());
		
		this.badgeType.setCellValueFactory(new Callback<CellDataFeatures<Badge, BadgeType>, ObservableValue<BadgeType>>() {
			@Override
            public ObservableValue<BadgeType> call(CellDataFeatures<Badge, BadgeType> param) {
            	Badge badge = param.getValue();
            	BadgeType b = BadgeType.getType(badge.getBadgeType());
                return new SimpleObjectProperty<BadgeType>(b);
            }
        });
		this.badgeType.setCellFactory(ComboBoxTableCell.forTableColumn(type));
		 
		this.badgeType.setOnEditCommit((CellEditEvent<Badge, BadgeType> event) -> {
            TablePosition<Badge, BadgeType> pos = event.getTablePosition();
            BadgeType newGender = event.getNewValue();
            int row = pos.getRow();
            Badge b = event.getTableView().getItems().get(row);
            b.setBadgeType(newGender.toString());
        });
		

	}
	/**
	 * Auto Numbering of Rows
	 */
	private void autoNumberingColumn() {
		this.number.setCellValueFactory(new Callback<CellDataFeatures<Badge, Badge>, ObservableValue<Badge>>() {
            @Override public ObservableValue<Badge> call(CellDataFeatures<Badge, Badge> p) {
                return new ReadOnlyObjectWrapper<>(p.getValue());
            }
        });
        
		//This column contain the auto numbering for table content.
        this.number.setCellFactory(new Callback<TableColumn<Badge, Badge>, TableCell<Badge, Badge>>() {
            @Override public TableCell<Badge, Badge> call(TableColumn<Badge, Badge> param) {
                return new TableCell<Badge, Badge>() {
                    @Override protected void updateItem(Badge item, boolean empty) {
                        super.updateItem(item, empty);

                        if (this.getTableRow() != null && item != null) {
                            setText(this.getTableRow().getIndex() + 1 + ".");
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });
	}
	
	
}
