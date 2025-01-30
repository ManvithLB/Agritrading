package com.agritrading.AgritradingApplication.controller;

import com.agritrading.AgritradingApplication.dto.CustomerDTO;
import com.agritrading.AgritradingApplication.dto.FarmerDTO;
import com.agritrading.AgritradingApplication.dto.Response;
import com.agritrading.AgritradingApplication.model.Customers;
import com.agritrading.AgritradingApplication.model.Farmers;
import com.agritrading.AgritradingApplication.model.Users;
import com.agritrading.AgritradingApplication.repo.CustomersRepository;
import com.agritrading.AgritradingApplication.repo.FarmersRepository;
import com.agritrading.AgritradingApplication.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountProfileController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private  FarmersRepository farmersRepository;
    @Autowired
    private CustomersRepository customersRepository;

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
    public AccountProfileController(FarmersRepository farmersRepository) {
        this.farmersRepository = farmersRepository;
    }

    @GetMapping("/profile/farmer")
    public ResponseEntity<Response> farmerProfile(Authentication authentication) {
        System.out.println(farmerId(authentication));
        Farmers Farmer = farmersRepository.findById(farmerId(authentication)).get();

        FarmerDTO farmerProfileResponse = FarmerDTO.builder()
                .name(Farmer.getName())
                .farmType(Farmer.getFarmType())
                .contactInfo(Farmer.getContactInfo())
                .farmLocation(Farmer.getFarmLocation())
                .certification(Farmer.getCertification())
                .build();

        Response response = Response.builder()
                .status(HttpStatus.CREATED.value())
                .message("Profile Received Succesfully")
                .farmer(farmerProfileResponse).build();


        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/profile/customer")
    public ResponseEntity<Response> customerProfile(Authentication authentication) {
        Customers customer= customersRepository.findById(customerId(authentication)).get();



        CustomerDTO customerProfileResponse = CustomerDTO.builder()
                .name(customer.getName())
                .address(customer.getAddress())
                .contactInfo(customer.getContactInfo())
                .preferredPaymentMethod(customer.getPreferredPaymentMethod())
                .build();

        Response response = Response.builder()
                .status(HttpStatus.CREATED.value())
                .message("Profile Received Succesfully")
                .customer(customerProfileResponse).build();


        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/profile/farmer")
    public ResponseEntity<Response> updateFarmerProfile(@RequestParam("attribute") String attribute,
                                                        @RequestParam("value") String value,
                                                        Authentication authentication) {
        Integer id = farmerId(authentication);
        Farmers farmer = farmersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Farmer not found"));

        switch (attribute.toLowerCase()) {
            case "name":
                farmer.setName(value);
                break;
            case "farmtype":
                farmer.setFarmType(value);
                break;
            case "contactinfo":
                farmer.setContactInfo(value);
                break;
            case "farmlocation":
                farmer.setFarmLocation(value);
                break;
            case "certification":
                farmer.setCertification(value);
                break;
            default:
                throw new IllegalArgumentException("Invalid attribute: " + attribute);
        }

        farmersRepository.save(farmer);

        Response response = Response.builder()
                .status(HttpStatus.OK.value())
                .message("Farmer profile updated successfully")
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/profile/customer")
    public ResponseEntity<Response> updateCustomerProfile(@RequestParam("attribute") String attribute,
                                                          @RequestParam("value") String value,
                                                          Authentication authentication) {
        Integer id = customerId(authentication);
        Customers customer = customersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        switch (attribute.toLowerCase()) {
            case "name":
                customer.setName(value);
                break;
            case "address":
                customer.setAddress(value);
                break;
            case "contactinfo":
                customer.setContactInfo(value);
                break;
            case "preferredpaymentmethod":
                customer.setPreferredPaymentMethod(value);
                break;
            default:
                throw new IllegalArgumentException("Invalid attribute: " + attribute);
        }

        customersRepository.save(customer);

        Response response = Response.builder()
                .status(HttpStatus.OK.value())
                .message("Customer profile updated successfully")
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
