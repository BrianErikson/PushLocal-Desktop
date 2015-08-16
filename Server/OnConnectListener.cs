using System;
using System.Net;

namespace PushLocal
{
	public interface OnConnectListener
	{
		void OnConnect(IPEndPoint connection);
	}
}

