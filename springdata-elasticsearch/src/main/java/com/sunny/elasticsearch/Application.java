/**
 * FileName: Application Author:   sunny Date:     2020/5/14 11:17 History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sunny.elasticsearch;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 〈一句话功能简述〉<br> 
 *
 * @author sunny
 * @create 2020/5/14
 * @since 1.0.0
 */
@SpringBootApplication(scanBasePackages = "com.sunny.elasticsearch",exclude = DataSourceAutoConfiguration.class)
@MapperScan(basePackages = "com.sunny.elasticsearch.dao")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
