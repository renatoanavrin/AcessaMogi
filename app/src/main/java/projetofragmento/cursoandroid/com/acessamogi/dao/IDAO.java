package projetofragmento.cursoandroid.com.acessamogi.dao;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import projetofragmento.cursoandroid.com.acessamogi.modelo.EntidadeDominio;

/**
 * Created by Renato Garcia on 24/03/2018.
 */

public interface IDAO {

    public void salvar(EntidadeDominio entidadeDominio) throws DatabaseException;

    public void alterar(EntidadeDominio entidadeDominio) throws DatabaseException;

    public void excluir(EntidadeDominio entidadeDominio) throws DatabaseException;

    public List<EntidadeDominio> consultar(EntidadeDominio entidadeDominio) throws DatabaseException;
}
