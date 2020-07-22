package com.example.apirestio.api.controller;

import com.example.apirestio.domain.model.Cliente;
import com.example.apirestio.domain.repository.ClienteRepository;
import com.example.apirestio.domain.service.CadastroClienteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ClienteControllerTeste {

	@InjectMocks
	private ClienteController controller;

	@Mock
	private ClienteRepository repository;

	@Mock
    private CadastroClienteService service;

	@Test
    public void testaBuscarExistente () {
		Mockito.when(repository.findById(10L)).thenReturn(Optional.of(new Cliente()));
		ResponseEntity<Cliente> resultado = controller.buscar(10L);
		Assertions.assertEquals(HttpStatus.OK, resultado.getStatusCode());
//    }
	}

}