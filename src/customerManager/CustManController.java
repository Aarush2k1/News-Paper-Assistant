package customerManager;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import jdbcc.ConnectToDB;

public class CustManController {

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="heading"
	private Label heading; // Value injected by FXMLLoader

	@FXML // fx:id="txtName"
	private TextField txtName; // Value injected by FXMLLoader

	@FXML
	private TextField txtAddress;

	@FXML // fx:id="btnSave"
	private Button btnSave; // Value injected by FXMLLoader

	@FXML
	private Button btnDelete;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnNew;

	@FXML // fx:id="lstPapers"
	private ListView<String> lstPapers; // Value injected by FXMLLoader

	@FXML
	private ListView<String> lstHawker;
	
    @FXML
    private ListView<String> lstPrice; 

	@FXML // fx:id="comboArea"
	private ComboBox<String> comboArea;

	@FXML
	private ComboBox<String> comboMob;
	
    @FXML
    void doClear(ActionEvent event) {
    	initialize();
    	comboMob.getEditor().clear();
    }

    @FXML
    void doNewEntry(ActionEvent event) {

    }
	@FXML
	void doDelete(ActionEvent event) {
		try {
			prpStmt = con.prepareStatement("delete from customers where mobile=?");
			prpStmt.setString(1, comboMob.getEditor().getText());

			int count = prpStmt.executeUpdate();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setContentText(count + " Records deleted");
			alert.show();

			initialize();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void fetchHwkName(ActionEvent event) {
		String selArea = comboArea.getSelectionModel().getSelectedItem();
		lstHawker.getItems().clear();
		try {
			prpStmt = con.prepareStatement("select name from hawkers where areas like ?");
			prpStmt.setString(1, "%" + selArea + "%");
			ResultSet tab = prpStmt.executeQuery();
			String hawks = "";
			while (tab.next()) {
				hawks = hawks + tab.getString("name") + ",";
			}
			String[] hawkName = hawks.split(",");

			lstHawker.getItems().addAll(hawkName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void searchMob(ActionEvent event) {
//		lstPapers.getItems().clear();
//		lstHawker.getItems().clear();
//		comboArea.getItems().clear();
//		try {
//			prpStmt = con.prepareStatement("select * from customers where mobile=?");
//			prpStmt.setInt(1, Integer.parseInt(comboMob.getEditor().getText()));
//
//			ResultSet table = prpStmt.executeQuery();
//			if (table.next()) {
//				txtName.setText(table.getString(1));
//				txtAddress.setText(table.getString(3));
//				String[] aop = table.getString(5).split(",");
//				lstPapers.getItems().addAll(aop);
//				comboArea.getItems().addAll(table.getString(4));
//				String[] aoh = table.getString(6).split(",");
//				lstHawker.getItems().addAll(aoh);
//			} else {
//				initialize();
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
	}

	@FXML
	void doSave(ActionEvent event) {
		try {
			prpStmt = con.prepareStatement("insert into customers values(?,?,?,?,?,?,curdate(),?)");
			prpStmt.setString(1, txtName.getText());
			prpStmt.setInt(2, Integer.parseInt(comboMob.getEditor().getText()));
			prpStmt.setString(3, txtAddress.getText());

			String papersLst = "";
			ObservableList<String> selPapers = lstPapers.getSelectionModel().getSelectedItems();
			for (String selpaper : selPapers) {
				papersLst = papersLst + selpaper + ",";
			}
			String pL = papersLst.substring(0, papersLst.length() - 1);

//			auto selection in price list
//			for (int n : selIndex) {
//				lstPrice.getSelectionModel().select(n);
//			}
			String priceLst = "";
			ObservableList<String> selPrice = lstPrice.getSelectionModel().getSelectedItems();
			for (String sel : selPrice) {
				priceLst = priceLst + sel + ",";
			}
			String priceL = priceLst.substring(0, priceLst.length() - 1);

			prpStmt.setString(4, pL);
			prpStmt.setString(5, priceL);
			prpStmt.setString(6, lstHawker.getSelectionModel().getSelectedItem());
			prpStmt.setString(7, comboArea.getSelectionModel().getSelectedItem());

			prpStmt.executeUpdate();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Done");
			alert.setContentText("Saved successfully");
			alert.show();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	Connection con;
	PreparedStatement prpStmt;

	public static int removeDuplicate(String arr[], int n) {
		if (n == 0 || n == 1)
			return n;

		String[] temp = new String[n];
		int j = 0;
		for (int i = 0; i < n - 1; i++)
			if (arr[i] != arr[i + 1])
				temp[j++] = arr[i];

		temp[j++] = arr[n - 1];

		for (int i = 0; i < j; i++)
			arr[i] = temp[i];

		return j;
	}

	@FXML
	void initialize() {
		con = ConnectToDB.getConnection();

		comboMob.getItems().clear();
		comboArea.getItems().clear();
		txtName.setText("");
		txtAddress.setText("");
		lstPapers.getItems().clear();
		lstPapers.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		lstPrice.getItems().clear();
		lstPrice.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		try {
			prpStmt = con.prepareStatement("select * from customers");

			ResultSet table = prpStmt.executeQuery();
			while (table.next()) {
				comboMob.getItems().addAll(String.valueOf(table.getInt(2)));
			}

			prpStmt = con.prepareStatement("select * from papers");
			ResultSet tab = prpStmt.executeQuery();
			while (tab.next()) {
				lstPapers.getItems().addAll(tab.getString(1));
				lstPrice.getItems().addAll(tab.getString(2));
			}

			prpStmt = con.prepareStatement("select distinct areas from hawkers");

			ResultSet table1 = prpStmt.executeQuery();
			while (table1.next()) {
				String[] soa = table1.getString("areas").split(",");
				int n = soa.length;
				n = removeDuplicate(soa, n);
//				for (int i = 0; i < n; i++) {
//					System.out.println(soa[i]);
//				}
				comboArea.getItems().addAll(soa);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
