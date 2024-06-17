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

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelo.documento.AdicionadorLinkDocumento;
import com.autobots.automanager.modelo.documento.DocumentoSelecionador;
import com.autobots.automanager.repositorios.RepositorioDocumento;

@RestController
@RequestMapping("/documento")
public class DocumentoControle {
	@Autowired
	private RepositorioDocumento repositorio;
	@Autowired
	private AdicionadorLinkDocumento adicionadorLinkDocumento;
	@Autowired
	private DocumentoSelecionador selecionarDocumento;

	@GetMapping("/documentos")
	public ResponseEntity<List<Documento>> obterDocumentos() {
		HttpStatus status = HttpStatus.NOT_FOUND;
		List<Documento> documentos = repositorio.findAll();
		if (documentos == null) {
			return new ResponseEntity<List<Documento>>(status);
		} else {
			adicionadorLinkDocumento.adicionarLink(documentos);
			status = HttpStatus.FOUND;
			return new ResponseEntity<List<Documento>>(documentos, status);
		}
	}

	@GetMapping("/{id}")
	public Documento obterDocumento(@PathVariable long id) {
		List<Documento> documentos = repositorio.findAll();
		return selecionarDocumento.selecionar(documentos, id);
	}

	@PostMapping("/cadastro")
	public ResponseEntity<?> cadastrarDocumento(@RequestBody Documento documento) {
		HttpStatus status = HttpStatus.CONFLICT;
		if (documento.getId() == null) {
			repositorio.save(documento);
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<>(status);
	}

	@DeleteMapping("/excluir/")
	public ResponseEntity<?> deletarDocumento(@RequestBody Documento exclusao) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Documento documento = repositorio.getById(exclusao.getId());
		if (documento != null) {
			repositorio.delete(documento);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
	}
}
