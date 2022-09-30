package vttp2022.assessment.csf.orderbackend.models;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

// IMPORTANT: You can add to this class, but you cannot delete its original content

public class Order {

	private Integer orderId;
	private String name;
	private String email;
	private Integer size;
	private String sauce;
	private Boolean thickCrust;
	private List<String> toppings = new LinkedList<>();
	private String comments;

	public void setOrderId(Integer orderId) { this.orderId = orderId; }
	public Integer getOrderId() { return this.orderId; }

	public void setName(String name) { this.name = name; }
	public String getName() { return this.name; }

	public void setEmail(String email) { this.email = email; }
	public String getEmail() { return this.email; }

	public void setSize(Integer size) { this.size = size; }
	public Integer getSize() { return this.size; }

	public void setSauce(String sauce) { this.sauce = sauce; }
	public String getSauce() { return this.sauce; }

	public void setThickCrust(Boolean thickCrust) { this.thickCrust = thickCrust; }
	public Boolean isThickCrust() { return this.thickCrust; }

	public void setToppings(List<String> toppings) { this.toppings = toppings; }
	public List<String> getToppings() { return this.toppings; }
	public void addTopping(String topping) { this.toppings.add(topping); }

	public void setComments(String comments) { this.comments = comments; }
	public String getComments() { return this.comments; }

	public static Order createPayload(String payload) {
		JsonReader reader = Json.createReader(new StringReader(payload));
		JsonObject object = reader.readObject();

		Order order = new Order();
		order.setName(object.getString("name"));
		order.setEmail(object.getString("email"));
		order.setSize(object.getInt("size"));
		order.setSauce(object.getString("sauce"));
		String base = object.getString("base");
		if (base.equals("thin")) {
			order.setThickCrust(false);
		} else {
			order.setThickCrust(true);
		}

		List<String> toppingsList = new LinkedList<>();
		JsonArray toppingsArray = object.getJsonArray("toppings");
		for (int i = 0; i < toppingsArray.size(); i++) {
			String topping = toppingsArray.getJsonObject(i).getString("topping");
			toppingsList.add(topping);
		}
		order.setToppings(toppingsList);
		order.setComments(object.getString("comments"));

		return order;
	}
	
	public static Order createRowSet(SqlRowSet rs) {
		Order order = new Order();
		order.setOrderId(rs.getInt("order_id"));
		order.setName(rs.getString("name"));
		order.setEmail(rs.getString("email"));
		order.setSize(rs.getInt("pizza_size"));
		order.setSauce(rs.getString("sauce"));

		if (rs.getBoolean("thick_crust")) {
			order.setThickCrust(true);
		} else {
			order.setThickCrust(false);
		}

		String toppingsStr = rs.getString("toppings");
		String[] toppingsStrArr;
		List<String> toppingsList = new LinkedList<>();
		if (toppingsStr != null) {
			toppingsStrArr = toppingsStr.split(",");
			for (int i = 0; i < toppingsStrArr.length; i++) {
				toppingsList.add(toppingsStrArr[i]);
			}
		}
		order.setToppings(toppingsList);

		order.setComments(rs.getString("comments"));
		return order;
	}
}
