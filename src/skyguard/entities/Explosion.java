package skyguard.entities;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import skyguard.SkyGuard;

/**
 * @author qanazoga
 * @version 12/13/16
 */
public class Explosion extends ImageView implements Entity {

	long createdTime;
	
	public Explosion(double x, double y) {
		this.setImage(new Image(getClass().getResource("/skyguard/resources/images/explosion.gif").toExternalForm()));
		this.setLayoutX(x - 75);
		this.setLayoutY(y - 115);
		this.createdTime = SkyGuard.time;
		SkyGuard.playfield.getChildren().add(this);
		SkyGuard.entities.add(this);
	}
	
	@Override
	public TYPE getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onTick() {			
		if (SkyGuard.time > createdTime +40) SkyGuard.playfield.getChildren().remove(this);
	}

	@Override
	public void onCollision() {
		
	}

}
