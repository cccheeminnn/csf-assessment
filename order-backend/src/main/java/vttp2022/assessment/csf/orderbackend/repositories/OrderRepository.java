package vttp2022.assessment.csf.orderbackend.repositories;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.assessment.csf.orderbackend.models.Order;

@Repository
public class OrderRepository {

    private static final String SQL_SAVE_ORDER =
        "insert into orders(name, email, pizza_size, thick_crust, sauce, toppings, comments) values (?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_RETRIEVE_ORDERS =
        "select * from orders where email like ?";

    @Autowired
    private JdbcTemplate template;

    public Boolean saveOrder(Order order) {
        StringBuilder sb = new StringBuilder();
        List<String> orderToppings = order.getToppings();
        for (int i = 0; i < orderToppings.size(); i++) {
            System.out.println(">>> saveOrderToppings: " + orderToppings.get(i));
            sb.append(orderToppings.get(i));
            if (i != (orderToppings.size()-1)) {
                sb.append(",");
            }
        }
        System.out.println(">>>toppings: " + sb.toString());
        int update = template.update(SQL_SAVE_ORDER, 
            order.getName(), order.getEmail(), order.getSize(), 
            order.isThickCrust(), order.getSauce(), sb.toString(), order.getComments());

        if (update == 1) 
            return true;

        return false;
    }

    public List<Order> retrieveOrders(String email) {
        List<Order> orderList = new LinkedList<>();

        SqlRowSet rs = template.queryForRowSet(SQL_RETRIEVE_ORDERS, email);
        while (rs.next()) {
            Order order = Order.createRowSet(rs);
            orderList.add(order);
            System.out.println(">>> Repo retrieveOrders order: " + order.getEmail());
        }

        return orderList;
    }
}
