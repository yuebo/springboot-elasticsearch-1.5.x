package com.example.demoes.repository;

import com.example.demoes.es.TestEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;


public interface TestEntityRepository extends ElasticsearchCrudRepository<TestEntity,String> {

}
