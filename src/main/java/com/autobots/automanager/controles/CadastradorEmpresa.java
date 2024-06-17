package com.autobots.automanager.controles;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.repositorios.RepositorioServico;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.repositorios.RepositorioVeiculo;
import com.autobots.automanager.repositorios.RepositorioMercadoria;
import com.autobots.automanager.repositorios.RepositorioVenda;

@Service
public class CadastradorEmpresa {
    @Autowired
    RepositorioEmpresa empresaRepositorio;
    @Autowired
    RepositorioServico servicoRepositorio;
    @Autowired
    RepositorioUsuario usuarioRepositorio;
    @Autowired
    RepositorioVeiculo veiculoRepositorio;
    @Autowired
    RepositorioMercadoria mercadoriaRepositorio;
    @Autowired
    RepositorioVenda vendaRepositorio;

    public Empresa cadastro(Empresa empresa) {
        empresa.setCadastro(new Date());
        Empresa empresaCriada = empresaRepositorio.save(empresa);
        return empresaCriada;
    }

    public Empresa cadastroServico(Empresa empresa, Servico servico) {

        Set<Servico> listaServicos = empresa.getServicos();
        listaServicos.add(servico);
        empresa.setServicos(listaServicos);
        servicoRepositorio.save(servico);
        return empresaRepositorio.save(empresa);
    }

    public Empresa cadastroFuncionario(Empresa empresa, Usuario funcionario) {
        Set<Usuario> listaUsuario = empresa.getUsuarios();

        listaUsuario.add(funcionario);
        empresa.setUsuarios(listaUsuario);
        return empresaRepositorio.save(empresa);
    }

    public Venda cadastrarVenda(Empresa empresa, Venda venda) {

        Optional<Usuario> usuarioVenda = usuarioRepositorio.findById(venda.getCliente().getId());
        if (usuarioVenda.isEmpty()) {
            return null;
        }
        Optional<Usuario> funcionarioVenda = usuarioRepositorio.findById(venda.getFuncionario().getId());
        if (funcionarioVenda.isEmpty()) {
            return null;
        }
        Optional<Veiculo> veiculoVenda = veiculoRepositorio.findById(venda.getVeiculo().getId());
        if (veiculoVenda.isEmpty()) {
            return null;
        }
        List<Mercadoria> Mercadorias = new ArrayList<Mercadoria>();
        for (Mercadoria itemMercadoria : venda.getMercadorias()) {
            Mercadoria mercadoria = mercadoriaRepositorio.getById(itemMercadoria.getId());
            Mercadorias.add(mercadoria);
        }

        List<Servico> Servicos = new ArrayList<Servico>();
        for (Servico itemServico : venda.getServicos()) {
            Servico servico = servicoRepositorio.getById(itemServico.getId());
            Servicos.add(servico);
        }

        Venda bodyVenda = new Venda();
        bodyVenda.setFuncionario(funcionarioVenda.get());
        bodyVenda.setVeiculo(veiculoVenda.get());
        bodyVenda.setMercadorias(Mercadorias);
        bodyVenda.setServicos(Servicos);
        bodyVenda.setCadastro(new Date());

        Set<Venda> empresaVendas = empresa.getVendas();

        empresaVendas.add(venda);

        veiculoVenda.get().getVendas().add(bodyVenda);
        Venda vendaCriada = vendaRepositorio.save(bodyVenda);
        empresaRepositorio.save(empresa);
        return vendaCriada;
    }
}