package ipvs_is.database;

import java.sql.*;


public class ParsingOutputDatabase {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net:3306/ad_97053fa1b7b08e0";

	// Database credentials
	static final String USER = "bd6c79c61fba4f";
	static final String PASS = "9bcd28a7";

	/*public void insert() {
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// STEP 3: Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Execute a query
			stmt = conn.createStatement();
			String sql = "INSERT INTO DATA_SOURCE (CONTENT) VALUES ("
					+ "'Peter went to a part in Stuttgart. He liked it very much. Then he visited Audi museum. He felt that it is very beautiful'"
					+ ")";
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
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
	}

	public void insertPOS(String token, int begin, int end, String type) {
		Connection conn = null;
		Statement stmt = null;
		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// Execute a query
			stmt = conn.createStatement();
			String sql = "INSERT INTO POS_DATA (TOKEN, BEGIN, END, TYPE) VALUES (" + "'" + token + "'" + "," + begin
					+ "," + end + "," + "'" + type + "'" + ");";
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
			} // nothing we can do

			try {
				if (conn != null)
					conn.close();
			}

			catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
	}

	public void insertNamedEntity(String token, int begin, int end, String type) {
		Connection conn = null;
		Statement stmt = null;
		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// Execute a query
			stmt = conn.createStatement();
			String sql = "INSERT INTO NAMED_ENTITY_DATA (TOKEN, BEGIN, END, TYPE) VALUES (" + "'" + token + "'" + ","
					+ begin + "," + end + "," + "'" + type + "'" + ");";
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
			} // nothing we can do

			try {
				if (conn != null)
					conn.close();
			}

			catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
	}

	public void insertSC(String token, int begin, int end) {
		Connection conn = null;
		Statement stmt = null;
		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// Execute a query
			stmt = conn.createStatement();
			String sql = "INSERT INTO SC_DATA (TOKEN, BEGIN, END) VALUES (" + "'" + token + "'" + "," + begin + ","
					+ end + ");";
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
			} // nothing we can do

			try {
				if (conn != null)
					conn.close();
			}

			catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
	}

	public void insertCoreference(String token, int begin, int end, int nextBegin, int nextEnd) {
		Connection conn = null;
		Statement stmt = null;
		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// Execute a query
			stmt = conn.createStatement();
			String sql = "INSERT INTO COREFERENCE_DATA (TOKEN, BEGIN, END, TYPE) VALUES (" + "'" + token + "'" + ","
					+ begin + "," + end + "," + nextBegin + "," + nextEnd + ");";
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
			} // nothing we can do

			try {
				if (conn != null)
					conn.close();
			}

			catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
	}

	public void insertCRWithChild(String token, int begin, int end, String childToken, int childBegin, int childEnd) {
		// check if begin, end already exists. if yes, insert only token, else
		// fresh row

		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// Execute a query

			String sqlParent = "INSERT INTO CR_DATA (TOKEN, BEGIN, END) VALUES (" + "'" + token + "'" + "," + begin
					+ "," + end + ");";
			pstmt = conn.prepareStatement(sqlParent, Statement.RETURN_GENERATED_KEYS);
			pstmt.executeUpdate(sqlParent);
			rs = pstmt.getGeneratedKeys();
			rs.next();
			int parentId = rs.getInt(1);
			String sqlChild = "INSERT INTO CR_DATA (TOKEN, BEGIN, END) VALUES (" + "'" + childToken + "'" + ","
					+ childBegin + "," + childEnd + ");";
			pstmt = conn.prepareStatement(sqlChild, Statement.RETURN_GENERATED_KEYS);
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			rs.next();
			int childId = rs.getInt(1);
			String sqlParentUpdate = "UPDATE CR_DATA SET CHILD_ID=" + childId + " WHERE ID=" + parentId;
			stmt = conn.createStatement();
			stmt.executeUpdate(sqlParentUpdate);
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
			} // nothing we can do

			try {
				if (conn != null)
					conn.close();
			}

			catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
	}

	public int insertCR(String token, int begin, int end) {
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int id = 0;
		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// Execute a query

			String sqlParent = "INSERT INTO CR_DATA (TOKEN, BEGIN, END, CHILD_IDS) VALUES (" + "'" + token + "'" + ","
					+ begin + "," + end + "," + "''" + ");";
			
			pstmt = conn.prepareStatement(sqlParent, Statement.RETURN_GENERATED_KEYS);
			pstmt.executeUpdate(sqlParent);
			rs = pstmt.getGeneratedKeys();
			rs.next();
			id = rs.getInt(1);

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
			} // nothing we can do

			try {
				if (conn != null)
					conn.close();
			}

			catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try

		return id;

	}

	public void updateCRParent(int parentId, String childIds) {
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement stmt = null;
		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// Execute a query

			String sql = "UPDATE CR_DATA SET CHILD_IDS='" + childIds + "' WHERE ID=" + parentId + ";";
			stmt = conn.createStatement();
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
			} // nothing we can do

			try {
				if (conn != null)
					conn.close();
			}

			catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try

	}

	public boolean checkIfCorefExists(int begin, int end) {
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// Execute a query

			String sqlCheck = "SELECT COUNT(*) FROM CR_DATA WHERE BEGIN=" + begin + " AND END=" + end + ";";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlCheck);
			rs.next();
			count = rs.getInt(1);
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
			} // nothing we can do

			try {
				if (conn != null)
					conn.close();
			}

			catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		if (count == 0)
			return true;
		else
			return false;
	}
*/}
