package com.productservice.service;


import java.util.List;

import com.productservice.model.Product;

public interface ProductServiceIntf {

	void saveProduct(Product product);

	List<Product> findAllProducts();

	Product findProductById(Long productId);

	void deleteProduct(Product product);

}
