package vttp2022.assessment.csf.orderbackend.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2022.assessment.csf.orderbackend.models.Order;
import vttp2022.assessment.csf.orderbackend.models.OrderSummary;
import vttp2022.assessment.csf.orderbackend.repositories.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private PricingService priceSvc;

	@Autowired
	private OrderRepository orderRepo;

	// POST /api/order
	// Create a new order by inserting into orders table in pizzafactory database
	// IMPORTANT: Do not change the method's signature
	public void createOrder(Order order) {
		orderRepo.saveOrder(order);
	}

	// GET /api/order/<email>/all
	// Get a list of orders for email from orders table in pizzafactory database
	// IMPORTANT: Do not change the method's signature
	public List<OrderSummary> getOrdersByEmail(String email) {
		List<OrderSummary> orderSummaryList = new LinkedList<>();
		// Use priceSvc to calculate the total cost of an order
		List<Order> orderList = orderRepo.retrieveOrders(email);
		for (int i = 0; i < orderList.size(); i++) {
			Order order = orderList.get(i);

			Float totalCost = 
				priceSvc.size(order.getSize()) + 
				priceSvc.sauce(order.getSauce()) + 
				calculateToppingsCost(order.getToppings());

			if (order.isThickCrust()) {
				totalCost = totalCost + priceSvc.thickCrust();
			} else {
				totalCost = totalCost + priceSvc.thinCrust();
			}

			OrderSummary orderSummary = OrderSummary.create(order, totalCost);
			orderSummaryList.add(orderSummary);
		}
		return orderSummaryList;
	}

	private Float calculateToppingsCost(List<String> toppings) {
		Float toppingsCost = 0f;
		for (int i = 0; i < toppings.size(); i++) {
			toppingsCost = toppingsCost + priceSvc.topping(toppings.get(i));
		}
		return toppingsCost;
	}
}
