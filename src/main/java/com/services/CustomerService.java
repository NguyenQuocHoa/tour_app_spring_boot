package com.services;

import com.models.Customer;
import com.repositories.CustomerRepository;
import com.ultils.modelHelper.ModelResult;
import com.ultils.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public ModelResult<Customer> getListCustomerWithSearch(int pageIndex, int pageSize, String sortColumn, String sortOrder,
                                                           List<SearchCriteria> searchCriteriaList) {
        ModelService<Customer> modelService = new ModelService<>(customerRepository);
        return modelService.getListWithSearch(pageIndex, pageSize, sortColumn, sortOrder, searchCriteriaList);
    }
}
