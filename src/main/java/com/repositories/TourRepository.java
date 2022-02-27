package com.repositories;

import com.models.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourRepository extends PagingAndSortingRepository<Tour, Long>, JpaSpecificationExecutor<Tour> {
    List<Tour> findByCode(String code);

    Tour findTourById(Long id);

    @Query("SELECT t FROM Tour t JOIN t.orderDetails od WHERE t.code LIKE %?1%")
    public Page<Tour> joinTourWithOrder(String keyword, Pageable pageable);
}