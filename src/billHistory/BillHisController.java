package billHistory;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

public class BillHisController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<String> tableHistory;

    @FXML
    private ComboBox<String> comboMob;

    @FXML
    private Button btnShow;

    @FXML
    void showAll(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert tableHistory != null : "fx:id=\"tableHistory\" was not injected: check your FXML file 'BillHis.fxml'.";
        assert comboMob != null : "fx:id=\"comboMob\" was not injected: check your FXML file 'BillHis.fxml'.";
        assert btnShow != null : "fx:id=\"btnShow\" was not injected: check your FXML file 'BillHis.fxml'.";

    }
}
