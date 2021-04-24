package display;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import jdbcc.ConnectToDB;

public class disController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ComboBox<String> comboAreas;

	@FXML
	private ComboBox<String> comboPapers;

	@FXML
	private TableView<tabBean> table;

	@FXML
	private Button btnExport;

	@FXML
	private Button btnOpen;

	@FXML
	private Button btnShowAll;

	@SuppressWarnings("unchecked")
	@FXML
	void fetchAll(ActionEvent event) {
		TableColumn<tabBean, String> name = new TableColumn<tabBean, String>("Name");
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		name.setMinWidth(200);

		TableColumn<tabBean, Integer> mobile = new TableColumn<tabBean, Integer>("Mobile Number");
		mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
		mobile.setMinWidth(200);

		TableColumn<tabBean, String> address = new TableColumn<tabBean, String>("Address");
		address.setCellValueFactory(new PropertyValueFactory<>("address"));
		address.setMinWidth(200);

		table.getColumns().clear();
		table.getColumns().addAll(name, mobile, address);
		ObservableList<tabBean> ary = getAllRecords();

		table.setItems(null);
		table.setItems(ary);

	}

	@FXML
	void fetchAreas(ActionEvent event) {

	}

	@FXML
	void fetchPapers(ActionEvent event) {

	}

	Connection con;
	PreparedStatement pSmt;

	ObservableList<tabBean> getAllRecords() {
		ObservableList<tabBean> ary = FXCollections.observableArrayList();
		try {
			pSmt = con.prepareStatement("select * from customers where papers like ?");
			pSmt.setString(1, "%" + comboPapers.getSelectionModel().getSelectedItem() + "%");
			ResultSet rs = pSmt.executeQuery();
			while (rs.next()) {
				int mob = rs.getInt(2);
				String name = rs.getString(1);
				String add = rs.getString(3);

				tabBean ref = new tabBean(name, mob, add);
				ary.add(ref);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ary;
	}

	@FXML
	void initialize() {
		con = ConnectToDB.getConnection();

		comboAreas.getItems().clear();
		comboPapers.getItems().clear();

		try {
			pSmt = con.prepareStatement("select distinct * from hawkers");
			ResultSet tab = pSmt.executeQuery();

			while (tab.next()) {
				String[] getAreas = tab.getString(4).split(",");
				comboAreas.getItems().addAll(getAreas);
			}

			pSmt = con.prepareStatement("select distinct paper from papers");
			ResultSet table = pSmt.executeQuery();
			while (table.next()) {
				comboPapers.getItems().addAll(table.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
