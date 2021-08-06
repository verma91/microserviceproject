package com.productservice.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.productservice.dao.ProductRepository;
import com.productservice.model.Product;

@Service
public class ProductServiceImpl implements ProductServiceIntf {

	@Autowired
	ProductRepository productRepository;
	
	public void saveProduct(Product product) {
		productRepository.save(product);
		
	}

	public List<Product> findAllProducts() {
		List<Product> products = productRepository.findAll();
		return products;
	}

	public Product findProductById(Long productId) {
		Optional<Product> product = productRepository.findById(productId);
		if(product.isPresent()) {
			return product.get();
		}
		return null;
	}

	public void deleteProduct(Product product) {
		
		productRepository.delete(product);
	}

}
