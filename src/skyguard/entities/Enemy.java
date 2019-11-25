package skyguard.entities;

import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import skyguard.SkyGuard;

/**
 * @author qanazoga
 * @version 12/13/16
 */
public class Enemy extends ImageView implements Entity {
	
	TYPE type = Entity.TYPE.UNASIGNED;
	Random rand = new Random();
	int speed = rand.nextInt(5) + 4;
	
	public Enemy() { // #1, understand?
		this.setImage(new Image(getClass().getResource("/skyguard/resources/images/entity_ufo.gif").toExternalForm()));
		this.setLayoutX(1366);
		this.setLayoutY(getStartingY());
		this.type = TYPE.ENEMY;
		SkyGuard.enemies.add(this);
		SkyGuard.entities.add(this);
		SkyGuard.playfield.getChildren().add(this);
		System.out.println("Spawning a new enemy at " + (int) this.getLayoutY() + ".");
	}

	@Override
	public TYPE getType() {
		return type;
	}

	@Override
	public void onTick() {
		this.setLayoutX(this.getLayoutX() - speed);
		if (this.getLayoutX() < -100) {
			if (SkyGuard.chance > 1) SkyGuard.chance--;
			if (Player.alive) SkyGuard.score -= 2500;
			SkyGuard.destroyed.add(this);
		}
	}
	
	@Override
	public void onCollision() {
		SkyGuard.score += 50;
		SkyGuard.destroyed.add(this);
		AudioClip explosionSound = new AudioClip(getClass().getResource("/skyguard/resources/audio/enemy-destroyed.wav").toString());
	    explosionSound.play();
	}

	private int getStartingY() {
		return rand.nextInt(768 - 39);
	}
	
	public static void considerSpawn() {
		Random rand = new Random();
		if (!Player.alive) return;
		if (SkyGuard.time % 50 == 0) {
			if (SkyGuard.chance > 1) SkyGuard.chance--;
			System.out.println("Reducing chance to " + SkyGuard.chance);
		}
		if (SkyGuard.chance >= 1) if (rand.nextInt(SkyGuard.chance) <= 1) new Enemy();
	}
}
