//package com.meixin.bid_admin.mybatis.config;
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.core.io.support.ResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.TransactionManagementConfigurer;
//
//import javax.sql.DataSource;
//import java.util.Properties;
//
///**
// * MyBatis基础配置
// */
//@Configuration
////@EnableTransactionManagement
//public class MyBatisConfig implements TransactionManagementConfigurer {
//
//    @Autowired
//    DataSource dataSource;
//
//    @Bean(name = "sqlSessionFactory")
//    @ConditionalOnMissingBean
//    public SqlSessionFactory sqlSessionFactoryBean() {
//        final SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        try {
//            bean.setDataSource(dataSource);
//            bean.setTypeAliasesPackage("com.meixin.bid_admin.entity");
////            bean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
//            Properties properties = new Properties();
//            properties.setProperty("logImpl", "SLF4J");
//            properties.setProperty("mapUnderscoreToCamelCase", "true");
//            bean.setConfigurationProperties(properties);
//            //分页插件设置
//    //        PageHelper pageHelper = new PageHelper();
//    //        Properties pageHelperProperties = new Properties();
//    //        pageHelperProperties.setProperty("reasonable", "true");
//    //        pageHelperProperties.setProperty("supportMethodsArguments", "true");
//    //        pageHelperProperties.setProperty("returnPageInfo", "check");
//    //        pageHelperProperties.setProperty("params", "count=countSql");
//    //        pageHelper.setProperties(pageHelperProperties);
//    //
//    //        //添加分页插件
//    //        bean.setPlugins(new Interceptor[]{pageHelper});
//
//            //添加XML目录
//            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//            //基于注解扫描Mapper，不需配置xml路径
//            bean.setMapperLocations(resolver.getResources("classpath*:mapper/*.xml"));
//            return bean.getObject();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("mybatis加载xml的mapper出错", e);
//        }
//    }
//
//    @Bean
//    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//
//    @Bean
//    public PlatformTransactionManager annotationDrivenTransactionManager() {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//}
