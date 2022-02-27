package com.controllers;

import com.repositories.TourRepository;
import com.ultils.modelHelper.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/report")
public class ReportController {
    @Autowired
    TourRepository tourRepository;

    @GetMapping("/report-profit-by-tour")
    @ResponseBody
    ResponseEntity<ResponseObject> reportProfitByTour(@RequestParam("keySearch") String keySearch) {
        Pageable pageable = new Pageable() {
            @Override
            public int getPageNumber() {
                return 0;
            }

            @Override
            public int getPageSize() {
                return 10;
            }

            @Override
            public long getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public Pageable withPage(int pageNumber) {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        };
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query list all post success", tourRepository.joinTourWithOrder(keySearch, pageable))
        );
    }
}
