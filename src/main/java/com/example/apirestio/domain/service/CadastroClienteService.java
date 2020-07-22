package com.example.apirestio.domain.service;


import com.example.apirestio.domain.exception.NegocioException;
import com.example.apirestio.domain.model.Cliente;
import com.example.apirestio.domain.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CadastroClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente buscarPorId ( Long clienteId) {
        Optional<Cliente> cliente = clienteRepository.findById(clienteId);

        return cliente.orElseThrow(() -> new NegocioException(
                "Objeto não encontrado! Id: " + clienteId + ", Tipo: " + Cliente.class.getName()));
    }

    public Cliente salvar(Cliente cliente) {
        Cliente clienteExistente = clienteRepository.findByEmail(cliente.getEmail());

        if (clienteExistente != null && !clienteExistente.equals(cliente)) {
            throw new NegocioException("Já existe um cliente cadastrado com este e-mail.");
        }

        return clienteRepository.save(cliente);
    }

    public void excluir(Long clienteId) {
        clienteRepository.deleteById(clienteId);
    }

}