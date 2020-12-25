package org.apache.hadoop.eclipse.preferences;

import org.apache.hadoop.eclipse.Activator;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * This class represents a preference page that is contributed to the
 * Preferences dialog. By sub-classing <tt>FieldEditorPreferencePage</tt>,
 * we can use the field support built into JFace that allows us to create a
 * page that is small and knows how to save, restore and apply itself.
 * 
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 */

public class MapReducePreferencePage extends FieldEditorPreferencePage
    implements IWorkbenchPreferencePage {

  public MapReducePreferencePage() {
    super(GRID);
    setPreferenceStore(Activator.getDefault().getPreferenceStore());
    setTitle("Hadoop Map/Reduce Tools");
    // setDescription("Hadoop Map/Reduce Preferences");
  }

  /**
   * Creates the field editors. Field editors are abstractions of the common
   * GUI blocks needed to manipulate various types of preferences. Each field
   * editor knows how to save and restore itself.
   */
  @Override
  public void createFieldEditors() {
    addField(new DirectoryFieldEditor(PreferenceConstants.P_PATH,
        "&Hadoop installation directory:", getFieldEditorParent()));

  }

  /* @inheritDoc */
  public void init(IWorkbench workbench) {
  }

}
