package com.autobots.automanager.modelo.veiculo;

import java.util.Set;

import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.modelo.StringVerificadorNulo;
import com.autobots.automanager.modelo.usuario.UsuarioAtualizador;
import com.autobots.automanager.modelo.venda.VendaAtualizador;

public class VeiculoAtualizador {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();
	private VendaAtualizador vendaAtualizador = new VendaAtualizador();
	private UsuarioAtualizador usuarioAtualizador = new UsuarioAtualizador();

	public void atualizarDados(Veiculo veiculo, Veiculo atualizacao) {
		if (!(atualizacao.getTipo() == null)) {
			veiculo.setTipo(atualizacao.getTipo());
		}
		if (!verificador.verificar(atualizacao.getModelo())) {
			veiculo.setModelo(atualizacao.getModelo());
		}
		if (!verificador.verificar(atualizacao.getPlaca())) {
			veiculo.setPlaca(atualizacao.getPlaca());
		}
		if (!(atualizacao.getProprietario() == null)) {
			veiculo.setProprietario(atualizacao.getProprietario());
		}
		if (!(atualizacao.getVendas() == null)) {
			veiculo.setVendas(atualizacao.getVendas());
		}
	}

	public void atualizar(Veiculo veiculo, Veiculo atualizacao) {
		atualizarDados(veiculo, atualizacao);
		vendaAtualizador.atualizar(veiculo.getVendas(), atualizacao.getVendas());
		usuarioAtualizador.atualizar(veiculo.getProprietario(), atualizacao.getProprietario());
	}

	public void atualizar(Set<Veiculo> veiculos, Set<Veiculo> atualizacoes) {
		for (Veiculo atualizacao : atualizacoes) {
			for (Veiculo veiculo : veiculos) {
				if (atualizacao.getId() != null) {
					if (atualizacao.getId() == veiculo.getId()) {
						atualizar(veiculo, atualizacao);
					}
				}
			}
		}
	}
}