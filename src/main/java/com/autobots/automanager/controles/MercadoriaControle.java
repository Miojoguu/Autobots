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

import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.modelo.mercadoria.AdicionadorLinkMercadoria;
import com.autobots.automanager.modelo.mercadoria.MercadoriaSelecionador;
import com.autobots.automanager.repositorios.RepositorioMercadoria;

@RestController
@RequestMapping("/mercadoria")
public class MercadoriaControle {
	@Autowired
	private RepositorioMercadoria repositorio;
	@Autowired
	private AdicionadorLinkMercadoria adicionadorLinkMercadoria;
	@Autowired
	private MercadoriaSelecionador selecionarMercadoria;

	@GetMapping("/mercadorias")
	public ResponseEntity<List<Mercadoria>> obterMercadorias() {
		HttpStatus status = HttpStatus.NOT_FOUND;
		List<Mercadoria> mercadorias = repositorio.findAll();
		if (mercadorias == null) {
			return new ResponseEntity<List<Mercadoria>>(status);
		} else {
			adicionadorLinkMercadoria.adicionarLink(mercadorias);
			status = HttpStatus.FOUND;
			return new ResponseEntity<List<Mercadoria>>(mercadorias, status);
		}
	}

	@GetMapping("/{id}")
	public Mercadoria obterMercadoria(@PathVariable long id) {
		List<Mercadoria> mercadorias = repositorio.findAll();
		return selecionarMercadoria.selecionar(mercadorias, id);
	}

	@PostMapping("/cadastro")
	public ResponseEntity<?> cadastrarMercadoria(@RequestBody Mercadoria mercadoria) {
		HttpStatus status = HttpStatus.CONFLICT;
		if (mercadoria.getId() == null) {
			repositorio.save(mercadoria);
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<>(status);
	}

	@DeleteMapping("/excluir/")
	public ResponseEntity<?> deletarMercadoria(@RequestBody Mercadoria exclusao) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Mercadoria mercadoria = repositorio.getById(exclusao.getId());
		if (mercadoria != null) {
			repositorio.delete(mercadoria);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
	}
}
