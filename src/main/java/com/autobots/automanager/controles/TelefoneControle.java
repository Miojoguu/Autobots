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

import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelo.telefone.AdicionadorLinkTelefone;
import com.autobots.automanager.modelo.telefone.TelefoneSelecionador;
import com.autobots.automanager.repositorios.RepositorioTelefone;

@RestController
@RequestMapping("/telefone")
public class TelefoneControle {
	@Autowired
	private RepositorioTelefone repositorio;
	@Autowired
	private AdicionadorLinkTelefone adicionadorLinkTelefone;
	@Autowired
	private TelefoneSelecionador selecionarTelefone;

	@GetMapping("/telefones")
	public ResponseEntity<List<Telefone>> obterTelefones() {
		HttpStatus status = HttpStatus.NOT_FOUND;
		List<Telefone> telefones = repositorio.findAll();
		if (telefones == null) {
			return new ResponseEntity<List<Telefone>>(status);
		} else {
			adicionadorLinkTelefone.adicionarLink(telefones);
			status = HttpStatus.FOUND;
			return new ResponseEntity<List<Telefone>>(telefones, status);
		}
	}

	@GetMapping("/{id}")
	public Telefone obterTelefone(@PathVariable long id) {
		List<Telefone> telefones = repositorio.findAll();
		return selecionarTelefone.selecionar(telefones, id);
	}

	@PostMapping("/cadastro")
	public ResponseEntity<?> cadastrarTelefone(@RequestBody Telefone telefone) {
		HttpStatus status = HttpStatus.CONFLICT;
		if (telefone.getId() == null) {
			repositorio.save(telefone);
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<>(status);
	}

	@DeleteMapping("/excluir/")
	public ResponseEntity<?> deletarTelefone(@RequestBody Telefone exclusao) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Telefone telefone = repositorio.getById(exclusao.getId());
		if (telefone != null) {
			repositorio.delete(telefone);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
	}
}
