package network.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
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

        // TODO: connect to socket before while loop
        try {
            socket = new Socket(address, 5577);
            sendMessage("connected");

            byte[] data = new byte[PACKET_SIZE];

            while (netClient.isRunning() && !socket.isClosed()) {
                if (socket.getInputStream().available() >= 0) {
                    socket.getInputStream().read(data);

                    logs.add("Recieved Message from " + socket.getInetAddress().getHostName());
                    handleMessage(data);
                    data = new byte[PACKET_SIZE];
                    Thread.sleep(0);
                }
            }
        } catch (IOException | InterruptedException e) {
            logs.add(e.getMessage());
        }
    }

    private void handleMessage(byte[] data) {
        // TODO: Detemine what file type was sent
        String msg = new String(data);
        logs.add("TCP Message recieved: " + msg);

        if (msg.contains("notification")) {
            String[] split = msg.split(NetClient.UNIT);
            String title = split[1];
            String text = split[2];
            String subText = split[3];
            logs.add("Title: " + title + " Text: " + text + " Subtext: " + subText);
            // TODO: Post notification
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

