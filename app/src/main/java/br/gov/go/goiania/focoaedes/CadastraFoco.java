package br.gov.go.goiania.focoaedes;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import br.gov.go.goiania.focoaedes.auxiliar.GerenciaSessao;
import br.gov.go.goiania.focoaedes.rede.EnviaFoco;
import br.gov.go.goiania.focoaedes.rede.EnviaFocoAedes;

public class CadastraFoco extends AppCompatActivity{

    private static final String TAG = "CadastraFoco";

    private ViewPager viewPager;

    private EditText dsFocoAedes;
    private TextView cdBairro;
    private TextView cdLogr;

    private Spinner selTpLocal;
    //private EditText melhorHorario;
    private CheckBox chkStLocal1;
    private CheckBox chkStLocal2;
    private CheckBox chkStLocal3;
    private CheckBox chkStLocal4;
    private CheckBox chkStLocal5;
    private CheckBox chkStLocal6;
    private CheckBox chkStLocal7;
    private CheckBox chkStLocal8;
    private CheckBox chkStLocal9;
    private Spinner selLoteVago;
    private EditText dsBairro;
    private EditText dsLogr;
    private EditText dsQuadra;
    private EditText dsLote;
    private EditText dsNumero;
    //private Spinner selTpLogr;

    private GerenciaSessao sessao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastra_foco);

        sessao = new GerenciaSessao(getApplicationContext());

        viewPager = (ViewPager) findViewById(R.id.pager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Inicio"));
        tabLayout.addTab(tabLayout.newTab().setText("Perguntas"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final PagerAdapter adapter = new FocoAedesPager (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_cadastro_foco, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }else if(id == R.id.menu_salvar){
            cadastraFoco();
        }

        return true;
    }

    public void iniViewPagerVariaveis(){

        dsBairro = (EditText) viewPager.findViewById(R.id.busca_bairro);
        dsLogr = (EditText) viewPager.findViewById(R.id.busca_logr);
        dsQuadra = (EditText) viewPager.findViewById(R.id.nm_qudra);
        dsLote = (EditText) viewPager.findViewById(R.id.nm_lote);
        dsNumero = (EditText) viewPager.findViewById(R.id.nm_numero);
        //selTpLogr = (Spinner) viewPager.findViewById(R.id.sel_tp_logr);

        dsFocoAedes = (EditText) viewPager.findViewById(R.id.ds_foco_aedes);
        cdBairro = (TextView) viewPager.findViewById(R.id.cd_bairro);
        cdLogr = (TextView) viewPager.findViewById(R.id.cd_logr);

        selTpLocal = (Spinner) viewPager.findViewById(R.id.tp_local);

        //melhorHorario = (EditText) viewPager.findViewById(R.id.ds_horario);
        chkStLocal1 = (CheckBox) viewPager.findViewById(R.id.st_local_1);
        chkStLocal2 = (CheckBox) viewPager.findViewById(R.id.st_local_2);
        chkStLocal3 = (CheckBox) viewPager.findViewById(R.id.st_local_3);
        chkStLocal4 = (CheckBox) viewPager.findViewById(R.id.st_local_4);
        chkStLocal5 = (CheckBox) viewPager.findViewById(R.id.st_local_5);
        chkStLocal6 = (CheckBox) viewPager.findViewById(R.id.st_local_6);
        chkStLocal7 = (CheckBox) viewPager.findViewById(R.id.st_local_7);
        chkStLocal8 = (CheckBox) viewPager.findViewById(R.id.st_local_8);
        chkStLocal9 = (CheckBox) viewPager.findViewById(R.id.st_local_9);

        selLoteVago = (Spinner) viewPager.findViewById(R.id.sel_lote_vago);


    }

    public void cadastraFoco(){

        iniViewPagerVariaveis();

        if(consiste()){

            /*Toast.makeText(CadastraFoco.this, "dsFocoAedes: "+dsFocoAedes.getText().toString(), Toast.LENGTH_SHORT).show();
            Toast.makeText(CadastraFoco.this, "cdBairro: "+cdBairro.getText().toString(), Toast.LENGTH_SHORT).show();
            Toast.makeText(CadastraFoco.this, "cdLogr: "+cdLogr.getText().toString(), Toast.LENGTH_SHORT).show();
            Toast.makeText(CadastraFoco.this, "selTpLocal: "+selTpLocal.getSelectedItemPosition(), Toast.LENGTH_SHORT).show();
            Toast.makeText(CadastraFoco.this, "melhorHorario: "+melhorHorario.getText().toString(), Toast.LENGTH_SHORT).show();
            Toast.makeText(CadastraFoco.this, "chkStLocal1: "+chkStLocal1.isChecked(), Toast.LENGTH_SHORT).show();
            Toast.makeText(CadastraFoco.this, "chkStLocal2: "+chkStLocal2.isChecked(), Toast.LENGTH_SHORT).show();
            Toast.makeText(CadastraFoco.this, "chkStLocal3: "+chkStLocal3.isChecked(), Toast.LENGTH_SHORT).show();
            Toast.makeText(CadastraFoco.this, "chkStLocal4: "+chkStLocal4.isChecked(), Toast.LENGTH_SHORT).show();
            Toast.makeText(CadastraFoco.this, "chkStLocal5: "+chkStLocal5.isChecked(), Toast.LENGTH_SHORT).show();
            Toast.makeText(CadastraFoco.this, "chkStLocal6: "+chkStLocal6.isChecked(), Toast.LENGTH_SHORT).show();
            Toast.makeText(CadastraFoco.this, "chkStLocal7: "+chkStLocal7.isChecked(), Toast.LENGTH_SHORT).show();
            Toast.makeText(CadastraFoco.this, "chkStLocal8: "+chkStLocal8.isChecked(), Toast.LENGTH_SHORT).show();
            Toast.makeText(CadastraFoco.this, "chkStLocal9: "+chkStLocal9.isChecked(), Toast.LENGTH_SHORT).show();
            if(chkStLocal8.isChecked())
                Toast.makeText(CadastraFoco.this, "selLoteVago: "+selLoteVago.getSelectedItemPosition(), Toast.LENGTH_SHORT).show();*/

            //new EnviaFocoAedes(this, null).execute();

            Map<String, String> params = new HashMap<String,String>();
            params.put("opr","abre_solicitacao");
            params.put("txt_cd_contri",sessao.getDadosUsr().get(GerenciaSessao.KEY_CODIGO));
            params.put("txt_nm_contri",sessao.getDadosUsr().get(GerenciaSessao.KEY_NOME));
            params.put("txt_cd_munic","25300");
            params.put("txt_nr_cpf_contri",sessao.getDadosUsr().get(GerenciaSessao.KEY_CPF));
            params.put("txt_in_email_contri",sessao.getDadosUsr().get(GerenciaSessao.KEY_EMAIL));
            params.put("txt_cd_servico","1208");
            //params.put("txt_cd_servico","190");

            params.put("txt_cd_munic_solicitacao","25300");

            params.put("txt_cd_bairro_solicitacao",cdBairro.getText().toString());
            params.put("txt_cd_logr_solicitacao",cdLogr.getText().toString());
            params.put("txt_en_lt_logr_solicitacao",dsQuadra.getText().toString());
            params.put("txt_en_qd_logr_solicitacao",dsLote.getText().toString());
            params.put("txt_en_nr_logr_solicitacao",dsNumero.getText().toString());

            //params.put("txt_tp_logr_solicitacao",selTpLogr.getItemAtPosition(selTpLogr.getSelectedItemPosition()).toString());
            params.put("txt_tp_logr_solicitacao","");
            params.put("txt_nm__munic_solicitacao","GOIANIA");
            params.put("txt_nm_bairro_solicitacao",dsBairro.getText().toString());
            params.put("txt_nm_logr_solicitacao",dsLogr.getText().toString());
            params.put("txt_ds_solicitacao", dsFocoAedes.getText().toString());

            Map<String, String> paramsDesc = new HashMap<String,String>();
            boolean flagDec = false;

            if(chkStLocal1.isChecked()){
                paramsDesc.put("chkStLocal1",chkStLocal1.getText().toString());
                flagDec = true;
            }

            if(chkStLocal2.isChecked()){
                paramsDesc.put("chkStLocal2",chkStLocal2.getText().toString());
                flagDec = true;
            }

            if(chkStLocal3.isChecked()){
                paramsDesc.put("chkStLocal3",chkStLocal3.getText().toString());
                flagDec = true;
            }

            if(chkStLocal4.isChecked()){
                paramsDesc.put("chkStLocal4",chkStLocal4.getText().toString());
                flagDec = true;
            }

            if(chkStLocal5.isChecked()){
                paramsDesc.put("chkStLocal5",chkStLocal5.getText().toString());
                flagDec = true;
            }

            if(chkStLocal6.isChecked()){
                paramsDesc.put("chkStLocal6",chkStLocal6.getText().toString());
                flagDec = true;
            }

            if(chkStLocal7.isChecked()){
                paramsDesc.put("chkStLocal7",chkStLocal7.getText().toString());
                flagDec = true;
            }

            if(chkStLocal8.isChecked()){
                paramsDesc.put("chkStLocal8",chkStLocal8.getText().toString());
                flagDec = true;
            }

            if(chkStLocal9.isChecked()){
                paramsDesc.put("chkStLocal9",chkStLocal9.getText().toString());
                flagDec = true;
            }

            if(!flagDec){
                paramsDesc = null;
            }

            EnviaFoco envia = new EnviaFoco(this, params, paramsDesc);


            envia.executa();

        }

    }

    public boolean consiste(){

        iniViewPagerVariaveis();

        boolean flagStLocal = true;
        boolean flagLoteVago = false;
        Log.d(TAG, "consiste - cdBairro: " + cdBairro.getText());
        Log.d(TAG, "consiste - cdLogr: " + cdLogr.getText());
        if(cdBairro.getText().toString().equals("") || cdBairro.getText().toString().equals("0")){

            Toast.makeText(CadastraFoco.this, "Informe o bairro!", Toast.LENGTH_LONG).show();
            return false;

        }

        if(cdLogr.getText().toString().equals("") || cdLogr.getText().toString().equals("0")){

            Toast.makeText(CadastraFoco.this, "Informe o logradouro!", Toast.LENGTH_LONG).show();
            return false;

        }

        if(selTpLocal.getSelectedItemPosition() == 0){

            Toast.makeText(CadastraFoco.this, "Informe o tipo do local!", Toast.LENGTH_LONG).show();
            return false;

        }

        /*if(melhorHorario.getText().toString().equals("")){

            Toast.makeText(CadastraFoco.this, "Informe o melhor horario para visita!", Toast.LENGTH_LONG).show();
            return false;

        }*/

        if(chkStLocal1.isChecked())
            flagStLocal = false;

        if(chkStLocal2.isChecked())
            flagStLocal = false;

        if(chkStLocal3.isChecked())
            flagStLocal = false;

        if(chkStLocal4.isChecked())
            flagStLocal = false;

        if(chkStLocal5.isChecked())
            flagStLocal = false;

        if(chkStLocal6.isChecked())
            flagStLocal = false;

        if(chkStLocal7.isChecked())
            flagStLocal = false;

        if(chkStLocal8.isChecked()){
            flagStLocal = false;
            flagLoteVago = true;
        }

        if(chkStLocal9.isChecked())
            flagStLocal = false;

        if(flagLoteVago){
            if(selLoteVago.getSelectedItemPosition() == 0){
                Toast.makeText(CadastraFoco.this, "Informe a situação do lote vago!", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        if(flagStLocal){
            Toast.makeText(CadastraFoco.this, "Informe a situação do local!", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;

    }

}
