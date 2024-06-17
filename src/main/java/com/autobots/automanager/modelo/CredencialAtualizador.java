package com.autobots.automanager.modelo;

import java.util.List;
import java.util.Set;

import com.autobots.automanager.entidades.Credencial;

public class CredencialAtualizador {

	public void atualizar(Credencial credencial, Credencial atualizacao) {
		if (atualizacao != null) {
			if (!(atualizacao.getCriacao() == null)) {
				credencial.setCriacao(atualizacao.getCriacao());
			}
			if (!(atualizacao.getUltimoAcesso() == null)) {
				credencial.setUltimoAcesso(atualizacao.getUltimoAcesso());
			}
		}
	}

	public void atualizar(List<Credencial> credenciais, List<Credencial> atualizacoes) {
		for (Credencial atualizacao : atualizacoes) {
			for (Credencial credencial : credenciais) {
				if (atualizacao.getId() != null) {
					if (atualizacao.getId() == credencial.getId()) {
						atualizar(credencial, atualizacao);
					}
				}
			}
		}
	}

	public void atualizar(Set<Credencial> credenciais, Set<Credencial> atualizacoes) {
		for (Credencial atualizacao : atualizacoes) {
			for (Credencial credencial : credenciais) {
				if (atualizacao.getId() != null) {
					if (atualizacao.getId() == credencial.getId()) {
						atualizar(credencial, atualizacao);
					}
				}
			}
		}
	}
}