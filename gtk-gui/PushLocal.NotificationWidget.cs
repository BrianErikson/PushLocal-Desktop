
// This file has been generated by the GUI designer. Do not modify.
namespace PushLocal
{
	public partial class NotificationWidget
	{
		private global::Gtk.VBox vbox7;
		
		private global::Gtk.HBox hbox1;
		
		private global::Gtk.Image image2;
		
		private global::Gtk.VBox vbox3;
		
		private global::Gtk.Label appName;
		
		private global::Gtk.Label description;
		
		private global::Gtk.Label date;
		
		private global::Gtk.HSeparator hseparator2;

		protected virtual void Build ()
		{
			global::Stetic.Gui.Initialize (this);
			// Widget PushLocal.NotificationWidget
			global::Stetic.BinContainer.Attach (this);
			this.HeightRequest = 75;
			this.CanFocus = true;
			this.Events = ((global::Gdk.EventMask)(256));
			this.Name = "PushLocal.NotificationWidget";
			// Container child PushLocal.NotificationWidget.Gtk.Container+ContainerChild
			this.vbox7 = new global::Gtk.VBox ();
			this.vbox7.Name = "vbox7";
			this.vbox7.Spacing = 6;
			// Container child vbox7.Gtk.Box+BoxChild
			this.hbox1 = new global::Gtk.HBox ();
			this.hbox1.Name = "hbox1";
			this.hbox1.Spacing = 6;
			// Container child hbox1.Gtk.Box+BoxChild
			this.image2 = new global::Gtk.Image ();
			this.image2.WidthRequest = 75;
			this.image2.HeightRequest = 75;
			this.image2.Name = "image2";
			this.image2.Pixbuf = global::Stetic.IconLoader.LoadIcon (this, "gtk-dialog-info", global::Gtk.IconSize.Dialog);
			this.hbox1.Add (this.image2);
			global::Gtk.Box.BoxChild w1 = ((global::Gtk.Box.BoxChild)(this.hbox1 [this.image2]));
			w1.Position = 0;
			w1.Expand = false;
			w1.Fill = false;
			// Container child hbox1.Gtk.Box+BoxChild
			this.vbox3 = new global::Gtk.VBox ();
			this.vbox3.Name = "vbox3";
			this.vbox3.Spacing = 3;
			// Container child vbox3.Gtk.Box+BoxChild
			this.appName = new global::Gtk.Label ();
			this.appName.Name = "appName";
			this.appName.LabelProp = global::Mono.Unix.Catalog.GetString ("App Name");
			this.vbox3.Add (this.appName);
			global::Gtk.Box.BoxChild w2 = ((global::Gtk.Box.BoxChild)(this.vbox3 [this.appName]));
			w2.Position = 0;
			w2.Fill = false;
			// Container child vbox3.Gtk.Box+BoxChild
			this.description = new global::Gtk.Label ();
			this.description.Name = "description";
			this.description.Yalign = 0.2F;
			this.description.LabelProp = global::Mono.Unix.Catalog.GetString ("event description");
			this.vbox3.Add (this.description);
			global::Gtk.Box.BoxChild w3 = ((global::Gtk.Box.BoxChild)(this.vbox3 [this.description]));
			w3.PackType = ((global::Gtk.PackType)(1));
			w3.Position = 1;
			this.hbox1.Add (this.vbox3);
			global::Gtk.Box.BoxChild w4 = ((global::Gtk.Box.BoxChild)(this.hbox1 [this.vbox3]));
			w4.Position = 1;
			// Container child hbox1.Gtk.Box+BoxChild
			this.date = new global::Gtk.Label ();
			this.date.Name = "date";
			this.date.Yalign = 0.2F;
			this.date.LabelProp = global::Mono.Unix.Catalog.GetString ("0/00/00");
			this.date.Justify = ((global::Gtk.Justification)(1));
			this.hbox1.Add (this.date);
			global::Gtk.Box.BoxChild w5 = ((global::Gtk.Box.BoxChild)(this.hbox1 [this.date]));
			w5.PackType = ((global::Gtk.PackType)(1));
			w5.Position = 2;
			w5.Expand = false;
			w5.Fill = false;
			w5.Padding = ((uint)(2));
			this.vbox7.Add (this.hbox1);
			global::Gtk.Box.BoxChild w6 = ((global::Gtk.Box.BoxChild)(this.vbox7 [this.hbox1]));
			w6.Position = 0;
			// Container child vbox7.Gtk.Box+BoxChild
			this.hseparator2 = new global::Gtk.HSeparator ();
			this.hseparator2.Name = "hseparator2";
			this.vbox7.Add (this.hseparator2);
			global::Gtk.Box.BoxChild w7 = ((global::Gtk.Box.BoxChild)(this.vbox7 [this.hseparator2]));
			w7.Position = 1;
			w7.Expand = false;
			w7.Fill = false;
			this.Add (this.vbox7);
			if ((this.Child != null)) {
				this.Child.ShowAll ();
			}
			this.Hide ();
		}
	}
}
