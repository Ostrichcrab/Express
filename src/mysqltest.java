import java.sql.*;
public class mysqltest {

	
	
		
		static final String JDBC_DRIVER="com.mysql.jdbc.Driver";
		//static final String DB_URL = "jdbc:mysql://localhost:3306/extrace?useSSL=false";
		static final String DB_URL = "jdbc:mysql://localhost:3306/extrace?useSSL=false&serverTimezone=UTC";
		static final String USER="root";
		static final String PASS="1111";

		public static void main(String[] args) {
			// TODO Auto-generated method stub

			Connection conn=null;
			Statement stmt =null;
			try {
				//Class.forName("com.mysql.jdbc.Driver");
				Class.forName("com.mysql.cj.jdbc.Driver");
				System.out.println("�������ݿ�...");
				conn= DriverManager.getConnection(DB_URL,USER,PASS);
				if(conn!=null) System.out.println("it's ok");
				else System.out.println("it's not ok");
//				
//				System.out.println("ʵ����Statement����...");
//				stmt=conn.createStatement();
//				String sql;
//				sql="select id,name,url from websites";
//				ResultSet rs =stmt.executeQuery(sql);
//				
//				while(rs.next()) {
//					int id =rs.getInt("id");
//	                String name = rs.getString("name");
//	                String url = rs.getString("url");
//	    
//	                // �������
//	                System.out.print("ID: " + id);
//	                System.out.print(", վ������: " + name);
//	                System.out.print(", վ�� URL: " + url);
//	                System.out.print("\n");
//				}
//				rs.close();
//				stmt.close();
				conn.close();
			}catch(SQLException se) {
				se.printStackTrace();
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				
				try{
	                if(stmt!=null) stmt.close();
	            }catch(SQLException se2){
	            }// ʲô������
	            try{
	                if(conn!=null) conn.close();
	            }catch(SQLException se){
	                se.printStackTrace();
	            }
			}
	        System.out.println("Goodbye!");	
			
		}

	}


