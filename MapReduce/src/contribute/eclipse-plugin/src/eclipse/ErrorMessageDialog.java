package org.apache.hadoop.eclipse;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

/**
 * Error dialog helper
 */
public class ErrorMessageDialog {

  public static void display(final String title, final String message) {
    Display.getDefault().syncExec(new Runnable() {

      public void run() {
        MessageDialog.openError(Display.getDefault().getActiveShell(),
            title, message);
      }

    });
  }

  public static void display(Exception e) {
    display("An exception has occured!", "Exception description:\n"
        + e.getLocalizedMessage());
  }

}
