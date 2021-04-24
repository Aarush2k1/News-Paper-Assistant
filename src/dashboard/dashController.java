package dashboard;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class dashController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void openBillCol(MouseEvent event6) {
    	try {
			Parent root6=FXMLLoader.load(getClass().getClassLoader().getResource("billCollector/BillCol.fxml")); 
			Scene scene6 = new Scene(root6);
			//scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
			
			Stage primaryStage6=new Stage();
			primaryStage6.setScene(scene6);
			primaryStage6.show();
	    } 
	catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void openBillGen(MouseEvent event4) {
    	try {
			Parent root4=FXMLLoader.load(getClass().getClassLoader().getResource("billGenerator/BillGen.fxml")); 
			Scene scene4 = new Scene(root4);
			//scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
			
			Stage primaryStage4=new Stage();
			primaryStage4.setScene(scene4);
			primaryStage4.show();
	    } 
	catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void openCustMan(MouseEvent event2) {
    	try {
			Parent root2=FXMLLoader.load(getClass().getClassLoader().getResource("customerManager/CustMan.fxml")); 
			Scene scene2 = new Scene(root2);
			//scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
			
			Stage primaryStage2=new Stage();
			primaryStage2.setScene(scene2);
			primaryStage2.show();
	    } 
	catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void openHwkMan(MouseEvent event3) {
    	try {
			Parent root3=FXMLLoader.load(getClass().getClassLoader().getResource("hawkerControlPanel/hcp.fxml")); 
			Scene scene3 = new Scene(root3);
			//scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
			
			Stage primaryStage3=new Stage();
			primaryStage3.setScene(scene3);
			primaryStage3.show();
	    } 
	catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void openPaperMaster(MouseEvent event1) {
    	try {
			Parent root1=FXMLLoader.load(getClass().getClassLoader().getResource("paperMaster/PaperMaster.fxml")); 
			Scene scene1 = new Scene(root1);
			//scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene1);
			primaryStage.show();
	    } 
	catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void openViewCust(MouseEvent event5) {
    	try {
			Parent root5=FXMLLoader.load(getClass().getClassLoader().getResource("display/dis.fxml")); 
			Scene scene5 = new Scene(root5);
			//scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
			
			Stage primaryStage5=new Stage();
			primaryStage5.setScene(scene5);
			primaryStage5.show();
	    } 
	catch(Exception e)
		{
			e.printStackTrace();
		}

    }

    @FXML
    void initialize() {

    }
}
