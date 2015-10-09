import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionBean {
	
static Connection connection = null;
public static Connection getConString(){

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("MySQL JDBC notfound!");
		}

		System.out.println("MySQL JDBC Driver Registered!");
		

		try {
			connection = DriverManager
			.getConnection("jdbc:mysql://localhost:3306/GoAcess","root", "");

		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return connection;
	} 
}

