package com.example.demoes;

import com.example.demoes.es.TestEntity;
import com.example.demoes.repository.TestEntityRepository;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.sum.InternalSum;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoesApplicationTests {

    @Autowired
    TestEntityRepository testEntityRepository;
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;
    @Test
    public void contextLoads() {
        TestEntity testEntity=new TestEntity();
        testEntity.setName("xyb");
        testEntityRepository.save(testEntity);
        TestEntity result=testEntityRepository.findOne("xyb");
        result.setAge(1);
        result.setDate(new Date());
        testEntityRepository.save(result);
        Page<TestEntity> testEntityList=testEntityRepository.findAll(new PageRequest(0,1));
        Assert.assertEquals(1,testEntityList.getContent().size());
        testEntityRepository.delete("xyb");
        testEntityList=testEntityRepository.findAll(new PageRequest(0,1));
        Assert.assertEquals(0,testEntityList.getTotalPages());

    }
    @Test
    public void testAgg(){

        TestEntity testEntity=new TestEntity();
        testEntity.setName("xyb");
        testEntityRepository.save(testEntity);
        TestEntity result=testEntityRepository.findOne("xyb");
        result.setAge(1);
        result.setDate(new Date());
        testEntityRepository.save(result);
        QueryBuilder queryBuilder = QueryBuilders.boolQuery();
        SumAggregationBuilder sumAggregationBuilder=AggregationBuilders.sum("sum_age").field("age");

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .addAggregation(sumAggregationBuilder)
                .build();
        elasticsearchTemplate.query(searchQuery,response -> {
            InternalSum sum = (InternalSum)response.getAggregations().asList().get(0);
            return sum.getValue();
        });
    }
}
