public class Shard implements WritableComparable {

  // This method is copied from Path.
  public static String normalizePath(String path) {
    // remove double slashes & backslashes
    path = path.replace("//", "/");
    path = path.replace("\\", "/");

    // trim trailing slash from non-root path (ignoring windows drive)
    if (path.length() > 1 && path.endsWith("/")) {
      path = path.substring(0, path.length() - 1);
    }

    return path;
  }

  public static void setIndexShards(IndexUpdateConfiguration conf,
      Shard[] shards) {
    StringBuilder shardsString = new StringBuilder(shards[0].toString());
    for (int i = 1; i < shards.length; i++) {
      shardsString.append(",");
      shardsString.append(shards[i].toString());
    }
    conf.setIndexShards(shardsString.toString());
  }

  public static Shard[] getIndexShards(IndexUpdateConfiguration conf) {
    String shards = conf.getIndexShards();
    if (shards != null) {
      ArrayList<Object> list =
          Collections.list(new StringTokenizer(shards, ","));
      Shard[] result = new Shard[list.size()];
      for (int i = 0; i < list.size(); i++) {
        result[i] = Shard.createShardFromString((String) list.get(i));
      }
      return result;
    } else {
      return null;
    }
  }

  // assume str is formatted correctly as a shard string
  private static Shard createShardFromString(String str) {
    int first = str.indexOf("@");
    int second = str.indexOf("@", first + 1);
    long version = Long.parseLong(str.substring(0, first));
    String dir = str.substring(first + 1, second);
    long gen = Long.parseLong(str.substring(second + 1));
    return new Shard(version, dir, gen);
  }

  // index/shard version
  // the shards in the same version of an index have the same version number
  private long version;
  private String dir;
  private long gen; // Lucene's generation

  /**
   * Constructor.
   */
  public Shard() {
    this.version = -1;
    this.dir = null;
    this.gen = -1;
  }

  /**
   * Construct a shard from a versio number, a directory and a generation
   * number.
   * @param version  the version number of the entire index
   * @param dir  the directory where this shard resides
   * @param gen  the generation of the Lucene instance
   */
  public Shard(long version, String dir, long gen) {
    this.version = version;
    this.dir = normalizePath(dir);
    this.gen = gen;
  }

  /**
   * Construct using a shard object.
   * @param shard  the shard used by the constructor
   */
  public Shard(Shard shard) {
    this.version = shard.version;
    this.dir = shard.dir;
    this.gen = shard.gen;
  }

  /**
   * Get the version number of the entire index.
   * @return the version number of the entire index
   */
  public long getVersion() {
    return version;
  }

  /**
   * Get the directory where this shard resides.
   * @return the directory where this shard resides
   */
  public String getDirectory() {
    return dir;
  }

  /**
   * Get the generation of the Lucene instance.
   * @return the generation of the Lucene instance
   */
  public long getGeneration() {
    return gen;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return version + "@" + dir + "@" + gen;
  }

  // ///////////////////////////////////
  // Writable
  // ///////////////////////////////////
  /* (non-Javadoc)
   * @see org.apache.hadoop.io.Writable#write(java.io.DataOutput)
   */
  public void write(DataOutput out) throws IOException {
    out.writeLong(version);
    Text.writeString(out, dir);
    out.writeLong(gen);
  }

  /* (non-Javadoc)
   * @see org.apache.hadoop.io.Writable#readFields(java.io.DataInput)
   */
  public void readFields(DataInput in) throws IOException {
    version = in.readLong();
    dir = Text.readString(in);
    gen = in.readLong();
  }

  // ///////////////////////////////////
  // Comparable
  // ///////////////////////////////////
  /* (non-Javadoc)
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo(Object o) {
    return compareTo((Shard) o);
  }

  /**
   * Compare to another shard.
   * @param other  another shard
   * @return compare version first, then directory and finally generation
   */
  public int compareTo(Shard other) {
    // compare version
    if (version < other.version) {
      return -1;
    } else if (version > other.version) {
      return 1;
    }
    // compare dir
    int result = dir.compareTo(other.dir);
    if (result != 0) {
      return result;
    }
    // compare gen
    if (gen < other.gen) {
      return -1;
    } else if (gen == other.gen) {
      return 0;
    } else {
      return 1;
    }
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Shard)) {
      return false;
    }
    Shard other = (Shard) o;
    return version == other.version && dir.equals(other.dir)
        && gen == other.gen;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  public int hashCode() {
    return (int) version ^ dir.hashCode() ^ (int) gen;
  }

}
