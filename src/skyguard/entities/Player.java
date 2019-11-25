package skyguard.entities;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;
import skyguard.SkyGuard;

/**
 * @author qanazoga
 * @version 12/13/16
 */
public class Player extends ImageView implements Entity {

	public static boolean alive = true;
	public static ArrayList<KeyCode> movements = new ArrayList<>();
	private TYPE type = TYPE.UNASIGNED;
	
	public Player() {
		this.setImage(new Image(getClass().getResource("/skyguard/resources/images/entity_ship.gif").toExternalForm()));
		this.setLayoutX(100);
		this.setLayoutY(384 - (int) this.getScaleY());
		this.setFitHeight(68);
		this.setFitWidth(94);
		SkyGuard.playfield.getChildren().add(this);
		SkyGuard.entities.add(this);
		SkyGuard.friendlies.add(this);
		alive = true;
		type = TYPE.FRIENDLY;
	}
	
	@Override
	public TYPE getType() {
		return type;
	}

	@Override
	public void onTick() {
		if (movements.contains(KeyCode.UP) && (this.getLayoutY() > 0)) {
			this.setLayoutY(this.getLayoutY() - 10);
		}
		if (movements.contains(KeyCode.DOWN) && (this.getLayoutY() < 768 - this.getFitHeight())) {
			this.setLayoutY(this.getLayoutY() + 10);
		}
		if (movements.contains(KeyCode.LEFT) && (this.getLayoutX() > 0)) {
			this.setLayoutX(this.getLayoutX() - 10);
		}
		if (movements.contains(KeyCode.RIGHT) && (this.getLayoutX() < 1366 - this.getFitWidth())) {
			this.setLayoutX(this.getLayoutX() + 10);
		}
	}
	
	@Override
	public void onCollision() {
		alive = false;
		SkyGuard.destroyed.add(this);
		SkyGuard.backgroundMusic.stop();
		AudioClip playerDestroyed = new AudioClip(getClass().getResource("/skyguard/resources/audio/player-destroyed.wav").toString());
		playerDestroyed.play();
	}

	public ArrayList<KeyCode> getMovements() {
		return movements;
	}

	public void fire() {
		if (alive) new Bullet(this.type, (int) this.getLayoutX() + (int) this.getFitWidth(), (int) this.getLayoutY() + (int) this.getFitHeight() / 2);
	}
}
