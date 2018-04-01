package projetofragmento.cursoandroid.com.acessamogi.strategy;

import projetofragmento.cursoandroid.com.acessamogi.modelo.EntidadeDominio;
import projetofragmento.cursoandroid.com.acessamogi.modelo.Usuario;

public class VerificaEmailUsuarioVazio implements IStrategy {
    @Override
    public String processar(EntidadeDominio entidade) {
        if (entidade instanceof Usuario) {
            Usuario usuario = (Usuario) entidade;

            if (usuario.getEmail().trim().length() == 0)
                return "O campo email deve ser preenchido";
        }
        return null;
    }
}
