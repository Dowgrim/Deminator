package dem.net.server.actions;

import dem.model.GameCharacter;

import java.util.ArrayList;
import java.util.List;

public abstract class ServerCmdSender {
	private static final String SEPARATOR = " ";

	private static void sendToAllPlayers(String cmdId, String ... parameters) {
		String command = buildCommand(cmdId, parameters);
		/* TODO do send message trough network here */
	}
	private static void sendToSpecificPlayer(String playerIdentifier /* TODO */, String cmdId, String ... parameters) {
		String command = buildCommand(cmdId, parameters);
		/* TODO do send message trough network here */
	}

	private static String buildCommand(String cmdId, String ... parameters) {
		return cmdId + SEPARATOR + String.join(SEPARATOR, parameters);
	}



	public static void quit(String playerIdentifier /* TODO */) {
		sendToAllPlayers("QUIT", playerIdentifier);
	}
	public static void ping() {
		sendToAllPlayers("PING");
	}
	public static void lag(int value) {
		sendToAllPlayers("LAG", Integer.toString(value));
	}
	public static void err(String playerIdentifier /* TODO */, String description) {
		sendToSpecificPlayer(playerIdentifier, "ERR", description);
	}
	public static void rdy(GameCharacter c){
		sendToAllPlayers("RDY", c.getName() /* TODO identifier */, c.isReady()?"Y":"N");
	}
	public static void stn() {
		// TODO
	}
	public static void shd() {
		// TODO
	}
	public static void opt(String optId, Object optValue) {
		sendToAllPlayers("OPT", optId, optValue.toString());
	}
	public static void ldd() {
		sendToAllPlayers("LDD");
	}
	public static void mov(GameCharacter c){
		int[] loc = c.getLocation();
		sendToAllPlayers("MOV", c.getName() /* TODO identifier */, Integer.toString(loc[0]), Integer.toString(loc[1]));
	}
	public static void dis(GameCharacter c, int cellValue){
		int[] loc = c.getLocation();
		sendToSpecificPlayer(c.getName(),"DIS", Integer.toString(loc[0]), Integer.toString(loc[1]), Integer.toString(cellValue));
	}
	public static void pts(GameCharacter c) {
		sendToAllPlayers("PTS", c.getName() /* TODO identifier */, Integer.toString(c.getPts()));
	}
	public static void makeExplode(boolean areExploding, int[] ... explodingcells) {
		List<String> params = new ArrayList<>();

		for(int[] loc : explodingcells) {
			params.add(Integer.toString(loc[0]));
			params.add(Integer.toString(loc[1]));
		}

		sendToAllPlayers("EXP", areExploding?"Y":"N", String.join(SEPARATOR, params));
	}

	public static void end() {
		sendToAllPlayers("END");
	}
}
