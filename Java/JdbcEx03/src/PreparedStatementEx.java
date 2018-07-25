import java.sql.*;

public class PreparedStatementEx {

	static{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(ClassNotFoundException cnfe){
			cnfe.printStackTrace();			
		}
	}
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe",
					"scott","tiger");
			String sql = "create table test2(id varchar2(10)," +
					"	 					 password varchar2(10))";
			pstmt = con.prepareStatement(sql);
			int updCount = pstmt.executeUpdate();
			System.out.println("create count: " + updCount);
			
			sql = "insert into test2 values(?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "홍길동");
			pstmt.setString(2, "11111");
			updCount = pstmt.executeUpdate();
			System.out.println("insert count: " + updCount);
			
			sql = "select * from test2";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				System.out.print("id: " + rs.getString(1) + ", ");
				System.out.println("pswd: " + rs.getString("password"));
			}
			
			sql = "drop table test2";
			pstmt = con.prepareStatement(sql);
			updCount = pstmt.executeUpdate();
			System.out.println("drop count: " + updCount);
		}catch(SQLException sqle){
			System.out.println("Connection Error");
			sqle.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(SQLException sqle){}
		}
	}

}
