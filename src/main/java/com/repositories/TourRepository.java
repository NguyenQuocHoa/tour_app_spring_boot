package com.repositories;

import com.models.Tour;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourRepository extends PagingAndSortingRepository<Tour, Long> {
    List<Tour> findByCode(String code);
}
