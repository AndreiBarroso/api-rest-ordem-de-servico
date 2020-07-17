package com.example.apirestio.domain.service;


import com.itextpdf.text.DocumentException;

import java.io.ByteArrayInputStream;


public interface OrdemService {

    ByteArrayInputStream getReport() throws DocumentException;

}
