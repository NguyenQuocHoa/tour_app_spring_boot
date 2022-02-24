package com.controllers;

import com.models.TypePrice;
import com.ultils.modelHelper.ResponseObject;
import com.repositories.TypePriceRepository;
import com.services.TypePriceService;
import com.ultils.modelHelper.ModelResult;
import com.ultils.modelHelper.ResponseObjectBase;
import com.ultils.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/type-prices")
public class TypePriceController {
    // DI = Dependency Injection
    @Autowired
    private TypePriceRepository typePriceRepository;
    @Autowired
    private TypePriceService typePriceService;

    @GetMapping("/get-list-all")
    @ResponseBody
    ResponseEntity<ResponseObject> getListAllTypePrices() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query list all type price success", typePriceRepository.findAll(), typePriceRepository.count())
        );
    }

    @PostMapping("/get-list")
    @ResponseBody
    ResponseEntity<ResponseObject> getListTypePrices(@RequestParam("pageIndex") int pageIndex,
                                                     @RequestParam("pageSize") int pageSize,
                                                     @RequestParam("sortColumn") String sortColumn,
                                                     @RequestParam("sortOrder") String sortOrder,
                                                     @RequestBody List<SearchCriteria> searchCriteriaList) {
        ModelResult<TypePrice> typePriceResult = typePriceService.getListTypePriceWithSearch(pageIndex, pageSize, sortColumn, sortOrder, searchCriteriaList);
        List<TypePrice> listTypePrice = typePriceResult.getListResult();
        long count = typePriceResult.getCount();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query list type price success", listTypePrice, pageIndex, pageSize, count)
        );
    }

    @GetMapping("/get-by-id/{id}")
    @ResponseBody
    ResponseEntity<ResponseObjectBase> findById(@PathVariable Long id) {
        Optional<TypePrice> fundTypePrice = typePriceRepository.findById(id);
        if (fundTypePrice.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObjectBase("ok", "Query type price success", fundTypePrice)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObjectBase("failed", "Can't find type price with id " + id, "")
            );
        }
    }

    // insert new TypePrice with POST method
    @PostMapping("/insert")
    @ResponseBody
    ResponseEntity<ResponseObjectBase> insertTypePrice(@RequestBody TypePrice newTypePrice) {
        List<TypePrice> foundTypePrices = typePriceRepository.findByCode(newTypePrice.getCode().trim());
        if (foundTypePrices.size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObjectBase("failed", "Type price code already exist", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObjectBase("ok", "Insert type price success", typePriceRepository.save(newTypePrice))
        );
    }

    // update a TypePrice => Method PUT
    @PutMapping("/update/{id}")
    @ResponseBody
    ResponseEntity<ResponseObjectBase> updateTypePrice(@RequestBody TypePrice newTypePrice, @PathVariable Long id) {
        TypePrice foundTypePrice = typePriceRepository.findById(id).map(typePrice -> {
            typePrice.setCode(newTypePrice.getCode());
            typePrice.setPrice(newTypePrice.getPrice());
            typePrice.setIsActive(newTypePrice.getIsActive());
            return typePriceRepository.save(typePrice);
        }).orElseGet(() -> {
            newTypePrice.setId(id);
            return typePriceRepository.save(newTypePrice);
        });
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                new ResponseObjectBase("ok", "Upsert type price success", foundTypePrice)
        );
    }

    // Delete a TypePrice => Method DELETE
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    ResponseEntity<ResponseObjectBase> deleteTypePrice(@PathVariable Long id) {
        boolean isExist = typePriceRepository.existsById(id);
        if (isExist) {
            typePriceRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObjectBase("ok", "Delete type price success", id)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                new ResponseObjectBase("failed", "Can't find type price " + id, "")
        );
    }

}













