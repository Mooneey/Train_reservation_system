package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Confirm implements Initializable{	// 승차권 예매 확인 클래스
	private Stage primaryStage2;
	public String start;
	public String end;
	public String date;
	public String time;
	public String seatNum;
	public String MyId;
	public void SetBus(String start,String end, String date,String day) {
		this.start=start;
		this.end=end;
		this.time=date;
		this.date=day;
	}
	public void SetId(String id) {
		this.MyId=id;
	}
	public void SetSeat(String seatnum) {
		this.seatNum=seatnum;
	}
	public void SetStage(Stage primaryStage) {
		this.primaryStage2 = primaryStage;
	}
	@FXML
	private Button backBtn;
	@FXML private Label Start;
	@FXML private Label End;
	@FXML private Label Date;
	@FXML private Label Time;
	@FXML private Label Seatnum;
	
	
	public void SetText(String start,String end,String date,String time,String seatnum) {
		this.Start.setText(start);
		this.End.setText(end);
		this.Date.setText(date);
		this.Time.setText(time);
		this.Seatnum.setText(seatnum);
	}
	@FXML
	private void back_Act(ActionEvent event) throws IOException{
		primaryStage2.close();
	}

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {}

}
