using System;
using System.Threading;
using Gtk;

namespace PushLocal
{
	class Program
	{
		public static volatile bool isRunning = true;
		private static Server server;
		private static TestWindow win;

		public static void Main (string[] args)
		{
			SynchronizationContext.SetSynchronizationContext (new SynchronizationContext());

			Application.Init ();
			win = new TestWindow ();
			win.Show ();

			server = new Server ();
			server.Start ();

			Thread updateThread = new Thread (new ParameterizedThreadStart(Update));
			updateThread.Start(SynchronizationContext.Current);

			Application.Run ();

			isRunning = false;
			server.Stop ();
		}

		private static void Update(object state) {
			SynchronizationContext context = (SynchronizationContext)state;
			while (isRunning) {
				context.Send(AsyncUpdateNetworkText, null);
				Thread.Sleep (0);
			}
		}

		private static void AsyncUpdateNetworkText(object state) {
			if (win.NetworkShowing()) {
				string msg = server.GetConsoleMessage ();
				if (msg != null)
					win.AddNetworkText(msg);
			}
		}
	}
}
