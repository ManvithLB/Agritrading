package com.agritrading.AgritradingApplication.controller;

import com.agritrading.AgritradingApplication.dto.ProductDTO;
import com.agritrading.AgritradingApplication.dto.Response;
import com.agritrading.AgritradingApplication.model.Users;
import com.agritrading.AgritradingApplication.repo.UserRepo;
import com.agritrading.AgritradingApplication.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloController  {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProductsService productsService;

    @GetMapping("/")
    public String greet(Authentication authentication) {
        String username = authentication.getName();

        // Fetch user details from the database
        Users user = userRepo.findByUsername(username);

        // Return a personalized greeting
        return "Hello, " + user.getUsername() + "!";
    }

    @GetMapping("/customer/products")
    public ResponseEntity<Response> customer(Authentication authentication) {
        List<ProductDTO> productsList =  productsService.getAllProductsForCustomer();
        Response response = Response.builder()
                .status(HttpStatus.CREATED.value())
                .message("Product created successfully")
                .productList(productsList).build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }



}
