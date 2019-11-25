package skyguard;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import skyguard.entities.Entity;
import skyguard.entities.Player;

/**
 * @author qanazoga
 * @version 12/13/16
 */
public class SkyGuard extends Application {
	
	public static Stage stage;
	public static Pane playfield;
	public static ArrayList<Entity> entities = new ArrayList<>();
	public static ArrayList<Entity> destroyed = new ArrayList<>();
	public static ArrayList<Node> friendlies = new ArrayList<>();
	public static ArrayList<Node> enemies = new ArrayList<>();
	public static AudioClip backgroundMusic;
	public static long score = 0;
	public static long time = 0;
	public static long highScore = 0;
	public static Player player;
	public static int chance;
	public static BorderPane scorePane = new BorderPane();
	public static Label scoreLabel = new Label();
	public static Label highScoreLabel = new Label();
	public static Label instructions = new Label();
	public static Scene mainScene;
	public static Controller controller;
	public static Tick tick;
	public static Scene credits;
	
	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		
		playfield = new Pane();
		mainScene = new Scene(playfield, 1366, 768);
		scoreLabel.setTextFill(Color.WHITE);
		scoreLabel.setFont(new Font("Lucida Console", 36));
		highScoreLabel.setTextFill(Color.WHITE);
		highScoreLabel.setFont(new Font("Lucida Console", 36));
		instructions.setTextAlignment(TextAlignment.CENTER);
		instructions.setText("\nSKYGUARD!\n\nARROW KEYS TO MOVE\nSPACE TO FIRE\nENTER TO START\nC FOR CREDITS\n\nDESTORY ENEMY +200 POINTS\nENEMY ESCAPE -2500 POINTS\nOUT OF POINTS: GAME OVER");
		instructions.setTextFill(Color.WHITE);
		instructions.setFont(new Font("Lucida Console", 36));
		
		// building the credit window
		BorderPane creditPane = new BorderPane();
		credits = new Scene(creditPane, 1366, 768);
		credits.setOnKeyPressed(e -> {
			switch (e.getCode()) {
			case ESCAPE: stage.setScene(mainScene); break;
			default: break;
			}
		});
		creditPane.setPrefSize(1366, 768);
		
		Label titleLabel = new Label("SkyGuard!");
		titleLabel.setTextFill(Color.WHITE);
		titleLabel.setFont(new Font("Lucida Console", 36));
		creditPane.setTop(titleLabel);
		
		Label escapeInstruct = new Label("Press ESCAPE to return to menu.");
		escapeInstruct.setTextFill(Color.WHITE);
		escapeInstruct.setFont(new Font("Lucidia Console", 27));
		
		Label clickInstruct = new Label("Click a creator to go to their site!");
		clickInstruct.setTextFill(Color.WHITE);
		clickInstruct.setFont(new Font("Lucidia Console", 27));
		clickInstruct.setAlignment(Pos.BOTTOM_RIGHT);
		
		BorderPane creditBottom = new BorderPane();
		creditBottom.setMinWidth(1366);
		creditBottom.setLeft(escapeInstruct);
		creditBottom.setRight(clickInstruct);
		creditPane.setBottom(creditBottom);
		
		VBox creditList = new VBox(25);
		creditPane.setCenter(creditList);
		
		HBox qanazogaCredit = new HBox(150);
		Label qanazogaLabel = new Label("qanazoga");
		qanazogaLabel.setTextFill(Color.WHITE);
		qanazogaLabel.setFont(new Font("Lucidia Console", 36));
		qanazogaLabel.setOnMouseClicked(e -> getHostServices().showDocument("http://qanazoga.github.io"));
		Label qanazogaWork = new Label("Programming / Sprite Editing");
		qanazogaWork.setTextFill(Color.WHITE);
		qanazogaWork.setFont(new Font("Lucidia Console", 36));
		qanazogaCredit.getChildren().addAll(new Label(), qanazogaLabel, new Label(), qanazogaWork);
		
		HBox citricLilyCredit = new HBox(150);
		Label citricLilyLabel = new Label("citricLily");
		citricLilyLabel.setTextFill(Color.WHITE);
		citricLilyLabel.setFont(new Font("Lucidia Console", 36));
		citricLilyLabel.setOnMouseClicked(e -> getHostServices().showDocument("http://citricLily.deviantart.com/"));
		ImageView citricLilyWork = new ImageView(new Image(getClass().getResource("/skyguard/resources/images/entity_ship.gif").toExternalForm()));
		citricLilyCredit.getChildren().addAll(new Label(), citricLilyLabel, new Label("       "), citricLilyWork);
		
		HBox metalowyMetalowiecCredit = new HBox(150);
		Label metalowyMetalowiecLabel = new Label("metalowy-metalowiec");
		metalowyMetalowiecLabel.setTextFill(Color.WHITE);
		metalowyMetalowiecLabel.setFont(new Font("Lucidia Console", 36));
		metalowyMetalowiecLabel.setOnMouseClicked(e -> getHostServices().showDocument("http://metalowy-metalowiec.deviantart.com/"));
		ImageView metalowyMetalowiecWork = new ImageView(new Image(getClass().getResource("/skyguard/resources/images/entity_ufo.gif").toExternalForm()));
		metalowyMetalowiecCredit.getChildren().addAll(new Label(), metalowyMetalowiecLabel, metalowyMetalowiecWork);

		HBox explosionCredit = new HBox(150);
		Label explosionLabel = new Label("bestanimations.com");
		explosionLabel.setTextFill(Color.WHITE);
		explosionLabel.setFont(new Font("Lucidia Console", 36));
		explosionLabel.setOnMouseClicked(e -> getHostServices().showDocument("http://bestanimations.com/"));
		ImageView explosionWork = new ImageView(new Image(getClass().getResource("/skyguard/resources/images/explosion.gif").toExternalForm()));
		explosionWork.setPreserveRatio(true); explosionWork.setFitHeight(100);
		explosionCredit.getChildren().addAll(new Label(), explosionLabel, explosionWork);
		
		HBox adhesiveWombatCredit = new HBox(150);
		Label adhesiveWombatLabel = new Label("AdhesiveWombat");
		adhesiveWombatLabel.setTextFill(Color.WHITE);
		adhesiveWombatLabel.setFont(new Font("Lucidia Console", 36));
		adhesiveWombatLabel.setOnMouseClicked(e -> getHostServices().showDocument("https://soundcloud.com/adhesivewombat"));
		Label adhesiveWombatWork = new Label("Music (8-Bit Adventure)");
		adhesiveWombatWork.setTextFill(Color.WHITE);
		adhesiveWombatWork.setFont(new Font("Lucidia Console", 36));
		adhesiveWombatCredit.getChildren().addAll(new Label(), adhesiveWombatLabel, adhesiveWombatWork);
		
		HBox hamsterRepublicCredit = new HBox(150);
		Label hamsterRepublicLabel = new Label("Hamster Republic");
		hamsterRepublicLabel.setTextFill(Color.WHITE);
		hamsterRepublicLabel.setFont(new Font("Lucidia Console", 36));
		hamsterRepublicLabel.setOnMouseClicked(e -> getHostServices().showDocument("http://hamsterrepublic.com/"));
		Label hamsterRepublicWork = new Label("All other sounds");
		hamsterRepublicWork.setTextFill(Color.WHITE);
		hamsterRepublicWork.setFont(new Font("Lucidia Console", 36));
		hamsterRepublicCredit.getChildren().addAll(new Label(), hamsterRepublicLabel, hamsterRepublicWork);
		
		creditList.getChildren().addAll(new Label(), qanazogaCredit, citricLilyCredit, metalowyMetalowiecCredit, explosionCredit, adhesiveWombatCredit, hamsterRepublicCredit);
		
		// Sacrifice your soul to The Dark Gods of CSS.
		playfield.setStyle(
			"-fx-background-image: url('/skyguard/resources/images/background_tile.gif'); " +
			"-fx-background-position: center center; " +
			"-fx-background-repeat: repeat;"
		);	
		creditPane.setStyle(
			"-fx-background-image: url('/skyguard/resources/images/background_tile.gif'); " +
			"-fx-background-position: center center; " +
			"-fx-background-repeat: repeat;"
		);
		// The Sacrifice is complete.
		
		// Start the background music
		backgroundMusic = new AudioClip(getClass().getResource("/skyguard/resources/audio/8-Bit-Adventure.wav").toString());
	    backgroundMusic.setCycleCount(AudioClip.INDEFINITE);
	    
	    // Build the window.
	    primaryStage.setOnCloseRequest(e -> System.exit(0));
		primaryStage.setTitle("SKYGUARD!");
		primaryStage.setScene(mainScene);
		primaryStage.setResizable(false);
		playfield.getChildren().add(scorePane);
		scorePane.setPrefWidth(1366);
		primaryStage.show();
		backgroundMusic.play();
		
		Player.alive = false;
		controller = new Controller(mainScene);
		tick = new Tick();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static void reset() {
		if (!Player.alive) {
			SkyGuard.time = 0;
			SkyGuard.score = 5;
			
			for (Entity entity : entities) {
				destroyed.add(entity);
			}
			
			SkyGuard.player = new Player();
			
			if (!backgroundMusic.isPlaying()) backgroundMusic.play();
			
			scorePane.setCenter(null);
			scoreLabel.setTextFill(Color.WHITE);
			highScoreLabel.setTextFill(Color.WHITE);
			scorePane.setLeft(scoreLabel);
			chance = 125;
		}
	}
}
