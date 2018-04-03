package projetofragmento.cursoandroid.com.acessamogi.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import org.w3c.dom.Text;

import projetofragmento.cursoandroid.com.acessamogi.R;
import projetofragmento.cursoandroid.com.acessamogi.config.ConfiguracaoFirebase;

public class TrocarSenha extends AppCompatActivity {

    Button botaoRedifinirSenha;
    FirebaseAuth autenticacao;
    TextView txtEmail;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trocar_senha);

        botaoRedifinirSenha = findViewById(R.id.btRedefinirSenha);
        autenticacao = ConfiguracaoFirebase.getAutenticacao();
        txtEmail = findViewById(R.id.txtRedefinirEmail);



        botaoRedifinirSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trocarSenha();
            }
        });
    }


    private void trocarSenha() {

        email = txtEmail.getText().toString();

        autenticacao.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(TrocarSenha.this, "Reset email instructions sent to " + email, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(TrocarSenha.this, email + " does not exist", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void abrirTelaMensagemResetSenha(){
        
    }
}
