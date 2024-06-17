package com.autobots.automanager.modelo.usuario;

import java.util.Set;

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.modelo.CredencialAtualizador;
import com.autobots.automanager.modelo.StringVerificadorNulo;
import com.autobots.automanager.modelo.documento.DocumentoAtualizador;
import com.autobots.automanager.modelo.email.EmailAtualizador;
import com.autobots.automanager.modelo.endereco.EnderecoAtualizador;
import com.autobots.automanager.modelo.mercadoria.MercadoriaAtualizador;
import com.autobots.automanager.modelo.telefone.TelefoneAtualizador;
import com.autobots.automanager.modelo.veiculo.VeiculoAtualizador;
import com.autobots.automanager.modelo.venda.VendaAtualizador;

public class UsuarioAtualizador {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();
	private TelefoneAtualizador telefoneAtualizador = new TelefoneAtualizador();
	private EnderecoAtualizador enderecoAtualizador = new EnderecoAtualizador();
	private DocumentoAtualizador documentoAtualizador = new DocumentoAtualizador();
	private EmailAtualizador emailAtualizador = new EmailAtualizador();
	private CredencialAtualizador credencialAtualizador = new CredencialAtualizador();
	private MercadoriaAtualizador mercadoriaAtualizador = new MercadoriaAtualizador();
	private VendaAtualizador vendaAtualizador = new VendaAtualizador();
	private VeiculoAtualizador veiculoAtualizador = new VeiculoAtualizador();

	private void atualizarDados(Usuario usuario, Usuario atualizacao) {
		if (!verificador.verificar(atualizacao.getNome())) {
			usuario.setNome(atualizacao.getNome());
		}
		if (!verificador.verificar(atualizacao.getNomeSocial())) {
			usuario.setNomeSocial(atualizacao.getNomeSocial());
		}
		if (!(atualizacao.getPerfis() == null)) {
			usuario.setPerfis(atualizacao.getPerfis());
		}
		if (!(atualizacao.getTelefones() == null)) {
			usuario.setTelefones(atualizacao.getTelefones());
		}
		if (!(atualizacao.getEndereco() == null)) {
			usuario.setEndereco(atualizacao.getEndereco());
		}
		if (!(atualizacao.getDocumentos() == null)) {
			usuario.setDocumentos(atualizacao.getDocumentos());
		}
		if (!(atualizacao.getEmails() == null)) {
			usuario.setEmails(atualizacao.getEmails());
		}
		if (!(atualizacao.getCredenciais() == null)) {
			usuario.setCredenciais(atualizacao.getCredenciais());
		}
		if (!(atualizacao.getMercadorias() == null)) {
			usuario.setMercadorias(atualizacao.getMercadorias());
		}
		if (!(atualizacao.getVendas() == null)) {
			usuario.setVendas(atualizacao.getVendas());
		}
		if (!(atualizacao.getVeiculos() == null)) {
			usuario.setVeiculos(atualizacao.getVeiculos());
		}
	}

	public void atualizar(Usuario usuario, Usuario atualizacao) {
		atualizarDados(usuario, atualizacao);
		telefoneAtualizador.atualizar(usuario.getTelefones(), atualizacao.getTelefones());
		enderecoAtualizador.atualizar(usuario.getEndereco(), atualizacao.getEndereco());
		documentoAtualizador.atualizar(usuario.getDocumentos(), atualizacao.getDocumentos());
		emailAtualizador.atualizar(usuario.getEmails(), atualizacao.getEmails());
		credencialAtualizador.atualizar(usuario.getCredenciais(), atualizacao.getCredenciais());
		mercadoriaAtualizador.atualizar(usuario.getMercadorias(), atualizacao.getMercadorias());
		vendaAtualizador.atualizar(usuario.getVendas(), atualizacao.getVendas());
		veiculoAtualizador.atualizar(usuario.getVeiculos(), atualizacao.getVeiculos());
	}

	public void atualizar(Set<Usuario> usuarios, Set<Usuario> atualizacoes) {
		for (Usuario atualizacao : atualizacoes) {
			for (Usuario usuario : usuarios) {
				if (atualizacao.getId() != null) {
					if (atualizacao.getId() == usuario.getId()) {
						atualizar(usuario, atualizacao);
					}
				}
			}
		}
	}
}
