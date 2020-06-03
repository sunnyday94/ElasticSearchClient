/**
 * FileName: ArticleRepository Author:   sunny Date:     2020/5/14 13:40 History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sunny.elasticsearch.dao;

import com.sunny.elasticsearch.entity.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 *
 * @author sunny
 * @create 2020/5/14
 * @since 1.0.0
 */
public interface ArticleRepository extends ElasticsearchRepository<Article,Long> {

    /**
     * @Author sunny
     * @Description  自定义查询(根据content查询数据)
     * @Date 16:13 2020/5/14
     * @Param [content]
     * @return java.util.List<com.sunny.elasticsearch.entity.Article>
     */
    List<Article> findByContent(String content);
}
