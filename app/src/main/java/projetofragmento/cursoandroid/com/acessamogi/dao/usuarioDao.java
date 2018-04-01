package projetofragmento.cursoandroid.com.acessamogi.dao;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import projetofragmento.cursoandroid.com.acessamogi.config.ConfiguracaoFirebase;
import projetofragmento.cursoandroid.com.acessamogi.modelo.EntidadeDominio;
import projetofragmento.cursoandroid.com.acessamogi.modelo.Usuario;

/**
 * Created by Renato Garcia on 24/03/2018.
 */

public class usuarioDao implements IDAO {

    DatabaseReference referenciaDatabase;
    Usuario usuario;

    public usuarioDao() {
        referenciaDatabase = ConfiguracaoFirebase.getDataBase();

    }

    @Override
    public void salvar(EntidadeDominio entidadeDominio) throws DatabaseException {

        usuario = (Usuario) entidadeDominio;

        referenciaDatabase.child("usuarios").child(usuario.getId()).setValue(usuario);

        /*autenticacao.signOut();*/

    }

    @Override
    public void alterar(EntidadeDominio entidadeDominio) throws DatabaseException {

    }

    @Override
    public void excluir(EntidadeDominio entidadeDominio) throws DatabaseException {

    }

    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidadeDominio) throws DatabaseException {
        return null;
    }
}
