package dem.server.console;

import dem.server.ServerDem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ServerConsole extends ServerDem {
    private static final String STOP = "STOP";
    private final Scanner sc;
    private final List<String> logs = new ArrayList<>();

    private ServerConsole() {
        sc = new Scanner(System.in);

        openingServer();
    }
	public static void main(String[] args) {
		new ServerConsole();
	}


	private void openingServer() {
        int i=-1;
        do try {
            System.out.print("Server port number: ");
            String input = sc.nextLine();
            if(input.equals(STOP)) {
                return;
            }

            i=-1; // default value
            i = Integer.parseInt(input);
            openServer(i);

            // next step
            serverMenu();
        } catch(Exception e) {
            System.err.println("Cannot open server on port "+i+":\n\t"+e.toString()+" "+e.getMessage());
        } while(true);
    }

    private void serverMenu() {
        do try {
            System.out.println("Available commands: ACCEPT, MAX, PING, LAUNCH, READ, "+STOP);
            System.out.print("> ");
            String input = sc.nextLine();
            String[] cmd = input.split(" ");

            if(cmd.length < 1) {
                System.err.println("Found empty command");
                continue;
            }

            switch (cmd[0].toUpperCase()) {
                case STOP: return;
                case "ACCEPT":
                    acceptingClients(cmd);
                    break;
                case "MAX":
                    changingMaxClients(cmd);
                    break;
                case "PING":
                    pingAllClients();
                    break;
                case "LAUNCH":
                    changingMaxClients(cmd);
                    break;
                case "READ":
                    readLogs();
                    break;
                default:
                    System.err.println("Unknown command "+input);
            }

        } catch(Exception e) {
            System.err.println("Problem occurred:\n\t"+e.toString()+" "+e.getMessage());
        } while(true);
    }

    private void readLogs() {
        while(!logs.isEmpty()) {
            String s = logs.remove(0);
            System.out.println(s);
        }
        System.out.println("----- End Of Logs -----");
    }

    private void changingMaxClients(String[] cmd) {
        System.err.println("Not implemented");  // TODO
    }

    private void acceptingClients(String[] cmd) throws IOException {
        if(cmd.length == 1) {
            if(isAcceptingClients()) {
                System.out.println("No more accepting new clients.");
                acceptClients(false);
            } else {
                System.out.println("Accepting new clients.");
                acceptClients(false);
            }
        } else if(cmd.length == 2) {
            boolean b = Boolean.parseBoolean(cmd[1]);

            if(b) {
                System.out.println("Forcing to no more accept new clients.");
                acceptClients(false);
            } else {
                System.out.println("Forcing to accept new clients.");
                acceptClients(false);
            }
        } else {
            System.err.println("Usage: ACCEPT ([true/false])");
        }
    }

    @Override
    public void log(String s) {
        logs.add(new Date().toInstant().toString()+" "+s);
    }
}
