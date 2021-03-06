package game.networking;

import game.Main;
import game.models.messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerListener extends Thread {
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    public ServerListener(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

            while (socket.isConnected()) {
                Message message = (Message) input.readObject();
                Main.onMessageReceivedFromClient(message, output);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
