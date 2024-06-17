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

import com.autobots.automanager.entidades.Email;
import com.autobots.automanager.modelo.email.AdicionadorLinkEmail;
import com.autobots.automanager.modelo.email.EmailSelecionador;
import com.autobots.automanager.repositorios.RepositorioEmail;

@RestController
@RequestMapping("/email")
public class EmailControle {
	@Autowired
	private RepositorioEmail repositorio;
	@Autowired
	private AdicionadorLinkEmail adicionadorLinkEmail;
	@Autowired
	private EmailSelecionador selecionarEmail;

	@GetMapping("/emails")
	public ResponseEntity<List<Email>> obterEmails() {
		HttpStatus status = HttpStatus.NOT_FOUND;
		List<Email> emails = repositorio.findAll();
		if (emails == null) {
			return new ResponseEntity<List<Email>>(status);
		} else {
			adicionadorLinkEmail.adicionarLink(emails);
			status = HttpStatus.FOUND;
			return new ResponseEntity<List<Email>>(emails, status);
		}
	}

	@GetMapping("/{id}")
	public Email obterEmail(@PathVariable long id) {
		List<Email> emails = repositorio.findAll();
		return selecionarEmail.selecionar(emails, id);
	}

	@PostMapping("/cadastro")
	public ResponseEntity<?> cadastrarEmail(@RequestBody Email email) {
		HttpStatus status = HttpStatus.CONFLICT;
		if (email.getId() == null) {
			repositorio.save(email);
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<>(status);
	}

	@DeleteMapping("/excluir/")
	public ResponseEntity<?> deletarEmail(@RequestBody Email exclusao) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Email email = repositorio.getById(exclusao.getId());
		if (email != null) {
			repositorio.delete(email);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
	}
}
