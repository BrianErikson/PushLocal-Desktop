using System;
using System.Text;
using System.Net;
using System.Net.Sockets;
using System.Threading;
using System.Collections.Generic;
using System.Collections.Concurrent;

namespace PushLocal
{
	public class TcpListener
	{
		private TcpClient tcpSock;
		private ConcurrentQueue<string> consoleMsgs;
		private IPEndPoint dest;

		public TcpListener (IPEndPoint ep, ConcurrentQueue<string> consoleMsgs)
		{
			this.consoleMsgs = consoleMsgs;
			this.dest = ep;
		}

		// TCP Thread
		public void Start() {
			tcpSock = new TcpClient (new IPEndPoint(IPAddress.Any, 7777));

			consoleMsgs.Enqueue ("Attempting to connect to " + dest.Address.ToString() + ":5577");
			tcpSock.Connect (new IPEndPoint(dest.Address, 5577));

			if (IsConnected ()) {
				consoleMsgs.Enqueue ("Connected to " + dest.Address.ToString() + ":5577 successfully");
				tcpSock.Client.Send(Encoding.ASCII.GetBytes("connected"));
			}
			else
				consoleMsgs.Enqueue ("Connection to " + dest.Address.ToString() + ":5577 failed");
			// TODO: Begin listening for messages, and do certain things depending on the msg
			//tcpSock.Client.BeginReceive
		}

		private bool IsConnected() {
			try {
				return !(tcpSock.Client.Poll(1, SelectMode.SelectRead) && tcpSock.Client.Available == 0);
			}
			catch (SocketException) { 
				return false; 
			}
		}
	}
}

