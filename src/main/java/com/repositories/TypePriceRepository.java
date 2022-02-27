package com.repositories;

import com.models.TypePrice;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypePriceRepository extends PagingAndSortingRepository<TypePrice, Long>, JpaSpecificationExecutor<TypePrice> {
    List<TypePrice> findByCode(String code);
}