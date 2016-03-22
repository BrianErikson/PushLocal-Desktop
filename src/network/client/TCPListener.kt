package network.client

import main.PushLocal

import java.io.IOException
import java.net.InetAddress
import java.net.Socket
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * Created by brian on 8/30/15.
 */
class TCPListener(private val netClient: NetClient, private val logs: ConcurrentLinkedQueue<String>, private val address: InetAddress) : Thread() {
    private var socket: Socket? = null

    override fun run() {
        super.run()

        try {
            socket = Socket(address, 5577)
            sendMessage("connected")

            var data = ByteArray(PACKET_SIZE)

            while (netClient.isRunning && !socket!!.isClosed) {
                val len = socket!!.inputStream.read(data)
                if (len < 0)
                    break
                logs.add("Received Message from " + socket!!.inetAddress.hostName)
                handleMessage(data)
                data = ByteArray(PACKET_SIZE)
                Thread.sleep(0)
            }
        } catch (e: IOException) {
            logs.add(e.message)
        } catch (e: InterruptedException) {
            logs.add(e.message)
        }

        if (socket!!.isConnected) {
            try {
                dispose()
            } catch (e: IOException) {
                logs.add(e.message)
            }

        }

        logs.add("TCP socket disconnected")
    }

    @Throws(IOException::class)
    private fun handleMessage(data: ByteArray) {
        // TODO: Detemine what file type was sent
        var msg = String(data)
        msg = msg.trim { it <= ' ' }
        logs.add("TCP Message recieved: " + msg)

        if (msg.contains("notification")) {
            val split = msg.split(NetClient.UNIT.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val from = split[1] // split[0] is the type of message, in this case, "notification"
            val title = split[2]
            val text = split[3]
            val subText = split[4]
            logs.add("From: " + from + "Title: " + title + " Text: " + text + " Subtext: " + subText)
            PushLocal.singleton?.postNotification(from,
                    title, text, subText)
        } else if (msg.contains("Indeed,")) {
            sendMessage("Nice comma.")
        }
    }

    private fun sendMessage(msg: String) {
        try {
            socket!!.outputStream.write(msg.toByteArray())
            logs.add("Sent TCP Message: " + msg)
        } catch (e: IOException) {
            logs.add(e.message)
        }

    }

    @Throws(IOException::class)
    fun dispose() {
        socket?.close()
    }

    companion object {
        val PACKET_SIZE = 1024
    }
}

