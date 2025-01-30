package com.agritrading.AgritradingApplication.controller;

import com.agritrading.AgritradingApplication.dto.AddOrderDTO;
import com.agritrading.AgritradingApplication.dto.AddOrderResponseDTO;
import com.agritrading.AgritradingApplication.dto.Response;
import com.agritrading.AgritradingApplication.model.Customers;
import com.agritrading.AgritradingApplication.model.Orders;
import com.agritrading.AgritradingApplication.model.Users;
import com.agritrading.AgritradingApplication.repo.CustomersRepository;
import com.agritrading.AgritradingApplication.repo.OrdersRepository;
import com.agritrading.AgritradingApplication.repo.ProductsRepository;
import com.agritrading.AgritradingApplication.repo.UserRepo;
import com.agritrading.AgritradingApplication.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class OrdersController {

    @Autowired
    private OrdersRepository ordersRepository;

    public Integer customerId(Authentication authentication) {
        String username = authentication.getName();

        Users user = userRepo.findByUsername(username);

        return user.getCustomerId();
    }

    public Integer farmerId(Authentication authentication) {
        String username = authentication.getName();
        Users user = userRepo.findByUsername(username);
        return user.getFarmerId();
    }

    @Autowired
    private CustomersRepository customersRepository;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProductsRepository productsRepository;


//    ORDER API for customer

    @PostMapping("/customers/orders")
    public ResponseEntity<Response> createOrder(@RequestBody AddOrderDTO orderRequest, Authentication authentication) throws Exception {
        Customers customer = customersRepository.findById(customerId(authentication))
                .orElseThrow(() -> new Exception("Customer not found"));
        Orders order = ordersService.createOrder(orderRequest,customer);

        AddOrderResponseDTO orderResponse = AddOrderResponseDTO.builder()
                .customerName(customer.getName())
                .orderId(order.getOrder_Id())
                .productName(order.getProduct().getProd_Name())
                .quantity(order.getQuantity())
                .totalPrice(order.getTotal_Price())
                .orderStatus(order.getOrder_status())
                .build();

        Response response = Response.builder()
                .status(HttpStatus.CREATED.value())
                .message("Order created successfully")
                .addOrderResponse(orderResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }


    @GetMapping("/customers/orders/by-id")
    public ResponseEntity<Response> getOrdersById(@RequestParam("id")Optional<Integer> orderId, Authentication authentication) throws Exception {
        if(orderId.isPresent()) {
            Orders order =  ordersService.getOrder(orderId.get());

            AddOrderResponseDTO orderResponse = AddOrderResponseDTO.builder()
                    .customerName(order.getCustomer().getName())
                    .orderId(order.getOrder_Id())
                    .productName(order.getProduct().getProd_Name())
                    .quantity(order.getQuantity())
                    .totalPrice(order.getTotal_Price())
                    .orderStatus(order.getOrder_status())
                    .build();

            Response response = Response.builder()
                    .status(HttpStatus.CREATED.value())
                    .message("Order created successfully")
                    .addOrderResponse(orderResponse)
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        Response response = Response.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message("Order Not Found")
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }



    @GetMapping("/customers/orders")
    public ResponseEntity<Response> getAllOrders(Authentication authentication) {
        List<AddOrderResponseDTO> orderList =  ordersService.getAllOrders(customerId(authentication));
        Response response = Response.builder()
                .status(200)
                .message("Orders fetched successfully")
                .addOrderResposeList(orderList)
                .build();

        return ResponseEntity.ok(response);
    }


//ORDER API for farmer

    @GetMapping("/farmers/orders")
    public ResponseEntity<Response> getFarmerOrders(Authentication authentication) {
        String username = authentication.getName();
        Users user = userRepo.findByUsername(username);
        if(user == null) return null;
        int farmerId = user.getFarmerId();
        List<AddOrderResponseDTO> orderList =  ordersService.getAllOrdersForFarmer(farmerId);
        Response response = Response.builder()
                .status(200)
                .message("Orders fetched successfully")
                .addOrderResposeList(orderList)
                .build();

        return ResponseEntity.ok(response);
    }

//ORDER API for everyone

    @PutMapping("/orders")
    public ResponseEntity<Response> updateOrder(@RequestBody Orders orders,@RequestParam("OrderId") int orderId,  Authentication authentication) throws Exception {
        Integer farmerId = farmerId(authentication);
        Integer customerId = customerId(authentication);
        Orders updatedOrder = ordersService.updateOrder(orders, orderId, customerId, farmerId);
        AddOrderResponseDTO orderResponse = AddOrderResponseDTO.builder()
                .customerName(updatedOrder.getCustomer().getName())
                .orderId(updatedOrder.getOrder_Id())
                .productName(updatedOrder.getProduct().getProd_Name())
                .quantity(updatedOrder.getQuantity())
                .totalPrice(updatedOrder.getTotal_Price())
                .orderStatus(updatedOrder.getOrder_status())
                .build();

        Response response = Response.builder()
                .status(HttpStatus.CREATED.value())
                .message("Order created successfully")
                .addOrderResponse(orderResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PatchMapping("/orders/status")
    public ResponseEntity<Response> updateOrderStatus(
            @RequestParam("orderId") int orderId,
            Authentication authentication) throws Exception {
//        Integer farmerId = farmerId(authentication);
//        Integer customerId = customerId(authentication);

        // Retrieve the order to ensure it exists and is accessible to the user
        Orders order = ordersRepository.getById(orderId);

//        if (order.getCustomer().getCustomerId() != customerId && order.getFarmer().getFarmerId() != farmerId) {
//            throw new Exception("Not allowed to update this order");
//        }

        // Update the order status
        order.setOrder_status("COMPLETED");
        Orders updatedOrder = ordersRepository.save(order);

        // Prepare the response
        AddOrderResponseDTO orderResponse = AddOrderResponseDTO.builder()
                .customerName(updatedOrder.getCustomer().getName())
                .orderId(updatedOrder.getOrder_Id())
                .productName(updatedOrder.getProduct().getProd_Name())
                .quantity(updatedOrder.getQuantity())
                .totalPrice(updatedOrder.getTotal_Price())
                .orderStatus(updatedOrder.getOrder_status())
                .build();

        Response response = Response.builder()
                .status(HttpStatus.OK.value())
                .message("Order status updated to Completed")
                .addOrderResponse(orderResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



    @DeleteMapping("/orders")
    public void deleteOrder(@RequestParam("id")int orderId, Authentication authentication) throws Exception {
        Integer farmerId = farmerId(authentication);
//        Integer customerId = customerId(authentication);

//        if(customerId != null) {
//            if(ordersRepository.findById(orderId).isPresent()) {
//                if(ordersRepository.findById(orderId).get().getCustomer().getCustomerId()==customerId) { ordersRepository.deleteById(orderId); }
//            }
//        }

        if(farmerId != null) {
            if(ordersRepository.findById(orderId).isPresent()) {
                if(ordersRepository.findById(orderId).get().getProduct().getFarmer().getFarmerId()==farmerId) { ordersRepository.deleteById(orderId); }
            }
        }
    }






}
