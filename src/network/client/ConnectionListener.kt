package network.client

import java.net.InetAddress

/**
 * Created by brian on 8/30/15.
 */
interface ConnectionListener {
    fun onConnectRequest(address: InetAddress)
}
