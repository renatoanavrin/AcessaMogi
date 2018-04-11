package projetofragmento.cursoandroid.com.acessamogi.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import org.w3c.dom.Text;

import projetofragmento.cursoandroid.com.acessamogi.R;
import projetofragmento.cursoandroid.com.acessamogi.config.ConfiguracaoFirebase;
import projetofragmento.cursoandroid.com.acessamogi.modelo.Usuario;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference referenciaDataBase;
    private EditText email;
    private EditText senha;
    private Button botaoLogar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;
    private TextView adicionarUsuario;
    private TextView redefinirSenha;
    private TextView reenviarEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        autenticacao = ConfiguracaoFirebase.getAutenticacao();


        email = findViewById(R.id.editTextEmail);
        senha = findViewById(R.id.editTextSenha);
        botaoLogar = findViewById(R.id.btLogar);
        adicionarUsuario = findViewById(R.id.textViewAddConta);
        redefinirSenha = findViewById(R.id.txtEsqueciSenha);
        reenviarEmail = (TextView) findViewById(R.id.txtReenviarEmail);

        verificarUsuarioLogado();

        botaoLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = new Usuario();
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());
                validarLogin();
            }
        });

        adicionarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTelaCadastroUsuario();
            }
        });

        redefinirSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTelaRedefinirSenha();
            }
        });

        reenviarEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reenviarEmail.setVisibility(TextView.INVISIBLE);
                abrirTelaReenviarEmail();
            }
        });
    }

    private void verificarUsuarioLogado() {


        if (autenticacao.getCurrentUser() != null) {
            if (verificaSeEmailEstaVerificado()) {
                abrirTelaMapa();
                //abrirTelaPrincipal();
            }

        }
    }

    private void abrirTelaPrincipal() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void abrirTelaCadastroUsuario() {
        Intent intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
        startActivity(intent);

    }

    private void abrirTelaRedefinirSenha() {
        Intent intent = new Intent(LoginActivity.this, TrocarSenha.class);
        startActivity(intent);
    }

    private void abrirTelaReenviarEmail() {
        Intent intent = new Intent(LoginActivity.this, ReenviarEmailConfirmacao.class);
        startActivity(intent);
    }

    private void abrirTelaMapa() {
        Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
        startActivity(intent);
    }

    private void validarLogin() {

        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    if (verificaSeEmailEstaVerificado()) {
                        //abrirTelaPrincipal();
                        abrirTelaMapa();
                        Toast.makeText(LoginActivity.this, "Sucesso ao fazer login!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    String erro;
                    try {
                        throw task.getException();
                    } catch (Exception e) {
                        erro = e.getMessage();
                    }

                    Toast.makeText(LoginActivity.this, "Erro ao fazer login!" + erro, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private Boolean verificaSeEmailEstaVerificado() {

        FirebaseUser user = autenticacao.getCurrentUser();

        if (user.isEmailVerified()) {
            // user is verified, so you can finish this activity or send user to activity which you want.
            /*finish();
            Toast.makeText(LoginActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();*/

            return true;
        } else {
            // email is not verified, so just prompt the message to the user and restart this activity.
            // NOTE: don't forget to log out the user.
            //FirebaseAuth.getInstance().signOut();
            //restart this activity
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            reenviarEmail.setVisibility(TextView.VISIBLE);
            Toast.makeText(LoginActivity.this, "É necessário confirmar seu email, por favor, verifique sua caixa de entrada e clique no link de confirmação que enviamos para você!", Toast.LENGTH_LONG).show();

            return false;

        }
    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}
