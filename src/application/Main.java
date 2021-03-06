package application;
    
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
 
 
public class Main extends Application {		// 프로그램 시작 및 로그인 화면
	
	
    @Override
    public void start(Stage primaryStage) {
        try {
        	primaryStage.setTitle("TrainTicketBooking");
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/SceneBuilder/Main.fxml"));
        	
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            
           
            MainController maincontroller = loader.getController();
            maincontroller.SetStage(primaryStage);
            
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
