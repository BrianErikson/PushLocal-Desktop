using System;
using Gtk;

namespace PushLocal
{
	public partial class TestWindow : Gtk.Window
	{

		public TestWindow () :
			base (Gtk.WindowType.Toplevel)
		{
			this.Build ();

		}

		protected void OnDeleteEvent (object sender, DeleteEventArgs a)
		{
			Application.Quit ();
			a.RetVal = true;
		}

		public void AddNetworkText(string text) {
			NetworkTextArea.Buffer.Insert(NetworkTextArea.Buffer.EndIter, text + "\n");
		}

		public bool NetworkShowing() {
			return NetworkTextArea.Visible;
		}
	}
}

