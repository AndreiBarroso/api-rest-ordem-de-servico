package com.example.apirestio.domain.service;

import com.example.apirestio.domain.model.OrdemServico;
import com.itextpdf.text.DocumentException;

import java.io.ByteArrayInputStream;

public interface GestaoOrdemService {

	ByteArrayInputStream getReport() throws DocumentException;

	OrdemServico save (OrdemServico ordemServico);

	OrdemServico update (OrdemServico ordemServico);

	OrdemServico buscar (Long id);

	void delete(Long id);


}
