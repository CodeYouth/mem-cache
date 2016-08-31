package memory.cache.initial;

import it.sauronsoftware.cron4j.Scheduler;
import lombok.extern.slf4j.Slf4j;
import memory.cache.pojo.ApplicationContextHolder;
import memory.cache.task.CronTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * memory-cache source code.
 *
 * @Author codeyouth
 * @Version 0.1.0-SNAPSHOT
 * @Date 16/8/31
 */
@Slf4j
@Order(0)
@Component
public class LoadCronTaskRunner implements CommandLineRunner {
  
  private Scheduler scheduler = new Scheduler();
  
  @Autowired
  private ApplicationContextHolder contextHolder;
  
  @Override
  public void run(String... args) throws Exception {
    log.info("开始加载 CronTasks ...");
    Map<String, CronTask> cronTaskMap = contextHolder.getContext().getBeansOfType(CronTask.class);
    cronTaskMap.forEach((name, task) -> {
      String pattern = task.getCronPattern();
      scheduler.schedule(pattern, task);
      log.info("加载 [{}] 成功, pattern: {}", name, pattern);
    });
    log.info("CronTask 加载完成");
  }
  
}
