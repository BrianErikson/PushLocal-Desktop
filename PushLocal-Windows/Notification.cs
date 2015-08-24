using System;
using System.IO;
using System.Runtime.InteropServices;
using Windows.Data.Xml.Dom;
using Windows.Data.Xml;
using Windows.UI.Notifications;
using IWshRuntimeLibrary;

namespace PushLocalWindows
{
	public class Notification
	{
		public static string APP_ID = "Beariksonstudios.PushLocal";
		public static string EXE_PATH = Path.GetDirectoryName (System.AppDomain.CurrentDomain.BaseDirectory) + "PushLocal.exe";
		public static string APP_START_MENU_PATH = Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.CommonStartMenu), "Programs", "PushLocal");
		public static string SHORTCUT_LOCATION = Path.Combine(APP_START_MENU_PATH, "PushLocal" + ".lnk");

		public static void Post(string header, string text, string subtext) {
			if (!HasShortcut ())
				CreateShortcut ();

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

			notifier.Show (toast);
		}

		public static void CreateShortcut() {
			// TODO Access to the path 'C:\ProgramData\Microsoft\Windows\Start Menu\Programs\PushLocal' is denied.
			//if (!Directory.Exists(APP_START_MENU_PATH))
			//	Directory.CreateDirectory(APP_START_MENU_PATH);

			WshShell shell = new WshShell();
			shell.RegWrite ("{9F4C2855-9F79-4B39-A8D0-E1D42DE1D5F3}, 5", APP_ID, (ushort)VarEnum.VT_LPWSTR);
            IWshShortcut shortcut = shell.CreateShortcut(SHORTCUT_LOCATION) as IWshShortcut;
			shortcut.Description = "PushLocal Desktop App";
			//shortcut.IconLocation = @"C:\Program Files (x86)\TestApp\TestApp.ico"; //uncomment to set the icon of the shortcut
			shortcut.TargetPath = EXE_PATH;
			shortcut.Save(); 
		}

		public static bool HasShortcut() {
			return System.IO.File.Exists (SHORTCUT_LOCATION);
		}
	}
}
