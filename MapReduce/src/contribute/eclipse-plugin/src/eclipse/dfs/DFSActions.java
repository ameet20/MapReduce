package org.apache.hadoop.eclipse.dfs;

public enum DFSActions {

  DELETE("Delete"), REFRESH("Refresh"), DOWNLOAD("Download from DFS..."), OPEN(
      "View"), MKDIR("Create new directory..."), UPLOAD_FILES(
      "Upload files to DFS..."), UPLOAD_DIR("Upload directory to DFS..."), RECONNECT(
      "Reconnect"), DISCONNECT("Disconnect");

  final String title;

  final String id;

  private static final String PREFIX = "dfs.browser.action.";

  public static DFSActions getById(String def) {
    if (!def.startsWith(PREFIX))
      return null;
    return valueOf(def.substring(PREFIX.length()).toUpperCase());
  }

  DFSActions(String title) {
    this.title = title;
    this.id = PREFIX + this.name().toLowerCase();
  }
}
