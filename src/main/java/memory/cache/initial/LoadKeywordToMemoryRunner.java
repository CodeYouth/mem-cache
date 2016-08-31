package memory.cache.initial;

import lombok.extern.slf4j.Slf4j;
import memory.cache.mem.KeywordLoader;
import memory.cache.mem.MemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * memory-cache source code.
 *
 * @Author codeyouth
 * @Version 0.1.0-SNAPSHOT
 * @Date 16/8/27
 */
@Slf4j
@Component
public class LoadKeywordToMemoryRunner implements CommandLineRunner {
  
  @Autowired
  private KeywordLoader keywordLoader;
  
  @Override
  public void run(String... args) throws Exception {
    if (MemoryStore.keywordInfoStore.isEmpty()) {
      keywordLoader.load();
    }
  }
  
}
