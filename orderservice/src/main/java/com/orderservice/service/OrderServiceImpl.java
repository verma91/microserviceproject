package com.orderservice.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orderservice.dao.OrderRepository;
import com.orderservice.model.Order;

@Service
public class OrderServiceImpl implements OrderServiceIntf {

	@Autowired
	OrderRepository orderRepository;
	
	public void saveOrder(@Valid Order order) {
		orderRepository.save(order);
		
	}

	public List<Order> findAllOrder() {
		List<Order> orders = orderRepository.findAll();
		return orders;
	}

	public Order findOrderById(Long orderId) {
		Optional<Order> order = orderRepository.findById(orderId);
		if(order.isPresent()) {
			return order.get();
		}
		return null;
	}

	public void deleteOrder(Order order) {
		orderRepository.delete(order);
		
	}

}
