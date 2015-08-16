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
		private static Server singleton = null;

		private ConcurrentQueue<string> consoleMsgs;
		private UdpListener udpListener;
		private Thread udpThread;

		public Server ()
		{
			consoleMsgs = new ConcurrentQueue<string> ();

			udpListener = new UdpListener (consoleMsgs);
			udpThread = new Thread (new ThreadStart (udpListener.Start));
		}

		public void Start() {
			udpThread.Start ();
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
}

