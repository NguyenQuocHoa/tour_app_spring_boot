package com.controllers;

;
import com.models.Customer;
import com.models.Order;
import com.models.OrderDetail;
import com.models.Tour;
import com.repositories.CustomerRepository;
import com.repositories.OrderDetailRepository;
import com.repositories.OrderRepository;
import com.repositories.TourRepository;
import com.services.OrderService;
import com.ultils.modelHelper.ModelResult;
import com.ultils.modelHelper.ResponseObject;
import com.ultils.modelHelper.ResponseObjectBase;
import com.ultils.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.xml.soap.Detail;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/v1/orders")
public class OrderController {
    // DI = Dependency Injection
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private TourRepository tourRepository;
    @Autowired
    private CustomerRepository customerRepository;


    @PostMapping("/get-list")
    @ResponseBody
    ResponseEntity<ResponseObject> getListOrders(@RequestParam("pageIndex") int pageIndex,
                                                 @RequestParam("pageSize") int pageSize,
                                                 @RequestParam("sortColumn") String sortColumn,
                                                 @RequestParam("sortOrder") String sortOrder,
                                                 @RequestBody List<SearchCriteria> searchCriteriaList) {
        ModelResult<Order> orderResult = orderService.getListOrderWithSearch(pageIndex, pageSize, sortColumn, sortOrder, searchCriteriaList);
        List<Order> listOrder = orderResult.getListResult();
        long count = orderResult.getCount();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query list order success", listOrder, pageIndex, pageSize, count)
        );
    }

    @GetMapping("/get-by-id/{id}")
    @ResponseBody
    ResponseEntity<ResponseObjectBase> findById(@PathVariable Long id) {
        Optional<Order> fundOrder = orderRepository.findById(id);
        if (fundOrder.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObjectBase("ok", "Query order success", fundOrder)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObjectBase("failed", "Can't find order with id " + id, "")
            );
        }
    }

//    @GetMapping("/get-order-detail-by-order")
//    @ResponseBody
//    ResponseEntity<ResponseObjectBase> findCommentByOrder(@RequestParam Long orderId) {
//        Optional<Order> fundOrder = orderRepository.findById(orderId);
//        return fundOrder.map(order -> ResponseEntity.status(HttpStatus.OK).body(
//                new ResponseObjectBase("ok", "Query list order detail by order success", order.getOrderDetails())
//        )).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
//                new ResponseObjectBase("failed", "Can't find order detail by order id " + fundOrder, "")
//        ));
//    }


    // insert new Order with POST method
    @PostMapping("/insert")
    @ResponseBody
    @Transactional
    ResponseEntity<ResponseObjectBase> insertOrder(@RequestBody Order newOrder) {
        List<Order> foundOrders = orderRepository.findByCode(newOrder.getCode().trim());
        if (foundOrders.size() > 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObjectBase("failed", "Order code already exist", "")
            );
        }
        Customer customer = customerRepository.findCustomerById(20L);
        newOrder.setCustomer(customer);
        Order order = orderRepository.save(newOrder);
        newOrder.getOrderDetails().forEach(detail -> {
            Tour tour = tourRepository.findTourById(detail.getTour().getId());
            detail.setTour(tour);
            detail.setOrder(order);
            orderDetailRepository.save(detail);
        });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObjectBase("ok", "Insert order success", order)
        );
    }

    // update a Order => Method PUT
    @PutMapping("/update/{id}")
    @ResponseBody
    ResponseEntity<ResponseObjectBase> updateOrder(@RequestBody Order newOrder, @PathVariable Long id) {
        Order foundOrder = orderRepository.findById(id).map(order -> {
            order.setCode(newOrder.getCode());
            order.setNote(newOrder.getNote());
//            order.setContent(newOrder.getContent());
            return orderRepository.save(order);
        }).orElseGet(() -> {
            newOrder.setId(id);
            return orderRepository.save(newOrder);
        });
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                new ResponseObjectBase("ok", "Upsert order success", foundOrder)
        );
    }

    // Delete a Order => Method DELETE
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    ResponseEntity<ResponseObjectBase> deleteOrder(@PathVariable Long id) {
        boolean isExist = orderRepository.existsById(id);
        if (isExist) {
            orderRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObjectBase("ok", "Delete order success", id)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                new ResponseObjectBase("failed", "Can't find order " + id, "")
        );
    }
}
