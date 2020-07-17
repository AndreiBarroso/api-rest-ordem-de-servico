package com.example.apirestio.domain.service;


import com.itextpdf.text.DocumentException;

import java.io.ByteArrayInputStream;


public interface ClienteService {

    ByteArrayInputStream getReport() throws DocumentException;

}
