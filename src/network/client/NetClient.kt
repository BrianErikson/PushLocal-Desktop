package network.client

import main.PushLocal

import java.io.IOException
import java.net.InetAddress
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * Created by brian on 8/30/15.
 */
class NetClient(private val pushLocal: PushLocal) : Thread() {
    private val logs: ConcurrentLinkedQueue<String> = ConcurrentLinkedQueue()

    val udpConnectionListener: (InetAddress) -> Unit = { address ->
        logs.add("Connection requested...")
        if (tcpListener != null && tcpListener!!.isAlive) {
            logs.add("TCP Listener already connected to a source. Attempting to close current connection.")
            try {
                tcpListener!!.dispose()
                tcpListener!!.join()
            } catch (e: InterruptedException) {
                logs.add(e.message)
            } catch (e: IOException) {
                logs.add(e.message)
            }
        }

        logs.add("Starting new TCP listener")
        tcpListener = TCPListener(this, logs, address)
        tcpListener!!.isDaemon = true
        tcpListener!!.start()
    }

    private val udpListener: UDPListener = UDPListener(this, logs, udpConnectionListener)
    @Volatile var isRunning = false
        private set
    private var tcpListener: TCPListener? = null

    init {
        udpListener.isDaemon = true
        udpListener.start()

        isRunning = true
    }

    override fun run() {
        super.run()

        while (isRunning) {
            if (!logs.isEmpty()) {
                val log = logs.poll()
                pushLocal.log(log)
            }
            try {
                Thread.sleep(0)
            } catch (e: InterruptedException) {
                pushLocal.log(e.message as String)
            }

        }
    }

    @Throws(IOException::class)
    fun dispose() {
        this.isRunning = false
        udpListener.dispose()
        tcpListener?.dispose()
    }

    companion object {
        val UNIT = 31.toChar().toString()
        val RECORD = 30.toChar().toString()
        val GROUP = 29.toChar().toString()
        val FILE = 28.toChar().toString()
    }
}
