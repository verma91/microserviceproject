package com.orderservice.controller;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import com.orderservice.model.Order;
import com.orderservice.service.OrderServiceIntf;

@RestController
@RequestMapping("/orders")
@Validated
public class OrderServiceController {
	
	@Autowired
	OrderServiceIntf orderServiceIntf;
	
	@Autowired 
	OrderFeignClient orderFeignClient;

	@PostMapping("/saveOrder/{orderId}")
	public ResponseEntity<String> saveOrder(@Valid @RequestBody Order order , @PathVariable("orderId") Long orderId) throws JSONException{
		Boolean result = (Boolean) new JSONObject(orderFeignClient.findProductByIdResponse(orderId)).get("productFound");
		if(!result) {
			return new ResponseEntity<String>("Order cannot be processed for product with id : "+orderId,HttpStatus.NOT_FOUND);
		}
		order.setCreationDate(new Date());
		order.setOrderStatus("confirmed");
		order.setProductId(orderId);
		orderServiceIntf.saveOrder(order);
		return new ResponseEntity<String>("Order placed successfully",HttpStatus.OK);
		
	}
	
	@GetMapping("/findAllOrder")
	public ResponseEntity<List<Order>> findAllOrder(){
		List<Order> orders = orderServiceIntf.findAllOrder();
		if(orders.size()==0) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(orders,HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteOrderById/{orderId}")
	public ResponseEntity<String> deleteOrderById(@PathVariable("orderId") Long orderId){
		Order order = orderServiceIntf.findOrderById(orderId);
		if(Objects.isNull(order)) {
			return new ResponseEntity<String>("Order is not available with this id :"+orderId,HttpStatus.NOT_FOUND);
		}
		else {
			orderServiceIntf.deleteOrder(order);
		}
		
		return new ResponseEntity<String>("Order is deleted successfully",HttpStatus.OK);
	}
	
	@PutMapping("/cancelOrder/{orderId}")
	public ResponseEntity<Order> cancelOrder(@PathVariable("orderId") Long orderId){
		Order order = orderServiceIntf.findOrderById(orderId);
		if(Objects.isNull(order)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else {
			order.setOrderStatus("cancelled");
			order.setOrderCancelDate(new Date());
			orderServiceIntf.saveOrder(order);
		}
		
		return new ResponseEntity<Order>(order,HttpStatus.OK);
	}
	
	//@HystrixCommand(fallbackMethod = "fallback")
	@GetMapping("/findOrderById/{orderId}")
	public String findOrderById(@PathVariable("orderId") Long orderId) throws JSONException {
		Order order = orderServiceIntf.findOrderById(orderId);
		JSONObject jsonObject = new JSONObject();
		if(Objects.isNull(order)) {
			jsonObject.put("orderFound", false);
		}
		else if(order.getOrderStatus().equals("cancelled")) {
			jsonObject.put("orderFound", true);
			jsonObject.put("orderStatus", order.getOrderStatus());
		}
		else if(order.getOrderStatus().equals("confirmed")) {
			jsonObject = new JSONObject(orderFeignClient.findProductByIdResponse(order.getProductId()));
			if(jsonObject.getBoolean("productFound")) {
				jsonObject.put("orderFound", true);
				jsonObject.put("orderQuantity", order.getOrderQuantity());
				jsonObject.put("orderStatus", order.getOrderStatus());
			}
			else {
				jsonObject.put("orderFound", true);
				jsonObject.put("orderQuantity", order.getOrderQuantity());
				jsonObject.put("orderStatus", order.getOrderStatus());
			}
		}
		return jsonObject.toString();
	}
}
