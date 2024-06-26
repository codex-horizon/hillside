package com.metaverse.hillside.core.jpa;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Objects;

/**
 * DataSource数据源
 * EntityManager 实体管理器
 * EntityManagerFactoryBean 实体管理器工厂
 * PlatformTransactionManager 事务管理器
 */
@Configuration
@EnableJpaRepositories(
        // 配置连接工厂
        entityManagerFactoryRef = "entityManagerFactoryRefSlave",
        // 配置事物管理器
        transactionManagerRef = "transactionManagerRefSlave",
        // 设置持久层所在位置
        basePackages = {"com.metaverse.hillside.work.repository.slave"}
)
@EnableTransactionManagement
public class JpaManagerSlave {

    private final JpaProperties jpaProperties;

    private final DataSource slaveDataSource;

    JpaManagerSlave(final JpaProperties jpaProperties,
                    @Qualifier("slaveDataSource") DataSource slaveDataSource) {
        this.jpaProperties = jpaProperties;
        this.slaveDataSource = slaveDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryRefSlave(EntityManagerFactoryBuilder builder) {
        return builder
                // 设置数据源
                .dataSource(slaveDataSource)
                // 设置数据源属性
                .properties(jpaProperties.getProperties())
                // 设置实体类所在位置。扫描所有带有 @Entity 注解的类
                .packages("com.metaverse.hillside.work.entity.slave")
                // Spring会将EntityManagerFactory注入到Repository之中。有了EntityManagerFactory之后，
                // Repository就能用它来创建EntityManager了，然后EntityManager就可以针对数据库执行操作。
                .persistenceUnit("slavePersistenceUnit")
                .build();
    }

//    @Bean
//    public EntityManager entityManagerSlave(EntityManagerFactoryBuilder builder) {
//        return Objects.requireNonNull(entityManagerFactoryRefSlave(builder).getObject()).createEntityManager();
//    }

    @Bean
    public PlatformTransactionManager transactionManagerRefSlave(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactoryRefSlave(builder).getObject()));
    }

}
