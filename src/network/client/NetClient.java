package network.client;

import main.PushLocal;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by brian on 8/30/15.
 */
public class NetClient extends Thread {
    public static final String UNIT = String.valueOf((char)31);
    public static final String RECORD = String.valueOf((char)30);
    public static final String GROUP = String.valueOf((char)29);
    public static final String FILE = String.valueOf((char)28);

    private volatile boolean running = false;
    private ConcurrentLinkedQueue<String> logs;
    private UDPListener udpListener;
    private TCPListener tcpListener;
    private PushLocal pushLocal;

    public NetClient(PushLocal pushLocal) {
        this.pushLocal = pushLocal;
        logs = new ConcurrentLinkedQueue<>();
        NetClient _this = this;
        udpListener = new UDPListener(this, logs, _this::connect);
        udpListener.setDaemon(true);
        udpListener.start();

        running = true;
    }

    @Override
    public void run() {
        super.run();

        while (isRunning()) {
            if (!logs.isEmpty()) {
                String log = logs.poll();
                pushLocal.log(log);
            }
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                pushLocal.log(e.getMessage());
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    public synchronized void connect(InetAddress address) {
        if (tcpListener != null && tcpListener.isAlive()) {
            try {
                tcpListener.dispose();
                tcpListener.join();
            } catch (InterruptedException | IOException e) {
                logs.add(e.getMessage());
            }
        }

        tcpListener = new TCPListener(this, logs, address);
        tcpListener.setDaemon(true);
        tcpListener.start();
    }

    public void dispose() throws IOException {
        this.running = false;
        udpListener.dispose();
        if (tcpListener != null)
            tcpListener.dispose();
    }
}
