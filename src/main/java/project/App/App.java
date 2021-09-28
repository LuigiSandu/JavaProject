package project.App;

import org.hibernate.Session;
import javafx.application.Application;
import javafx.stage.Stage;
import project.Stage.StageService;
import project.hibernate.util.HibernateUtil;

public class App extends Application {
	@Override
	public void start(Stage stage) {
		StageService stageService = new StageService();
		stageService.startStage(stage);
		
	}

	
	public static void main(String args[]) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.close();
		
		
		launch(args);
	
}
}