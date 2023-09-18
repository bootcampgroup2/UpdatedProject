package com.casestudy.orderservice.model;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection="order")
public class Order {

    private String name;
    private int qty;
    private double price;
    
    public Order() {}
    
	@Override
	public String toString() {
		return "Order [ name=" + name + ", qty=" + qty + ", price=" + price + "]";
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Order(String orderId, String name, int qty, double price) {
		super();

		this.name = name;
		this.qty = qty;
		this.price = price;
	}
}
