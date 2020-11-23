package com.wergnet.wergnetoil.api.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import com.wergnet.wergnetoil.api.model.Card;
import com.wergnet.wergnetoil.api.resource.CardResource;
import com.wergnet.wergnetoil.api.service.CardNumberGenerator;
import com.wergnet.wergnetoil.api.service.CardService;

import io.restassured.http.ContentType;

@WebMvcTest
public class CardControllerTest {

	@Autowired
	private  CardResource cardResource;
	
	@MockBean
	private CardService cardService;
	
	@BeforeEach
	public void setup() {
		standaloneSetup(this.cardResource);
	}
	
	@Test
	public void returnSuccess_WhenGetCardByCode() {
		Card card = new Card();
		card.setId(1L);
		card.setCardNumber(new CardNumberGenerator().toString());
		card.setActive(true);
		card.setBalance(new BigDecimal(50.0));		
		
		when(this.cardService.getCardByCode(1L))
			.thenReturn(card);
		
		given()
			.accept(ContentType.JSON)
		.when()
			.get("/cards/{code}", 1L)
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
}
