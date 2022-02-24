package com.services;

import com.models.Type;
import com.repositories.TypeRepository;
import com.ultils.modelHelper.ModelResult;
import com.ultils.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeService {
    @Autowired
    TypeRepository typeRepository;

    public ModelResult<Type> getListTypeWithSearch(int pageIndex, int pageSize, String sortColumn, String sortOrder,
                                                   List<SearchCriteria> searchCriteriaList) {
        ModelService<Type> modelService = new ModelService<>(typeRepository);
        return modelService.getListWithSearch(pageIndex, pageSize, sortColumn, sortOrder, searchCriteriaList);
    }
}
