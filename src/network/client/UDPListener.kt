package network.client

import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * Created by brian on 8/30/15.
 */
class UDPListener(private val netClient: NetClient, private val logs: ConcurrentLinkedQueue<String>,
                  private val listener: (InetAddress) -> Unit) : Thread() {

    private var socket: DatagramSocket? = null

    override fun run() {
        super.run()

        val packet = DatagramPacket(ByteArray(1024), 1024)
        try {
            socket = DatagramSocket(7766)
            logs.add("Socket set up on " + socket!!.localSocketAddress.toString())
            logs.add("Waiting for an incoming request...")

            while (netClient.isRunning && !socket!!.isClosed) {
                socket!!.receive(packet)
                logs.add("UDP Message recieved from: " + packet.address.toString())
                handleMessage(packet)
                Thread.sleep(0)
            }
        } catch (e: IOException) {
            logs.add(e.message)
        } catch (e: InterruptedException) {
            logs.add(e.message)
        }

    }

    @Throws(IOException::class)
    private fun handleMessage(packet: DatagramPacket) {
        val data = String(packet.data).substring(0, packet.data.size)
        logs.add("Message: " + data)

        if (data.contains("broadcast")) {
            val msg = "hostName" + NetClient.UNIT + InetAddress.getLocalHost().hostName
            packet.data = msg.toByteArray()
            logs.add("Sending UDP Message: " + msg)
            if (socket != null) socket!!.send(packet) else throw IllegalStateException("Can't send UDP message; socket is null")
        } else if (data.contains("connect")) {
            logs.add("Attemping to connect to sender: " + packet.address)
            listener(packet.address)
        }
    }

    fun dispose() {
        socket?.close()
    }
}
