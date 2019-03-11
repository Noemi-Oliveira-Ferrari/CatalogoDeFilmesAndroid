package br.senai.sp.catlogodefilmes;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.senai.sp.dao.FilmeDAO;
import br.senai.sp.modelo.Filme;

public class MainActivity extends AppCompatActivity {

   private ListView listaFilmes;
   private Button btnNovoFilme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //*** Associa o objeto ListView à View ListView do layout xml
        listaFilmes = findViewById(R.id.list_filmes);

        btnNovoFilme = findViewById(R.id.bt_novo_filme);

        //*** Ação do botão novo
        btnNovoFilme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cadastroFilme = new Intent(MainActivity.this,CadastroFilmeActivity.class);
                startActivity(cadastroFilme);

            }
        });

        // Definição de um menu de contexto para a listview(lista de filmes)

        registerForContextMenu(listaFilmes);

        listaFilmes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Filme filme = (Filme) listaFilmes.getItemAtPosition(position);
                /*Intenção de abrir a cadastro de filmes*/
                Intent cadastro = new Intent(MainActivity.this, CadastroFilmeActivity.class);
                /*leva o filme*/
                cadastro.putExtra("filme", filme);
                startActivity(cadastro);

                Toast.makeText(MainActivity.this, String.valueOf(position), Toast.LENGTH_LONG).show();
            }
        });



    }

    //registerForContextMenu chama esse método
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_context_lista_filmes, menu);


       /* MenuItem deletar = menu.add("Excluir");
        MenuItem editar = menu.add("Editar");
        //detecta qual item do menu foi clicado
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(MainActivity.this, "DELETAR", Toast.LENGTH_LONG).show();
                return false;
            }
        });*/


        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);

    }

            /*detecta o item no menu do contexto*/
    @Override
    public boolean onContextItemSelected(final MenuItem item) {

        /* Caixa de diálogo que confirma exclusão */

        /*
          final FilmeDAO dao = new FilmeDAO(this);

                    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                    final  Filme filme = (Filme) listaFilmes.getItemAtPosition(info.position);
                    filme.setTitulo();
                    AlertDialog.Builder confirma = new
                    confirma.setTitle("Excluir");
                    confirma.setMessage("?" + filme.getTitulo());
                    confirma.setPositiveButton("SIM", new DialogInterface.onClickListener(){
                        @Override
                        public void onClick(Dialog Interface dialog, int which){
                            dao.excuir(filme);
                            Toast.makeText(MainActivity.this, filme.getTitulo() + " excluído!", Toast.LENGTH_LONG).show();
                            dao.close();
                            carregarLista();
                        }

                    }


         */
        /*Outra forma de caixa de diálogo*/
        new AlertDialog.Builder(this).setTitle("Excluir filme").setMessage("Tem certeza que deseja excluir o filme?").setPositiveButton("Sim",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ContextMenu.ContextMenuInfo menuInfo = item.getMenuInfo();
                        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                        Filme filme = (Filme) listaFilmes.getItemAtPosition(info.position);
                        FilmeDAO dao = new FilmeDAO(MainActivity.this);
                        dao.excuir(filme);
                        Toast.makeText(MainActivity.this, filme.getTitulo() + " excluído!", Toast.LENGTH_LONG).show();
                        dao.close();
                        carregarLista();
                    }
                }).setNegativeButton("Não", null).show();

        return super.onContextItemSelected(item);

    }

    @Override
    protected void onResume() {
        carregarLista();
        super.onResume();
    }

    private void carregarLista(){
        //*** MATRIZ DE FILMES QUE SERÃO EXIBIDOS NO ListView
        // abrir banco de dados, rodar uma query de consulta, retornar um arraylist

        FilmeDAO dao = new FilmeDAO(this);
        List<Filme> filmes = dao.getFilmes();
        dao.close();

        //*** Definimos um adapter para carregar os dados da ArrayList na ListView utilizando um layout pronto
        ArrayAdapter<Filme> listaFilmesAdapter = new ArrayAdapter<Filme>(this,android.R.layout.simple_list_item_1,filmes);

        //*** Injetamos o adapter no objeto ListView
        listaFilmes.setAdapter(listaFilmesAdapter);
    }
}
