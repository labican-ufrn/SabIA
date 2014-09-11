
package br.labican.ufrn.sabia.testes;

import br.labican.ufrn.sabia.dao.AutorizacaoJpaController;
import br.labican.ufrn.sabia.dao.UsuarioJpaController;
import br.labican.ufrn.sabia.modelo.Autorizacao;
import br.labican.ufrn.sabia.modelo.Usuario;
import br.labican.ufrn.sabia.util.Util;
import java.util.List;

/**
 *
 * @author Rummenigge Maia
 */
public class Teste {

    public static void main(String[] asd) {
        AutorizacaoJpaController ajc = new AutorizacaoJpaController(Util.EMF);
        List<Autorizacao> as = ajc.findAutorizacaoEntities();

        Autorizacao a1 = new Autorizacao(null, "ROLE_ADMIN");
        Autorizacao a2 = new Autorizacao(null, "ROLE_AVALI");
        Autorizacao a3 = new Autorizacao(null, "ROLE_OPERA");
        ajc.create(a1);
        ajc.create(a2);
        ajc.create(a3);

        Usuario u = new Usuario();
        u.setUsername("hyago");
        u.setPassword("asd123");
        u.setAtivo(true);
        u.setAutorizacoes(as);

        UsuarioJpaController ujc = new UsuarioJpaController(Util.EMF);
        ujc.create(u);
    }
}
