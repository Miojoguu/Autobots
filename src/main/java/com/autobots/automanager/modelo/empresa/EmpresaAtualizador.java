package com.autobots.automanager.modelo.empresa;

import java.util.Set;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.modelo.StringVerificadorNulo;
import com.autobots.automanager.modelo.endereco.EnderecoAtualizador;
import com.autobots.automanager.modelo.mercadoria.MercadoriaAtualizador;
import com.autobots.automanager.modelo.servico.ServicoAtualizador;
import com.autobots.automanager.modelo.telefone.TelefoneAtualizador;
import com.autobots.automanager.modelo.usuario.UsuarioAtualizador;
import com.autobots.automanager.modelo.venda.VendaAtualizador;

public class EmpresaAtualizador {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();
	private TelefoneAtualizador telefoneAtualizador = new TelefoneAtualizador();
	private EnderecoAtualizador enderecoAtualizador = new EnderecoAtualizador();
	private UsuarioAtualizador usuarioAtualizador = new UsuarioAtualizador();
	private MercadoriaAtualizador mercadoriaAtualizador = new MercadoriaAtualizador();
	private ServicoAtualizador servicoAtualizador = new ServicoAtualizador();
	private VendaAtualizador vendaAtualizador = new VendaAtualizador();

	private void atualizarDados(Empresa empresa, Empresa atualizacao) {
		if (!verificador.verificar(atualizacao.getRazaoSocial())) {
			empresa.setRazaoSocial(atualizacao.getRazaoSocial());
		}
		if (!verificador.verificar(atualizacao.getNomeFantasia())) {
			empresa.setNomeFantasia(atualizacao.getNomeFantasia());
		}
		if (!(atualizacao.getTelefones() == null)) {
			empresa.setTelefones(atualizacao.getTelefones());
		}
		if (!(atualizacao.getEndereco() == null)) {
			empresa.setEndereco(atualizacao.getEndereco());
		}
		if (!(atualizacao.getCadastro() == null)) {
			empresa.setCadastro(atualizacao.getCadastro());
		}
		if (!(atualizacao.getUsuarios() == null)) {
			empresa.setUsuarios(atualizacao.getUsuarios());
		}
		if (!(atualizacao.getMercadorias() == null)) {
			empresa.setMercadorias(atualizacao.getMercadorias());
		}
		if (!(atualizacao.getServicos() == null)) {
			empresa.setServicos(atualizacao.getServicos());
		}
		if (!(atualizacao.getVendas() == null)) {
			empresa.setVendas(atualizacao.getVendas());
		}
	}

	public void atualizar(Empresa empresa, Empresa atualizacao) {
		atualizarDados(empresa, atualizacao);
		telefoneAtualizador.atualizar(empresa.getTelefones(), atualizacao.getTelefones());
		enderecoAtualizador.atualizar(empresa.getEndereco(), atualizacao.getEndereco());
		usuarioAtualizador.atualizar(empresa.getUsuarios(), atualizacao.getUsuarios());
		mercadoriaAtualizador.atualizar(empresa.getMercadorias(), atualizacao.getMercadorias());
		servicoAtualizador.atualizar(empresa.getServicos(), atualizacao.getServicos());
		vendaAtualizador.atualizar(empresa.getVendas(), atualizacao.getVendas());
	}

	public void atualizar(Set<Empresa> empresas, Set<Empresa> atualizacoes) {
		for (Empresa atualizacao : atualizacoes) {
			for (Empresa empresa : empresas) {
				if (atualizacao.getId() != null) {
					if (atualizacao.getId() == empresa.getId()) {
						atualizar(empresa, atualizacao);
					}
				}
			}
		}
	}
}
