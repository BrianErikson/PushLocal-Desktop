using System;
using Gtk;

namespace PushLocal
{
	class MainClass
	{
		public static void Main (string[] args)
		{
			Application.Init ();
			TestWindow win = new TestWindow ();
			win.Show ();
			Application.Run ();
		}
	}
}
