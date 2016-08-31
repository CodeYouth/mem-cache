package memory.cache.util;

import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.*;

/**
 * memory-cache source code.
 *
 * @Author codeyouth
 * @Version 0.1.0-SNAPSHOT
 * @Date 16/8/27
 */
public final class CompressUtils {
  
  private static final int BUFFER_SIZE = 4096;
  
  public static byte[] compress(byte[] data, Level level) throws IOException {
    Deflater deflater = new Deflater();
    // set compression level
    deflater.setLevel(level.getLevel());
    deflater.setInput(data);
  
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
  
    deflater.finish();
    byte[] buffer = new byte[BUFFER_SIZE];
    while (!deflater.finished()) {
      int count = deflater.deflate(buffer); // returns the generated code... index
      outputStream.write(buffer, 0, count);
    }
    byte[] output = outputStream.toByteArray();
    outputStream.close();
    return output;
  }
  
  public static byte[] decompress(byte[] data) throws DataFormatException, IOException {
    Inflater inflater = new Inflater();
    inflater.setInput(data);
  
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
    
    byte[] buffer = new byte[BUFFER_SIZE];
    while (!inflater.finished()) {
      int count = inflater.inflate(buffer);
      outputStream.write(buffer, 0, count);
    }
    byte[] output = outputStream.toByteArray();
    outputStream.close();
    return output;
  }
  
  public static String gzip(String origin, String charset) throws IOException {
    if (StringUtils.isEmpty(origin)) {
      return origin;
    }
    
    // 创建一个新的 byte 数组输出流
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    // 使用默认缓冲区大小创建新的输出流
    GZIPOutputStream gzip = new GZIPOutputStream(out);
    // 将 b.length 个字节写入此输出流
    gzip.write(origin.getBytes("UTF-8"));
    gzip.close();
    // 使用指定的 charsetName, 通过解码字节将缓冲内容转为字符串
    return out.toString(charset);
  }
  
  public static String ungzip(String compressed, String charset) throws IOException {
    if (StringUtils.isEmpty(compressed)) {
      return compressed;
    }
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ByteArrayInputStream in = new ByteArrayInputStream(compressed.getBytes(charset));
    GZIPInputStream gzip = new GZIPInputStream(in);
    
    byte[] buffer = new byte[256];
    int n;
    while ((n = gzip.read(buffer)) >= 0) {
      out.write(buffer, 0, n);
    }
    
    return out.toString(charset);
  }
  
  public enum Level {
    /**
     * Compression level for no compression.
     */
    NO_COMPRESSION(0),
  
    /**
     * Compression level for fastest compression.
     */
    BEST_SPEED(1),
  
    /**
     * Compression level for best compression.
     */
    BEST_COMPRESSION(9),
  
    /**
     * Default compression level.
     */
    DEFAULT_COMPRESSION(-1);
  
    private int level;
  
    Level(int level) {
      this.level = level;
    }
  
    public int getLevel() {
      return level;
    }
  }
  
}
