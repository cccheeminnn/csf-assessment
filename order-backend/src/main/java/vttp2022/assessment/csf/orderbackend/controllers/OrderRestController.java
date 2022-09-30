package vttp2022.assessment.csf.orderbackend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import vttp2022.assessment.csf.orderbackend.models.Order;
import vttp2022.assessment.csf.orderbackend.models.OrderSummary;
import vttp2022.assessment.csf.orderbackend.models.Response;
import vttp2022.assessment.csf.orderbackend.services.OrderService;

@RestController
@RequestMapping(path = "/api/order/", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderRestController {

    @Autowired
    private OrderService orderSvc;

    @GetMapping(path = "{email}/all")
    public ResponseEntity<String> getOrders(@PathVariable String email) {
        System.out.println(">>> GetMapping email: " + email);
        List<OrderSummary> ordSummList = orderSvc.getOrdersByEmail(email);
        Response resp = new Response();
        // order found
        if (ordSummList.size() >= 1) {
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder(); 
            for (int i = 0; i < ordSummList.size(); i++) {
                JsonObject jsonObject = ordSummList.get(i).toJson();
                arrayBuilder.add(jsonObject);
            }
            resp.setMessage("Orders retrieved.");
            resp.setCode(HttpStatus.OK.value());
            resp.setData(arrayBuilder.build());
        } else {
            // no orders found
            resp.setMessage("No orders found for email inputted.");
            resp.setCode(HttpStatus.NOT_FOUND.value());
            resp.setData(null);
        }

        return ResponseEntity.status(resp.getCode()).body(resp.toJson().toString());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postOrders(@RequestBody String payload) {
        Order order = Order.createPayload(payload);
        orderSvc.createOrder(order);

        Response resp = new Response();
        resp.setMessage("Order saved.");
        resp.setCode(HttpStatus.OK.value());
        resp.setData(null);

        return ResponseEntity.status(resp.getCode()).body(resp.toJson().toString());
    }
}
