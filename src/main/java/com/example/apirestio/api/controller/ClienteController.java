package com.example.apirestio.api.controller;

import com.example.apirestio.domain.model.Cliente;
import com.example.apirestio.domain.repository.ClienteRepository;
import com.example.apirestio.domain.service.CadastroClienteService;
import com.example.apirestio.domain.service.ClienteServiceImpl;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CadastroClienteService cadastroCliente;

    private ClienteServiceImpl clienteServiceReport;

    public ClienteController (ClienteServiceImpl clienteServiceReport) {
        this.clienteServiceReport = clienteServiceReport;
    }

    @GetMapping
    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) {
        Optional<Cliente> cliente = clienteRepository.findById(clienteId);

        if (cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente adicionar(@Valid @RequestBody Cliente cliente) {
        return cadastroCliente.salvar(cliente);
    }

    @PutMapping("/{clienteId}")
    public ResponseEntity<Cliente> atualizar(@Valid @PathVariable Long clienteId,
                                             @RequestBody Cliente cliente) {

        if (!clienteRepository.existsById(clienteId)) {
            return ResponseEntity.notFound().build();
        }

        cliente.setId(clienteId);
        cliente = cadastroCliente.salvar(cliente);

        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/{clienteId}")
    public ResponseEntity<Void> remover(@PathVariable Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            return ResponseEntity.notFound().build();
        }

        cadastroCliente.excluir(clienteId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/report")
    public @ResponseBody
    ResponseEntity<InputStreamResource> downloadReport(HttpServletResponse response) throws DocumentException {
        ByteArrayInputStream result = clienteServiceReport.getReport();
        HttpHeaders headers = new HttpHeaders();
        String documentName = "cliente-" + LocalDate.now().toString() + ".pdf";
        headers.add("Content-Disposition", "inline; filename=" + documentName);

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(result));
    }


}

