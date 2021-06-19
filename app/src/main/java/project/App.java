package project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import project.client.ClientExecutor;
import project.server.ServerExecutor;

public class App {

    private static final String HOST = "localhost";
    private static final int PORT = 8888;
    private static String SERVER_MODE = "server";
    private static String CLIENT_MODE = "client";

    private void execute(String mode) {
        if (SERVER_MODE.equals(mode)) {
            new ServerExecutor(PORT).execute();
        }
        if (CLIENT_MODE.equals(mode)) {
            new ClientExecutor(HOST, PORT).execute();
        }
    }

    private static String getMode() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter mode [server, client]: ");
        String mode = in.readLine();
        validateMode(mode);
        return mode;
    }

    private static boolean validateMode(String mode) {
        if (SERVER_MODE.equals(mode) || CLIENT_MODE.equals(mode)) {
            return true;
        }
        throw new IllegalArgumentException("Unknown mode: " + mode); 
    }

    public static void main(String[] args) throws IOException {
        String mode = getMode();
        new App().execute(mode);
    }
}
