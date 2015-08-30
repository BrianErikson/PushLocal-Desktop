package network.client;

import com.sun.jndi.dns.DnsName;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by brian on 8/30/15.
 */
public class UDPListener extends Thread {
    private final ConnectListener listener;
    private NetClient netClient;
    private ConcurrentLinkedQueue<String> logs;
    private DatagramSocket socket;

    public UDPListener(NetClient netClient, ConcurrentLinkedQueue<String> logs, ConnectListener listener) {
        this.netClient = netClient;
        this.logs = logs;
        this.listener = listener;
    }

    @Override
    public void run() {
        super.run();

        DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
        try {
            socket = new DatagramSocket(7766);
            logs.add("Socket set up on " + socket.getLocalSocketAddress().toString());
            logs.add("Waiting for an incoming request...");

            while (netClient.isRunning()) {
                socket.receive(packet);
                logs.add("UDP Message recieved from: " + packet.getAddress().toString());
                handleMessage(packet);
                Thread.sleep(0);
            }
        } catch (IOException | InterruptedException e) {
            logs.add(e.getMessage());
        }
    }

    private void handleMessage(DatagramPacket packet) throws IOException {
        String data = new String(packet.getData()).substring(0, packet.getData().length);
        logs.add("Message: " + data);

        if (data.contains("broadcast")) {
            String msg = "hostName" + NetClient.UNIT + InetAddress.getLocalHost().getHostName();
            packet.setData(msg.getBytes());
            logs.add("Sending UDP Message: " + msg);
            socket.send(packet);
        }
        else if (data.contains("connect")) {
            logs.add("Attemping to connect to sender: " + packet.getAddress());
            listener.onConnectRequest(packet.getAddress());
        }
    }
}
