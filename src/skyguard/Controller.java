package skyguard;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import skyguard.entities.Player;

/**
 * @author qanazoga
 * @version 12/13/16
 */
public class Controller {

	Scene scene;
	
	public Controller(Scene scene) {
		this.scene = scene;
		scene.setOnKeyPressed(e -> {
			switch (e.getCode()) {
			case C: 
				if (!Player.alive) SkyGuard.stage.setScene(SkyGuard.credits);
			case UP:
			case DOWN:
			case LEFT:
			case RIGHT: 
				if (!Player.movements.contains(e.getCode())) {
					Player.movements.add(e.getCode()); 
				} break; 
			case ENTER:
				SkyGuard.reset();
				break;
			default: 
				break;
			}			
		});
		
		scene.setOnKeyReleased(e -> {
			if (Player.movements.contains(e.getCode())) Player.movements.remove(e.getCode()); // Remove any movement once the key is released.
			if (e.getCode().equals(KeyCode.SPACE) && Player.alive) SkyGuard.player.fire(); // Fire is on key release so it's harder to spam.
		});
	}
}
