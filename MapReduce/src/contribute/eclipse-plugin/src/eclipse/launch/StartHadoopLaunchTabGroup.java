package org.apache.hadoop.eclipse.launch;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.jdt.debug.ui.launchConfigurations.JavaArgumentsTab;
import org.eclipse.jdt.debug.ui.launchConfigurations.JavaClasspathTab;
import org.eclipse.jdt.debug.ui.launchConfigurations.JavaJRETab;

/**
 * Create the tab group for the dialog window for starting a Hadoop job.
 */

public class StartHadoopLaunchTabGroup extends
    AbstractLaunchConfigurationTabGroup {

  public StartHadoopLaunchTabGroup() {
    // TODO Auto-generated constructor stub
  }

  /**
   * TODO(jz) consider the appropriate tabs for this case
   */
  public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
    setTabs(new ILaunchConfigurationTab[] { new JavaArgumentsTab(),
        new JavaJRETab(), new JavaClasspathTab(), new CommonTab() });
  }
}
