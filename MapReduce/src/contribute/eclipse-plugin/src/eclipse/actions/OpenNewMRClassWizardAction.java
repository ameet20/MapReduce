package org.apache.hadoop.eclipse.actions;

import java.util.logging.Logger;

import org.apache.hadoop.eclipse.NewDriverWizard;
import org.apache.hadoop.eclipse.NewMapperWizard;
import org.apache.hadoop.eclipse.NewReducerWizard;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.cheatsheets.ICheatSheetAction;
import org.eclipse.ui.cheatsheets.ICheatSheetManager;


/**
 * Action to open a new MapReduce Class.
 */

public class OpenNewMRClassWizardAction extends Action implements
    ICheatSheetAction {

  static Logger log = Logger.getLogger(OpenNewMRClassWizardAction.class
      .getName());

  public void run(String[] params, ICheatSheetManager manager) {

    if ((params != null) && (params.length > 0)) {
      IWorkbench workbench = PlatformUI.getWorkbench();
      INewWizard wizard = getWizard(params[0]);
      wizard.init(workbench, new StructuredSelection());
      WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench()
          .getActiveWorkbenchWindow().getShell(), wizard);
      dialog.create();
      dialog.open();

      // did the wizard succeed ?
      notifyResult(dialog.getReturnCode() == Window.OK);
    }
  }

  private INewWizard getWizard(String typeName) {
    if (typeName.equals("Mapper")) {
      return new NewMapperWizard();
    } else if (typeName.equals("Reducer")) {
      return new NewReducerWizard();
    } else if (typeName.equals("Driver")) {
      return new NewDriverWizard();
    } else {
      log.severe("Invalid Wizard requested");
      return null;
    }
  }

}
