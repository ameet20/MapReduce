package org.apache.hadoop.eclipse.actions;

import org.apache.hadoop.eclipse.ImageLibrary;
import org.apache.hadoop.eclipse.servers.HadoopLocationWizard;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;


/**
 * Action corresponding to creating a new MapReduce Server.
 */

public class NewLocationAction extends Action {
  public NewLocationAction() {
    setText("New Hadoop location...");
    setImageDescriptor(ImageLibrary.get("server.view.action.location.new"));
  }

  @Override
  public void run() {
    WizardDialog dialog = new WizardDialog(null, new Wizard() {
      private HadoopLocationWizard page = new HadoopLocationWizard();

      @Override
      public void addPages() {
        super.addPages();
        setWindowTitle("New Hadoop location...");
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
