package com.example.apirestio.domain.service;


import com.example.apirestio.domain.exception.NegocioException;
import com.example.apirestio.domain.model.Cliente;
import com.example.apirestio.domain.repository.ClienteRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
@AllArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private ClienteRepository clienteRepository;
    private ReportService reportService;


    @Override
    public ByteArrayInputStream getReport() throws DocumentException {
        String[] fields = {"Nome", "Telefone", "Email"};
        return reportService.createPDFReport(fillReportRows(), "Clientes", fields, BaseColor.LIGHT_GRAY);
    }

    @Override
    public Cliente save(Cliente cliente) {
        Cliente clienteExistente = clienteRepository.findByEmail(cliente.getEmail());

        if (clienteExistente != null && !clienteExistente.equals(cliente)) {
            throw new NegocioException("Já existe um cliente cadastrado com este e-mail.");
        }

        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente update(Cliente cliente) {
        return null;
    }

    @Override
    public Cliente buscar(Long clienteId) {
        Optional<Cliente> cliente = clienteRepository.findById(clienteId);

        return cliente.orElseThrow(() -> new NegocioException(
                "Objeto não encontrado! Id: " + clienteId + ", Tipo: " + Cliente.class.getName()));
    }

    @Override
    public void delete(Long clienteId) {
        clienteRepository.deleteById(clienteId);
    }


    private List<String> fillReportRows() {
        List<Cliente> result = clienteRepository.findAll();
        List<String> cellsValues = new ArrayList<>();

        result.forEach(cliente -> {

            cellsValues.add(cliente.getNome());
            cellsValues.add(cliente.getTelefone());
            cellsValues.add(cliente.getEmail());

        });


        return cellsValues;
    }
}
