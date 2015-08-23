using System;
using System.Text;
using System.Net;
using System.Net.Sockets;
using System.Threading;
using System.Collections.Generic;
using System.Collections.Concurrent;
using PushLocalWindows;

namespace PushLocal
{
	public class TcpListener
	{
		private TcpClient tcpSock;
		private ConcurrentQueue<string> consoleMsgs;
		private IPEndPoint dest;

		public TcpListener (IPEndPoint ep, ConcurrentQueue<string> consoleMsgs) {
			this.consoleMsgs = consoleMsgs;
			this.dest = ep;
		}

		// TCP Thread
		public void Start() {
			tcpSock = new TcpClient (new IPEndPoint(IPAddress.Any, 7777));

			consoleMsgs.Enqueue ("Attempting to connect to " + dest.Address.ToString() + ":5577");
			tcpSock.Connect (new IPEndPoint(dest.Address, 5577));
			tcpSock.Client.Send(Encoding.ASCII.GetBytes("connected"));

			byte[] data = new byte[1024];
			while (Program.isRunning) {
				tcpSock.Client.Receive (data);
				string msg = Encoding.ASCII.GetString (data);
				consoleMsgs.Enqueue ("TCP Msg: " + msg);
				HandleMessage (msg);
				Thread.Sleep (0);
			}
		}

		public void HandleMessage(string msg) {
			if (msg.Contains ("notification")) {
				string[] split = msg.Split (NetSeperator.UNIT);
				string title = split [1];
				string text = split [2];
				string subText = split [3];
				consoleMsgs.Enqueue ("Title: " + title + " Text: " + text + " Subtext: " + subText);
				Notification.Post (title, text, subText);
			}
			else if (msg.Contains ("Indeed,")) {
				tcpSock.Client.Send(Encoding.ASCII.GetBytes("Nice comma."));
			}
		}

		public TcpClient GetSocket() {
			lock (this) {
				return tcpSock;
			}
		}

		private bool IsConnected() {
			try {
				return !(tcpSock.Client.Poll(1, SelectMode.SelectRead) && tcpSock.Client.Available == 0);
			}
			catch (SocketException) { 
				return false; 
			}
		}

		public void Stop() {
			tcpSock.Close ();
		}
	}
}

