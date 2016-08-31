package memory.cache.task;

/**
 * memory-cache source code.
 *
 * @Author codeyouth
 * @Version 0.1.0-SNAPSHOT
 * @Date 16/8/31
 */
public interface CronTask extends Runnable {
  
  /**
   * 返回定时任务 pattern
   * pattern : * * * * *
   * 1. Minutes sub-pattern.
   * 2. Hours sub-pattern.
   * 3. Days of month sub-pattern.
   * 4. Months sub-pattern.
   * 5. Days of week sub-pattern. "sun","mon","tue","wed","thu","fri","sat
   *
   * The star wildcard character is also admitted,indicating "every minute of the hour", "every hour of the day",
   * "every day of the month", "every month of the year", "every day of the week".
   *
   * Some examples:
   *
   * 5 * * * * : This pattern causes a task to be launched once every hour, at the begin of the fifth minute(0:05, 01:05, 02:05 etc.).
   *
   * * * * * * : This pattern causes a task to be launched every minute.
   *
   * * 12 * * Mon : This pattern causes a task to be launched every minute during the 12th hour of Monday.
   *
   * * 12 16 * Mon : This pattern causes a task to be launched every minute during the 12th hour of Monday, 16th, but only if the day is the 16th of the month.
   *
   * (Every sub-pattern can contain two or more comma separated values.)
   *
   * 59 11 * * 1,2,3,4,5 : This pattern causes a task to be launched at 11:59AM on Monday, Tuesday, Thursday and Friday.
   *
   * (Values intervals are admitted and defined using the minus character.)
   *
   * 59 11 * * 1-5 : This pattern is equivalent to the previous one.
   *
   * (The slash character can be used to identify step values within a range. It can be used both in the form *\/c and a-b/c. The sub-patterns is matched every c values of the range 0, maxvalue or a-b.)
   *
   * *\/5 * * * * : This pattern causes a task to be launched every 5 minutes(0:00, 0:05, 0:10, 0:15 and so on).
   *
   * 3-18/5 * * * * : This pattern causes a task to be launched every 5 minutes starting from the third minute of the hour, up to the 18th(0:03,0:08,0:13,0:18,1:03,1:08 and so on).
   *
   * *\/15 9-17 * * * : This pattern causes a task to be launched every 15 minutes between the 9th and 17th hour of the day(9:00,9:15,9:30,9:45 and so on... note that the last execution will be at 17:45).
   *
   * (All the fresh described syntax rules can be used together.)
   *
   * * 12 10-16/2 * * : This pattern causes a task to be launched every minute during the 12th hour of the day, but if the day is the 10th, the 12th, the 14th or the 16th of the month.
   *
   * * 12 1-15,17,20-25 * * : This patter causes a task to be launched every minute during the 12th hour of the day, but the day of the month must be between the 1st and the 15th, the 20th and the 25th, or at least it must be the 17th.
   *
   * (Finally cron4j lets you combine more scheduling patterns into one, with the pipe character)
   *
   * 0 5 * * *|8 10 * * *|22 17 * * * : This pattern causes a task to be launched every day at 05:00, 10:08 and 17:22.
   *
   * @return Pattern
   */
  String getCronPattern();
  
}