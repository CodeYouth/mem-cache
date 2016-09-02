package memory.cache.web;

import lombok.extern.slf4j.Slf4j;
import memory.cache.constant.HttpConstants;
import memory.cache.service.KeywordService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * memory-cache source code.
 *
 * @Author codeyouth
 * @Version 0.1.0-SNAPSHOT
 * @Date 16/8/30
 */
@Slf4j
@RestController
@RequestMapping("/keyword")
public class KeywordController {
  
  @Autowired
  private KeywordService keywordService;
  
  @GetMapping
  public void getOneKeywordInfo(@RequestParam("k") String keyword, HttpServletResponse response) {
    if (StringUtils.isEmpty(keyword)) {
      throw new IllegalArgumentException("关键词不能为空");
    }
    
    response.setHeader(HttpConstants.CONTENT_ENCODING, HttpConstants.ENCODING_DEFLATE);
    response.setCharacterEncoding("UTF-8");
    
    byte[] keywordInfo = keywordService.getOneCompressedKeywordInfo(keyword);
    if (keywordInfo == null) {
      keywordInfo = new byte[0];
    }
    OutputStream out;
    try {
      out = response.getOutputStream();
      IOUtils.write(keywordInfo, out);
      out.flush();
      out.close();
    } catch (IOException e) {
      log.error("返回关键词信息异常", e);
    }
  }
  
}
