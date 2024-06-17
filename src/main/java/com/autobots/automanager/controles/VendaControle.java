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

import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.modelo.venda.AdicionadorLinkVenda;
import com.autobots.automanager.modelo.venda.VendaSelecionador;
import com.autobots.automanager.repositorios.RepositorioVenda;

@RestController
@RequestMapping("/venda")
public class VendaControle {
	@Autowired
	private RepositorioVenda repositorio;
	@Autowired
	private AdicionadorLinkVenda adicionadorLinkVenda;
	@Autowired
	private VendaSelecionador selecionarVenda;

	@GetMapping("/vendas")
	public ResponseEntity<List<Venda>> obterVendas() {
		HttpStatus status = HttpStatus.NOT_FOUND;
		List<Venda> vendas = repositorio.findAll();
		if (vendas == null) {
			return new ResponseEntity<List<Venda>>(status);
		} else {
			adicionadorLinkVenda.adicionarLink(vendas);
			status = HttpStatus.FOUND;
			return new ResponseEntity<List<Venda>>(vendas, status);
		}
	}

	@GetMapping("/{id}")
	public Venda obterVenda(@PathVariable long id) {
		List<Venda> vendas = repositorio.findAll();
		return selecionarVenda.selecionar(vendas, id);
	}

	@PostMapping("/cadastro")
	public ResponseEntity<?> cadastrarVenda(@RequestBody Venda venda) {
		HttpStatus status = HttpStatus.CONFLICT;
		if (venda.getId() == null) {
			repositorio.save(venda);
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<>(status);
	}

	@DeleteMapping("/excluir/")
	public ResponseEntity<?> deletarVenda(@RequestBody Venda exclusao) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Venda venda = repositorio.getById(exclusao.getId());
		if (venda != null) {
			repositorio.delete(venda);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
	}
}
