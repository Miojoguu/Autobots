package com.autobots.automanager.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.repositorios.RepositorioVeiculo;

@Service
public class CadastradorVeiculo {
    @Autowired
    RepositorioVeiculo veiculoRepositorio;

    public Veiculo cadastrar(Veiculo veiculo) {
        Veiculo veiculoCriado = veiculoRepositorio.save(veiculo);
        return veiculoCriado;
    }
}