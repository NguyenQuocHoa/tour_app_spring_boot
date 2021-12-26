package com.services;

import com.ultils.modelHelper.ModelResult;
import com.ultils.specification.ModSpecificationsBuilder;
import com.ultils.specification.SearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ModelService<T> {
    private final JpaSpecificationExecutor<T> modelRepository;

    public ModelService(JpaSpecificationExecutor<T> modelRepository) {
        this.modelRepository = modelRepository;
    }

    public ModelResult<T> getListWithSearch(int pageIndex, int pageSize, String sortColumn, String sortOrder,
                                            List<SearchCriteria> searchCriteriaList) {
        ModSpecificationsBuilder<T> builder = new ModSpecificationsBuilder<T>();
        searchCriteriaList.forEach(body -> {
            builder.with(body.getColumn(), body.getKeySearch(), body.getExpression());
        });
        Specification<T> spec = builder.build();

        Pageable paging;
        if (Objects.equals(sortOrder, "0")) {
            paging = PageRequest.of(pageIndex - 1, pageSize, Sort.by(sortColumn).ascending());
        } else {
            paging = PageRequest.of(pageIndex - 1, pageSize, Sort.by(sortColumn).descending());
        }

        Page<T> pagedResult = modelRepository.findAll(spec, paging);
        long count = modelRepository.count(spec);

        if (pagedResult.hasContent()) {
            return new ModelResult<T>(pagedResult.getContent(), count);
        } else {
            return new ModelResult<T>(new ArrayList<>(), 0);
        }
    }
}
