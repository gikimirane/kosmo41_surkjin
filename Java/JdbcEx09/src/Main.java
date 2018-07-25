import java.sql.*;

public class Main {

	public static void main(String[] args) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ConnectionPool cp = null;
		
		try {
			
			cp = ConnectionPool.getInstance(
					"jdbc:oracle:thin:@localhost:1521:xe",
					"scott","tiger", 5, 10);
			con = cp.getConnection();
			pstmt = con.prepareStatement("select * from department");
			
			rs = pstmt.executeQuery();	
			while(rs.next()) 
				System.out.println(	"deptno: " + rs.getInt(1) + ", "
							+ "dname: " + rs.getString(2) + ", "
							+ "loc: " + rs.getString(3));
									
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(SQLException sqle){
				sqle.printStackTrace();
			}
		}
		cp.closeAll();
	}

}
