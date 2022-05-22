package com.metaverse.hillside.core.env;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Data
@Configuration
@PropertySource("classpath:db.properties")
public class DbProperties {

//    @Primary
//    @Bean(name = "masterDataSource")
//    @Qualifier("masterDataSource")
////    @ConfigurationProperties(prefix = "spring.datasource.master")
//    public DataSource masterDataSource(DataSourceProperties properties) {
////        log.info("init master data source={}", properties);
////        return DataSourceBuilder.create().type().build();
//        HikariConfig hikariConfig = new HikariConfig();
//        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        hikariConfig.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/hillside_master?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai");
//        hikariConfig.setUsername("root");
//        hikariConfig.setPassword("root");
//        HikariDataSource masterDataSource = new HikariDataSource(hikariConfig);
//        return masterDataSource;
//    }

//    @Bean(name = "slaveDataSource")
//    @Qualifier("slaveDataSource")
////    @ConfigurationProperties(prefix = "spring.datasource.slave")
//    public DataSource slaveDataSource(DataSourceProperties properties) {
////        log.info("init slave data source={}", properties);
////        return DataSourceBuilder.create().build();
//        HikariConfig hikariConfig = new HikariConfig();
//        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        hikariConfig.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/hillside_slave?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai");
//        hikariConfig.setUsername("root");
//        hikariConfig.setPassword("root");
//        HikariDataSource slaveDataSource = new HikariDataSource(hikariConfig);
//        return slaveDataSource;
//    }

    /**
     * 在实例化 RoutingMultipleDataSourceSelector 之前，需要保证 master和 slave 数据源已注入IoC容器
     */
    @Bean
    public AbstractRoutingDataSource routingMultipleDataSourceSelector(
            @Qualifier("masterDataSource") DataSource masterDataSource,
            @Qualifier("slaveDataSource") DataSource slaveDataSource
    ) {
        Map<Object, Object> targetDataSources = new HashMap<>(2);
        targetDataSources.put(SpecifyDataSourceEnum.MASTER, masterDataSource);
        targetDataSources.put(SpecifyDataSourceEnum.SLAVE, slaveDataSource);

        RoutingMultipleDataSourceSelector routingMultipleDataSourceSelector = new RoutingMultipleDataSourceSelector();
        routingMultipleDataSourceSelector.setDefaultTargetDataSource(masterDataSource);
        routingMultipleDataSourceSelector.setTargetDataSources(targetDataSources);
        return routingMultipleDataSourceSelector;
    }
}