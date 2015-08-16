using System;
using System.Text;
using System.Net;
using System.Net.Sockets;
using System.Collections.Generic;
using System.Collections.Concurrent;
using System.Threading;

namespace PushLocal
{
	public class Server
	{
		public SynchronizationContext synchronizationContext;
		private static Server singleton = null;

		private ConcurrentQueue<string> consoleMsgs;

		private UdpListener udpListener;
		private Thread udpThread;

		private TcpListener tcpListener;
		private Thread tcpThread;

		public Server ()
		{
			synchronizationContext = SynchronizationContext.Current;
			consoleMsgs = new ConcurrentQueue<string> ();

			udpListener = new UdpListener (consoleMsgs);

			udpListener.AddOnConnectListener (new TcpConnectListener(this));

			udpThread = new Thread (new ThreadStart (udpListener.Start));
		}

		public void Start() {
			udpThread.Start ();
		}

		public void StartTcpClient(object state) {
			consoleMsgs.Enqueue ("Starting TCP Client for remote connection");
			IPEndPoint sender = (IPEndPoint)state;
			tcpListener = new TcpListener (sender, consoleMsgs);
			tcpThread = new Thread (new ThreadStart (tcpListener.Start));
		}

		public static Server fetch() {
			if (singleton == null)
				singleton = new Server ();
			return singleton;
		}

		public List<string> GetConsoleMessages() {
			return new List<string> (consoleMsgs);
		}

		public string GetConsoleMessage() {
			string str = null;
			consoleMsgs.TryDequeue(out str);
			return str;
		}

		public void Stop() {
			udpListener.Stop ();
		}
	}

	public class TcpConnectListener : OnConnectListener {
		private Server server;

		public TcpConnectListener(Server server) {
			this.server = server;
		}

		public void OnConnect(IPEndPoint connection) {
			server.synchronizationContext.Post (server.StartTcpClient, connection);
		}
	}
}

