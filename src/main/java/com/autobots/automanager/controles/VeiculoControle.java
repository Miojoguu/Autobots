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

import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.modelo.veiculo.AdicionadorLinkVeiculo;
import com.autobots.automanager.modelo.veiculo.VeiculoSelecionador;
import com.autobots.automanager.repositorios.RepositorioVeiculo;

@RestController
@RequestMapping("/veiculo")
public class VeiculoControle {
	@Autowired
	private RepositorioVeiculo repositorio;
	@Autowired
	private AdicionadorLinkVeiculo adicionadorLinkVeiculo;
	@Autowired
	private VeiculoSelecionador selecionarVeiculo;

	@GetMapping("/veiculos")
	public ResponseEntity<List<Veiculo>> obterVeiculos() {
		HttpStatus status = HttpStatus.NOT_FOUND;
		List<Veiculo> veiculos = repositorio.findAll();
		if (veiculos == null) {
			return new ResponseEntity<List<Veiculo>>(status);
		} else {
			adicionadorLinkVeiculo.adicionarLink(veiculos);
			status = HttpStatus.FOUND;
			return new ResponseEntity<List<Veiculo>>(veiculos, status);
		}
	}

	@GetMapping("/{id}")
	public Veiculo obterVeiculo(@PathVariable long id) {
		List<Veiculo> veiculos = repositorio.findAll();
		return selecionarVeiculo.selecionar(veiculos, id);
	}

	@PostMapping("/cadastro")
	public ResponseEntity<?> cadastrarVeiculo(@RequestBody Veiculo veiculo) {
		HttpStatus status = HttpStatus.CONFLICT;
		if (veiculo.getId() == null) {
			repositorio.save(veiculo);
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<>(status);
	}

	@DeleteMapping("/excluir/")
	public ResponseEntity<?> deletarVeiculo(@RequestBody Veiculo exclusao) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Veiculo veiculo = repositorio.getById(exclusao.getId());
		if (veiculo != null) {
			repositorio.delete(veiculo);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
	}
}
