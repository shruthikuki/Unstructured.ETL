package ipvs_is.database;

import java.sql.*;
import java.util.ArrayList;

/**
 * DatabaseConnectionHandler 
 * This java class handles interaction with the database.
 */

public class DatabaseConnectionHandler {

	public void deleteTableContents(String tableName) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ConnectionFactory.getConnection();

			stmt = conn.createStatement();
			String sql;
			sql = "DELETE FROM " + tableName + ";";
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
				if (conn != null)
					conn.close();
			} 
			catch (SQLException se) {
				se.printStackTrace();
			} 
		} 
	}

	public int insertDataSourceContent(String dataSourceContent, String type, String name, String langaugeCode) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int id = 0;
		try {
			conn = ConnectionFactory.getConnection();

			String sql;
			sql = "INSERT INTO DATA_SOURCE (content, type,name, languageCode) VALUES ('" + dataSourceContent + "','"
					+ type + "','" + name + "','" + langaugeCode + "');";
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			id = rs.getInt(1);
	
			rs.close();
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
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} 
			catch (SQLException se) {
				se.printStackTrace();
			} 
		} 
		return id;
	}

	public String getDataSourceContent(int id) {
		Connection conn = null;
		Statement stmt = null;
		String dataSourceContent = null;
		try {
			conn = ConnectionFactory.getConnection();

			stmt = conn.createStatement();
			String sql;
			sql = "SELECT * from DATA_SOURCE WHERE ID = " + id;
			ResultSet rs = stmt.executeQuery(sql);

			//Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				dataSourceContent = rs.getString("content");
			}

			rs.close();
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
				if (conn != null)
					conn.close();
			} 
			catch (SQLException se) {
				se.printStackTrace();
			} 
		} 
		return dataSourceContent;
	}

	public String getDataSourceContent() {
		Connection conn = null;
		Statement stmt = null;
		String dataSourceContent = null;
		try {
			conn = ConnectionFactory.getConnection();

			stmt = conn.createStatement();
			String sql;
			sql = "SELECT * from DATA_SOURCE WHERE ID = (SELECT MAX(ID) FROM DATA_SOURCE)";
			ResultSet rs = stmt.executeQuery(sql);

			//Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				dataSourceContent = rs.getString("content");
			}
			rs.close();
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
				if (conn != null)
					conn.close();
			} 
			catch (SQLException se) {
				se.printStackTrace();
			} 
		} 
		return dataSourceContent;
	}

	public String getResultData(String columnName, int id) {
		Connection conn = null;
		Statement stmt = null;
		String result = null;
		try {
			conn = ConnectionFactory.getConnection();

			stmt = conn.createStatement();
			String sql;
			sql = "SELECT " + columnName + " from RESULT_DATA WHERE DATASOURCEID = " + id;
			ResultSet rs = stmt.executeQuery(sql);

			//Extract data from result set
			rs.next();
			result = rs.getString(columnName);

			rs.close();
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
				if (conn != null)
					conn.close();
			} 
			catch (SQLException se) {
				se.printStackTrace();
			} 
		} 
		return result;
	}

	public ArrayList<String> getAllPOSData() {
		ArrayList<String> posDataList = new ArrayList<String>();
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ConnectionFactory.getConnection();

			stmt = conn.createStatement();
			String sql;
			sql = "SELECT BEGIN , END , TYPE from POS_DATA WHERE ID ORDER BY BEGIN DESC;";
			ResultSet rs = stmt.executeQuery(sql);

			//Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				int begin = rs.getInt("begin");
				int end = rs.getInt("end");
				String posType = rs.getString("type");
				posDataList.add(String.valueOf(begin));
				posDataList.add(String.valueOf(end));
				posDataList.add(posType);
			}
			rs.close();
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
				if (conn != null)
					conn.close();
			} 
			catch (SQLException se) {
				se.printStackTrace();
			} 
		} 
		return posDataList;
	}

	public ArrayList<String> getAllNERData() {
		ArrayList<String> nerDataList = new ArrayList<String>();
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ConnectionFactory.getConnection();

			stmt = conn.createStatement();
			String sql;
			sql = "SELECT BEGIN , END , TYPE from NAMED_ENTITY_DATA ORDER BY BEGIN DESC;";
			ResultSet rs = stmt.executeQuery(sql);

			//Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				int begin = rs.getInt("begin");
				int end = rs.getInt("end");
				String nerType = rs.getString("type");
				nerDataList.add(String.valueOf(begin));
				nerDataList.add(String.valueOf(end));
				nerDataList.add(nerType);
			}
			rs.close();
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
				if (conn != null)
					conn.close();
			} 
			catch (SQLException se) {
				se.printStackTrace();
			} 
		} 
		return nerDataList;
	}

	public void writeResultData(int dataSourceId) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ConnectionFactory.getConnection();

			stmt = conn.createStatement();
			String sql;
			sql = "INSERT INTO RESULT_DATA (DATASOURCEID,POSRESULT,NERRESULT,SCRESULT,CRRESULT) VALUES (" + dataSourceId
					+ ",'','','','');";
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
				if (conn != null)
					conn.close();
			} 
			catch (SQLException se) {
				se.printStackTrace();
			} 
		} 
	}

	public void updatePOSResultData(String resultText, int dataSourceId) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ConnectionFactory.getConnection();

			stmt = conn.createStatement();
			String sql;
			sql = "UPDATE RESULT_DATA SET POSRESULT = '" + resultText + "' WHERE DATASOURCEID = " + dataSourceId + ";";
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
				if (conn != null)
					conn.close();
			} 
			catch (SQLException se) {
				se.printStackTrace();
			} 
		} 
	}

	public void updateNERResultData(String resultText, int dataSourceId) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ConnectionFactory.getConnection();

			stmt = conn.createStatement();
			String sql;
			sql = "UPDATE RESULT_DATA SET NERRESULT = '" + resultText + "' WHERE DATASOURCEID = " + dataSourceId + ";";
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
				if (conn != null)
					conn.close();
			} 
			catch (SQLException se) {
				se.printStackTrace();
			} 
		} 
	}

	public ArrayList<String> getDataSource(String DataSourceType) {
		Connection conn = null;
		Statement stmt = null;

		ArrayList<String> datalist = new ArrayList<String>();
		try {
			conn = ConnectionFactory.getConnection();

			stmt = conn.createStatement();
			String sql;
			sql = "SELECT id,name,languageCode from DATA_SOURCE WHERE  type='" + DataSourceType + "';";
			ResultSet rs = stmt.executeQuery(sql);

			//Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				datalist.add(rs.getString("id"));
				datalist.add(rs.getString("name"));
				datalist.add(rs.getString("languageCode"));
			}

			rs.close();
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
				if (conn != null)
					conn.close();
			} 
			catch (SQLException se) {
				se.printStackTrace();
			} 
		} 
		return datalist;
	}

	public ArrayList<String> getAllSCData() {
		ArrayList<String> scDataList = new ArrayList<String>();
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ConnectionFactory.getConnection();

			stmt = conn.createStatement();
			String sql;
			sql = "SELECT BEGIN , END from SC_DATA ORDER BY BEGIN DESC;";
			ResultSet rs = stmt.executeQuery(sql);

			//Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				int begin = rs.getInt("begin");
				int end = rs.getInt("end");
				scDataList.add(String.valueOf(begin));
				scDataList.add(String.valueOf(end));
			}
			rs.close();
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
				if (conn != null)
					conn.close();
			} 
			catch (SQLException se) {
				se.printStackTrace();
			} 
		} 
		return scDataList;
	}

	public void updateSCResultData(String resultText, int dataSourceId) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ConnectionFactory.getConnection();

			stmt = conn.createStatement();
			String sql;
			sql = "UPDATE RESULT_DATA SET SCRESULT = '" + resultText + "' WHERE DATASOURCEID = " + dataSourceId + ";";
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
				if (conn != null)
					conn.close();
			} 
			catch (SQLException se) {
				se.printStackTrace();
			} 
		} 
	}

	public ArrayList<String> getCoRefIds() {
		ArrayList<String> idsList = new ArrayList<String>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = ConnectionFactory.getConnection();

			stmt = conn.createStatement();
			String sql;
			sql = "SELECT CHILD_IDS FROM CR_DATA" + ";";
			rs = stmt.executeQuery(sql);
			
			//Extract data from result set
			while (rs.next()) {
				if (rs.getString("CHILD_IDS") != null && !rs.getString("CHILD_IDS").equals("")) {
					idsList.add(rs.getString("CHILD_IDS"));
				}
			}
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
				if (conn != null)
					conn.close();
			} 
			catch (SQLException se) {
				se.printStackTrace();
			} 
		} 
		return idsList;
	}

	public ArrayList<String> getCoRefInfo() {
		Connection conn = null;
		Statement stmt = null;
		ArrayList<String> corefInfo = new ArrayList<String>();
		try {
			conn = ConnectionFactory.getConnection();

			stmt = conn.createStatement();
			String sql;
			sql = "SELECT BEGIN , END , ID from CR_DATA ORDER BY BEGIN DESC;";
			ResultSet rs = stmt.executeQuery(sql);

			//Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				int begin = rs.getInt("begin");
				int end = rs.getInt("end");
				int id = rs.getInt("id");
				corefInfo.add(String.valueOf(begin));
				corefInfo.add(String.valueOf(end));
				corefInfo.add(String.valueOf(id));
			}
			rs.close();
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
				if (conn != null)
					conn.close();
			} 
			catch (SQLException se) {
				se.printStackTrace();
			} 
		} 
		return corefInfo;
	}

	public void updateCRResultData(String resultText, int dataSourceId) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ConnectionFactory.getConnection();

			stmt = conn.createStatement();
			String sql;
			sql = "UPDATE RESULT_DATA SET CRRESULT = '" + resultText + "' WHERE DATASOURCEID = " + dataSourceId + ";";
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
				if (conn != null)
					conn.close();
			} 
			catch (SQLException se) {
				se.printStackTrace();
			} 
		} 
	}

	public int getArchiveCount(String type) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			conn = ConnectionFactory.getConnection();

			// Execute a query
			String sql = "SELECT COUNT(*) FROM DATA_SOURCE WHERE TYPE='" + type + "';";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
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
				if (conn != null)
					conn.close();
			} 
			catch (SQLException se) {
				se.printStackTrace();
			} 
		} 
		return count;
	}


	public void insertPOS(String token, int begin, int end, String type) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ConnectionFactory.getConnection();

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
				if (conn != null)
					conn.close();
			} 
			catch (SQLException se) {
				se.printStackTrace();
			} 
		} 
	}

	public void insertNamedEntity(String token, int begin, int end, String type) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ConnectionFactory.getConnection();

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
				if (conn != null)
					conn.close();
			} 
			catch (SQLException se) {
				se.printStackTrace();
			} 
		} 
	}

	public void insertSC(String token, int begin, int end) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ConnectionFactory.getConnection();

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
				if (conn != null)
					conn.close();
			} 
			catch (SQLException se) {
				se.printStackTrace();
			} 
		} 
	}

	public void insertCoreference(String token, int begin, int end, int nextBegin, int nextEnd) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ConnectionFactory.getConnection();

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
				if (conn != null)
					conn.close();
			} 
			catch (SQLException se) {
				se.printStackTrace();
			} 
		} 
	}

	public void insertCRWithChild(String token, int begin, int end, String childToken, int childBegin, int childEnd) {
		// check if begin, end already exists. if yes, insert only token, else
		// fresh row

		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = ConnectionFactory.getConnection();

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
				if (conn != null)
					conn.close();
			} 
			catch (SQLException se) {
				se.printStackTrace();
			} 
		} 
	}

	public int insertCR(String token, int begin, int end) {
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int id = 0;
		try {
			conn = ConnectionFactory.getConnection();

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
				if (conn != null)
					conn.close();
			} 
			catch (SQLException se) {
				se.printStackTrace();
			} 
		} 
		return id;
	}

	public void updateCRParent(int parentId, String childIds) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ConnectionFactory.getConnection();

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
				if (conn != null)
					conn.close();
			} 
			catch (SQLException se) {
				se.printStackTrace();
			} 
		} 
	}

	public boolean checkIfCorefExists(int begin, int end) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			conn = ConnectionFactory.getConnection();

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
				if (conn != null)
					conn.close();
			} 
			catch (SQLException se) {
				se.printStackTrace();
			} 
		} 
		
		if (count == 0)
			return true;
		else
			return false;
	}
}
