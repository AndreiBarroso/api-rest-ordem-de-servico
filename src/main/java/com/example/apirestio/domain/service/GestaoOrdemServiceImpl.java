package com.example.apirestio.domain.service;

import com.example.apirestio.api.model.Comentario;
import com.example.apirestio.domain.exception.EntidadeNaoEncontradaException;
import com.example.apirestio.domain.exception.NegocioException;
import com.example.apirestio.domain.model.Cliente;
import com.example.apirestio.domain.model.OrdemServico;
import com.example.apirestio.domain.model.StatusOrdemServico;
import com.example.apirestio.domain.repository.ClienteRepository;
import com.example.apirestio.domain.repository.ComentarioRepository;
import com.example.apirestio.domain.repository.OrdemServicoRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Transactional
@AllArgsConstructor
public class GestaoOrdemServiceImpl implements  GestaoOrdemService{

	@Autowired
	private OrdemServicoRepository ordemServicoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ReportService reportService;

	@Autowired
	private ComentarioRepository comentarioRepository;



	@Override
	public OrdemServico save(OrdemServico ordemServico) {
		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
				.orElseThrow(() -> new NegocioException("Cliente não encontrado"));

		ordemServico.setCliente(cliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(Date.from(Instant.now()));

		return ordemServicoRepository.save(ordemServico);
	}

	public void finalizar(Long ordemServicoId) {
		OrdemServico ordemServico = buscar(ordemServicoId);

		ordemServico.finalizar();

		ordemServicoRepository.save(ordemServico);
	}

	@Override
	public OrdemServico update(OrdemServico ordemServico) {
		return null;
	}

	@Override
	public OrdemServico buscar(Long ordemServicoId) {
		return ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada"));
	}

	@Override
	public void delete(Long id) {

	}

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

	public Comentario adicionarComentario(Long ordemServicoId, String descricao) {
		OrdemServico ordemServico = buscar(ordemServicoId);

		Comentario comentario = new Comentario();
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setDescricao(descricao);
		comentario.setOrdemServico(ordemServico);

		return comentarioRepository.save(comentario);
	}
}
