package network.client;

import common.OsUtils;
import main.PushLocal;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by brian on 8/30/15.
 */
public class TCPListener extends Thread {
    public static final int PACKET_SIZE = 1024;
    private Socket socket;
    private NetClient netClient;
    private ConcurrentLinkedQueue<String> logs;
    private InetAddress address;

    public TCPListener(NetClient netClient, ConcurrentLinkedQueue<String> logs, InetAddress address) {
        this.netClient = netClient;
        this.logs = logs;
        this.address = address;
    }

    @Override
    public void run() {
        super.run();

        try {
            socket = new Socket(address, 5577);
            sendMessage("connected");

            byte[] data = new byte[PACKET_SIZE];

            while (netClient.isRunning() && !socket.isClosed()) {
                int len = socket.getInputStream().read(data);
                if (len < 0)
                    break;
                logs.add("Received Message from " + socket.getInetAddress().getHostName());
                handleMessage(data);
                data = new byte[PACKET_SIZE];
                Thread.sleep(0);
            }
        } catch (IOException | InterruptedException e) {
            logs.add(e.getMessage());
        }

        if (socket.isConnected()) {
            try {
                dispose();
            } catch (IOException e) {
                logs.add(e.getMessage());
            }
        }

        logs.add("TCP socket disconnected");
    }

    private void handleMessage(byte[] data) throws IOException {
        // TODO: Detemine what file type was sent
        String msg = new String(data);
        msg = msg.trim();
        logs.add("TCP Message recieved: " + msg);

        if (msg.contains("notification")) {
            String[] split = msg.split(NetClient.UNIT);
            String from = split[1]; // split[0] is the type of message, in this case, "notification"
            String title = split[2];
            String text = split[3];
            String subText = split[4];
            logs.add("From: " + from + "Title: " + title + " Text: " + text + " Subtext: " + subText);
            PushLocal.fetch().postNotification(from,
                    title, text, subText);
        }
        else if (msg.contains("Indeed,")) {
            sendMessage("Nice comma.");
        }
    }

    private void sendMessage(String msg) {
        try {
            socket.getOutputStream().write(msg.getBytes());
            logs.add("Sent TCP Message: " + msg);
        } catch (IOException e) {
            logs.add(e.getMessage());
        }
    }

    public void dispose() throws IOException {
        socket.close();
    }
}

