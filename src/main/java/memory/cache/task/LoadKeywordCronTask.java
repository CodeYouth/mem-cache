package memory.cache.task;

import lombok.extern.slf4j.Slf4j;
import memory.cache.mem.KeywordLoader;
import memory.cache.mem.MemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * memory-cache source code.
 *
 * @Author codeyouth
 * @Version 0.1.0-SNAPSHOT
 * @Date 16/8/31
 */
@Slf4j
@Component
public class LoadKeywordCronTask implements CronTask {
  
  @Autowired
  private KeywordLoader keywordLoader;
  
  @Override
  public String getCronPattern() {
    return "*/5 * * * *";
  }
  
  @Override
  public void run() {
    if (!MemoryStore.keywordInfoStore.isEmpty()) {
      return;
    }
    
    keywordLoader.load();
  }
  
}
