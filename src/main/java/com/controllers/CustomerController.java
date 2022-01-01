package com.controllers;

import com.models.Customer;
import com.repositories.CustomerRepository;
import com.services.CustomerService;
import com.ultils.modelHelper.ModelResult;
import com.ultils.modelHelper.ResponseObject;
import com.ultils.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/customers")
public class CustomerController {
    // DI = Dependency Injection
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerService customerService;

    @GetMapping("/get-list-all")
    @ResponseBody
    ResponseEntity<ResponseObject> getListAllTours() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query list all customer success", customerRepository.findAll(), customerRepository.count())
        );
    }

    @PostMapping("/get-list")
    @ResponseBody
    ResponseEntity<ResponseObject> getListCustomers(@RequestParam("pageIndex") int pageIndex,
                                                    @RequestParam("pageSize") int pageSize,
                                                    @RequestParam("sortColumn") String sortColumn,
                                                    @RequestParam("sortOrder") String sortOrder,
                                                    @RequestBody List<SearchCriteria> searchCriteriaList) {
        ModelResult<Customer> customerResult = customerService.getListCustomerWithSearch(pageIndex, pageSize, sortColumn, sortOrder, searchCriteriaList);
        List<Customer> listCustomer = customerResult.getListResult();
        long count = customerResult.getCount();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query list customer success", listCustomer, pageIndex, pageSize, count)
        );
    }

    @GetMapping("/get-by-id/{id}")
    @ResponseBody
    ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<Customer> fundCustomer = customerRepository.findById(id);
        if (fundCustomer.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query customer success", fundCustomer)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("false", "Can't find customer with id " + id, "")
            );
        }
    }

    // insert new Customer with POST method
    @PostMapping("/insert")
    @ResponseBody
    ResponseEntity<ResponseObject> insertCustomer(@RequestBody Customer newCustomer) {
        List<Customer> foundCustomers = customerRepository.findByCode(newCustomer.getCode().trim());
        if (foundCustomers.size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Customer code already exist", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert customer success", customerRepository.save(newCustomer))
        );
    }

    // update a Customer => Method PUT
    @PutMapping("/update/{id}")
    @ResponseBody
    ResponseEntity<ResponseObject> updateCustomer(@RequestBody Customer newCustomer, @PathVariable Long id) {
        Customer foundCustomer = customerRepository.findById(id).map(customer -> {
            customer.setCode(newCustomer.getCode());
            customer.setName(newCustomer.getName());
            customer.setPhone(newCustomer.getPhone());
            customer.setEmail(newCustomer.getEmail());
            customer.setAddress(newCustomer.getAddress());
            customer.setPassword(newCustomer.getPassword());
            customer.setDob(newCustomer.getDob());
            customer.setNote(newCustomer.getNote());
            return customerRepository.save(customer);
        }).orElseGet(() -> {
            newCustomer.setId(id);
            return customerRepository.save(newCustomer);
        });
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                new ResponseObject("ok", "Upsert customer success", foundCustomer)
        );
    }

    // Delete a Customer => Method DELETE
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    ResponseEntity<ResponseObject> deleteCustomer(@PathVariable Long id) {
        boolean isExist = customerRepository.existsById(id);
        if (isExist) {
            customerRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("ok", "Delete customer success", id)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                new ResponseObject("failed", "Can't find customer " + id, "")
        );
    }
}
