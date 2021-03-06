package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DataController implements Initializable{	// 회원가입 및 ID 찾기
	@FXML
	private TextField name;
	@FXML
	private TextField id;
	@FXML
	private TextField password;
	@FXML
	private TextField birth;
	@FXML
	private TextField phone;
	@FXML
	private Button save;
	@FXML private TextField findphone;
	@FXML private TextField findbirth;
	@FXML private TextField findname;
	@FXML private TextField findid;
	@FXML private Label result2;
	@FXML private Label error;
	
	private Stage primaryStage;
	private Stage primaryStage2;
	private boolean check=true;
	private boolean idcheck=false;
	
	public void SetStage(Stage primaryStage2) {
		this.primaryStage2 = primaryStage2;
	}
	public void Set_Stage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {}
	public void OkAction(ActionEvent event) throws Exception{
		primaryStage2.close();
	}
	public void NoAction(ActionEvent event) throws Exception{
		primaryStage2.close();
	}

	public void DuplicateInspection(ActionEvent event) throws Exception{
		int result=0;
	
			idcheck=true;
		
		String jdbcUrl = "jdbc:mysql://localhost:3306/trail?allowPublicKeyRetrieval=true&serverTimezone=UTC&SSL=false&useSSL=false";
	    String dbId = "root";
		String dbPw = "root";	  
		System.out.println("DB Loading...");
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		if(!id.getText().isEmpty()) {
		try {
			conn = DriverManager.getConnection(jdbcUrl,dbId,dbPw);
			System.out.println("DB Loading Success!!");
			String query = "select * from `member` ";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
			
				String Tid=rs.getString("id");
				if(id.getText().equals(Tid)) {	
					result=1;
					check=false;
				}
			}
			
			System.out.println(result);
			
			// 사용중인 ID
			if(result!=0) {	
				id.setText("");
				idcheck=false;
				Stage ok2 = new Stage();
				ok2.setTitle("TrainTicketBooking");
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/SceneBuilder/using_id.fxml"));
				Parent root = loader.load();
				Scene scene = new Scene(root);
		        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		        DataController da1 = loader.getController();
	            da1.SetStage(ok2);
		        ok2.setScene(scene);
				ok2.show();
			}
			
			// 사용 가능한 ID
			else{
				check=true;
				Stage no = new Stage();
				no.setTitle("TrainTicketBooking");
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/SceneBuilder/idok.fxml"));
				Parent root = loader.load();
				Scene scene = new Scene(root);
		        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		        DataController da = loader.getController();
	            da.SetStage(no);
		        no.setScene(scene);
				no.show();
			}
		}
		catch (Exception e) {e.printStackTrace();}
		}
	}
	
	public void Out(ActionEvent event)throws Exception{
		primaryStage.close();
	}

	// ID 찾기 ( 입력 전 )
	public void findAction2(ActionEvent event) throws Exception{
		int count=0;
		String jdbcUrl = "jdbc:mysql://localhost:3306/trail?allowPublicKeyRetrieval=true&serverTimezone=UTC&SSL=false&useSSL=false";
	    String dbId = "root";
		String dbPw = "root";	  
		System.out.println("DB Loading...");
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String fname=findname.getText();
		String fid=findid.getText();
		String fphone=findphone.getText();
		String fpw = null;
		try {
			conn = DriverManager.getConnection(jdbcUrl,dbId,dbPw);
			System.out.println("DB Loading Success!!");
			String query = "select * from `member` ";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			System.out.println(fid);
			while(rs.next()) {
				if(fname.equals(rs.getString("name"))&&fid.equals(rs.getString("id"))&&fphone.equals(rs.getString("phone"))){
					fpw=rs.getString("password");
					count=1;
				}
				
			}
			
			if(count!=0) {
				
				result2.setText("회원님의 PW는 '"+fpw+"'");
		 
		    
	           
				
			
			}
			else{
				result2.setText("정보가 없습니다.");
			
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// ID 찾기 ( 한번 입력 후 )
	public void findAction(ActionEvent event) throws Exception{
		int count=0;
		String jdbcUrl = "jdbc:mysql://localhost:3306/trail?allowPublicKeyRetrieval=true&serverTimezone=UTC&SSL=false&useSSL=false";

	    String dbId = "root";

		String dbPw = "root";	  
		System.out.println("DB Loading...");
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String fname=findname.getText();
		String fbirth=findbirth.getText();
		String fphone=findphone.getText();
		String fid = null;
		try {
			conn = DriverManager.getConnection(jdbcUrl,dbId,dbPw);
			System.out.println("DB Loading Success!!");
			String query = "select * from `member` ";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			System.out.println(fid);
			while(rs.next()) {
				if(fname.equals(rs.getString("name"))&&fbirth.equals(rs.getString("birth"))&&fphone.equals(rs.getString("phone"))){
					fid=rs.getString("id");
					count=1;
					System.out.println(fid);
				}
				
			}
			
			if(count!=0) {
				
				result2.setText("회원님의 ID는 '"+fid+"'");
		
			}
			else{
				result2.setText("결과가 없습니다.");
			
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Outjoin(ActionEvent event) throws Exception{
		primaryStage.close();
	}
	public void membersAction(ActionEvent event) throws Exception{
			
			if(idcheck==true ) {
				if(check==true) {
					if(save.isPressed()==false) {
						primaryStage.close();
						String uName = name.getText();
					    String uId = id.getText();
					    String uPwd = password.getText();  
					    String uBirth=birth.getText();
					    String uPhone=phone.getText();
					
					 // MySQL url
						  String jdbcUrl = "jdbc:mysql://localhost:3306/trail?allowPublicKeyRetrieval=true&serverTimezone=UTC&SSL=false&useSSL=false";
						  // MySQL id
						  String dbId = "root";
						  // MySQL password
						  String dbPw = "root";	  
						  System.out.println("DB Loading...");
						  Class.forName("com.mysql.cj.jdbc.Driver");
						  Connection conn = null;
							PreparedStatement pstmt = null;
							System.out.println(uName);
							if(uName.isEmpty() || uId.isEmpty() || uPwd.isEmpty() || uBirth.isEmpty() || uPhone.isEmpty()) {
								error.setText("빈칸없이 입력해주세요.");
							}
							else {
							try {
								conn = DriverManager.getConnection(jdbcUrl,dbId,dbPw);
								System.out.println("DB Loading Success!!");
								
								String query = "insert into `member`(name,id,password,birth,phone) values (?,?,?,?,?)";
								pstmt = conn.prepareStatement(query);
								pstmt.setString(1, uName);
								pstmt.setString(2, uId);
								pstmt.setString(3,uPwd);
								pstmt.setString(4, uBirth);
								pstmt.setString(5, uPhone);
								pstmt.executeUpdate();
								
							} catch (Exception e) {
								e.printStackTrace();
							} finally {
								try {
									if(pstmt != null) pstmt.close();
									if(conn != null) conn.close();
								} catch(Exception e) {
									e.printStackTrace();
								}
							}
					}
				}
				
			}
			
			else{
				
			
				Stage check = new Stage();
				check.setTitle("TrainTicketBooking");
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/SceneBuilder/idcheck.fxml"));
			
				Parent root = loader.load();
				Scene scene = new Scene(root);
				
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    
				DataController da1 = loader.getController();
				da1.SetStage(check);
           
			
				check.setScene(scene);
				check.show();
				
			}
			
		}
			
	 }

}
