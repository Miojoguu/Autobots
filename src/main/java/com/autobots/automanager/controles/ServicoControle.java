package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.modelo.servico.AdicionadorLinkServico;
import com.autobots.automanager.modelo.servico.ServicoSelecionador;
import com.autobots.automanager.repositorios.RepositorioServico;

@RestController
@RequestMapping("/servico")
public class ServicoControle {
	@Autowired
	private RepositorioServico repositorio;
	@Autowired
	private AdicionadorLinkServico adicionadorLinkServico;
	@Autowired
	private ServicoSelecionador selecionarServico;

	@GetMapping("/servicos")
	public ResponseEntity<List<Servico>> obterServicos() {
		HttpStatus status = HttpStatus.NOT_FOUND;
		List<Servico> servicos = repositorio.findAll();
		if (servicos == null) {
			return new ResponseEntity<List<Servico>>(status);
		} else {
			adicionadorLinkServico.adicionarLink(servicos);
			status = HttpStatus.FOUND;
			return new ResponseEntity<List<Servico>>(servicos, status);
		}
	}

	@GetMapping("/{id}")
	public Servico obterServico(@PathVariable long id) {
		List<Servico> servicos = repositorio.findAll();
		return selecionarServico.selecionar(servicos, id);
	}

	@PostMapping("/cadastro")
	public ResponseEntity<?> cadastrarServico(@RequestBody Servico servico) {
		HttpStatus status = HttpStatus.CONFLICT;
		if (servico.getId() == null) {
			repositorio.save(servico);
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<>(status);
	}

	@DeleteMapping("/excluir/")
	public ResponseEntity<?> deletarServico(@RequestBody Servico exclusao) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Servico servico = repositorio.getById(exclusao.getId());
		if (servico != null) {
			repositorio.delete(servico);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
	}
}
