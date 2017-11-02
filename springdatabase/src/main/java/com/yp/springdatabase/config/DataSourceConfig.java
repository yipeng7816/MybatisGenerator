package com.yp.springdatabase.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.dangdang.ddframe.rdb.sharding.api.ShardingDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.DatabaseShardingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuwf on 17/4/19.
 */
@Configuration
public class DataSourceConfig {
    @Bean
    public DataSource getDataSource() {
        return buildDataSource();
    }

    private DataSource buildDataSource() {
        //设置分库映射 映射5个库
        Map<String, DataSource> dataSourceMap = new HashMap<>(5);
        //添加5个数据库到map里
        dataSourceMap.put("test0", createDataSource("test0"));
        dataSourceMap.put("test1", createDataSource("test1"));
        dataSourceMap.put("test2", createDataSource("test2"));
        dataSourceMap.put("test3", createDataSource("test3"));
        dataSourceMap.put("test4", createDataSource("test4"));
        //设置默认db为test0，也就是为那些没有配置分库分表策略的指定的默认库
        //如果只有一个库，也就是不需要分库的话，map里只放一个映射就行了，只有一个库时不需要指定默认库，但2个及以上时必须指定默认库，否则那些没有配置策略的表将无法操作数据
        DataSourceRule dataSourceRule = new DataSourceRule(dataSourceMap, "test0");

        //设置分表映射，将basic_user和basic_user两个实际的表映射到t_order逻辑表
        //0和1两个表是真实的表，basic_user是个虚拟不存在的表，只是供使用。如查询所有数据就是select * from basic就能查完0和1表的
        TableRule orderTableRule = TableRule.builder("basic_user")
                //配置List分表
                .actualTables(Arrays.asList("basic_user"))
                .dataSourceRule(dataSourceRule)
                .build();

        //具体分库分表策略，按什么规则来分
        ShardingRule shardingRule = ShardingRule.builder()
                .dataSourceRule(dataSourceRule)
                .tableRules(Arrays.asList(orderTableRule))
                .databaseShardingStrategy(new DatabaseShardingStrategy("id", new ModuloDatabaseShardingAlgorithm())).build();
                //.tableShardingStrategy(new TableShardingStrategy("id", new ModuloTableShardingAlgorithm()))



        DataSource dataSource = ShardingDataSourceFactory.createDataSource(shardingRule);

        return dataSource;
    }

    private static DataSource createDataSource(final String dataSourceName) {
        //使用druid连接数据库
        DruidDataSource result = new DruidDataSource();
        result.setDriverClassName("com.mysql.jdbc.Driver");
        result.setUrl(String.format("jdbc:mysql://localhost:3306/%s", dataSourceName));
        result.setUsername("root");
        result.setPassword("root");
        return result;
    }
}