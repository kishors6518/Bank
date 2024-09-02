package bankaccount;

import java.sql.Connection;
import java.sql.DriverManager;

public class CreateDBConnection {

	private static final String PATH="com.mysql.cj.jdbc.Driver";
	private static final String URL="jdbc:mysql://localhost:3306/bankdb";
	private static final String USER="root";
	private static final String PASS="root";
	
	public static Connection createConnection()
	{
		try {
			Class.forName(PATH);
	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Issue in registering the server");
		}
		try {
			return DriverManager.getConnection(URL, USER, PASS);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Issue in connecting the db");
			return null;
		}
	}




}
