

package br.labican.ufrn.sabia.negocio;

import br.labican.ufrn.sabia.modelo.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/**
 *
 * @author Rummenigge Maia
 */
public class LoginMB {
    
    private Usuario usuario;

    public LoginMB() {
        usuario = new Usuario();
        SecurityContext sc = SecurityContextHolder.getContext();
        Authentication aut = sc.getAuthentication();
        User user = (User) aut.getPrincipal();
        usuario.setUsername(user.getUsername());
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
