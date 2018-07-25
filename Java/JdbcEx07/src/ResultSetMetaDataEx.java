import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

public class ResultSetMetaDataEx {

	static{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(ClassNotFoundException cnfe){
			cnfe.printStackTrace();			
		}
	}
	
	public static void main(String[] args) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Properties pro = null;
		FileInputStream fis = null;
		
		try {
			
			pro = new Properties() ;
			fis = new FileInputStream("D:\\jsj\\study\\Java\\jdbc.properties");
			pro.load(fis);
			String url = pro.getProperty("url");
			String user = pro.getProperty("user");
			String pswd = pro.getProperty("password");
			
			con = DriverManager.getConnection(url, user, pswd);

			String sql = "select * from employee";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int numberofColummn = rsmd.getColumnCount();
			
			System.out.println("Number of Columns : " + numberofColummn);
			System.out.println("[Column Name] [DBMS Type] [Null Allowed]");
			
			for (int i=1; i<=numberofColummn; i++) {
				String colName = rsmd.getColumnName(i);
				String dbmsType = rsmd.getColumnTypeName(i);
				int isNull = rsmd.isNullable(i);
				
				String strNull = "Null";
				if(isNull == 0)
					strNull = "Not Null";
				
				String space1 = "";
				for(int j=0; j<14-colName.length(); j++)
					space1 += " ";
				
				String space2 = "";
				for(int j=0; j<12-dbmsType.length(); j++)
					space2 += " ";
				
				System.out.print(colName + space1);
				System.out.print(dbmsType + space2);
				System.out.println(strNull);
			}

			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(SQLException sqle){}
		}	
	}

}
