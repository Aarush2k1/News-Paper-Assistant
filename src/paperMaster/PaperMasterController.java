package paperMaster;

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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import jdbcc.ConnectToDB;

public class PaperMasterController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button btnNew;

	@FXML
	private Button btnSave;

	@FXML
	private Button btnUpdate;

	@FXML
	private Button btnRemove;

	@FXML
	private Button btnFecthSel;

	@FXML
	private ComboBox<String> comboTitle;

	@FXML
	private TextField txtPrice;

	@FXML
	void doFetchSelected(ActionEvent event) {
		String sel = comboTitle.getSelectionModel().getSelectedItem();
		if (sel != null) {
			try {
				prpStmt = con.prepareStatement("select * from papers where paper=?");
				prpStmt.setString(1, sel);

				ResultSet table = prpStmt.executeQuery();
				if (table.next()) {
					String paper = table.getString(1);
					Float price = table.getFloat(2);
					comboTitle.getItems().addAll(paper);
					txtPrice.setText(String.valueOf(price));
				}
				comboTitle.getItems().clear();
				initialize();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Sorry");
			alert.setContentText("Wrong Selection");
			alert.show();
		}
	}

	@FXML
	void doRemove(ActionEvent event) {
		try {
			prpStmt = con.prepareStatement("delete from papers where paper=?");
			prpStmt.setString(1, comboTitle.getEditor().getText());

			int count = prpStmt.executeUpdate();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setContentText(count + " Records deleted");
			alert.show();
			comboTitle.getItems().clear();
			initialize();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void doSave(ActionEvent event) {
		try {
			prpStmt = con.prepareStatement("insert into papers values(?,?)");
			prpStmt.setString(1, comboTitle.getEditor().getText());
			prpStmt.setFloat(2, Float.parseFloat(txtPrice.getText()));

			prpStmt.executeUpdate();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Done");
			alert.setContentText("Saved successfully");
			alert.show();
			comboTitle.getItems().clear();
			initialize();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void doUpdate(ActionEvent event) {
		try {
			prpStmt = con.prepareStatement("update papers set price=? where paper=?");
			prpStmt.setString(2, comboTitle.getEditor().getText());
			prpStmt.setFloat(1, Float.parseFloat(txtPrice.getText()));

			prpStmt.executeUpdate();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Done");
			alert.setContentText("Updated successfully");
			alert.show();
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
			prpStmt = con.prepareStatement("select * from papers");

			ResultSet table = prpStmt.executeQuery();
			while (table.next()) {
				String paper = table.getString("paper");
				comboTitle.getItems().addAll(paper);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
