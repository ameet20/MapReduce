package org.apache.hadoop.eclipse.actions;

import org.apache.hadoop.eclipse.ImageLibrary;
import org.apache.hadoop.eclipse.server.HadoopServer;
import org.apache.hadoop.eclipse.servers.HadoopLocationWizard;
import org.apache.hadoop.eclipse.view.servers.ServerView;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;

/**
 * Editing server properties action
 */
public class EditLocationAction extends Action {

  private ServerView serverView;

  public EditLocationAction(ServerView serverView) {
    this.serverView = serverView;

    setText("Edit Hadoop location...");
    setImageDescriptor(ImageLibrary.get("server.view.action.location.edit"));
  }

  @Override
  public void run() {

    final HadoopServer server = serverView.getSelectedServer();
    if (server == null)
      return;

    WizardDialog dialog = new WizardDialog(null, new Wizard() {
      private HadoopLocationWizard page = new HadoopLocationWizard(server);

      @Override
      public void addPages() {
        super.addPages();
        setWindowTitle("Edit Hadoop location...");
        addPage(page);
      }

      @Override
      public boolean performFinish() {
        page.performFinish();
        return true;
      }
    });

    dialog.create();
    dialog.setBlockOnOpen(true);
    dialog.open();

    super.run();
  }
}
