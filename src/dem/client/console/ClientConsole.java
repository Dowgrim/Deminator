package dem.client.console;

import dem.client.ClientDem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientConsole extends ClientDem {
    private final List<String> logs = new ArrayList<>();

    private ClientConsole() {
        Scanner sc = new Scanner(System.in);
    }


    public static void main(String[] args) {
        new ClientConsole();
	}


    @Override
    public void log(String s) {
        logs.add(s);
    }
}
