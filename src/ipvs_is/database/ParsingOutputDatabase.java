package ipvs_is.database;

import java.sql.*;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class ParsingOutputDatabase {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net:3306/ad_97053fa1b7b08e0";

	// Database credentials
	static final String USER = "bd6c79c61fba4f";
	static final String PASS = "9bcd28a7";

	public void insert() {
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// STEP 3: Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Execute a query
			stmt = conn.createStatement();
			String sql = "INSERT INTO DATA_SOURCE (CONTENT) VALUES (" + "'Peter went to a part in Stuttgart. He liked it very much. Then he visited Audi museum. He felt that it is very beautiful'" + ")";
			stmt.executeUpdate(sql); 

			// STEP 5: Extract data from result set
					stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}// nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try
	}
	
	public void insertPOS(String token, int begin, int end, String type) {
		Connection conn = null;
		Statement stmt = null;
		try {
			//Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			//Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			//Execute a query
			stmt = conn.createStatement();
			String sql = "INSERT INTO POS_DATA (TOKEN, BEGIN, END, TYPE) VALUES ("
					+ "'" + token + "'" + "," + begin + "," + end + "," + "'" + type + "'" +");";
			stmt.executeUpdate(sql); 
		} 
		
		catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} 
		
		catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} 
		
		finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} 
			
			catch (SQLException se2) {
			}// nothing we can do
			
			try {
				if (conn != null)
					conn.close();
			} 
			
			catch (SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try
	}
	
	public void insertNamedEntity(String token, int begin, int end, String type) {
		Connection conn = null;
		Statement stmt = null;
		try {
			//Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			//Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			//Execute a query
			stmt = conn.createStatement();
			String sql = "INSERT INTO NAMED_ENTITY_DATA (TOKEN, BEGIN, END, TYPE) VALUES ("
					+ "'" + token + "'" + "," + begin + "," + end + "," + "'" + type + "'" +");";
			stmt.executeUpdate(sql); 
		} 
		
		catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} 
		
		catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} 
		
		finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} 
			
			catch (SQLException se2) {
			}// nothing we can do
			
			try {
				if (conn != null)
					conn.close();
			} 
			
			catch (SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try
	}

	public void insertCoreference(String token, int begin, int end, int nextBegin, int nextEnd) {
		Connection conn = null;
		Statement stmt = null;
		try {
			//Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			//Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			//Execute a query
			stmt = conn.createStatement();
			String sql = "INSERT INTO COREFERENCE_DATA (TOKEN, BEGIN, END, TYPE) VALUES ("
					+ "'" + token + "'" + "," + begin + "," + end + "," + nextBegin + "," + nextEnd +");";
			stmt.executeUpdate(sql); 
		} 
		
		catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} 
		
		catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} 
		
		finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} 
			
			catch (SQLException se2) {
			}// nothing we can do
			
			try {
				if (conn != null)
					conn.close();
			} 
			
			catch (SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try
	}
}
