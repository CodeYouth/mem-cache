package memory.cache.mem;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import memory.cache.domain.Keyword;
import memory.cache.repository.KeywordRepository;
import memory.cache.util.CompressUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rx.Observable;
import rx.schedulers.Schedulers;
import rx.util.async.Async;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * memory-cache source code.
 *
 * @Author codeyouth
 * @Version 0.1.0-SNAPSHOT
 * @Date 16/8/31
 */
@Slf4j
@Component
public class KeywordLoader {
  
  private static final int DEFAULT_SIZE = 1000;
  
  private static Semaphore insertLock = new Semaphore(1);
  
  @Autowired
  private KeywordRepository keywordRepository;
  
  public void load() {
    log.info("加载关键词数据到内存开始...");
    int id = 0;
    int loadCount = 0;
    Long totalRecords = this.keywordRepository.count();
    
    printMemoryUsage();
    
    while (true) {
      List<Keyword> keywords = this.keywordRepository.getListByOptimizedPage(id, DEFAULT_SIZE);
      int count = keywords.size();
      if (count == 0) {
        break;
      }
      
      loadCount += count;
      id = keywords.get(count - 1).getId();
      
      asyncInsert(keywords, 100);
      
      log.info("已经加载关键词数: {}  总词数: {}  加载进度: {}%", loadCount, totalRecords, loadCount * 100 / totalRecords.intValue());
    }
    
    log.info("加载关键词数据成功完成!!!");
    
    printMemoryUsage();
  }
  
  private void insertToMemory2(List<Keyword> keywords) {
    keywords.forEach(word -> {
      String keyword = word.getKeyword();
      if (StringUtils.isNotEmpty(keyword)) {
        try {
          byte[] compressed = CompressUtils.compress(keyword.getBytes(), CompressUtils.Level.BEST_COMPRESSION);
          log.debug("压缩前关键词大小: {}  压缩后大小: {}", keyword.getBytes().length, compressed.length);
          MemoryStore.keywordInfoStore.put(compressed, new byte[]{0});
        } catch (IOException e) {
          log.error("写入内存失败 keyword: {}", keyword, e);
        }
      }
    });
    printMemoryUsage();
  }
  
  private void asyncInsert(List<Keyword> keywords, int bufferSize) {
    try {
      insertLock.acquire();
    } catch (InterruptedException e) {
      log.error(e.getMessage(), e);
    }
    Observable.from(keywords)
        .buffer(bufferSize)
        .flatMap(buffer -> Async.fromAction(() -> insertToMemory(buffer), null, Schedulers.computation()))
        .count()
        .subscribe(i -> insertLock.release());
    
    printMemoryUsage();
  }
  
  private void insertToMemory(List<Keyword> keywords) {
    keywords.forEach(keyword -> {
      String key = keyword.getKeyword();
      String json = JSON.toJSONString(keyword);
      if (StringUtils.isNotEmpty(json) && StringUtils.isNotEmpty(key))
        try {
          byte[] compressedKey = CompressUtils.compress(key.getBytes(), CompressUtils.Level.BEST_COMPRESSION);
          byte[] bestCompressed = CompressUtils.compress(json.getBytes(), CompressUtils.Level.BEST_COMPRESSION);
          log.debug("压缩前Json大小: {}  最佳压缩后大小: {}", json.getBytes().length, bestCompressed.length);
          MemoryStore.keywordInfoMapStore.put(compressedKey, bestCompressed);
        } catch (IOException e) {
          log.error("写入内存失败  keyword: {}", keyword, e);
        }
    });
  }
  
  private void printMemoryUsage() {
    Runtime runtime = Runtime.getRuntime();
    long max = runtime.maxMemory() >> 10 >> 10;
    long total = runtime.totalMemory() >> 10 >> 10;
    long free = runtime.freeMemory() >> 10 >> 10;
    long usable = max - total + free;
    
    log.info("最大内存: {}M", max);
    log.info("已分配内存: {}M", total);
    log.info("已分配内存剩余空间: {}M", free);
    log.info("最大可用内存: {}M", usable);
  }
  
}
