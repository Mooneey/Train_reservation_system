package application;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Reserve_Demo implements Initializable{	// 배차 조회 기능
private Stage primaryStage3;
	private String MyName;
	private String MyId;
	public void SetStage(Stage primaryStage) {	
		this.primaryStage3 = primaryStage;
	}
	
	public void SetName(String name) {		// 이름 설정
		this.MyName=name;
	}
	public void SetId(String id) {			// ID 설정
		this.MyId=id;
	}
	@FXML
	private DatePicker datepicker;			// JavaFX의 날짜 선택
	@FXML
	private ComboBox<String> departureBox;	// 출발지 선택
	@FXML
	private ComboBox<String> destinationBox;	// 도착지 선택
	@FXML
	private Button listBtn;	// 조회하기 버튼
	@FXML
	private Button back1;	// 뒤로 가기 버튼

	@FXML
	private ListView<String> listV;		// 버스 시간에 대한 리스트
	
	@FXML
	private void backToManu(ActionEvent event) throws IOException{	// 뒤로가기 버튼 이벤트
		Stage loginStage = new Stage();
		loginStage.setTitle("TrainTicketBooking");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/SceneBuilder/Menu.fxml"));
		
		Parent root = loader.load();
		Scene scene = new Scene(root);
		
		Menu_controller menu = loader.getController();
		menu.SetText(MyName);
		menu.SetId(MyId);
        menu.setstage(loginStage);
   
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	
		loginStage.setScene(scene);
		loginStage.show();
		primaryStage3.close();
		
	}
	public void ShowChange(ActionEvent event)throws IOException{
		switch(departureBox.getValue()) {
		case "오송":
			ObservableList<String> destionatinList= FXCollections.observableArrayList("서울","대전","동대구","부산");
			destinationBox.setItems(destionatinList);
			break;
		case "서울":
			ObservableList<String> destionatinList2= FXCollections.observableArrayList("오송","대전","동대구","부산");
			destinationBox.setItems(destionatinList2);
			break;
		case "대전":
			ObservableList<String> destionatinList3= FXCollections.observableArrayList("서울","오송","동대구","부산");
			destinationBox.setItems(destionatinList3);
			break;
		case "동대구":
			ObservableList<String> destionatinList4= FXCollections.observableArrayList("서울","오송","대전","부산");
			destinationBox.setItems(destionatinList4);
			break;
		case "부산":
			ObservableList<String> destionatinList5= FXCollections.observableArrayList("서울","오송","대전","동대구");
			destinationBox.setItems(destionatinList5);
			break;			
		}
	}
	@FXML
	private void showList(ActionEvent event) throws Exception {
		System.out.println(datepicker.getValue());
		System.out.println(departureBox.getValue());
		System.out.println(destinationBox.getValue());
		String dep=departureBox.getValue();
		String des=destinationBox.getValue();
				
		String jdbcUrl = "jdbc:mysql://localhost:3306/trail?allowPublicKeyRetrieval=true&serverTimezone=UTC&SSL=false&useSSL=false";
	    String dbId = "root";
		String dbPw = "root";	
		
		System.out.println("DB Loading...");
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ObservableList<String> list = FXCollections.observableArrayList();
		try {
			conn = DriverManager.getConnection(jdbcUrl,dbId,dbPw);
			System.out.println("DB Loading Success!!");
			String query ="select *from info_1 where start = "+"'"+dep+"' and end = '"+des+"'";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				if(dep.equals(rs.getString("start"))&&des.equals(rs.getString("end"))) {
					list.add(rs.getString(4)+"           "+rs.getString(5)+"           "+rs.getString(7)+"           "+rs.getString(6));
				}
			}
			listV.setItems(list);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		
	}
	@FXML
	private void Search_Act(ActionEvent event) throws Exception{
		
		listV.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		String starttime=listV.getSelectionModel().getSelectedItem().substring(0, 5);
		String udeparture = departureBox.getValue();
        String udestinaion = destinationBox.getValue();
        String udate = datepicker.getValue().toString();
        String price=null;
        int seatnum = 0;
        
        String jdbcUrl = "jdbc:mysql://localhost:3306/trail?allowPublicKeyRetrieval=true&serverTimezone=UTC&SSL=false&useSSL=false";
          // MySQL id
          String dbId = "root";
          // MySQL password
          String dbPw = "root";     
          System.out.println("DB Loading...");
      	Class.forName("com.mysql.cj.jdbc.Driver");
          Connection conn = null;
           PreparedStatement pstmt = null;
           ResultSet rs = null;
           System.out.println(MyId);
           Stage sea = new Stage();
           sea.setTitle("TrainTicketBooking");
      		FXMLLoader loader = new FXMLLoader(getClass().getResource("/SceneBuilder/Seat.fxml"));		
      		 
      		Parent root = loader.load();
      		Scene scene = new Scene(root);
      		Seat_controller sc=loader.getController();
           try {
              conn = DriverManager.getConnection(jdbcUrl,dbId,dbPw);
              System.out.println("DB Loading Success!!");
             
              String query = "select distinct price from info_1 where start= '"+udeparture+"' and end = '"+udestinaion+"'";
              pstmt = conn.prepareStatement(query);
  			  rs = pstmt.executeQuery();
              if(rs.next()) {
            	  price=rs.getString("price");
            	  System.out.println("price"+price);
              }
              
              query="select * from `member` where start= '"+udeparture+"' and destination = '"+udestinaion+"' and time = '"+starttime+"'";
              pstmt = conn.prepareStatement(query);
  			  rs = pstmt.executeQuery();
  			  while(rs.next()) {
  				seatnum = rs.getInt(10);
  				System.out.println(seatnum);
  				switch(seatnum) {
  				case 1:
  					sc.SetImage("file:///D:/JAVA_LAB/Trail/safety-seat.png"); break;
  				case 2:
  					sc.SetImage2("file:///D:/JAVA_LAB/Trail/safety-seat.png"); break;
  				case 3:
  					sc.SetImage3("file:///D:/JAVA_LAB/Trail/safety-seat.png"); break;
  				case 4:
  					sc.SetImage4("file:///D:/JAVA_LAB/Trail/safety-seat.png"); break;
  				case 5:
  					sc.SetImage5("file:///D:/JAVA_LAB/Trail/safety-seat.png"); break;
  				case 6:
  					sc.SetImage6("file:///D:/JAVA_LAB/Trail/safety-seat.png"); break;
  				case 7:
  					sc.SetImage7("file:///D:/JAVA_LAB/Trail/safety-seat.png"); break;
  				case 8:
  					sc.SetImage8("file:///D:/JAVA_LAB/Trail/safety-seat.png"); break;
  				case 9:
  					sc.SetImage9("file:///D:/JAVA_LAB/Trail/safety-seat.png"); break;
  				case 10:
  					sc.SetImage10("file:///D:/JAVA_LAB/Trail/safety-seat.png"); break;
  				case 11:
  					sc.SetImage11("file:///D:/JAVA_LAB/Trail/safety-seat.png"); break;
  				case 12:
  					sc.SetImage12("file:///D:/JAVA_LAB/Trail/safety-seat.png"); break;
  				case 13:
  					sc.SetImage13("file:///D:/JAVA_LAB/Trail/safety-seat.png"); break;
  				case 14:
  					sc.SetImage14("file:///D:/JAVA_LAB/Trail/safety-seat.png"); break;
  				case 15:
  					sc.SetImage15("file:///D:/JAVA_LAB/Trail/safety-seat.png"); break;
  				case 16:
  					sc.SetImage16("file:///D:/JAVA_LAB/Trail/safety-seat.png"); break;
  				case 17:
  					sc.SetImage17("file:///D:/JAVA_LAB/Trail/safety-seat.png"); break;
  				case 18:
  					sc.SetImage18("file:///D:/JAVA_LAB/Trail/safety-seat.png"); break;
  				case 19:
  					sc.SetImage19("file:///D:/JAVA_LAB/Trail/safety-seat.png"); break;
  				case 20:
  					sc.SetImage20("file:///D:/JAVA_LAB/Trail/safety-seat.png"); break;
  				case 21:
  					sc.SetImage21("file:///D:/JAVA_LAB/Trail/safety-seat.png"); break;
  				case 22:
  					sc.SetImage22("file:///D:/JAVA_LAB/Trail/safety-seat.png"); break;
  				case 23:
  					sc.SetImage23("file:///D:/JAVA_LAB/Trail/safety-seat.png"); break;
  				case 24:
  					sc.SetImage24("file:///D:/JAVA_LAB/Trail/safety-seat.png"); break;
  				}
  			  }
  			sc.SetId(MyId);
     		sc.SetStage2(sea);
     		sc.SetPrice(price);
     		sc.SetName(MyName);
     		sc.SetBus(udeparture,udestinaion,starttime,udate);
  			  
              
                    
           } 
           
           catch (Exception e) {
              e.printStackTrace();
           }
               		
     		scene.getStylesheets().add(getClass().getResource("application.css").toString());
     		
     		sea.setScene(scene);
     		sea.show();
     		primaryStage3.close();
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> departureList = FXCollections.observableArrayList("서울","오송","대전","동대구","부산");
		ObservableList<String> destionatinList= FXCollections.observableArrayList("서울","오송","대전","동대구","부산");
		departureBox.setItems(departureList);
		destinationBox.setItems(destionatinList);
	
	}


}
