package com.services;

import com.models.TypePrice;
import com.repositories.TypePriceRepository;
import com.ultils.modelHelper.ModelResult;
import com.ultils.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypePriceService {
    @Autowired
    TypePriceRepository typeRepository;

    public ModelResult<TypePrice> getListTypeWithSearch(int pageIndex, int pageSize, String sortColumn, String sortOrder,
                                                        List<SearchCriteria> searchCriteriaList) {
        ModelService<TypePrice> modelService = new ModelService<>(typeRepository);
        return modelService.getListWithSearch(pageIndex, pageSize, sortColumn, sortOrder, searchCriteriaList);
    }
}
