package com.casestudy.orderservice.kafka;


//import com.casestudy.orderservice.model.Order;
import com.fasterxml.jackson.annotation.*;


@JsonIgnoreType // Exclude type information from serialization
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public class OrderEvent {


    private String message;
    private String email;

    
    public OrderEvent() {

	}

//	@JsonCreator
//	public OrderEvent(@JsonProperty("message") String message, @JsonProperty("email") String email) {
//		this.message = message;
//		this.email = email;
//	}

	public OrderEvent(String message, String email) {
		this.message = message;
		this.email = email;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "OrderEvent{" +
				"message='" + message  +
				", email='" + email +
				'}';
	}
}
