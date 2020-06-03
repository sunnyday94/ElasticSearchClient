/**
 * FileName: ElasticSearchClientTest Author:   sunny Date:     2020/5/13 10:36 History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sunny.practice.es.test;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * 〈一句话功能简述〉<br> 
 *
 * @author sunny
 * @create 2020/5/13
 * @since 1.0.0
 */
@Slf4j
public class ElasticSearchClientTest {

    private Settings settings;
    private TransportClient client;

    @Before
    public void initConfig() throws Exception {
        //1.创建一个Settings对象，相当于一个配置信息,主要配置集群的名称
        settings = Settings.builder().put("cluster.name","sunny-application").build();
        //2.创建一个客户端Client对象
        client = new PreBuiltTransportClient(settings  );
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9301));
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9302));
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9303));
    }

    @Test
    public void createIndex(){
        log.info(">>>>>>创建索引库");
        //使用client对象创建索引库
        client.admin().indices().prepareCreate("index_hello")
                //执行操作
                .get();
        //关闭client对象
        client.close();
        log.info(">>>>>>索引库创建完毕");
    }



    @Test
    public void setMappings() throws IOException {
        log.info(">>>>>>设置mappings");
        //设置mappings信息
        /*
            {
                "artical":{
                    "properties":{
                        "id":{
                            "type":"long",
                            "store":true
                        },
                        "title":{
                            "type":"text",
                            "store":true,
                            "index":true,
                            "analyzer":"ik_smart"
                        },
                        "content":{
                            "type":"text",
                            "store":true,
                            "index":true,
                            "analyzer":"ik_smart"
                        }
                    }
                }
            }
        */
        client.admin().indices()
                //设置索引
                .preparePutMapping("index_hello")
                //设置类型
                .setType("artical")
                //设置mapping映射
                .setSource("{\"artical\":{\"properties\":{\"id\":{\"type\":\"long\",\"store\":true},\"title\":{\"type\":\"text\",\"store\":true,\"index\":true,\"analyzer\":\"standard\"},\"content\":{\"type\":\"text\",\"store\":true,\"index\":true,\"analyzer\":\"standard\"}}}}").get();
        //关闭客户端
        client.close();
        log.info(">>>>>>mappings设置完毕");
    }



    @Test
    public void insertDocument() throws Exception{
        log.info(">>>>>>创建文档记录");
        client.prepareIndex("index_hello", "artical", "1")
                .setSource("{\"id\":1,\"title\":\"id为1的title\",\"content\":\"id为1的content\"}").get();
        client.prepareIndex("index_hello", "artical", "2")
                .setSource("{\"id\":2,\"title\":\"id为2的title\",\"content\":\"id为2的content\"}").get();
        client.prepareIndex("index_hello", "artical", "3")
                .setSource("{\"id\":3,\"title\":\"id为3的title\",\"content\":\"id为3的content\"}").get();


        client.prepareIndex("index_hello", "artical", "4")
                .setSource("{\"id\":4,\"title\":\"id为4的title\",\"content\":\"id为4的content\"}").get();
        client.prepareIndex("index_hello", "artical", "5")
                .setSource("{\"id\":5,\"title\":\"id为5的title\",\"content\":\"id为5的content\"}").get();
        client.prepareIndex("index_hello", "artical", "6")
                .setSource("{\"id\":6,\"title\":\"id为6的title\",\"content\":\"id为6的content\"}").get();


        client.prepareIndex("index_hello", "artical", "7")
                .setSource("{\"id\":7,\"title\":\"id为7的title\",\"content\":\"id为7的content\"}").get();
        client.prepareIndex("index_hello", "artical", "8")
                .setSource("{\"id\":8,\"title\":\"id为8的title\",\"content\":\"id为8的content\"}").get();
        client.prepareIndex("index_hello", "artical", "9")
                .setSource("{\"id\":9,\"title\":\"id为9的title\",\"content\":\"id为9的content\"}").get();


        client.prepareIndex("index_hello", "artical", "10")
                .setSource("{\"id\":10,\"title\":\"id为10的title\",\"content\":\"id为10的content\"}").get();
        client.prepareIndex("index_hello", "artical", "11")
                .setSource("{\"id\":11,\"title\":\"id为11的title\",\"content\":\"id为11的content\"}").get();
        client.prepareIndex("index_hello", "artical", "12")
                .setSource("{\"id\":12,\"title\":\"id为12的title\",\"content\":\"id为12的content\"}").get();


        client.prepareIndex("index_hello", "artical", "13")
                .setSource("{\"id\":13,\"title\":\"id为13的title\",\"content\":\"id为13的content\"}").get();
        client.prepareIndex("index_hello", "artical", "14")
                .setSource("{\"id\":14,\"title\":\"id为14的title\",\"content\":\"id为14的content\"}").get();
        client.prepareIndex("index_hello", "artical", "15")
                .setSource("{\"id\":15,\"title\":\"id为15的title\",\"content\":\"id为15的content\"}").get();
        client.close();
        log.info(">>>>>>文档创建完毕");
    }


    //根据id查询
    @Test
    public void queryById() throws Exception{
        //创建查询对象
        QueryBuilder queryBuilder = QueryBuilders.idsQuery().addIds("1","2");
        search(queryBuilder);
        client.close();
    }


    //根据关键词查询
    @Test
    public void queryForTerm() throws Exception{
        QueryBuilder queryBuilder = QueryBuilders.termQuery("content", "content");
        search(queryBuilder);
        client.close();
    }


    //带分析器的查询
    @Test
    public void queryForQueryString() throws Exception{
        QueryBuilder queryBuilder = QueryBuilders.queryStringQuery("1").defaultField("content");
        search(queryBuilder,"content");
    }


    //带分页搜索
    private void search(QueryBuilder queryBuilder){
        //通过client执行查询
        SearchResponse searchResponse = client.prepareSearch("index_hello").setTypes("artical")
                .setQuery(queryBuilder)
                //分页处理,设置分页信息,然后再执行查询,默认每页显示十条记录
                 //from起始行号(从0开始),size每页条目数
                .setFrom(0).setSize(15)
                .get();
        //取得查询结果
        SearchHits hits = searchResponse.getHits();
        log.info(">>>>>>>查询结果总记录数:{}",hits.getTotalHits());
        //查询结果列表遍历
        for (SearchHit searchHit : hits) {
            log.info(">>>>>>文档结果:{}", searchHit.getSourceAsString());
        }
    }


    //带分页搜索(高亮显示)
    private void search(QueryBuilder queryBuilder,String highlightField){
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field(highlightField);
        highlightBuilder.preTags("<em>");
        highlightBuilder.postTags("</em>");

        //通过client执行查询
        SearchResponse searchResponse = client.prepareSearch("index_hello").setTypes("artical")
                .setQuery(queryBuilder)
                //分页处理,设置分页信息,然后再执行查询,默认每页显示十条记录
                //from起始行号(从0开始),size每页条目数
                .setFrom(0).setSize(15)
                //设置高亮显示
                .highlighter(highlightBuilder)
                .get();
        //取得查询结果
        SearchHits hits = searchResponse.getHits();
        log.info(">>>>>>>查询结果总记录数:{}",hits.getTotalHits());
        //查询结果列表遍历
        for (SearchHit searchHit : hits) {
            log.info(">>>>>>文档结果:{}", searchHit.getSourceAsString());
            log.info("******************高量结果************************");
            Map<String, HighlightField> highlight = searchHit.getHighlightFields();
            log.info(highlight.toString());
        }
    }
}
