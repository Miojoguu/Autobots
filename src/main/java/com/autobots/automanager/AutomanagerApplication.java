package com.autobots.automanager;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.DocumentoRepositorio;
import com.autobots.automanager.repositorios.EnderecoRepositorio;
import com.autobots.automanager.repositorios.TelefoneRepositorio;

@SpringBootApplication
public class AutomanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutomanagerApplication.class, args);
	}

	@Component
	public static class Runner implements ApplicationRunner {

		@Autowired
		public ClienteRepositorio repositorio_cliente;

		@Autowired
		public DocumentoRepositorio repositorio_documento;

		@Autowired
		public EnderecoRepositorio repositorio_endereco;

		@Autowired
		public TelefoneRepositorio repositorio_telefone;

		@Override
		public void run(ApplicationArguments args) throws Exception {
			Calendar calendario = Calendar.getInstance();
			calendario.set(2002, 05, 15);

			Cliente cliente = new Cliente();
			cliente.setNome("Pedro Alcântara de Bragança e Bourbon");
			cliente.setDataCadastro(Calendar.getInstance().getTime());
			cliente.setDataNascimento(calendario.getTime());
			cliente.setNomeSocial("Dom Pedro");

			Telefone telefone = new Telefone();
			telefone.setDdd("21");
			telefone.setNumero("981234576");
			cliente.getTelefones().add(telefone);

			Endereco endereco = new Endereco();
			endereco.setEstado("Rio de Janeiro");
			endereco.setCidade("Rio de Janeiro");
			endereco.setBairro("Copacabana");
			endereco.setRua("Avenida Atlântica");
			endereco.setNumero("1702");
			endereco.setCodigoPostal("22021001");
			endereco.setInformacoesAdicionais("Hotel Copacabana palace");
			cliente.setEndereco(endereco);

			Documento rg = new Documento();
			rg.setTipo("RG");
			rg.setNumero("1500");

			Documento cpf = new Documento();
			cpf.setTipo("RG");
			cpf.setNumero("00000000001");

			cliente.getDocumentos().add(rg);
			cliente.getDocumentos().add(cpf);

			repositorio_cliente.save(cliente);

			// testes de busca

			System.out.println("Busca por id");
			System.out.println(repositorio_cliente.findById(1L).get().getNome());
			System.out.println(repositorio_documento.findById(1L).get().getNumero());
			System.out.println(repositorio_endereco.findById(1L).get().getRua());
			System.out.println(repositorio_telefone.findById(1L).get().getNumero());

			// testes de update

			System.out.println("Update");
			cliente.setNome("Pedro II");
			repositorio_cliente.save(cliente);
			System.out.println(repositorio_cliente.findById(1L).get().getNome());

			// testes de delete

			System.out.println("Delete");
			repositorio_cliente.deleteById(1L);
			try {
				System.out.println(repositorio_cliente.findById(1L).get().getNome());
			} catch (Exception e) {
				System.out.println("Cliente não encontrado");
			}
		}
	}
}
