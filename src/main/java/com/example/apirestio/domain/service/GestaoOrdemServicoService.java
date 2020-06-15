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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;


@Service
public class GestaoOrdemServicoService {

	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	public OrdemServico criar(OrdemServico ordemServico) {
		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
				.orElseThrow(() -> new NegocioException("Cliente não encontrado"));
		
		ordemServico.setCliente(cliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		
		return ordemServicoRepository.save(ordemServico);
	}
	
	public void finalizar(Long ordemServicoId) {
		OrdemServico ordemServico = buscar(ordemServicoId);
		
		ordemServico.finalizar();
		
		ordemServicoRepository.save(ordemServico);
	}
	
	public Comentario adicionarComentario(Long ordemServicoId, String descricao) {
		OrdemServico ordemServico = buscar(ordemServicoId);
		
		Comentario comentario = new Comentario();
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setDescricao(descricao);
		comentario.setOrdemServico(ordemServico);
		
		return comentarioRepository.save(comentario);
	}
	
	private OrdemServico buscar(Long ordemServicoId) {
		return ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada"));
	}
	
}
