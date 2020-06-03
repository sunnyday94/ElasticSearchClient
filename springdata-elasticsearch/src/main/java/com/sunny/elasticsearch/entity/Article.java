/**
 * FileName: Article Author:   sunny Date:     2020/5/14 13:36 History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sunny.elasticsearch.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 〈一句话功能简述〉<br> 
 *
 * @author sunny
 * @create 2020/5/14
 * @since 1.0.0
 */
@Document(indexName = "spring_data_es_blog",type="article",shards = 6,replicas = 1)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    @Id
    @Field(type = FieldType.Long,store = true)
    private Long id;

    @Field(type = FieldType.Text,store = true,analyzer = "ik_max_word")
    private String title; //标题

    @Field(type = FieldType.Text,store = true,analyzer = "ik_max_word")
    private String content; // 内容
}
