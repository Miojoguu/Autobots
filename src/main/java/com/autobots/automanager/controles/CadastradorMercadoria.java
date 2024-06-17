package com.autobots.automanager.controles;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.repositorios.RepositorioMercadoria;
import com.autobots.automanager.repositorios.RepositorioUsuario;

public class CadastradorMercadoria {

    @Autowired
    private RepositorioMercadoria mercadoriaRepositorio;
    @Autowired
    private RepositorioUsuario usuarioRepositorio;

    public Usuario cadastroMercadoria(Usuario usuario, Mercadoria mercadoria) {
        Set<Mercadoria> listaMercadorias = usuario.getMercadorias();
        listaMercadorias.add(mercadoria);
        usuario.setMercadorias(listaMercadorias);
        mercadoriaRepositorio.save(mercadoria);
        return usuarioRepositorio.save(usuario);
    }

}