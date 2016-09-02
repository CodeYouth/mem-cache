package memory.cache.mem;

import memory.cache.domain.Keyword;
import org.mapdb.BTreeMap;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * memory-cache source code.
 *
 * @Author codeyouth
 * @Version 0.1.0-SNAPSHOT
 * @Date 16/8/27
 */
public final class MemoryStore {
  
  public static final HashSet<Keyword> keywordStore = new HashSet<>(4500000);
  
  public static final HashSet<String> keywordJsonStore = new HashSet<>(4500000);
  
  public static final HashSet<byte[]> keywordJsonBinaryStore = new HashSet<>(4500000);
  
  public static final Map<byte[], byte[]> keywordInfoStore = new ConcurrentHashMap<>(4500000);
  
  public static final BTreeMap<byte[], byte[]> keywordInfoMapStore = DBMaker.memoryDirectDB().make().treeMap("keywordMap")
      .keySerializer(Serializer.BYTE_ARRAY)
      .valueSerializer(Serializer.BYTE_ARRAY)
      .createOrOpen();
  
  private MemoryStore() {}
  
}
