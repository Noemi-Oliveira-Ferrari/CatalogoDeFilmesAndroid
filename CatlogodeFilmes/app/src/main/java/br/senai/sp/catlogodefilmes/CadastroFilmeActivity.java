package br.senai.sp.catlogodefilmes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import br.senai.sp.dao.FilmeDAO;
import br.senai.sp.modelo.Filme;

public class CadastroFilmeActivity extends AppCompatActivity {

    private CadastroFilmeHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_filme);

         helper = new CadastroFilmeHelper(CadastroFilmeActivity.this);



         /*obter intenção*/
         Intent intent = getIntent();

         Filme filme = (Filme) intent.getSerializableExtra("filme");
            if(filme != null) {
                helper.preencherFormulario(filme);
            }

    }

    /*Colocar menu na activity(menu_cadastro_filmes.xml)*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    /*Inflador de menu, enche o menu*/
        MenuInflater menuInflater = getMenuInflater();
        /*O método inflate precisa de 2 parâmetros (o layout e o objeto menu)*/
        menuInflater.inflate(R.menu.menu_cadastro_filmes, menu);
        /*Retorna o menu pra quem chamou*/
        return super.onCreateOptionsMenu(menu);
    }

    /*Retorna o item selecionado no menu*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      /*getItemId() traz o id do item selecionado*/
       switch (item.getItemId()){
           case R.id.menu_salvar:

               Filme filme = helper.getFilme();
            //*** abrir o banco, query de insert, fechar o banco

               FilmeDAO dao = new FilmeDAO(this);
               if(filme.getId() == 0){
                   dao.salvar(filme);

               }else{
                   dao.atualizar(filme);
               }

               dao.close();
               Toast.makeText(this, filme.getTitulo() + " gravado com sucesso!", Toast.LENGTH_LONG).show();
               finish();


               break;
           case R.id.menu_del:

               Toast.makeText(CadastroFilmeActivity.this, "Excluir", Toast.LENGTH_LONG).show();
               break;
           case R.id.menu_configuracoes:
               Toast.makeText(CadastroFilmeActivity.this, "Configurações", Toast.LENGTH_LONG).show();
               break;
               default:
                   Toast.makeText(CadastroFilmeActivity.this, "Nada", Toast.LENGTH_LONG).show();
                   break;

       }


        return super.onOptionsItemSelected(item);
    }
}
