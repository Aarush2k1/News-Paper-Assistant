package hawkDisplay;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import jdbcc.ConnectToDB;

public class HawkDisController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TableView<HawkBean> tableDis;

	@FXML
	private Button btnExport;

	@FXML
	private Button btnOpen;

	@FXML
	private Button btnShowAll;

	@SuppressWarnings("unchecked")
	@FXML
	void fetchAll(ActionEvent event) {
		TableColumn<HawkBean, String> name = new TableColumn<HawkBean, String>("Name");
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		name.setMinWidth(150);

		TableColumn<HawkBean, Integer> mobile = new TableColumn<HawkBean, Integer>("Mobile Number");
		mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
		mobile.setMinWidth(150);

		TableColumn<HawkBean, String> address = new TableColumn<HawkBean, String>("Address");
		address.setCellValueFactory(new PropertyValueFactory<>("address"));
		address.setMinWidth(200);

		TableColumn<HawkBean, String> areas = new TableColumn<HawkBean, String>("Areas");
		areas.setCellValueFactory(new PropertyValueFactory<>("areas"));
		areas.setMinWidth(150);

		TableColumn<HawkBean, Float> sal = new TableColumn<HawkBean, Float>("Salary");
		sal.setCellValueFactory(new PropertyValueFactory<>("salary"));
		sal.setMinWidth(200);

		TableColumn<HawkBean, String> pic = new TableColumn<HawkBean, String>("Adhaar Pic");
		pic.setCellValueFactory(new PropertyValueFactory<>("pic"));
		pic.setMinWidth(200);

		tableDis.getColumns().clear();
		tableDis.getColumns().addAll(name, mobile, address, areas, pic);
		ObservableList<HawkBean> ary = getAllRecords();
		tableDis.setItems(null);
		tableDis.setItems(ary);

	}

	Connection con;
	PreparedStatement pSmt;

	ObservableList<HawkBean> getAllRecords() {
		ObservableList<HawkBean> ary = FXCollections.observableArrayList();
		try {
			pSmt = con.prepareStatement("select * from hawkers");
			ResultSet rs = pSmt.executeQuery();
			while (rs.next()) {
				int mob = rs.getInt(2);
				String name = rs.getString(1);
				String add = rs.getString(3);
				String areas = rs.getString(4);
				float sal = rs.getFloat(5);
				String pic = rs.getString(6);

				HawkBean ref = new HawkBean(name, mob, add, areas, sal, pic);
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
	}
}
