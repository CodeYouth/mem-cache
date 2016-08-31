package memory.cache.repository;

import memory.cache.domain.Keyword;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * memory-cache source code.
 *
 * @Author codeyouth
 * @Version 0.1.0-SNAPSHOT
 * @Date 16/8/27
 */
public interface KeywordRepository extends CrudRepository<Keyword, Integer> {
  
  String TABLE_NAME = "crawled_word";
  
  String ALL_COLUMNS = "id, keyword, keyword_value, click_num, search_num, c2c_click, b2c_click, uni_search_num, " +
      "product_num, buy_rate, click_rate, che_price, date, find_date, root_cids, cat_weights, cat_names, gmt_update, " +
      "brand_cids, type";
  
  @Query(nativeQuery = true, value = "SELECT " + ALL_COLUMNS + " FROM " + TABLE_NAME + " WHERE id > ? AND keyword != '' ORDER BY id ASC LIMIT ?")
  List<Keyword> getListByOptimizedPage(int id, int size);
  
  /*@Query(nativeQuery = true, countQuery = "SELECT COUNT(keyword) AS total FROM " + TABLE_NAME + " WHERE keyword != ''")
  int totalRecords();*/
  
  @Query(nativeQuery = true, value = "SELECT id, keyword FROM " + TABLE_NAME + " WHERE id > ? AND keyword != '' ORDER BY id ASC LIMIT ?")
  List<Keyword> getKeywordListByOptimizedPage(int id, int size);
  
  @Query(nativeQuery = true, value = "SELECT " + ALL_COLUMNS + " FROM " + TABLE_NAME + " WHERE keyword = ?")
  Keyword getOneByKeyword(String keyword);
  
}
