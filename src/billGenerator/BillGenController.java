package billGenerator;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import jdbcc.ConnectToDB;

public class BillGenController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Label heading;

	@FXML
	private Button btnBill;

	@FXML
	private ListView<String> lstPapers;

	@FXML
	private ListView<String> lstPrice;

	@FXML
	private ComboBox<String> comboMob;

	@FXML
	private TextField txtBill;

	@FXML
	private TextField txtDays;

	private String[] setPapers;

	private String[] setPrice;

	private String date;

	@FXML
	void generateBill(ActionEvent event) {
		float sum=0;
		for(int i=0;i<setPrice.length;i++) {
			sum=sum+Float.parseFloat(setPrice[i]);			
		}
		
		//Date billStartMorning = Date.valueOf(data.dos.toLocalDate().atStartOfDay().toLocalDate());
		Date billStartMorning = Date.valueOf(date);
    	Date todayEvening = Date.valueOf(LocalDate.now().plusDays(1).atStartOfDay().toLocalDate());
		Long days= (todayEvening.getTime()-billStartMorning.getTime())/86400000;
		System.out.println(days);
		float bill = days*sum;
		
		try {
			prpStmt = con.prepareStatement("insert into billing values(null,?,?,curdate(),?,0)");

			prpStmt.setString(1, comboMob.getEditor().getText());
			prpStmt.setFloat(2, days);
			prpStmt.setFloat(3, bill);
			
			prpStmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		txtBill.setText(String.valueOf(bill));
		txtDays.setText(String.valueOf(days));
	}

	@FXML
	void searchMob(ActionEvent event) {
		lstPapers.getItems().clear();
		lstPrice.getItems().clear();
		
				
		try {
			prpStmt = con.prepareStatement("select * from customers where mobile=?");
			prpStmt.setString(1, comboMob.getEditor().getText());

			ResultSet table = prpStmt.executeQuery();
			while (table.next()) {
				date=String.valueOf(table.getDate("date"));
				setPapers=table.getString(4).split(",");
				setPrice=table.getString(5).split(",");
				lstPapers.getItems().addAll(setPapers);
				lstPrice.getItems().addAll(setPrice);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	Connection con;
	PreparedStatement prpStmt;

	@FXML
	void initialize() {
		con = ConnectToDB.getConnection();

		try {
			prpStmt = con.prepareStatement("select * from customers");

			ResultSet table = prpStmt.executeQuery();
			while (table.next()) {
				comboMob.getItems().addAll(String.valueOf(table.getInt(2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
