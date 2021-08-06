package com.orderservice.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.orderservice.model.Order;

@FeignClient(value = "productapi-service")
public interface OrderFeignClient {

	@GetMapping("/products/findProductById/{productId}")
	String findProductByIdResponse(@PathVariable("productId") Long productId);
}
