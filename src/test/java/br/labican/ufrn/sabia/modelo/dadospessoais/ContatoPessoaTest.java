package br.labican.ufrn.sabia.modelo.dadospessoais;

import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import br.labican.ufrn.sabia.dao.dadospessoais.ContatoPessoaJpaController;
import br.labican.ufrn.sabia.dao.dadospessoais.PessoaJpaController;
import br.labican.ufrn.sabia.dao.dadospessoais.TipoContatoJpaController;
import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.util.Util;

public class ContatoPessoaTest {

    ContatoPessoa cp;
    ContatoPessoa cp1;
    TipoContato tipoContato;
    Pessoa pessoa;

    TipoContatoJpaController tcController;
    ContatoPessoaJpaController cpController;
    PessoaJpaController pessoaController;

    List<ContatoPessoa> cps;
    List<TipoContato> tipoContatos;
    List<Pessoa> pessoas;

    Random gerador = new Random();

    @Before
    public void iniciar() throws Exception {
        cpController = new ContatoPessoaJpaController(Util.EMF);
        tcController = new TipoContatoJpaController(Util.EMF);
        pessoaController = new PessoaJpaController(Util.EMF);

        tipoContatos = tcController.findTipoContatoEntities();
        if (!tipoContatos.isEmpty())
            tipoContato = tipoContatos.get(0);

        pessoas = pessoaController.findPessoaEntities();
        if (!pessoas.isEmpty()) {
            pessoa = pessoas.get(0);
        }

        cp = new ContatoPessoa();
        cp1 = new ContatoPessoa();

        cp.setValorContatoPessoa(String.valueOf(gerador.nextInt(10000)));
        cp.setTipoContato(tipoContato);
        cp.setPessoa(pessoa);

        cp1.setValorContatoPessoa(String.valueOf(gerador.nextInt(10000)));
        cp1.setTipoContato(tipoContato);
        cp1.setPessoa(pessoa);
    }

    @Test
    public void testInserir() {
        cpController.create(cp);
        cpController.create(cp1);
    }

    @Test
    public void testEditar() throws NonexistentEntityException, Exception {
        cps = cpController.findContatoPessoaEntities();

        if (!cps.isEmpty()) {
            cp = cps.get(0);
        }

        cp.setValorContatoPessoa(String.valueOf(gerador.nextInt(10000)));
        cpController.edit(cp);
    }

    @Test
    public void pesquisar() {
        cps = cpController.findContatoPessoaEntities();
    }

    @Test
    public void excluir() throws NonexistentEntityException, Exception {
        cps = cpController.findContatoPessoaEntities();

        if (!cps.isEmpty()) {
            cp = cps.get(0);
            cpController.destroy(cp.getIdContatoPessoa());
        }
    }


}
