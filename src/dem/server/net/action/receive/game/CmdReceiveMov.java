package dem.server.net.action.receive.game;

import com.sun.istack.internal.NotNull;
import dem.common.Direction;
import dem.common.net.CmdReceiver;
import dem.server.model.GameCharacter;
import dem.server.model.Grid;
import dem.server.model.Player;
import dem.server.net.action.ServerCmdSender;

import java.util.List;

/**
 * MOV direction
 * received by SERVER
 */
public class CmdReceiveMov extends CmdReceiver {
	@Override
	public void receive(ServerCmdSender cmdSender, Grid grid, Player emitter, List<String> params) {
		// MOV direction
		Direction direction = Direction.valueOf(params.remove(0).trim());

		if(params.size() > 0) {
			// TODO Error
		}

		// TODO obtain information according to context
		receive(null, null, direction);
	}

	private void receive(@NotNull Grid grid, @NotNull GameCharacter c, @NotNull Direction d) {
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
