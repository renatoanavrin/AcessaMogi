package projetofragmento.cursoandroid.com.acessamogi.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Renato Garcia on 20/03/2018.
 */

public class ConfiguracaoFirebase {
    private static DatabaseReference referenciaDataBase;
    private  static FirebaseAuth autenticacao;

    public static DatabaseReference getDataBase(){
        if(referenciaDataBase == null){
            referenciaDataBase = FirebaseDatabase.getInstance().getReference();
        }

        return referenciaDataBase;
    }

    public static FirebaseAuth getAutenticacao(){

        if(autenticacao == null){
            autenticacao = FirebaseAuth.getInstance();
        }

        return autenticacao;
    }
}


