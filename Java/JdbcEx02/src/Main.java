import java.sql.*;

public class Main {
	
	static{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(ClassNotFoundException cnfe){
			cnfe.printStackTrace();			
		}
	}

	public static void main(String[] args) {
		try {
			Connection con = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe",
					"scott","tiger");
			Statement stmt = con.createStatement();
			
			StringBuffer sb = new StringBuffer();
			
			sb.append("create table test1( ");
			sb.append("id varchar(10), ");
			sb.append("age number)");

			int updCount = stmt.executeUpdate(sb.toString());
			System.out.println("create count: " + updCount);
			
			sb.setLength(0);
			sb.append("insert into test1 ");
			sb.append("values ('홍길동', 10)");
			updCount = stmt.executeUpdate(sb.toString());
			System.out.println("insert count: " + updCount);

			sb.setLength(0);
			sb.append("select * from test1");
			ResultSet rs = stmt.executeQuery(sb.toString());
			while(rs.next()) {
				System.out.print("id: " + rs.getString(1) + ", ");
				System.out.println("age: " + rs.getInt("age"));
			}
			
			sb.setLength(0);
			sb.append("update test1 ");
			sb.append("set id = '전우치', ");
			sb.append("    age = 20 ");
			sb.append("where id='홍길동'");
			updCount = stmt.executeUpdate(sb.toString());
			System.out.println("update count: " + updCount);
			
			sb.setLength(0);
			sb.append("select * from test1");
			rs = stmt.executeQuery(sb.toString());
			while(rs.next()) {
				System.out.print("id: " + rs.getString(1) + ", ");
				System.out.println("age: " + rs.getInt("age"));
			}
			
			sb.setLength(0);
			sb.append("delete from test1");
			updCount = stmt.executeUpdate(sb.toString());
			System.out.println("delete count: " + updCount);
			
			sb.setLength(0);
			sb.append("drop table test1");
			updCount = stmt.executeUpdate(sb.toString());
			System.out.println("drop count: " + updCount);
			
			rs.close();
			stmt.close();
			con.close();
		}catch(SQLException sqle){
			System.out.println("Connection Error");
			sqle.printStackTrace();
		}
	}
}
