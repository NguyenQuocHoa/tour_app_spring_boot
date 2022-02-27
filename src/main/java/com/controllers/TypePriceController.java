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

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:3006", "http://someserver:3000"})
@RestController
@RequestMapping(path = "/api/v1/types")
public class TypePriceController {
    // DI = Dependency Injection
    @Autowired
    private TypePriceRepository typeRepository;
    @Autowired
    private TypePriceService typePriceService;

    @GetMapping("/get-list-all")
    @ResponseBody
    ResponseEntity<ResponseObject> getListAllTypes() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query list all type success", typeRepository.findAll(), typeRepository.count())
        );
    }

    @PostMapping("/get-list")
    @ResponseBody
    ResponseEntity<ResponseObject> getListTypes(@RequestParam("pageIndex") int pageIndex,
                                                @RequestParam("pageSize") int pageSize,
                                                @RequestParam("sortColumn") String sortColumn,
                                                @RequestParam("sortOrder") String sortOrder,
                                                @RequestBody List<SearchCriteria> searchCriteriaList) {
        ModelResult<TypePrice> typeResult = typePriceService.getListTypeWithSearch(pageIndex, pageSize, sortColumn, sortOrder, searchCriteriaList);
        List<TypePrice> listType = typeResult.getListResult();
        long count = typeResult.getCount();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query list type success", listType, pageIndex, pageSize, count)
        );
    }

    @GetMapping("/get-by-id/{id}")
    @ResponseBody
    ResponseEntity<ResponseObjectBase> findById(@PathVariable Long id) {
        Optional<TypePrice> fundType = typeRepository.findById(id);
        if (fundType.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObjectBase("ok", "Query type success", fundType)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObjectBase("failed", "Can't find type with id " + id, "")
            );
        }
    }

    // insert new TypePrice with POST method
    @PostMapping("/insert")
    @ResponseBody
    ResponseEntity<ResponseObjectBase> insertType(@RequestBody TypePrice newType) {
        List<TypePrice> foundTypes = typeRepository.findByCode(newType.getCode().trim());
        System.out.println("tour: " + newType.getTour().getId());
        if (foundTypes.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObjectBase("failed", "TypePrice code already exist", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObjectBase("ok", "Insert type success", typeRepository.save(newType))
        );
    }

    // update a TypePrice => Method PUT
    @PutMapping("/update/{id}")
    @ResponseBody
    ResponseEntity<ResponseObjectBase> updateType(@RequestBody TypePrice newType, @PathVariable Long id) {
        TypePrice foundType = typeRepository.findById(id).map(type -> {
            type.setCode(newType.getCode());
            type.setPrice(newType.getPrice());
            type.setIsActive(newType.getIsActive());
            type.setTour(newType.getTour());
            return typeRepository.save(type);
        }).orElseGet(() -> {
            newType.setId(id);
            return typeRepository.save(newType);
        });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObjectBase("ok", "Upsert type success", foundType)
        );
    }

    // Delete a TypePrice => Method DELETE
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    ResponseEntity<ResponseObjectBase> deleteType(@PathVariable Long id) {
        boolean isExist = typeRepository.existsById(id);
        if (isExist) {
            typeRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObjectBase("ok", "Delete type success", id)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                new ResponseObjectBase("failed", "Can't find type " + id, "")
        );
    }

}













