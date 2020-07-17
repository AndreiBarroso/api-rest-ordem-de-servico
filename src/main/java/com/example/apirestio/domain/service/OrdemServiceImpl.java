package com.example.apirestio.domain.service;


import com.example.apirestio.domain.model.OrdemServico;
import com.example.apirestio.domain.repository.OrdemServicoRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Transactional
@AllArgsConstructor
public class OrdemServiceImpl implements ClienteService {

    private OrdemServicoRepository ordemServicoRepository;
    private ReportService reportService;


    @Override
    public ByteArrayInputStream getReport() throws DocumentException {
        String[] fields = {"Nome", "Descrição", "Preço" , "Data Abertura", "Data Finalização"};
        return reportService.createPDFReport(fillReportRows(), "Ordem Serviço", fields, BaseColor.LIGHT_GRAY);
    }

    private List<String> fillReportRows() {
        List<OrdemServico> result = ordemServicoRepository.findAll();
        List<String> cellsValues = new ArrayList<>();
        Date agora= new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        result.forEach(ordemServico -> {

            cellsValues.add(ordemServico.getCliente().getNome());
            cellsValues.add(ordemServico.getDescricao());
            cellsValues.add(Integer.toString(ordemServico.getPreco()));
            cellsValues.add(ordemServico.getDataAbertura() != null ? dateFormat.format(ordemServico.getDataAbertura()) : "");
            cellsValues.add(ordemServico.getDataFinalizacao() != null ? dateFormat.format(ordemServico.getDataFinalizacao()) : "");



        });


        return cellsValues;
    }
}
