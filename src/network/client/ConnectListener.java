package network.client;

import java.net.InetAddress;

/**
 * Created by brian on 8/30/15.
 */
public interface ConnectListener {
    void onConnectRequest(InetAddress address);
}
