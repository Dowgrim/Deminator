package dem.net.server.actions.receive.game;

import com.sun.istack.internal.NotNull;
import dem.model.GameCharacter;
import dem.model.GridNew;
import dem.net.common.CmdReceiver;
import dem.util.Direction;

import java.util.List;

/**
 * MOV direction
 * received by SERVER
 */
public class CmdReveiveMov extends CmdReceiver {
	@Override
	public void receive(List<String> params) {
		// MOV direction
		Direction direction = Direction.valueOf(params.remove(0).trim());

		if(params.size() > 0) {
			// TODO Error
		}

		// TODO obtain information according to context
		receive(null, null, direction);
	}

	private void receive(@NotNull GridNew grid, @NotNull GameCharacter c, @NotNull Direction d) {
		int[] newLocation = c.getLocation(d.decal);

		if(!grid.isCorrectLocation(newLocation[0], newLocation[1])) {
			// TODO FAIL: wall
		} else if(grid.isBombOn(newLocation[0], newLocation[1])) {
			// TODO FAIL: bomb on the place
		} else if(grid.isPlayerOn(newLocation[0], newLocation[1])) {
			// TODO FAIL: another player on the place
		} else {
			c.moveTo(newLocation[0], newLocation[1]);
			// TODO SUCCESS: send info to others players
		}
	}
}
