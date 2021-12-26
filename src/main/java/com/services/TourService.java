package com.services;

import com.models.Tour;
import com.repositories.TourRepository;
import com.ultils.modelHelper.ModelResult;
import com.ultils.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourService {
    @Autowired
    TourRepository tourRepository;

    public ModelResult<Tour> getListTourWithSearch(int pageIndex, int pageSize, String sortColumn, String sortOrder,
                                                   List<SearchCriteria> searchCriteriaList) {
        ModelService<Tour> modelService = new ModelService<>(tourRepository);
        return modelService.getListWithSearch(pageIndex, pageSize, sortColumn, sortOrder, searchCriteriaList);
    }
}
