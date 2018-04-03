package projetofragmento.cursoandroid.com.acessamogi.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import projetofragmento.cursoandroid.com.acessamogi.R;

public class MensagemConfirmacaoEmail extends AppCompatActivity {

    private Button retornarLogin;
    private TextView mensagemConfirmacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensagem_confirmacao_email);

        String email = getIntent().getStringExtra("email");

        retornarLogin = findViewById(R.id.btRetornaLogin);
        mensagemConfirmacao = findViewById(R.id.textViewMsgVerificacao);
        mensagemConfirmacao.setText("Um email de confirmação foi enviado para o endereço " + email + ". Por favor, clique no link de confirmação para que o cadastro possa ser terminado.");

        retornarLogin.setOnClickListener(new View.OnClickListener() {




            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MensagemConfirmacaoEmail.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
