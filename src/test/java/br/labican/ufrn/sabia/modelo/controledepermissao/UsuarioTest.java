package br.labican.ufrn.sabia.modelo.controledepermissao;

import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import br.labican.ufrn.sabia.dao.controlepermissao.PerfilJpaController;
import br.labican.ufrn.sabia.dao.controlepermissao.UsuarioJpaController;
import br.labican.ufrn.sabia.dao.dadospessoais.PessoaJpaController;
import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.modelo.dadospessoais.Pessoa;
import br.labican.ufrn.sabia.util.Util;

public class UsuarioTest {

    Usuario usuario;
    Usuario usuario1;
    Pessoa pessoa;
    Pessoa pessoa1;


    UsuarioJpaController usuarioController;
    PessoaJpaController pessoaController;
    PerfilJpaController perfilController;


    List<Usuario> usuarios;
    List<Pessoa> pessoas;
    List<Perfil> perfis;
    Random gerador = new Random();

    @Before
    public void iniciar() throws Exception {
        usuarioController = new UsuarioJpaController(Util.EMF);
        pessoaController = new PessoaJpaController(Util.EMF);
        perfilController = new PerfilJpaController(Util.EMF);

        usuario = new Usuario();
        usuario1 = new Usuario();

        pessoas = pessoaController.findPessoaEntities();
        if (!pessoas.isEmpty()) {
            pessoa = pessoas.get(0);
            pessoa1 = pessoas.get(1);
        }

        perfis = perfilController.findPerfilEntities();

        usuario.setLogin(String.valueOf(gerador.nextInt(10000)));
        usuario.setStatus(String.valueOf(gerador.nextInt(10000)));
        usuario.setSenha(String.valueOf(gerador.nextInt(10000)));
        usuario.setCodPessoa(pessoa.getIdPessoa());
        usuario.setPerfils(perfis);

        usuario1.setLogin(String.valueOf(gerador.nextInt(10000)));
        usuario1.setStatus(String.valueOf(gerador.nextInt(10000)));
        usuario1.setSenha(String.valueOf(gerador.nextInt(10000)));
        usuario1.setCodPessoa(pessoa1.getIdPessoa());
        usuario1.setPerfils(perfis);
    }

    @Test
    public void testInserir() {
        usuarioController.create(usuario);
        usuarioController.create(usuario1);
    }

    @Test
    public void testEditar() throws NonexistentEntityException, Exception {
        usuarios = usuarioController.findUsuarioEntities();

        if (!usuarios.isEmpty()) {
            usuario = usuarios.get(0);
        }

        usuario.setStatus(String.valueOf(gerador.nextInt(10000)));
        usuarioController.edit(usuario);
    }

    @Test
    public void pesquisar() {
        usuarios = usuarioController.findUsuarioEntities();
    }

    @Test
    public void excluir() throws NonexistentEntityException, Exception {
        usuarios = usuarioController.findUsuarioEntities();
        if (!usuarios.isEmpty()) {
            usuario = usuarios.get(0);
        }
        usuarioController.destroy(usuario.getIdUsuario());
    }
}
