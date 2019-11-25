package skyguard.entities;

import javafx.scene.Node;
import skyguard.SkyGuard;

/**
 * @author qanazoga
 * @version 12/13/16
 */
public interface Entity{
	
	enum TYPE {
		UNASIGNED, FRIENDLY, ENEMY
	}
	
    public TYPE getType();
    public void onTick();
    public void onCollision();
    

	public static void detectCollisions() {
		for (Node friendly : SkyGuard.friendlies) {
			for (Node enemy : SkyGuard.enemies) {
				if (friendly.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
					((Entity) friendly).onCollision();
					((Entity) enemy).onCollision();
				}
			}
		}
	}
}
