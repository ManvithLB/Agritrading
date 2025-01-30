package com.agritrading.AgritradingApplication.service;

import com.agritrading.AgritradingApplication.dto.AddProductResponseDTO;
import com.agritrading.AgritradingApplication.dto.ProductDTO;
import com.agritrading.AgritradingApplication.model.Products;
import com.agritrading.AgritradingApplication.repo.ProductsRepository;
import com.agritrading.AgritradingApplication.util.MapProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductsService {
    @Autowired
    private ProductsRepository productsRepository;

    public ProductDTO addProduct(Products product) {
        productsRepository.save(product);
        return MapProductDTO.map(product);
    }

    public List<ProductDTO> getAllProducts(int farmerId) {
        List<Products> productsList = productsRepository.findAllByFarmerId(farmerId);
        return MapProductDTO.map(productsList);
    }

    public List<ProductDTO> getAllProductsForCustomer() {
        List<Products> productsList = productsRepository.findAll();
        return MapProductDTO.map(productsList);
    }

    public ProductDTO getProductById(Integer id) throws Exception {
        Optional<Products> productOptional = productsRepository.findById(id);

        // Check if the product exists and return it, otherwise return null or throw an exception
        if (productOptional.isPresent()) {
            Products product =  productOptional.get();
            return MapProductDTO.map(product);
        } else {
            // Handle the case where the product is not found
            throw new Exception("Product not foundww with id: " + id);
        }
    }

    public List<ProductDTO> getProductsByCategory(String category, Integer farmerId) {
        List<Products> productsList =  productsRepository.findProductsByCategory(farmerId, category);
        return MapProductDTO.map(productsList);
    }

}
