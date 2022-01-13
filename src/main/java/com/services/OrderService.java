package com.services;

import com.models.Order;
import com.repositories.OrderRepository;
import com.ultils.modelHelper.ModelResult;
import com.ultils.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    public ModelResult<Order> getListOrderWithSearch(int pageIndex, int pageSize, String sortColumn, String sortOrder,
                                                     List<SearchCriteria> searchCriteriaList) {
        ModelService<Order> modelService = new ModelService<>(orderRepository);
        return modelService.getListWithSearch(pageIndex, pageSize, sortColumn, sortOrder, searchCriteriaList);
    }
}
