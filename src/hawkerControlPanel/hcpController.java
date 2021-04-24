package hawkerControlPanel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.scene.input.MouseEvent;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import jdbcc.ConnectToDB;

public class hcpController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Label heading;

	@FXML
	private TextField txtMobile;

	@FXML
	private TextField txtAddress;

	@FXML
	private TextField txtSalary;

	@FXML
	private TextField txtArea;

	@FXML
	private ImageView adhaarImg;

	@FXML
	private Button btnSave;
	
	@FXML
	private Button btnClear;

	@FXML
	private Button btnUpdate;

	@FXML
	private Button btnDelete;

	@FXML
	private Button btnUpload;

	@FXML
	private ListView<String> listAreas;

	@FXML
	private ComboBox<String> comboName;

	private String imgName = null;

	@FXML
	void addArea(ActionEvent event) {
		String ar = txtArea.getText();
		listAreas.getItems().addAll(ar);
		txtArea.setText(null);
	}
	@FXML
	void doClear(ActionEvent event) {
		txtMobile.setText(null);
		txtAddress.setText(null);
		txtSalary.setText(null);
		txtArea.setText(null);
		listAreas.getItems().clear();
		adhaarImg.setImage(new Image("file:src\\hawkerControlPanel\\aaadhar-card.png"));
	}

	@FXML
	void doDelete(ActionEvent event) {
		try {
			prpStmt = con.prepareStatement("delete from hawkers where name=?");
			prpStmt.setString(1, comboName.getEditor().getText());

			int count = prpStmt.executeUpdate();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setContentText(count + " Records deleted");
			alert.show();

			comboName.getItems().clear();
			initialize();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void fetchSel(ActionEvent event) {
		String sel = comboName.getSelectionModel().getSelectedItem();
		if (sel != null) {
			System.out.println("Inside fetchSel");
			try {
				prpStmt = con.prepareStatement("select * from hawkers where name=?");
				prpStmt.setString(1, sel);

				ResultSet table = prpStmt.executeQuery();
				if (table.next()) {
					int mobile = table.getInt(2);
					String address = table.getString(3);
					String fetchAreas = table.getString(4);
					int salary = table.getInt(5);
					String Img = "file:src\\hawkerControlPanel\\" + table.getString(6);

					txtMobile.setText(String.valueOf(mobile));
					txtAddress.setText(address);
					txtSalary.setText(String.valueOf(salary));
					listAreas.getItems().clear();
					String fetchedAr[] = null;
					fetchedAr = fetchAreas.split(",");
					listAreas.getItems().addAll(fetchedAr);
					adhaarImg.setImage(new Image(Img));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
//			else {
//			Alert alert = new Alert(AlertType.ERROR);
//			alert.setTitle("Sorry");
//			alert.setContentText("Wrong Selection");
//			alert.show();
//		}
	}

	@FXML
	void doSave(ActionEvent event) {
		if (imgName == null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Add Adhaar Card Image");
			alert.setContentText("Adhaar Card not uploaded, Mandatory to add");
			alert.show();
		} else {
			try {
				prpStmt = con.prepareStatement("insert into hawkers values(?,?,?,?,?,?,curdate())");
				prpStmt.setString(1, comboName.getEditor().getText());
				prpStmt.setInt(2, Integer.parseInt(txtMobile.getText()));
				prpStmt.setString(3, txtAddress.getText());

				String s = "";
				ObservableList<String> areas = listAreas.getItems();
				for (String v : areas) {
					s = s + v + ",";
				}
				String Area = s.substring(0, s.length() - 1);
				prpStmt.setString(4, Area);
				prpStmt.setString(5, txtSalary.getText());
				prpStmt.setString(6, imgName);

				prpStmt.executeUpdate();
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Done");
				alert.setContentText("Saved successfully");
				alert.show();
				comboName.getItems().clear();
				initialize();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	void doUpdate(ActionEvent event) {
		try {
			prpStmt = con.prepareStatement("update hawkers set mobile=?,address=?,adhaarPic=?,salary=? where name=?");
			prpStmt.setInt(1, Integer.parseInt(txtMobile.getText()));
			prpStmt.setString(2, txtAddress.getText());
			prpStmt.setString(3, imgName);
			prpStmt.setInt(4, Integer.parseInt(txtSalary.getText()));
			prpStmt.setString(5, comboName.getEditor().getText());

			prpStmt.executeUpdate();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Done");
			alert.setContentText("Updated successfully");
			alert.show();
			comboName.getItems().clear();
			initialize();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void doUpload(MouseEvent event) {
		FileChooser fc = new FileChooser();
		fc.setTitle("Choose Adhaar Card");
		fc.getExtensionFilters();
		Window mainStage = null;
		File sel = fc.showOpenDialog(mainStage);
		if (sel != null) {
			String imgPath = sel.getPath();
			adhaarImg.setImage(new Image(sel.toURI().toString()));

			imgName = imgPath.substring(imgPath.lastIndexOf("\\") + 1);

			File srcImg = new File(imgPath);
			File destImg = new File(
					"C:\\Users\\AARUSH\\eclipse-workspace\\newsPaperAssistant\\src\\hawkerControlPanel\\" + imgName);
			try {
				FileInputStream fis = new FileInputStream(srcImg);
				FileOutputStream fos = new FileOutputStream(destImg);
				while (true) {
					int pix = fis.read();
					if (pix == -1)
						break;
					fos.write(pix);
				}
				fis.close();
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			System.out.println("No image Selected");
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Complete");
			alert.setContentText("No image was selected");
			alert.show();
			alert.hide();
		}
	}

	Connection con;
	PreparedStatement prpStmt;

	@FXML
	void initialize() {
		con = ConnectToDB.getConnection();

		txtMobile.setText(null);
		txtAddress.setText(null);
		txtSalary.setText(null);
		listAreas.getItems().clear();
		adhaarImg.setImage(new Image("file:src\\hawkerControlPanel\\aaadhar-card.png"));

		try {
			prpStmt = con.prepareStatement("select * from hawkers");

			ResultSet table = prpStmt.executeQuery();
			while (table.next()) {
				String name = table.getString("name");
				comboName.getItems().addAll(name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
