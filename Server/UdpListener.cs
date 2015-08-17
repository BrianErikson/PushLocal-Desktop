using System;
using System.Text;
using System.Net;
using System.Net.Sockets;
using System.Threading;
using System.Collections.Generic;
using System.Collections.Concurrent;

namespace PushLocal
{
	public class UdpListener
	{
		private ConcurrentQueue<string> consoleMsgs;
		private ConcurrentBag<OnConnectListener> onConnectListeners;
		private UdpClient sock;
		private byte[] data;
		private IPEndPoint sender;

		public UdpListener (ConcurrentQueue<string> consoleMsgs)
		{
			this.consoleMsgs = consoleMsgs;
			onConnectListeners = new ConcurrentBag<OnConnectListener> ();
		}

		// UDP Thread
		public void Start() {
			data = new byte[1024];
			IPEndPoint ipep = new IPEndPoint (IPAddress.Any, 7766);
			sock = new UdpClient (ipep);
			consoleMsgs.Enqueue ("Socket set up on " + ipep.ToString ());
			consoleMsgs.Enqueue ("Waiting for a client...");

			sender = new IPEndPoint (IPAddress.Any, 0);
			string retString = Dns.GetHostName();

			while (Program.isRunning) {
				try {
					data = sock.Receive(ref sender);
					consoleMsgs.Enqueue ("Message recieved from: " + sender.ToString ());
					HandleMessage(Encoding.ASCII.GetString (data, 0, data.Length));
				}
				catch (SocketException e) {
					consoleMsgs.Enqueue (e.ToString ());
					return;
				}

				consoleMsgs.Enqueue ("Sending message" + " " + retString);

				data = Encoding.ASCII.GetBytes (retString);
				sock.Send (data, data.Length, sender);

				Thread.Sleep (0);
			}
		}

		// UDP Thread
		private void HandleMessage(string msg) {
			consoleMsgs.Enqueue (msg);
			if (msg.Contains ("broadcast")) {
				string sending = "hostName" + NetSeperator.UNIT + Dns.GetHostName (); 
				consoleMsgs.Enqueue ("Sending message" + " " + sending);
				data = Encoding.ASCII.GetBytes (sending);
				sock.Send (data, data.Length, sender);
			} else if (msg.Contains ("connect")) {
				consoleMsgs.Enqueue ("Attempting to connect to sender");
				foreach (OnConnectListener listener in onConnectListeners) {
					listener.OnConnect (sender);
				}
			}
		}

		// Main Thread
		public void AddOnConnectListener(OnConnectListener listener) {
			onConnectListeners.Add (listener);
		}

		public void Stop() {
			sock.Close ();
		}
	}
}

