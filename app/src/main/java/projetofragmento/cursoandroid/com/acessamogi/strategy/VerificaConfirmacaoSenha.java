package projetofragmento.cursoandroid.com.acessamogi.strategy;

import projetofragmento.cursoandroid.com.acessamogi.modelo.EntidadeDominio;
import projetofragmento.cursoandroid.com.acessamogi.modelo.Usuario;

public class VerificaConfirmacaoSenha implements IStrategy {
    public String processar(EntidadeDominio entidade) {
        if (entidade instanceof Usuario) {
            Usuario usuario = (Usuario) entidade;

            if (!usuario.getSenha().equals(usuario.getConfirmacaoSenha()) )
                return "As senha devem ser iguais";
        }
        return null;
    }
}

