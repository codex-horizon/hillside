package com.metaverse.hillside.core.env;

import com.metaverse.hillside.common.constants.SpecifyDataSourceEnum;
import com.metaverse.hillside.core.configurer.database.RoutingMultipleDataSourceSelector;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Data
@Configuration
@PropertySource("classpath:db.properties")
public class DbProperties {

    @Primary
    @Bean(name = "masterDataSource")
    @Qualifier("masterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource(DataSourceProperties properties) {
        log.info("init master data source={}", properties);
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "slaveDataSource")
    @Qualifier("slaveDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDataSource(DataSourceProperties properties) {
        log.info("init slave data source={}", properties);
        return DataSourceBuilder.create().build();
    }

    /**
     * 在实例化 RoutingMultipleDataSourceSelector 之前，需要保证 master和 slave 数据源已注入IoC容器
     */
    @Bean
    public RoutingMultipleDataSourceSelector routingMultipleDataSourceSelector(
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