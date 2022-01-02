package com.controllers;

import com.models.Tour;
import com.ultils.modelHelper.ResponseObject;
import com.repositories.TourRepository;
import com.services.TourService;
import com.ultils.modelHelper.ModelResult;
import com.ultils.modelHelper.ResponseObjectBase;
import com.ultils.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/tours")
public class TourController {
    // DI = Dependency Injection
    @Autowired
    private TourRepository tourRepository;
    @Autowired
    private TourService tourService;

    @GetMapping("/get-list-all")
    @ResponseBody
    ResponseEntity<ResponseObject> getListAllTours() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query list all tour success", tourRepository.findAll(), tourRepository.count())
        );
    }

    @PostMapping("/get-list")
    @ResponseBody
    ResponseEntity<ResponseObject> getListTours(@RequestParam("pageIndex") int pageIndex,
                                                @RequestParam("pageSize") int pageSize,
                                                @RequestParam("sortColumn") String sortColumn,
                                                @RequestParam("sortOrder") String sortOrder,
                                                @RequestBody List<SearchCriteria> searchCriteriaList) {
        ModelResult<Tour> tourResult = tourService.getListTourWithSearch(pageIndex, pageSize, sortColumn, sortOrder, searchCriteriaList);
        List<Tour> listTour = tourResult.getListResult();
        long count = tourResult.getCount();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query list tour success", listTour, pageIndex, pageSize, count)
        );
    }

    @GetMapping("/get-by-id/{id}")
    @ResponseBody
    ResponseEntity<ResponseObjectBase> findById(@PathVariable Long id) {
        Optional<Tour> fundTour = tourRepository.findById(id);
        if (fundTour.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObjectBase("ok", "Query tour success", fundTour)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObjectBase("failed", "Can't find tour with id " + id, "")
            );
        }
    }

    // insert new Tour with POST method
    @PostMapping("/insert")
    @ResponseBody
    ResponseEntity<ResponseObjectBase> insertTour(@RequestBody Tour newTour) {
        List<Tour> foundTours = tourRepository.findByCode(newTour.getCode().trim());
        if (foundTours.size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObjectBase("failed", "Tour code already exist", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObjectBase("ok", "Insert tour success", tourRepository.save(newTour))
        );
    }

    // update a Tour => Method PUT
    @PutMapping("/update/{id}")
    @ResponseBody
    ResponseEntity<ResponseObjectBase> updateTour(@RequestBody Tour newTour, @PathVariable Long id) {
        Tour foundTour = tourRepository.findById(id).map(tour -> {
            tour.setCode(newTour.getCode());
            tour.setPriceAdult(newTour.getPriceAdult());
            tour.setPriceChild(newTour.getPriceChild());
            tour.setImage(newTour.getImage());
            tour.setNote(newTour.getNote());
            return tourRepository.save(tour);
        }).orElseGet(() -> {
            newTour.setId(id);
            return tourRepository.save(newTour);
        });
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                new ResponseObjectBase("ok", "Upsert tour success", foundTour)
        );
    }

    // Delete a Tour => Method DELETE
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    ResponseEntity<ResponseObjectBase> deleteTour(@PathVariable Long id) {
        boolean isExist = tourRepository.existsById(id);
        if (isExist) {
            tourRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObjectBase("ok", "Delete tour success", id)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                new ResponseObjectBase("failed", "Can't find tour " + id, "")
        );
    }

}













