package com.controllers;

import com.models.Tour;
import com.models.ResponseObject;
import com.repositories.TourRepository;
import com.services.TourService;
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
    private TourRepository repository;
    @Autowired
    private TourService service;

    @PostMapping("/get-list")
    @ResponseBody
    ResponseEntity<ResponseObject> getListTours(@RequestParam("pageIndex") int pageIndex,
                                               @RequestParam("pageSize") int pageSize,
                                               @RequestParam("sortColumn") String sortColumn,
                                               @RequestParam("sortOrder") String sortOrder) {
        List<Tour> listTour = service.getListTours(pageIndex, pageSize, sortColumn, sortOrder);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query list tour success",  listTour)
        );
    }

    @GetMapping("/get-by-id/{id}")
    @ResponseBody
    ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<Tour> fundTour = repository.findById(id);
        if (fundTour.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query tour success", fundTour)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("false", "Can't find tour with id " + id, "")
            );
        }
    }

    // insert new Tour with POST method
    @PostMapping("/insert")
    @ResponseBody
    ResponseEntity<ResponseObject> insertTour(@RequestBody Tour newTour) {
        List<Tour> foundTours = repository.findByCode(newTour.getCode().trim());
        if (foundTours.size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Tour code already exist", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert tour success", repository.save(newTour))
        );
    }

    // update a Tour => Method PUT
    @PutMapping("/update/{id}")
    @ResponseBody
    ResponseEntity<ResponseObject> updateTour(@RequestBody Tour newTour, @PathVariable Long id) {
        Tour foundTour = repository.findById(id).map(tour -> {
            tour.setCode(newTour.getCode());
            tour.setPriceAdult(newTour.getPriceAdult());
            tour.setPriceChild(newTour.getPriceChild());
            tour.setImage(newTour.getImage());
            tour.setNote(newTour.getNote());
            return repository.save(tour);
        }).orElseGet(() -> {
            newTour.setId(id);
            return repository.save(newTour);
        });
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                new ResponseObject("ok", "Upsert tour success", foundTour)
        );
    }

    // Delete a Tour => Method DELETE
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    ResponseEntity<ResponseObject> deleteTour(@PathVariable Long id) {
        boolean isExist = repository.existsById(id);
        if (isExist) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("ok", "Delete tour success", id)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                new ResponseObject("failed", "Can't find tour " + id, "")
        );
    }

}













