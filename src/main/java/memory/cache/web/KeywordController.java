package memory.cache.web;

import memory.cache.domain.Keyword;
import memory.cache.service.KeywordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * memory-cache source code.
 *
 * @Author codeyouth
 * @Version 0.1.0-SNAPSHOT
 * @Date 16/8/30
 */
@RestController
@RequestMapping("/keyword")
public class KeywordController {
  
  @Autowired
  private KeywordService keywordService;
  
  @GetMapping
  public Keyword getOneKeywordInfo(@RequestParam("k") String keyword) {
    if (StringUtils.isEmpty(keyword)) {
      throw new IllegalArgumentException("关键词不能为空");
    }
    
    return keywordService.getOneKeywordInfo(keyword);
  }
  
}
