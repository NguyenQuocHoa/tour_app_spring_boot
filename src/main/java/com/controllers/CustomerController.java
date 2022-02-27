package com.controllers;

import com.models.Comment;
import com.models.Customer;
import com.repositories.CustomerRepository;
import com.services.CustomerService;
import com.ultils.modelHelper.ModelResult;
import com.ultils.modelHelper.ResponseObject;
import com.ultils.modelHelper.ResponseObjectBase;
import com.ultils.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:3006", "http://someserver:3000"})
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
    ResponseEntity<ResponseObjectBase> findById(@PathVariable Long id) {
        Optional<Customer> fundCustomer = customerRepository.findById(id);
        if (fundCustomer.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObjectBase("ok", "Query customer success", fundCustomer)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObjectBase("failed", "Can't find customer with id " + id, "")
            );
        }
    }

    @GetMapping("/get-comment-by-customer")
    @ResponseBody
    ResponseEntity<ResponseObjectBase> findCommentByCustomer(@RequestParam Long customerId) {
        Optional<Customer> fundCustomer = customerRepository.findById(customerId);
        return fundCustomer.map(customer -> ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObjectBase("ok", "Query list comment by customer success", customer.getComments())
        )).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObjectBase("failed", "Can't find comment bu customer id " + customerId, "")
        ));
    }

    @GetMapping("/get-comment-text-by-customer")
    @ResponseBody
    ResponseEntity<ResponseObjectBase> findCommentTextByCustomer(@RequestParam Long customerId) {
        Optional<Customer> fundCustomer = customerRepository.findById(customerId);
        return fundCustomer.map(customer -> ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObjectBase("ok", "Query list comment text by customer success",
                        customer.getComments().stream().filter((Comment comment) -> true)) // comment.getAction().contentEquals("text")))
        )).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObjectBase("failed", "Can't find comments by customer id " + customerId, "")
        ));
    }

    // insert new Customer with POST method
    @PostMapping("/insert")
    @ResponseBody
    ResponseEntity<ResponseObjectBase> insertCustomer(@RequestBody Customer newCustomer) {
        List<Customer> foundCustomers = customerRepository.findByCode(newCustomer.getCode().trim());
        if (foundCustomers.size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObjectBase("failed", "Customer code already exist", "")
            );
        }
        newCustomer.setCreated(new Date());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObjectBase("ok", "Insert customer success", customerRepository.save(newCustomer))
        );
    }

    // update a Customer => Method PUT
    @PutMapping("/update/{id}")
    @ResponseBody
    ResponseEntity<ResponseObjectBase> updateCustomer(@RequestBody Customer newCustomer, @PathVariable Long id) {
        Customer foundCustomer = customerRepository.findById(id).map(customer -> {
            customer.setCode(newCustomer.getCode());
            customer.setName(newCustomer.getName());
            customer.setPhone(newCustomer.getPhone());
            customer.setEmail(newCustomer.getEmail());
            customer.setAddress(newCustomer.getAddress());
            customer.setPassword(newCustomer.getPassword());
            customer.setDob(newCustomer.getDob());
            customer.setNote(newCustomer.getNote());
            customer.setIsActive(newCustomer.getIsActive());
            return customerRepository.save(customer);
        }).orElseGet(() -> {
            newCustomer.setId(id);
            return customerRepository.save(newCustomer);
        });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObjectBase("ok", "Upsert customer success", foundCustomer)
        );
    }

    // Delete a Customer => Method DELETE
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    ResponseEntity<ResponseObjectBase> deleteCustomer(@PathVariable Long id) {
        boolean isExist = customerRepository.existsById(id);
        if (isExist) {
            customerRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObjectBase("ok", "Delete customer success", id)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                new ResponseObjectBase("failed", "Can't find customer " + id, "")
        );
    }
}
