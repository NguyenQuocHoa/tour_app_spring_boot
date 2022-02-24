package com.repositories;

import com.models.Type;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeRepository extends PagingAndSortingRepository<Type, Long>, JpaSpecificationExecutor<Type> {
    List<Type> findByCode(String code);
}