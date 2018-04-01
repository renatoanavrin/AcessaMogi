package projetofragmento.cursoandroid.com.acessamogi.strategy;

import projetofragmento.cursoandroid.com.acessamogi.modelo.EntidadeDominio;
import projetofragmento.cursoandroid.com.acessamogi.modelo.Usuario;

public class VerificaDeficienciaVazia implements IStrategy {
    public String processar(EntidadeDominio entidade) {
        if (entidade instanceof Usuario) {
            Usuario usuario = (Usuario) entidade;

            if (usuario.getDeficiencia().equals("Selecione uma Deficiência"))
                return "Selecione uma deficiência";
        }
        return null;
    }
}

