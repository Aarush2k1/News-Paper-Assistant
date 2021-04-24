package jdbcc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToDB {
	public static Connection getConnection() {
		Connection con = null; // machine/dbName,uid,pwd
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/realjavadb", "root", "");
			// System.out.println("Connected");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	public static void main(String args[]) {
		Connection conRef = getConnection();
		if (conRef == null)
			System.out.println("Error");
		else
			System.out.println("Connected...");
	}
}
