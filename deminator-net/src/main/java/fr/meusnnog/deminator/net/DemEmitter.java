package fr.meusnnog.deminator.net;

/**
 * @author Michael Eusebe, Nathaël Noguès
 * @since 2016-04-29
 */
public interface DemEmitter {
	String getCommandName();

	String buildCommand();

	default boolean isBroadcast() {
		return false;
	}
}
