package dem.server.net.action;

import dem.common.net.CmdSender;
import dem.server.ServerDem;
import dem.server.model.Player;

import java.util.ArrayList;
import java.util.List;

public class ServerCmdSender extends CmdSender {
	private final ServerDem server;

	public ServerCmdSender(ServerDem server) {
		this.server = server;
	}

	private void sendToAllPlayers(String cmdId, String ... parameters) {
		send(buildCommand(cmdId, parameters), "TODO" /* TODO */);
	}
	private void sendToSpecificPlayer(String playerIdentifier /* TODO */, String cmdId, String ... parameters) {
		send(buildCommand(cmdId, parameters), "TODO" /* TODO */);
	}

	public void quit(String playerIdentifier /* TODO */) {
		sendToAllPlayers("QUIT", playerIdentifier);
	}
	public void cmdNew(Player p) {
		sendToAllPlayers("NEW", p.getName(), Integer.toString(p.getColorHue()));
	}
	public void ping(Player p) {
		sendToSpecificPlayer(p.getName(), "PING");
	}
	public void lag(Player p, int value) {
		sendToAllPlayers("LAG", p.getName(), Integer.toString(value));
	}
	public void err(String playerIdentifier /* TODO */, String description) {
		sendToSpecificPlayer(playerIdentifier, "ERR", description);
	}
	public void rdy(Player p){
		sendToAllPlayers("RDY", p.getName() /* TODO identifier */, p.isReady()?"Y":"N");
	}
	public void stn() {
		// TODO
	}
	public void shd() {
		// TODO
	}
	public void opt(String optId, Object optValue) {
		sendToAllPlayers("OPT", optId, optValue.toString());
	}
	public void ldd() {

		sendToAllPlayers("LDD");
	}
	public void mov(Player p){
		int[] loc = p.getCharacter().getLocation();
		sendToAllPlayers("MOV", p.getName() /* TODO identifier */, Integer.toString(loc[0]), Integer.toString(loc[1]));
	}
	public void dis(Player p, int cellValue){
		int[] loc = p.getCharacter().getLocation();
		sendToSpecificPlayer(p.getName(),"DIS", Integer.toString(loc[0]), Integer.toString(loc[1]), Integer.toString(cellValue));
	}
	public void pts(Player p) {
		sendToAllPlayers("PTS", p.getName() /* TODO identifier */, Integer.toString(p.getCharacter().getPts()));
	}
	public void exp(boolean areExploding, int[] ... explodingCells) {
		List<String> params = new ArrayList<>();

		params.add(areExploding?"Y":"N");

		for(int[] loc : explodingCells) {
			params.add(Integer.toString(loc[0]));
			params.add(Integer.toString(loc[1]));
		}

		sendToAllPlayers("EXP", params.toArray(new String[params.size()]));
	}

	public void end() {
		sendToAllPlayers("END");
	}
}
