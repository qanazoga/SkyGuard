package skyguard.entities;

import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import skyguard.SkyGuard;

/**
 * @author qanazoga
 * @version 12/13/16
 */
public class Bullet extends Circle implements Entity {

	TYPE type = TYPE.UNASIGNED;
	public Bullet(TYPE type, int X, int Y) {
		this.type = type;
		this.setRadius(2);
		this.setFill(Color.YELLOW);
		this.setLayoutX(X);
		this.setLayoutY(Y);
		SkyGuard.friendlies.add(this);
		SkyGuard.entities.add(this);
		SkyGuard.playfield.getChildren().add(this);
		AudioClip playerFire = new AudioClip(getClass().getResource("/skyguard/resources/audio/player-fire.wav").toString());
	    playerFire.play();
	}
	
	@Override
	public TYPE getType() {
		return type;
	}

	@Override
	public void onTick() {
		this.setLayoutX(this.getLayoutX() + 20);
		if (this.getLayoutX() > 1366
				|| this.getLayoutX() < 0
				|| this.getLayoutY() > 768
				|| this.getLayoutY() < 0) {
			System.out.println("Destroying out of bounds bullet.");
			SkyGuard.destroyed.add(this);
		}
	}

	@Override
	public void onCollision() {
		if (type == TYPE.FRIENDLY) SkyGuard.score += 50;
		SkyGuard.destroyed.add(this);
	}

}
