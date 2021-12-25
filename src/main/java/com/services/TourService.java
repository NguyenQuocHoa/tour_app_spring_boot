package com.services;

import com.models.Tour;
import com.repositories.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TourService
{
    @Autowired
    TourRepository repository;

    public List<Tour> getListTours(int pageIndex, int pageSize, String sortColumn, String sortOrder)
    {
        Pageable paging;
        if (Objects.equals(sortOrder, "0")) {
            paging = PageRequest.of(pageIndex - 1, pageSize, Sort.by(sortColumn).ascending());
        } else {
            paging = PageRequest.of(pageIndex - 1, pageSize, Sort.by(sortColumn).descending());
        }

        Page<Tour> pagedResult = repository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }
}