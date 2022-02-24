package com.services;

import com.models.TypePrice;
import com.repositories.TypePriceRepository;
import com.ultils.modelHelper.ModelResult;
import com.ultils.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypePriceService {
    @Autowired
    TypePriceRepository typePriceRepository;

    public ModelResult<TypePrice> getListTypePriceWithSearch(int pageIndex, int pageSize, String sortColumn, String sortOrder,
                                                             List<SearchCriteria> searchCriteriaList) {
        ModelService<TypePrice> modelService = new ModelService<>(typePriceRepository);
        return modelService.getListWithSearch(pageIndex, pageSize, sortColumn, sortOrder, searchCriteriaList);
    }
}
