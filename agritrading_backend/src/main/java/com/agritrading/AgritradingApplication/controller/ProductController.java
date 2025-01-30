package com.agritrading.AgritradingApplication.controller;

import com.agritrading.AgritradingApplication.dto.AddProductResponseDTO;
import com.agritrading.AgritradingApplication.dto.ProductDTO;
import com.agritrading.AgritradingApplication.dto.Response;
import com.agritrading.AgritradingApplication.model.Products;
import com.agritrading.AgritradingApplication.model.Users;
import com.agritrading.AgritradingApplication.repo.FarmersRepository;
import com.agritrading.AgritradingApplication.repo.ProductsRepository;
import com.agritrading.AgritradingApplication.repo.UserRepo;
import com.agritrading.AgritradingApplication.service.ImageService;
import com.agritrading.AgritradingApplication.service.ProductsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/farmers")
public class ProductController {
    private final UserRepo userRepo;
    @Autowired
    private ProductsService productsService;
    @Autowired
    private FarmersRepository farmersRepository;
    @Autowired
    private ProductsRepository productsRepository;

    public ProductController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping("/product")
    public ResponseEntity<Response> addProducts(
            @RequestPart("products") String productsJson,
            @RequestParam("prodImage") MultipartFile prodImage,
            Authentication authentication
    ) throws IOException {
        String username = authentication.getName();
        String uploadDirectory = "src/main/resources/static/images/products";
        String prodImageUrl = ImageService.saveImageToStorage(uploadDirectory, prodImage);
        ObjectMapper objectMapper = new ObjectMapper();
        Products products = objectMapper.readValue(productsJson, Products.class);
        products.setProd_Img(prodImageUrl);
        Users user = userRepo.findByUsername(username);
        products.setFarmer(farmersRepository.findById(user.getFarmerId()).orElse(null));
        ProductDTO productResponse = productsService.addProduct(products);
        Response response = Response.builder()
                .status(HttpStatus.CREATED.value())
                .message("Product created successfully")
                .product(productResponse)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("/product/all")
    public ResponseEntity<Response> getProducts( Authentication authentication)  throws Exception {
        String username = authentication.getName();

        Users user = userRepo.findByUsername(username);
        int farmerId = user.getFarmerId();
        List<ProductDTO> productsList =  productsService.getAllProducts(farmerId);
        Response response = Response.builder()
                .status(HttpStatus.CREATED.value())
                .message("Product created successfully")
                .productList(productsList).build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/product/by-category")
    public ResponseEntity<Response> getProductsByCategory(@RequestParam("category") Optional<String> category,  Authentication authentication) throws Exception {
        String username = authentication.getName();
        Users user = userRepo.findByUsername(username);
        int farmerId = user.getFarmerId();
        if(category.isPresent()) {
            String categoryName = category.get();
            List<ProductDTO> productsList =  productsService.getProductsByCategory(categoryName , farmerId);
            Response response = Response.builder()
                    .status(HttpStatus.CREATED.value())
                    .message("Product created successfully")
                    .productList(productsList).build();

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        }
        throw new Exception();
    }
    @GetMapping("/product")
    public ResponseEntity<Response> getProducts(@RequestParam("id") Optional<Integer> id ,  Authentication authentication) throws Exception {
        if(id.isPresent()) {
            ProductDTO productResponse =  productsService.getProductById(id.get());
            Response response = Response.builder()
                    .status(HttpStatus.CREATED.value())
                    .message("Product created successfully")
                    .product(productResponse).build();

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        else{
            throw new Exception();
        }
    }

    @PutMapping("/product")
    public ResponseEntity<Response> updateProduct(
            @RequestPart("products") String productsJson,
            @RequestParam(value = "prodImage", required = false) MultipartFile prodImage,
            @RequestParam("id") int id,
            Authentication authentication) throws Exception {
        String username = authentication.getName();
        Users user = userRepo.findByUsername(username);
        int farmerId = user.getFarmerId();

        Products existingProduct = productsRepository.findById(id).orElseThrow(() -> new Exception("Product not found"));

        // Check ownership
        if (existingProduct.getFarmer().getFarmerId() != farmerId) {
            throw new Exception("Not allowed to update this product");
        }

        // Parse product JSON
        ObjectMapper objectMapper = new ObjectMapper();
        Products updatedProduct = objectMapper.readValue(productsJson, Products.class);

        // Handle image upload
        if (prodImage != null && !prodImage.isEmpty()) {
            String uploadDirectory = "src/main/resources/static/images/products";
            String prodImageUrl = ImageService.saveImageToStorage(uploadDirectory, prodImage);
            updatedProduct.setProd_Img(prodImageUrl);
        } else {
            updatedProduct.setProd_Img(existingProduct.getProd_Img()); // Use existing image
        }

        updatedProduct.setProd_id(existingProduct.getProd_id());
        updatedProduct.setFarmer(farmersRepository.findById(farmerId).orElse(null));

        ProductDTO productResponse = productsService.addProduct(updatedProduct);

        Response response = Response.builder()
                .status(HttpStatus.OK.value())
                .message("Product updated successfully")
                .product(productResponse)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/product")
    public ResponseEntity<Response> deleteProduct(
            @RequestParam("id") int id,
            Authentication authentication) throws Exception {
        String username = authentication.getName();
        Users user = userRepo.findByUsername(username);
        int farmerId = user.getFarmerId();

        Products product = productsRepository.findById(id).orElseThrow(() -> new Exception("Product not found"));

        // Check ownership
        if (product.getFarmer().getFarmerId() != farmerId) {
            throw new Exception("Not allowed to delete this product");
        }

        productsRepository.deleteById(id);

        Response response = Response.builder()
                .status(HttpStatus.OK.value())
                .message("Product deleted successfully")
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }





}
