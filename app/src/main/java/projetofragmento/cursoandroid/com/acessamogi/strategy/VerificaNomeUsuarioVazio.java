package projetofragmento.cursoandroid.com.acessamogi.strategy;

import projetofragmento.cursoandroid.com.acessamogi.modelo.EntidadeDominio;
import projetofragmento.cursoandroid.com.acessamogi.modelo.Usuario;

public class VerificaNomeUsuarioVazio implements IStrategy {

    @Override
    public String processar(EntidadeDominio entidade) {
        if(entidade instanceof Usuario){
            Usuario usuario = (Usuario) entidade;

            if(usuario.getNome().trim().length() == 0)
                return "O campo usuario deve ser Preenchido";
        }
        return null;
    }
}
