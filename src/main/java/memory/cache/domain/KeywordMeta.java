package memory.cache.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * memory-cache source code.
 *
 * @Author codeyouth
 * @Version 0.1.0-SNAPSHOT
 * @Date 16/8/29
 */
@Getter
@Setter
@Table(name = "crawled_word")
@Entity
public class KeywordMeta {
  
  @Id
  private Integer id;
  
  @Column
  private String keyword;
}
