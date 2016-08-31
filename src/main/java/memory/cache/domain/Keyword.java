package memory.cache.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * memory-cache source code.
 *
 * @Author codeyouth
 * @Version 0.1.0-SNAPSHOT
 * @Date 16/8/27
 */
@Getter
@Setter
@Table(name = "crawled_word")
@Entity
public class Keyword {
  
  @Id
  @JSONField(serialize = false)
  private Integer id;
  
  /**
   * 关键词
   */
  @Column
  private String keyword;
  
  /**
   * 关键词类型 0:all,1:pc,2:wl
   */
  @Column
  private Integer type;
  
  /**
   * 关键词权重
   */
  @Column
  private Double keywordValue;
  
  /**
   * 点击次数
   */
  @Column
  @JSONField(name = "clickHot")// 点击热度 对应魔方的 点击指数
  private Integer clickNum;
  
  /**
   * 搜索次数
   */
  @Column
  @JSONField(name = "shv")// 搜索热度 对应魔方的 搜索指数
  private Double searchNum;
  
  /**
   * C2C点击数
   */
  @Column
  @JSONField(name = "c2cClick")
  private Integer c2cClick;
  
  /**
   * B2C点击数
   */
  @Column
  @JSONField(name = "b2cClick")
  private Integer b2cClick;
  
  /**
   * 搜索人气
   */
  @Column
  @JSONField(name = "suv")// 搜索人气 对应魔方的 搜索人气
  private Double uniSearchNum;
  
  /**
   * 关联商品数
   */
  @Column
  @JSONField(name = "onlineGoodsCnt")// 在线商品数
  private Integer productNum;
  
  /**
   * 购买率
   */
  @Column
  @JSONField(name = "payConvRate")// 支付转化率
  private Double buyRate;
  
  /**
   * 点击率
   */
  @Column
  private Double clickRate;
  
  /**
   * 直通车价格
   */
  @Column
  @JSONField(name = "p4pAmt")// 直通车参考价 单位分 需要除以100
  private Double chePrice;
  
  /**
   * 创建时间
   */
  @Column
  @JSONField(serialize = false)
  private Date findDate;
  
  /**
   * 更新时间
   */
  @Column
  @JSONField(serialize = false)
  private Date date;
  
  /**
   * 关键词涉及的父类目
   */
  @Column
  private String rootCids;
  
  /**
   * 类目权重
   */
  @Column
  private String catWeights;
  
  /**
   * 类目名称
   */
  @Column
  @JSONField(serialize = false)
  private String catNames;
  
  /**
   * 权重抓取更新时间
   */
  @Column
  @JSONField(serialize = false)
  private Date gmtUpdate;
  
  /**
   * 包含的品牌词所在的类目cid
   */
  @Column
  private String brandCids;
  
  public Double getKeywordValue() {
    this.keywordValue = this.clickNum * 1.0 / (this.productNum == 0 ? 1 : this.productNum);
    return this.keywordValue;
  }
  
  public Double getChePrice() {
    if (this.chePrice == null) {
      this.chePrice = 0D;
    }
    return chePrice;
  }
  
  public void setChePrice(Double chePrice) {
    if (chePrice != null) {
      this.chePrice = chePrice / 100;
    }
  }
  
}
