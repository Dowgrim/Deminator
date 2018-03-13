package dem.server.net.action.receive.game;

import com.sun.istack.internal.NotNull;
import dem.common.model.*;
import dem.server.net.action.AServerCmdReceive;
import dem.server.model.ServerGrid;
import dem.server.net.action.ServerCmdSender;

import java.util.List;

/**
 * MOV direction
 * received by SERVER
 */
public class CmdReceiveMov extends AServerCmdReceive {
	@Override
	public void receive(ServerCmdSender cmdSender, ServerGrid grid, int playerId, List<String> params) {
		// MOV direction
		Direction direction = Direction.valueOf(params.remove(0).trim());

		if(params.size() > 0) {
			// TODO Error
		}

		Player p = grid.getPlayer(playerId);
		int[] newLocation = grid.getLocationOf(p);
		int newX = direction.decal[0]+newLocation[0];
		int newY = direction.decal[1]+newLocation[1];

		if(!grid.isCorrectLocation(newX, newY)) {
			// TODO FAIL: wall
		} else if(grid.isBombOn(newX, newY)) {
			// TODO FAIL: bomb on the place
		} else if(grid.isPlayerOn(newX, newY)) {
			// TODO FAIL: another player on the place
		} else {
			grid.moveElementTo(p, newX, newY);
			// TODO SUCCESS: send info to others players
		}
	}
}
