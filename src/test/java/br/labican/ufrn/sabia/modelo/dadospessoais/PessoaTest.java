package br.labican.ufrn.sabia.modelo.dadospessoais;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import br.labican.ufrn.sabia.dao.EnderecoJpaController;
import br.labican.ufrn.sabia.dao.cadastroibge.CidadeJpaController;
import br.labican.ufrn.sabia.dao.cadastroibge.PaisJpaController;
import br.labican.ufrn.sabia.dao.dadospessoais.PessoaJpaController;
import br.labican.ufrn.sabia.dao.dadospessoais.RgJpaController;
import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.modelo.Endereco;
import br.labican.ufrn.sabia.modelo.cadastroibge.Cidade;
import br.labican.ufrn.sabia.modelo.cadastroibge.Pais;
import br.labican.ufrn.sabia.util.Util;

public class PessoaTest {

    Pessoa pessoa;
    Pessoa pessoa1;
    Endereco endereco;
    Pais pais;
    Cidade cidade;
    Rg rg;
    Rg rg1;

    PessoaJpaController pessoaController;
    RgJpaController rgController;
    EnderecoJpaController enderecoController;
    PaisJpaController paisController;
    CidadeJpaController cidadeController;

    List<Cidade> cidades;
    List<Rg> rgs;
    List<Pessoa> pessoas;
    List<Endereco> enderecos;
    List<Pais> paises;
    Random gerador = new Random();

    @Before
    public void iniciar() throws Exception {
        pessoaController = new PessoaJpaController(Util.EMF);
        enderecoController = new EnderecoJpaController(Util.EMF);
        paisController = new PaisJpaController(Util.EMF);
        cidadeController = new CidadeJpaController(Util.EMF);
        rgController = new RgJpaController(Util.EMF);

        enderecos = enderecoController.findEnderecoEntities();
        if (!enderecos.isEmpty()) {
            endereco = enderecos.get(0);
        }

        paises = paisController.findPaisEntities();
        if (!paises.isEmpty()) {
            pais = paises.get(0);
        }

        cidades = cidadeController.findCidadeEntities();
        if (!cidades.isEmpty()) {
            cidade = cidades.get(0);
        }

        rgs = rgController.findRgEntities();
        if (!rgs.isEmpty()) {
            rg = rgs.get(0);
            rg1 = rgs.get(1);
        }

        pessoa = new Pessoa();
        pessoa.setCodEndereco(endereco.getIdEndereco());
        pessoa.setCpf(String.valueOf(gerador.nextInt(10000)));
        pessoa.setCurriculoLattes(String.valueOf(gerador.nextInt(10000)));
        pessoa.setDataNascimento(new Date());
        pessoa.setNacionalidade(pais.getIdPais());
        pessoa.setNaturalidade(cidade.getIdCidade());
        pessoa.setNomeMeioPessoa(String.valueOf(gerador.nextInt(10000)));
        pessoa.setNomePrimeiroPessoa(String.valueOf(gerador.nextInt(10000)));
        pessoa.setNomeUltimoPessoa(String.valueOf(gerador.nextInt(10000)));
        pessoa.setPassaporte(String.valueOf(gerador.nextInt(10000)));
        pessoa.setRg(rg);
        pessoa.setSexo(String.valueOf(gerador.nextInt(10000)));

        pessoa1 = new Pessoa();
        pessoa1.setCodEndereco(endereco.getIdEndereco());
        pessoa1.setCpf(String.valueOf(gerador.nextInt(10000)));
        pessoa1.setCurriculoLattes(String.valueOf(gerador.nextInt(10000)));
        pessoa1.setDataNascimento(new Date());
        pessoa1.setNacionalidade(pais.getIdPais());
        pessoa1.setNaturalidade(cidade.getIdCidade());
        pessoa1.setNomeMeioPessoa(String.valueOf(gerador.nextInt(10000)));
        pessoa1.setNomePrimeiroPessoa(String.valueOf(gerador.nextInt(10000)));
        pessoa1.setNomeUltimoPessoa(String.valueOf(gerador.nextInt(10000)));
        pessoa1.setPassaporte(String.valueOf(gerador.nextInt(10000)));
        pessoa1.setRg(rg1);
        pessoa1.setSexo(String.valueOf(gerador.nextInt(10000)));
    }

    @Test
    public void testInserir() {
        pessoaController.create(pessoa);
        pessoaController.create(pessoa1);
    }

    @Test
    public void testEditar() throws NonexistentEntityException, Exception {
        pessoas = pessoaController.findPessoaEntities();

        if (!pessoas.isEmpty()) {
            pessoa = pessoas.get(0);
        }

        pessoa.setCurriculoLattes(String.valueOf(gerador.nextInt(10000)));
        pessoaController.edit(pessoa);
    }

    @Test
    public void pesquisar() {
        pessoas = pessoaController.findPessoaEntities();
    }

    @Test
    public void excluir() throws NonexistentEntityException, Exception {
        pessoas = pessoaController.findPessoaEntities();

        if (!pessoas.isEmpty()) {
            pessoa = pessoas.get(0);
            pessoaController.destroy(pessoa.getIdPessoa());
        }

    }

}
