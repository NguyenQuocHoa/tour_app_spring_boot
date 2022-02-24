package com.controllers;

import com.models.Type;
import com.ultils.modelHelper.ResponseObject;
import com.repositories.TypeRepository;
import com.services.TypeService;
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
@RequestMapping(path = "/api/v1/types")
public class TypeController {
    // DI = Dependency Injection
    @Autowired
    private TypeRepository typeRepository;
    @Autowired
    private TypeService typeService;

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
        ModelResult<Type> typeResult = typeService.getListTypeWithSearch(pageIndex, pageSize, sortColumn, sortOrder, searchCriteriaList);
        List<Type> listType = typeResult.getListResult();
        long count = typeResult.getCount();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query list type success", listType, pageIndex, pageSize, count)
        );
    }

    @GetMapping("/get-by-id/{id}")
    @ResponseBody
    ResponseEntity<ResponseObjectBase> findById(@PathVariable Long id) {
        Optional<Type> fundType = typeRepository.findById(id);
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

    // insert new Type with POST method
    @PostMapping("/insert")
    @ResponseBody
    ResponseEntity<ResponseObjectBase> insertType(@RequestBody Type newType) {
        List<Type> foundTypes = typeRepository.findByCode(newType.getCode().trim());
        if (foundTypes.size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObjectBase("failed", "Type price code already exist", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObjectBase("ok", "Insert type success", typeRepository.save(newType))
        );
    }

    // update a Type => Method PUT
    @PutMapping("/update/{id}")
    @ResponseBody
    ResponseEntity<ResponseObjectBase> updateType(@RequestBody Type newType, @PathVariable Long id) {
        Type foundType = typeRepository.findById(id).map(type -> {
            type.setCode(newType.getCode());
            type.setPrice(newType.getPrice());
            type.setIsActive(newType.getIsActive());
            return typeRepository.save(type);
        }).orElseGet(() -> {
            newType.setId(id);
            return typeRepository.save(newType);
        });
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                new ResponseObjectBase("ok", "Upsert type success", foundType)
        );
    }

    // Delete a Type => Method DELETE
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    ResponseEntity<ResponseObjectBase> deleteType(@PathVariable Long id) {
        boolean isExist = typeRepository.existsById(id);
        if (isExist) {
            typeRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObjectBase("ok", "Delete type success", id)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                new ResponseObjectBase("failed", "Can't find type " + id, "")
        );
    }

}













