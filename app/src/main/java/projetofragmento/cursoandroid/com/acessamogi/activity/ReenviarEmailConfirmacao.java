package projetofragmento.cursoandroid.com.acessamogi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import projetofragmento.cursoandroid.com.acessamogi.R;
import projetofragmento.cursoandroid.com.acessamogi.config.ConfiguracaoFirebase;

public class ReenviarEmailConfirmacao extends AppCompatActivity {


    Button botaoReenviarEmail;
    FirebaseAuth autenticacao;
    FirebaseUser usuarioFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reenviar_email_confirmacao);

        autenticacao = ConfiguracaoFirebase.getAutenticacao();
        usuarioFirebase = autenticacao.getCurrentUser();
        botaoReenviarEmail = findViewById(R.id.btReenviarEmailConfirmacao);

        botaoReenviarEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuarioFirebase.sendEmailVerification();
                finish();
            }
        });
    }
}
