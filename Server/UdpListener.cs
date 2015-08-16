using System;
using System.Text;
using System.Net;
using System.Net.Sockets;
using System.Threading;
using System.Collections.Concurrent;

namespace PushLocal
{
	public class UdpListener
	{
		ConcurrentQueue<string> consoleMsgs;
		UdpClient sock;

		public UdpListener (ConcurrentQueue<string> consoleMsgs)
		{
			this.consoleMsgs = consoleMsgs;
		}

		public void Start() {
			byte[] data = new byte[1024];
			IPEndPoint ipep = new IPEndPoint (IPAddress.Any, 7766);
			sock = new UdpClient (ipep);
			consoleMsgs.Enqueue ("Socket set up on " + ipep.ToString ());
			consoleMsgs.Enqueue ("Waiting for a client...");

			IPEndPoint sender = new IPEndPoint (IPAddress.Any, 0);
			// listen
			try {
				data = sock.Receive(ref sender);
			}
			catch (SocketException e) {
				consoleMsgs.Enqueue (e.ToString ());
				return;
			}

			consoleMsgs.Enqueue ("Message recieved from: " + sender.ToString ());
			consoleMsgs.Enqueue (Encoding.ASCII.GetString (data, 0, data.Length));

			string retString = Dns.GetHostName();
			consoleMsgs.Enqueue ("Sending message" + " " + retString);
			data = Encoding.ASCII.GetBytes (retString);
			sock.Send (data, data.Length, sender);

			while (Program.isRunning) {
				try {
					data = sock.Receive(ref sender);
					consoleMsgs.Enqueue ("Message recieved from: " + sender.ToString ());
					consoleMsgs.Enqueue (Encoding.ASCII.GetString (data, 0, data.Length));
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

		public void Stop() {
			sock.Close ();
		}
	}
}

