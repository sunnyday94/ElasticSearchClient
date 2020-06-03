/**
 * FileName: SpringDataElasticsearchTest Author:   sunny Date:     2020/5/14 13:43 History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

import com.sunny.elasticsearch.Application;
import com.sunny.elasticsearch.dao.ArticleRepository;
import com.sunny.elasticsearch.entity.Article;
import org.assertj.core.util.Lists;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

/**
 * 〈一句话功能简述〉<br> 
 * 测试基础的增删改查
 * 更多操作可自行摸索
 * @author sunny
 * @create 2020/5/14
 * @since 1.0.0
 */
@SpringBootTest(classes = Application.class,properties = "classpath:application.yml")
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class SpringDataElasticsearchTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ElasticsearchTemplate esTemplate;

    /**
     * @Author sunny
     * @Description  创建索引
     * @Date 15:23 2020/5/14
     * @Param []
     * @return void
     */
    @Test
    public void createIndex() throws Exception{
        log.info(">>>>>>创建索引");
        //创建索引，并配置映射关系
        esTemplate.createIndex(Article.class);
        log.info(">>>>>>索引创建完毕");
    }

    /**
     * @Author sunny
     * @Description  删除索引
     * @Date 15:23 2020/5/14
     * @Param []
     * @return void
     */
    @Test
    public void deleteIndex() throws Exception{
        log.info(">>>>>删除索引");
        //传递实体,删除索引
        esTemplate.deleteIndex(Article.class);
        log.info(">>>>>>索引删除完毕");
    }

    /**
     * @Author sunny
     * @Description  批量添加文档
     * @Date 15:23 2020/5/14
     * @Param []
     * @return void
     */
    @Test
    public void batchAddDocument() throws Exception{
        List<Article> articleList = Lists.newArrayList();
        Article a1 = new Article(1L, "Maven项目对象模型", "Maven是一个采用纯JAVA编写的开源项目管理工具.");
        Article a2 = new Article(2L,"小米手机", "小米10虽然已经问世,但是真正的旗舰手机,应该是还没有发布的小米mix4,你期待吗?");
        Article a3 = new Article(3L,"多人运动","自从周扬青和罗志祥的分手，\"多人运动\"这个词马上也火了起来,你了解了吗，嘿嘿嘿");
        Article a4 = new Article(4L,"就是一条测试数据","此数据只是单纯的做测试,一会儿我就用删除方法删了这条数据");
        log.info(">>>>>>添加文档");
        articleList.add(a1);
        articleList.add(a2);
        articleList.add(a3);
        articleList.add(a4);
        articleRepository.saveAll(articleList);
        log.info(">>>>>>文档添加完毕");
    }


    /**
     * @Author sunny
     * @Description  根据id删除文档
     * @Date 15:23 2020/5/14
     * @Param []
     * @return void
     */
    @Test
    public void deleteDocumentById() throws Exception{
        log.info(">>>>>>根据id删除文档");
        articleRepository.deleteById(4L);
        //删除全部文档
//        articleRepository.deleteAll();
        log.info(">>>>>>>文档删除完毕");
    }


    /**
     * @Author sunny
     * @Description  根据id更新文档
     * 所谓的更新文档,底层是先删除文档，然后再添加新文档,即为更新操作，
     * 故想要做到更新，添加一条即可
     * @Date 15:25 2020/5/14
     * @Param []
     * @return void
     */
    @Test
    public void updateDocumentById() throws Exception{
        log.info(">>>>>>根据id更新文档");
        articleRepository.save(new Article(4L, "现在这是一条正式数据，不要删不要删", "现在变成了正式数据内容了!"));
        log.info(">>>>>>文档更新完毕");
    }

    /**
     * @Author sunny
     * @Description  查询全部数据
     * @Date 15:34 2020/5/14
     * @Param []
     * @return void
     */
    @Test
    public void selectAll() throws Exception{
        log.info(">>>>>>查询全部数据");
        Iterable<Article> all = articleRepository.findAll();
        all.forEach(article -> log.info(article.toString()));
        log.info(">>>>>>所有数据查询完毕");
    }


    /**
     * @Author sunny
     * @Description  根据id查询数据
     * @Date 15:36 2020/5/14
     * @Param []
     * @return void
     */
    @Test
    public void selectById() throws Exception{
        log.info(">>>>>根据id查询数据");
        Optional<Article> optional = articleRepository.findById(3L);
        log.info(optional.isPresent()?optional.get().toString():"未查到id为3L的结果");
        log.info(">>>>>>根据id查询数据完毕,");
    }


    /*
    自定义方法
    Spring Data 的另一个强大功能，是根据方法名称自动实现功能。
    比如：你的方法名叫做：findByTitle，那么它就知道你是根据title查询，然后自动帮你完成，无需写实现类。
    当然，方法名称要符合一定的约定：

    Keyword	                                Sample
    And	                                    findByNameAndPrice
    Or	                                    findByNameOrPrice
    Is	                                    findByName
    Not	                                    findByNameNot
    Between	                                findByPriceBetween
    LessThanEqual	                        findByPriceLessThan
    GreaterThanEqual	                    findByPriceGreaterThan
    Before	                                findByPriceBefore
    After	                                findByPriceAfter
    Like	                                findByNameLike
    StartingWith	                        findByNameStartingWith
    EndingWith	                            findByNameEndingWith
    Contains/Containing	                    findByNameContaining
    In	                                    findByNameIn(Collection<String>names)
    NotIn	                                findByNameNotIn(Collection<String>names)
    Near	                                findByStoreNear
    True	                                findByAvailableTrue
    False	                                findByAvailableFalse
    OrderBy	                                findByAvailableTrueOrderByNameDesc
     */


    /**
     * @Author sunny
     * @Description  自定义查询【根据content查询】
     * 如果不设置分页信息,默认带分页,每页显示10条数据
     * 如果设置分页信息，应该在方法中添加一个参数Pageable
     * 如：Pageable pageable = PageRequest.of(0,15);
     * 设置分页信息，默认是从0页开始
     * @Date 15:49 2020/5/14
     * @Param []
     * @return void
     */
    @Test
    public void selectByCustomer(){
        log.info(">>>>>>>执行自定义查询,根据title查询数据");
        List<Article> articles = articleRepository.findByContent("这个");
        articles.forEach(article -> log.info(article.toString()));
        log.info(">>>>>>>>自定义查询执行完毕");
    }


    /**
     * @Author sunny
     * @Description  使用原生的查询条件查询
     * @Date 16:04 2020/5/14
     * @Param []
     * @return void
     */
    @Test
    public void selectForNativeSearchQuery() throws Exception{
        log.info(">>>>>>>>执行原生查询");
            //1.创建一个NativeSearchQuery对象
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.queryStringQuery("一个").defaultField("content"))
                .withPageable(PageRequest.of(0,15))
                .build();

           //2.使用ElasticsearchTemplate对象执行查询
        List<Article> articles = esTemplate.queryForList(query, Article.class);
        articles.forEach(article -> log.info(article.toString()));
        log.info(">>>>>>>>原生查询执行完毕");
    }
}
