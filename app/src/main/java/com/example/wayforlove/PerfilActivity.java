package com.example.wayforlove;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.wayforlove.dao.UsuarioDao;
import com.example.wayforlove.modelo.Usuario;
import com.example.wayforlove.util.DialogSalvarUsuarioUtil;

public class PerfilActivity extends AppCompatActivity {

    public static final String TAG = "PerfilLog";
    private Toolbar toolbar;
    private EditText campo_nome;
    private Spinner campo_sexo, campo_tipo_fisico, campo_cor;
    private Usuario usuario;
    private UsuarioDao usuarioDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        IniciarCampos();

    }

    private void PreencheCampoSpinner(Spinner spinner, int arrays) {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void IniciarCampos() {

        campo_nome = findViewById(R.id.campo_nome);
        campo_sexo = findViewById(R.id.campo_sexo);
        campo_tipo_fisico = findViewById(R.id.campo_tipo_fisico);
        campo_cor = findViewById(R.id.campo_cor);

        PreencheCampoSpinner(campo_sexo, R.array.sexo);
        PreencheCampoSpinner(campo_cor, R.array.cor);
        PreencheCampoSpinner(campo_tipo_fisico, R.array.tipo_fisico);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_salavar:
                save(usuario);
                break;
        }

        return true;
    }

    private void save(Usuario usuario) {

        usuario = new Usuario();
        usuario.setNome(campo_nome.getText().toString());

        if (validaCampo()) {

            usuario.setSexo(campo_sexo.getSelectedItem().toString());
            usuario.setCor(campo_cor.getSelectedItem().toString());
            usuario.setTipoFisico(campo_tipo_fisico.getSelectedItem().toString());

            usuarioDao = new UsuarioDao();

            //add usuario na lista
            if (usuarioDao.addUsuario(usuario)) {
                Intent intent = new Intent(this, MapaActivity.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            } else {
                new DialogSalvarUsuarioUtil(PerfilActivity.this).showMensagem("Aviso", "Erro ao cadsatrar usuário;");
            }

        } else {
            new DialogSalvarUsuarioUtil(PerfilActivity.this).showMensagem("Aviso", "Campos invalidos, preencha novamente !");
        }
    }

    private  boolean validaCampo(){
        if (!campo_sexo.getSelectedItem().equals("Sexo")
                && !campo_cor.getSelectedItem().equals("Cor")
                && !campo_tipo_fisico.getSelectedItem().equals("Tipo físico")
                && !campo_nome.getText().toString().equals("")) {

            return true;
        }

        return  false;
    }

}
