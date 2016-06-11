import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
public class connectivity {
	 // JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost:3306/sample";

	   //  Database credentials
	   static final String USER = "root";
	   static final String PASS = "root";
	   
	   public static void main(String[] args) {
	   Connection conn = null;
	   Statement stmt = null;
	   try{
	      //STEP 2: Register JDBC driver
	      Class.forName("com.mysql.jdbc.Driver");

	      //STEP 3: Open a connection
	      System.out.println("Connecting to database...");
	      conn = DriverManager.getConnection(DB_URL,USER,PASS);

	      //STEP 4: Execute a query
	      System.out.println("Creating statement...");
	      stmt = conn.createStatement();
	      String sCurrentLine;
	      BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\TUSHAR\\Downloads\\mutect_immediate.vcf"));
	      String query = " insert into my_vcf (Chr, Pos, ID, Ref, Alt, Qual, Filter, Info, Sample)"
	    	        + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	    	 
	    	      // create the mysql insert preparedstatement
	      while ((sCurrentLine = br.readLine()) != null) {

              if(sCurrentLine.contains("#")){
              	
              }
              else{
              String[] information = sCurrentLine.split("\t");
              System.out.println(information[0]);
              System.out.println(information[1]);
              System.out.println(information[2]);
              System.out.println(information[3]);
              System.out.println(information[4]);
              System.out.println(information[5]);
              System.out.println(information[6]);
              System.out.println(information[7]);
              System.out.println(information[8]);
              
	    	      PreparedStatement preparedStmt = conn.prepareStatement(query);
	    	      preparedStmt.setString (1, information[0]);
	    	      preparedStmt.setInt(2, Integer.parseInt(information[1]));
	    	      preparedStmt.setString (3, information[2]);
	    	      preparedStmt.setString(4,information[3]);
	    	      preparedStmt.setString (5, information[4]);
	    	      preparedStmt.setString (6, information[5]);
	    	      preparedStmt.setString (7,information[6]);
	    	      preparedStmt.setString (8, information[7]);
	    	      preparedStmt.setString (9, information[8]);
	    	 
	    	      // execute the preparedstatement
	    	      preparedStmt.execute();
              }
              }
	      
	      
	      String sql;
	      sql = "SELECT * FROM my_vcf";
	      ResultSet rs = stmt.executeQuery(sql);

	      //STEP 5: Extract data from result set
	      while(rs.next()){
	         //Retrieve by column name
	        
	         
	         String Chr = rs.getString("Chr");
	         int Pos  = rs.getInt("Pos");
	         String ID = rs.getString("ID");	         
	         String Ref = rs.getString("Ref");
	         String Alt = rs.getString("Alt");
	         String Qual = rs.getString("Qual");
	         String Filter = rs.getString("Filter");
	         String Info = rs.getString("Info");
	         String Sample=rs.getString("Sample");

	         //Display values
	         System.out.print("Chr: " + Chr);
	         System.out.print(", Pos: " + Pos);
	         System.out.print(", ID: " +ID );
	         System.out.println(", Ref: " + Ref);
	         System.out.println(", Alt: " + Alt);
	         System.out.println(", Qual: " + Qual);
	         System.out.println(", Ref: " + Ref);
	         System.out.println(", Filter: " + Filter);
	         System.out.println(", Info: " + Info);
	         System.out.println(", Sample: " + Sample);
	      }
	      //STEP 6: Clean-up environment
	      rs.close();
	      stmt.close();
	      conn.close();
	   }catch(SQLException se){
	      //Handle errors for JDBC
	      se.printStackTrace();
	   }catch(Exception e){
	      //Handle errors for Class.forName
	      e.printStackTrace();
	   }finally{
	      //finally block used to close resources
	      try{
	         if(stmt!=null)
	            stmt.close();
	      }catch(SQLException se2){
	      }// nothing we can do
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }//end finally try
	   }//end try
	  


		
		
	   }
	   
}
	   
	   
