using System;
using System.Threading;
using Gtk;

namespace PushLocal
{
	public class Updater
	{
		private Thread updateThread;
		private TestWindow win;
		private Server server;

		public Updater (TestWindow window, Server server)
		{
			this.win = window;
			this.server = server;
			updateThread = new Thread (new ParameterizedThreadStart(Update));
		}

		public void Start() {
			updateThread.Start(SynchronizationContext.Current);
		}

		private void Update(object state) {
			SynchronizationContext context = (SynchronizationContext)state;
			while (Program.isRunning && context != null) {
				context.Send(this.AsyncUpdateNetworkText, new object());
				Thread.Sleep (0);
			}
		}

		private void AsyncUpdateNetworkText(object state) {
			if (win.NetworkShowing()) {
				string msg = server.GetConsoleMessage ();
				if (msg != null)
					win.AddNetworkText(msg);
			}
		}
	}
}

