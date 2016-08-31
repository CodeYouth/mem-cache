package memory.cache.service;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import memory.cache.domain.Keyword;
import memory.cache.mem.MemoryStore;
import memory.cache.repository.KeywordRepository;
import memory.cache.util.CompressUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.zip.DataFormatException;

/**
 * memory-cache source code.
 *
 * @Author codeyouth
 * @Version 0.1.0-SNAPSHOT
 * @Date 16/8/27
 */
@Slf4j
@Service
public class KeywordService {
  
  @Autowired
  private KeywordRepository keywordRepository;
  
  public Keyword getOneKeywordInfo(String keyword) {
    byte[] key;
    try {
      key = CompressUtils.compress(keyword.getBytes(), CompressUtils.Level.BEST_COMPRESSION);
    } catch (IOException e) {
      log.error("压缩关键词key失败, keyword: {}", keyword, e);
      return null;
    }
  
    byte[] compressedKeyword = MemoryStore.keywordInfoStore.getOrDefault(key, null);
    
    if (compressedKeyword == null) {
      return keywordRepository.getOneByKeyword(keyword);
    }
    
    String keywordJson;
    try {
      keywordJson = new String(CompressUtils.decompress(compressedKeyword));
    } catch (DataFormatException | IOException e) {
      log.error("解压缩关键词信息失败 keyword: {}", keyword, e);
      return null;
    }
    
    return JSON.parseObject(keywordJson, Keyword.class);
  }
  
}
