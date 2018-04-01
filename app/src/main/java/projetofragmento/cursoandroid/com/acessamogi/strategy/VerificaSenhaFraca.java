package projetofragmento.cursoandroid.com.acessamogi.strategy;

import projetofragmento.cursoandroid.com.acessamogi.modelo.EntidadeDominio;
import projetofragmento.cursoandroid.com.acessamogi.modelo.Usuario;

public class VerificaSenhaFraca implements IStrategy {
    public String processar(EntidadeDominio entidade) {
        if (entidade instanceof Usuario) {
            Usuario usuario = (Usuario) entidade;

            if (usuario.getSenha().trim().length() < 6)
                return "O campo senha deve ter pelo menos 6 caracteres";
        }
        return null;
    }
}

