package billCollector;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import jdbcc.ConnectToDB;

public class BillColController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button btnPaid;

	@FXML
	private ComboBox<String> comboMob;

	@FXML
	private ListView<String> lstDobill;

	@FXML
	private ListView<String> lstAmount;

	@FXML
	private TextField txtAmount;
	
	private String[] setAmount;

	@FXML
	void doPaid(ActionEvent event) {
		try {
			prpStmt = con.prepareStatement("update billing set status=1 where cust_mobile=?");
			prpStmt.setString(1,comboMob.getSelectionModel().getSelectedItem());
			prpStmt.executeUpdate();
			initialize();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	void fetchData(ActionEvent event) {		
		lstDobill.getItems().clear();
		lstAmount.getItems().clear();
		try {
			prpStmt = con.prepareStatement("select * from billing where cust_mobile=? and status=0");
			prpStmt.setString(1,comboMob.getSelectionModel().getSelectedItem());
			ResultSet table = prpStmt.executeQuery();
			while(table.next()){
				lstDobill.getItems().addAll(table.getString(4));
				setAmount=table.getString(5).split(" ");
				lstAmount.getItems().addAll(table.getString(5));
			}
			float sum=0;
			for(int i=0;i<setAmount.length;i++) {
				sum=sum+Float.parseFloat(setAmount[i]);
			}
			txtAmount.setText(String.valueOf(sum));
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

	}

	Connection con;
	PreparedStatement prpStmt;

	@FXML
	void initialize() {
		con = ConnectToDB.getConnection();
		comboMob.getItems().clear();
		lstDobill.getItems().clear();
		lstAmount.getItems().clear();
		txtAmount.setText(null);

		try {
			prpStmt = con.prepareStatement("select cust_mobile from billing where status=0");

			ResultSet table = prpStmt.executeQuery();
			while (table.next()) {
				comboMob.getItems().addAll(String.valueOf(table.getInt(1)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
