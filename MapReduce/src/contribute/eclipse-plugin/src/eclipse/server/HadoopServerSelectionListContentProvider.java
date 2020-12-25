package org.apache.hadoop.eclipse.servers;

import org.apache.hadoop.eclipse.server.HadoopServer;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;

/**
 * Provider that enables selection of a predefined Hadoop server.
 */

public class HadoopServerSelectionListContentProvider implements
    IContentProvider, ITableLabelProvider, IStructuredContentProvider {
  public void dispose() {

  }

  public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

  }

  public Image getColumnImage(Object element, int columnIndex) {
    return null;
  }

  public String getColumnText(Object element, int columnIndex) {
    if (element instanceof HadoopServer) {
      HadoopServer location = (HadoopServer) element;
      if (columnIndex == 0) {
        return location.getLocationName();

      } else if (columnIndex == 1) {
        return location.getMasterHostName();
      }
    }

    return element.toString();
  }

  public void addListener(ILabelProviderListener listener) {

  }

  public boolean isLabelProperty(Object element, String property) {
    return false;
  }

  public void removeListener(ILabelProviderListener listener) {

  }

  public Object[] getElements(Object inputElement) {
    return ServerRegistry.getInstance().getServers().toArray();
  }
}
