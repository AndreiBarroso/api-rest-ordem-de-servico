package com.example.apirestio.domain.service;


import com.example.apirestio.domain.model.Cliente;
import com.itextpdf.text.DocumentException;

import java.io.ByteArrayInputStream;


public interface ClienteService {

    ByteArrayInputStream getReport() throws DocumentException;

    Cliente save (Cliente cliente);

    Cliente update (Cliente cliente);

    Cliente buscar (Long id);

    void delete(Long id);


}
