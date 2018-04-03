package projetofragmento.cursoandroid.com.acessamogi.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import projetofragmento.cursoandroid.com.acessamogi.R;
import projetofragmento.cursoandroid.com.acessamogi.config.ConfiguracaoFirebase;
import projetofragmento.cursoandroid.com.acessamogi.dao.usuarioDao;
import projetofragmento.cursoandroid.com.acessamogi.modelo.Usuario;
import projetofragmento.cursoandroid.com.acessamogi.strategy.IStrategy;
import projetofragmento.cursoandroid.com.acessamogi.strategy.VerificaConfirmacaoSenha;
import projetofragmento.cursoandroid.com.acessamogi.strategy.VerificaDeficienciaVazia;
import projetofragmento.cursoandroid.com.acessamogi.strategy.VerificaEmailUsuarioVazio;
import projetofragmento.cursoandroid.com.acessamogi.strategy.VerificaNomeUsuarioVazio;
import projetofragmento.cursoandroid.com.acessamogi.strategy.VerificaSenhaFraca;

public class CadastroUsuarioActivity extends AppCompatActivity {

    //Spinner element
    private Spinner spinner;
    Button botaoCadastrar;
    private EditText nome;
    private EditText email;
    private EditText senha;
    private EditText confirmaSenha;
    private String deficienciaEscolha;
    private Usuario usuario;
    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        /*//Spinner element
        spinner = findViewById(R.id.spinDefiniciencia);

        // Spinner DropDown elements
        List<String> tipoDeAcesso = new ArrayList<>();
        tipoDeAcesso.add("Selecione uma Deficiência");
        tipoDeAcesso.add("Nenhuma");
        tipoDeAcesso.add("Cadeirante");
        tipoDeAcesso.add("Deficiente Visual");

        //Criando adaptador para Spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, tipoDeAcesso);

        //DropDown layout style
        dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        //Anexando dataAdapter ao Spinner
        spinner.setAdapter(dataAdapter);*/

     //   nome = findViewById(R.id.editTextCadNome);
        email = findViewById(R.id.editTextCadEmail);
        senha = findViewById(R.id.editTextCadSenha);
        confirmaSenha = findViewById(R.id.editTextCadRepSenha);


        botaoCadastrar = findViewById(R.id.btCadastrarUsuario);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = new Usuario();
                //usuario.setNome(nome.getText().toString());
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());
                usuario.setConfirmacaoSenha(confirmaSenha.getText().toString());
              //  usuario.setDeficiencia(spinner.getSelectedItem().toString());

                //Caso algum dado de entrada esteja incorreta ele interrompe a execução do programa
                if (!validaDadosEntrada()) {
                    return;
                }
                //Cadastra o usuário na base de dados de usuario e no banco de dados do Firebase
                cadastrarUsuario();
            }
        });


    }

    private void cadastrarUsuario() {
        autenticacao = ConfiguracaoFirebase.getAutenticacao();

        //Cria usuario e senha no Firebase Auth utilizando Email e senha como parãmetros de autenticação
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(CadastroUsuarioActivity.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) { //Caso a autenticação tenha ocorrido com sucesso
                            Toast.makeText(CadastroUsuarioActivity.this,
                                    "Sucesso ao cadastrar usuário",
                                    Toast.LENGTH_LONG).show();
                            FirebaseUser usuarioFirebase = task.getResult().getUser();

                            //Envia o email de Verificacao;
                            usuarioFirebase.sendEmailVerification();

                           /* usuario.setId(usuarioFirebase.getUid());
                            usuarioDao usrDao = new usuarioDao();
                            //Salva o usuario no Database do Firebase
                            usrDao.salvar(usuario);*/
                            autenticacao.signOut();//Ao rodar o comando para deslogar o salvar da linha acima não é autorizado, ao comentar a linha, eu consigo gravar o dado acima sem problemas
                            telaMensagemConfirmacaoEmail();

                        } else {//Se houver algum problema na autenticação

                            String erroExcecao = "";

                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {//Erro de autenticação Fraca
                                erroExcecao = "Digite uma senha mais forte, contendo mais caracteres e com letras e números";
                            } catch (FirebaseAuthInvalidCredentialsException e) {//Credencias incorretas
                                erroExcecao = "O email digitado é inválido, digite um novo e-mail!";
                            } catch (FirebaseAuthUserCollisionException e) {//Caso o email já esteja na base de autenticação do Firebase
                                erroExcecao = "Esse email já esta em uso no app";
                            } catch (Exception e) {//Erro generico
                                erroExcecao = "Esse ao efetuar o cadastro" + e.getMessage(); //Retorna a mensagem de erro
                                e.printStackTrace();
                            }

                            Toast.makeText(CadastroUsuarioActivity.this, "Erro ao cadastrar usuário" + erroExcecao, Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }

    /**
     * Valida dados de entrada do usuário
     */

    private boolean validaDadosEntrada() {


        ArrayList<IStrategy> regras = new ArrayList<IStrategy>();
        //regras.add(new VerificaNomeUsuarioVazio());
        regras.add(new VerificaEmailUsuarioVazio());
        regras.add(new VerificaSenhaFraca());
        regras.add(new VerificaConfirmacaoSenha());
        //regras.add(new VerificaDeficienciaVazia());

        Boolean correto = true;
        StringBuilder mensagemErro = new StringBuilder();

        for (IStrategy s : regras) {
            String mensagem = s.processar(usuario);

            if (mensagem != null) {
                correto = false;
                mensagemErro.append(mensagem);
                mensagemErro.append("\n");

            }
        }

        if (!correto) {
            Toast.makeText(CadastroUsuarioActivity.this, mensagemErro, Toast.LENGTH_LONG).show();
        }

        return correto;
    }

    private void telaMensagemConfirmacaoEmail(){
        Intent intent = new Intent(CadastroUsuarioActivity.this,MensagemConfirmacaoEmail.class);
        intent.putExtra("email",usuario.getEmail());
        startActivity(intent);
        finish();
    }

}
