package projetofragmento.cursoandroid.com.acessamogi.modelo;

import java.util.Date;

/**
 * Created by Renato Garcia on 24/03/2018.
 */

public abstract class EntidadeDominio {

    private String id;
    private Date dtCadastro;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDtCadastro() {
        return dtCadastro;
    }

    public void setDtCadastro(Date dtCadastro) {
        this.dtCadastro = dtCadastro;
    }
}
