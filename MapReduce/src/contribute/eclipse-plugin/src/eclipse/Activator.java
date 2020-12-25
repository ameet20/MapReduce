package org.apache.hadoop.eclipse;

import org.apache.hadoop.eclipse.servers.ServerRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

  /**
   * The plug-in ID
   */
  public static final String PLUGIN_ID = "org.apache.hadoop.eclipse";

  /**
   * The shared unique instance (singleton)
   */
  private static Activator plugin;

  /**
   * Constructor
   */
  public Activator() {
    synchronized (Activator.class) {
      if (plugin != null) {
        // Not a singleton!?
        throw new RuntimeException("Activator for " + PLUGIN_ID
            + " is not a singleton");
      }
      plugin = this;
    }
  }

  /* @inheritDoc */
  @Override
  public void start(BundleContext context) throws Exception {
    super.start(context);
  }

  /* @inheritDoc */
  @Override
  public void stop(BundleContext context) throws Exception {
    ServerRegistry.getInstance().dispose();
    plugin = null;
    super.stop(context);
  }

  /**
   * Returns the shared unique instance (singleton)
   * 
   * @return the shared unique instance (singleton)
   */
  public static Activator getDefault() {
    return plugin;
  }

}
