package com.example.apirestio.api.controller;


import com.example.apirestio.domain.model.Cliente;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.core.Is.is;

class ClienteControllerTest {


	@Test
	public void listaClienteGet () {

		when().
				get("/clientes").
				then().statusCode(200).
				body("id[0]", is(1)).
				body("id[1]",is( 2));

	}

	@Test
	public void criaClientePost (){

		Cliente cliente= new Cliente (null ,  "andr"  , "andrei.@h", "981560715");

		given()
				.body(cliente).contentType(ContentType.JSON).
				when().
				post("/clientes").
				then().statusCode(201);

	}

}