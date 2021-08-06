package com.orderservice.service;

import java.util.List;

import javax.validation.Valid;

import com.orderservice.model.Order;

public interface OrderServiceIntf {

	void saveOrder(@Valid Order order);

	List<Order> findAllOrder();

	Order findOrderById(Long orderId);

	void deleteOrder(Order order);

}
