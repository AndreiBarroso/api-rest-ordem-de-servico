package com.example.apirestio.domain.service;


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
