package com.repositories;

import com.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderDetailRepository extends PagingAndSortingRepository<OrderDetail, Long>, JpaSpecificationExecutor<OrderDetail> {
}
