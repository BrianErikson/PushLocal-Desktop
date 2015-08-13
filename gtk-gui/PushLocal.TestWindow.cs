
// This file has been generated by the GUI designer. Do not modify.
namespace PushLocal
{
	public partial class TestWindow
	{
		private global::Gtk.Notebook NotificationTab;
		
		private global::Gtk.ScrolledWindow NotifyScrollbar;
		
		private global::Gtk.VBox notifyVBox;
		
		private global::PushLocal.NotificationWidget notificationwidget1;
		
		private global::PushLocal.NotificationWidget notificationwidget2;
		
		private global::PushLocal.NotificationWidget notificationwidget3;
		
		private global::Gtk.Label Notifications;

		protected virtual void Build ()
		{
			global::Stetic.Gui.Initialize (this);
			// Widget PushLocal.TestWindow
			this.Name = "PushLocal.TestWindow";
			this.Title = global::Mono.Unix.Catalog.GetString ("TestWindow");
			this.WindowPosition = ((global::Gtk.WindowPosition)(4));
			// Container child PushLocal.TestWindow.Gtk.Container+ContainerChild
			this.NotificationTab = new global::Gtk.Notebook ();
			this.NotificationTab.CanFocus = true;
			this.NotificationTab.Name = "NotificationTab";
			this.NotificationTab.CurrentPage = 0;
			// Container child NotificationTab.Gtk.Notebook+NotebookChild
			this.NotifyScrollbar = new global::Gtk.ScrolledWindow ();
			this.NotifyScrollbar.CanFocus = true;
			this.NotifyScrollbar.Name = "NotifyScrollbar";
			this.NotifyScrollbar.HscrollbarPolicy = ((global::Gtk.PolicyType)(2));
			this.NotifyScrollbar.ShadowType = ((global::Gtk.ShadowType)(1));
			// Container child NotifyScrollbar.Gtk.Container+ContainerChild
			global::Gtk.Viewport w1 = new global::Gtk.Viewport ();
			w1.ShadowType = ((global::Gtk.ShadowType)(0));
			// Container child GtkViewport.Gtk.Container+ContainerChild
			this.notifyVBox = new global::Gtk.VBox ();
			this.notifyVBox.Name = "notifyVBox";
			this.notifyVBox.Spacing = 6;
			// Container child notifyVBox.Gtk.Box+BoxChild
			this.notificationwidget1 = new global::PushLocal.NotificationWidget ();
			this.notificationwidget1.HeightRequest = 75;
			this.notificationwidget1.Events = ((global::Gdk.EventMask)(256));
			this.notificationwidget1.Name = "notificationwidget1";
			this.notifyVBox.Add (this.notificationwidget1);
			global::Gtk.Box.BoxChild w2 = ((global::Gtk.Box.BoxChild)(this.notifyVBox [this.notificationwidget1]));
			w2.Position = 0;
			w2.Expand = false;
			w2.Fill = false;
			// Container child notifyVBox.Gtk.Box+BoxChild
			this.notificationwidget2 = new global::PushLocal.NotificationWidget ();
			this.notificationwidget2.HeightRequest = 75;
			this.notificationwidget2.Events = ((global::Gdk.EventMask)(256));
			this.notificationwidget2.Name = "notificationwidget2";
			this.notifyVBox.Add (this.notificationwidget2);
			global::Gtk.Box.BoxChild w3 = ((global::Gtk.Box.BoxChild)(this.notifyVBox [this.notificationwidget2]));
			w3.Position = 1;
			w3.Expand = false;
			w3.Fill = false;
			// Container child notifyVBox.Gtk.Box+BoxChild
			this.notificationwidget3 = new global::PushLocal.NotificationWidget ();
			this.notificationwidget3.HeightRequest = 75;
			this.notificationwidget3.Events = ((global::Gdk.EventMask)(256));
			this.notificationwidget3.Name = "notificationwidget3";
			this.notifyVBox.Add (this.notificationwidget3);
			global::Gtk.Box.BoxChild w4 = ((global::Gtk.Box.BoxChild)(this.notifyVBox [this.notificationwidget3]));
			w4.Position = 2;
			w4.Expand = false;
			w4.Fill = false;
			w1.Add (this.notifyVBox);
			this.NotifyScrollbar.Add (w1);
			this.NotificationTab.Add (this.NotifyScrollbar);
			// Notebook tab
			this.Notifications = new global::Gtk.Label ();
			this.Notifications.Name = "Notifications";
			this.Notifications.Xalign = 1F;
			this.Notifications.LabelProp = global::Mono.Unix.Catalog.GetString ("Notifications");
			this.NotificationTab.SetTabLabel (this.NotifyScrollbar, this.Notifications);
			this.Notifications.ShowAll ();
			this.Add (this.NotificationTab);
			if ((this.Child != null)) {
				this.Child.ShowAll ();
			}
			this.DefaultWidth = 392;
			this.DefaultHeight = 300;
			this.Show ();
			this.DeleteEvent += new global::Gtk.DeleteEventHandler (this.OnDeleteEvent);
		}
	}
}
