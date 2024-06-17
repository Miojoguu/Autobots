package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelos.telefone.AdicionadorLinkTelefone;
import com.autobots.automanager.modelos.cliente.ClienteSelecionador;
import com.autobots.automanager.modelos.telefone.TelefoneSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.TelefoneRepositorio;

@RestController
@RequestMapping("/telefone")
public class TelefoneControle {
	@Autowired
	private TelefoneRepositorio repositorio;
	@Autowired
	private AdicionadorLinkTelefone adicionadorLinkTelefone;
	@Autowired
	private TelefoneSelecionador selecionarTelefone;
	@Autowired
	private ClienteRepositorio clienteRepositorio;
	@Autowired
	private ClienteSelecionador clienteSelecionador;

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

	@PutMapping("/cadastro")
	public void cadastrarTelefone(@RequestBody Cliente cliente) {
		List<Cliente> clientes = clienteRepositorio.findAll();
		Cliente clienteAlvo = clienteSelecionador.selecionar(clientes, cliente.getId());
		clienteAlvo.getTelefones().addAll(cliente.getTelefones());
		clienteRepositorio.save(clienteAlvo);
	}

	@DeleteMapping("/excluir/{clienteId}/{telefoneId}")
	public void deletarTelefone(@PathVariable long clienteId, @PathVariable long telefoneId) {
		Cliente cliente = clienteRepositorio.getById(clienteId);
		List<Telefone> listaTelefones = cliente.getTelefones();
		Telefone findTelefone = null;
		for (Telefone telefone : listaTelefones) {
			if (telefone.getId() == telefoneId) {
				findTelefone = telefone;
			}
		}
		listaTelefones.remove(findTelefone);
		clienteRepositorio.save(cliente);
	}
}