package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
 
public class MainController {		// 로그인 화면의 기능
 
    @FXML private Label lblStatus;
    @FXML private Label error;
    @FXML private TextField txtUserName;
    @FXML private TextField txtPassword;
    @FXML private TextField PhoneNumber;
    @FXML private TextField password;
    @FXML private Button exitt;
    @FXML private Button log;
    @FXML private Button suout;
    @FXML private AnchorPane sc;
    @FXML private Label laId;
    @FXML private Label laName;
    @FXML private Label laBirth;
    @FXML private Label laPhone;
    @FXML private Label findbirth;
    @FXML private Label findPhone;
    @FXML private Label findname;
    @FXML private Label findId;
    
    private Stage primaryStage;
    private Stage primaryStage2;
    public void SetStage(Stage primaryStage) {
    	this.primaryStage = primaryStage;
    }
    public void SetStage2(Stage primaryStage) {
    	this.primaryStage2 = primaryStage;
    }
    public  String MyId;
    public  String MyId2;
    public  String MyName;
    public void SetMyId(String MyId) {
    	this.MyId=MyId;
    }
    public void SetMyName(String name) {
    	this.MyName=name;
    }
    public void SetText(String name,String id,String phone,String birth) {
    	this.laId.setText(id);
    	this.laName.setText(name);
    	this.laPhone.setText(phone);
    	this.laBirth.setText(birth);
    }
    public void SetText2(String id) {
    	this.findId.setText(id);
    }

    
    public void login(ActionEvent event) throws Exception{
    	int result =0;
    	String jdbcUrl = "jdbc:mysql://localhost:3306/trail?allowPublicKeyRetrieval=true&serverTimezone=UTC&SSL=false&useSSL=false";
	    String dbId = "root";
		String dbPw = "root";	  
		System.out.println("DB Loading...");
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		if(txtUserName.getText().isEmpty() || txtPassword.getText().isEmpty()) {
			
			lblStatus.setText("Login Faild");
		}
		try {
			conn = DriverManager.getConnection(jdbcUrl,dbId,dbPw);
			System.out.println("DB Loading Success!!");
			String query = "select * from `member` ";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String id=rs.getString(2);
				String pass=rs.getString(3);
				String name=rs.getString(1);
				if(txtUserName.getText().equals(id) && txtPassword.getText().equals(pass)) {	
				    this.MyName=name;
				    this.MyId2=id;
					result=1;
					lblStatus.setText("Login Success");
				}
			}
			
			if(result==1) {
					Stage loginStage = new Stage();
					loginStage.setTitle("TrainTicketBooking");
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/SceneBuilder/Menu.fxml"));
					
					Parent root = loader.load();
					Scene scene = new Scene(root);
					
					Menu_controller menu = loader.getController();
					menu.SetText(MyName);
					menu.SetId(MyId2);
		            menu.setstage(loginStage);
		       
					scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				
					loginStage.setScene(scene);
					loginStage.show();
			
					primaryStage.close();
				}else{
					lblStatus.setText("Login Failed");
					txtUserName.setText("");
					txtPassword.setText("");
			
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
        
    }
    
    public void ShowSignUp(ActionEvent event) throws Exception	
    {
    	Stage joinstage = new Stage();
    	joinstage.setTitle("TrainTicketBooking");
		FXMLLoader loader=new FXMLLoader(getClass().getResource("/SceneBuilder/join.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root,600,400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		DataController data =loader.getController();
		data.Set_Stage(joinstage);
		joinstage.setScene(scene);
		joinstage.show();
    }
    
    
    
    public void ShowFind_ID(ActionEvent event) throws Exception	
    {
    	Stage find = new Stage();
    	find.setTitle("TrainTicketBooking");
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/SceneBuilder/Find_ID.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        DataController da=loader.getController();
        da.Set_Stage(find);
        find.setScene(scene);
        find.show();
    }
    
    
    
  
    public void OutInfo(ActionEvent event) throws Exception{
    	 
    	Stage re = new Stage();
    	re.setTitle("TrainTicketBooking");
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/SceneBuilder/Menu.fxml"));
    	Parent root = loader.load();	
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        Menu_controller me=loader.getController();
        me.SetText(MyName);
        me.SetId(MyId);
        me.setstage(re);
        primaryStage2.close();
        
        re.setScene(scene);
        re.show();
    }
   
    public void showDelete(ActionEvent event) throws Exception{
    	
    	Stage delete = new Stage();
    	delete.setTitle("TrainTicketBooking");
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/SceneBuilder/Delete.fxml"));
    	Parent root = loader.load();	
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        MainController m=loader.getController();
        m.SetStage(delete);
        m.SetMyId(MyId);
        m.SetStage2(primaryStage2);
        delete.setScene(scene);
        delete.show();
    }
    @SuppressWarnings("resource")
	public void Delete(ActionEvent event) throws Exception {
    	//primaryStage.close();
    	int count=0;
    	String pass=password.getText();
  
    	@SuppressWarnings("unused")
		String sqlpass=null;
    	String jdbcUrl = "jdbc:mysql://localhost:3306/trail?allowPublicKeyRetrieval=true&serverTimezone=UTC&SSL=false&useSSL=false";
		  String dbId = "root";
		  String dbPw = "root";	  
		  System.out.println("DB Loading...");
		  Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
			conn = DriverManager.getConnection(jdbcUrl,dbId,dbPw);
			System.out.println("DB loading success!!");
			String query ="select *from `member` where id = "+"'"+MyId +"'";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				count++;
				sqlpass=rs.getString("password");
			}
			if(count!=0) {
				query ="delete from `member` where password = "+"'"+pass+"'";
				pstmt = conn.prepareStatement(query);
				pstmt.executeUpdate();
			}
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
		
		if(count!=0&&suout.isPressed()==false) {
			primaryStage.close();
			primaryStage2.close();
			
			Stage main = new Stage();
			main.setTitle("TrainTicketBooking");
			FXMLLoader loader=new FXMLLoader(getClass().getResource("/SceneBuilder/Main.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			main.setScene(scene);
			main.show();
		}
	}
    
 
}

