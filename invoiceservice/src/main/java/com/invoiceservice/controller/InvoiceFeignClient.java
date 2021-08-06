package com.invoiceservice.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "orderapi-service")
public interface InvoiceFeignClient {

	@GetMapping("/orders/findOrderById/{orderId}")
	String findOrderByIdResponse(@PathVariable("orderId") Long orderId);
}
