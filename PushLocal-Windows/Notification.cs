using System;
using System.IO;
using Windows.Data.Xml.Dom;
using Windows.Data.Xml;
using Windows.UI.Notifications;

namespace PushLocalWindows
{
	public class Notification
	{
		public static void Post(string header, string text, string subtext) {
			// Get a toast XML template
			XmlDocument toastXml = (XmlDocument) ToastNotificationManager.GetTemplateContent(ToastTemplateType.ToastImageAndText04);


			// Fill in the text elements
			XmlNodeList stringElements = toastXml.GetElementsByTagName("text");
			stringElements.Item(0).AppendChild(toastXml.CreateTextNode(header));
			stringElements.Item(1).AppendChild(toastXml.CreateTextNode(text));
			stringElements.Item(2).AppendChild(toastXml.CreateTextNode(subtext));

			// Specify the absolute path to an image
			//String imagePath = "file:///" + Path.GetFullPath("toastImageAndText.png");
			//XmlNodeList imageElements = toastXml.GetElementsByTagName("image");

			ToastNotification toast = new ToastNotification(toastXml);
			// TODO toast.Activated listener
			ToastNotifier notifier = ToastNotificationManager.CreateToastNotifier();
			if (notifier == null)
				Console.WriteLine ("Notifier null");

			notifier.Show (toast);
		}
	}
}
