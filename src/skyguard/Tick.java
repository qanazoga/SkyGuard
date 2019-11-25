package skyguard;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import skyguard.entities.Enemy;
import skyguard.entities.Entity;
import skyguard.entities.Explosion;
import skyguard.entities.Player;

/**
 * @author qanazoga
 * @version 12/13/16
 */
public class Tick {
	
	public Tick() {
		// Tick timeline.
		Timeline tick = new Timeline(new KeyFrame(
			Duration.millis(25),
			ae -> 	
			{ // Do everything in here every tick.
				SkyGuard.time++;
				if (SkyGuard.time%50 == 0  && Player.alive) SkyGuard.score += 5; Enemy.considerSpawn();
				SkyGuard.scoreLabel.setText("SCORE: " + SkyGuard.score);
				SkyGuard.highScoreLabel.setText("HIGH SCORE: " + SkyGuard.highScore);
				
				for (Entity entity : SkyGuard.entities) {
					entity.onTick();
					if (Player.alive) Entity.detectCollisions();
				}
    	
				SkyGuard.playfield.getChildren().removeAll(SkyGuard.destroyed);
				SkyGuard.entities.removeAll(SkyGuard.destroyed);
				
				for (Node enemy : SkyGuard.enemies) {
					if (SkyGuard.destroyed.contains(enemy)&&SkyGuard.score > 5) new Explosion(enemy.getLayoutX(), enemy.getLayoutY());
				}
		        	
				SkyGuard.enemies.removeAll(SkyGuard.destroyed);
				SkyGuard.friendlies.removeAll(SkyGuard.destroyed);
				SkyGuard.destroyed.clear();

				if (SkyGuard.score < 0) {
					SkyGuard.scoreLabel.setText("SCORE: " + SkyGuard.score);
	        		SkyGuard.scoreLabel.setTextFill(Color.RED);
	        		Player.alive = false;
	        		SkyGuard.destroyed.add(SkyGuard.player);
		        }
        	
	        	if (SkyGuard.score > SkyGuard.highScore) {
	        		SkyGuard.highScore = SkyGuard.score;
	        		SkyGuard.highScoreLabel.setTextFill(Color.LIME);
	        	}
   
	        	if (!Player.alive) {
	        		if (SkyGuard.highScore > 0) SkyGuard.scorePane.setRight(SkyGuard.highScoreLabel);		
	        		if (SkyGuard.score > 0) SkyGuard.scoreLabel.setText("FINAL SCORE: " + SkyGuard.score);
	        		SkyGuard.scorePane.setCenter(SkyGuard.instructions);
	        	}
	        }));
			tick.setCycleCount(Animation.INDEFINITE);
			tick.play();
		}
	}

