package com.example.willian_note.appestudo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.willian_note.appestudo.entidade.Pessoa;
import com.example.willian_note.appestudo.entidade.Profissao;
import com.example.willian_note.appestudo.entidade.Sexo;
import com.example.willian_note.appestudo.entidade.TipoPessoa;
import com.example.willian_note.appestudo.fragment.DatePickerFragment;
import com.example.willian_note.appestudo.repository.PessoaRepository;
import com.example.willian_note.appestudo.util.Mask;
import com.example.willian_note.appestudo.util.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PessoaActivity extends AppCompatActivity {

    private Spinner spnProfissao;

    private RadioGroup rbgCpfCnpj,rbgSexo;
    private RadioButton rbtCpf;
    private TextWatcher cpfMask,cnpjMask;
    private TextView txtCpfCnpj;
    private int CpfCnpjSelecionado;
    private EditText edtDataNasc, edtNome, edtEndereco, edtCpfCnpj;
    private PessoaRepository pessoaRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa);

        spnProfissao = (Spinner)findViewById(R.id.spnProfissao);
        edtCpfCnpj = (EditText)findViewById(R.id.edtCpfCnpj);
        rbgCpfCnpj = (RadioGroup)findViewById(R.id.rbgCpfCnpj);
        rbtCpf = (RadioButton)findViewById(R.id.rbtCpf);
        txtCpfCnpj = (TextView)findViewById(R.id.txtCpfCnpj);
        edtDataNasc = (EditText)findViewById(R.id.edtDataNasc);
        edtNome = (EditText)findViewById(R.id.edtNome);
        edtEndereco = (EditText)findViewById(R.id.edtEndereco);
        rbgSexo = (RadioGroup)findViewById(R.id.rbgSexo);
        pessoaRepository = new PessoaRepository(this);

        cpfMask = Mask.insert("###.###.###-##", edtCpfCnpj);
        edtCpfCnpj.addTextChangedListener(cpfMask);
        cnpjMask = Mask.insert("##.###.###/####-##", edtCpfCnpj);
        rbgCpfCnpj.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                edtCpfCnpj.requestFocus();
                edtCpfCnpj.setText("");
                CpfCnpjSelecionado  = group.getCheckedRadioButtonId();
                if (CpfCnpjSelecionado == rbtCpf.getId()){
                    edtCpfCnpj.removeTextChangedListener(cnpjMask);
                    edtCpfCnpj.addTextChangedListener(cpfMask);
                    txtCpfCnpj.setText("CPF:");

                } else{
                    edtCpfCnpj.removeTextChangedListener(cpfMask);
                    edtCpfCnpj.addTextChangedListener(cnpjMask);
                    txtCpfCnpj.setText("CNPJ:");
                }
            }
        });

        edtCpfCnpj.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(rbgCpfCnpj.getCheckedRadioButtonId() == rbgCpfCnpj.getId()){
                    if(edtCpfCnpj.getText().length() < 14)
                        edtCpfCnpj.setText("");
                } else{
                    if(edtCpfCnpj.getText().length() < 18)
                        edtCpfCnpj.setText("");
                }
            }
        });

        initProfissoes();
        //TODO:Continuar a implementação do cadastro do cliente
    }

    public void EnviarPessoa(View view){
        Pessoa p = MontarPessoa();
        if(!ValidarPessoa(p)){
           if(pessoaRepository.SalvarPessoa(p)) {
               Util.showMsgToast(this, "Inserido com Sucesso");
               Intent i = new Intent(this,ListaPessoaActivity.class);
               startActivity(i);
               finish();
           }
        }
    }
    private void initProfissoes(){
        ArrayList<String> profissoes = new ArrayList<>();
        for (Profissao p: Profissao.values()) {
            profissoes.add(p.getDescrica());
        }
        ArrayAdapter adapter = new ArrayAdapter(PessoaActivity.this, android.R.layout.simple_spinner_dropdown_item, profissoes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnProfissao.setAdapter(adapter);
    }

    public void setData(View view){
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        Calendar cal = Calendar.getInstance();

        Bundle bundle = new Bundle();

        bundle.putInt("dia",cal.get(Calendar.DAY_OF_MONTH));
        bundle.putInt("mes",cal.get(Calendar.MONTH));
        bundle.putInt("ano",cal.get(Calendar.YEAR));

        datePickerFragment.setArguments(bundle);
        datePickerFragment.setDateListener(dateListener);
        datePickerFragment.show(getFragmentManager(), "Data Nasc");
    }


    public DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            edtDataNasc.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            //Util.showMsgToast(PessoaActivity.this, "Ano:"+ year + "Mes: "+ (month + 1) + "Dia: "+ dayOfMonth);

        }
    };

    private  boolean ValidarPessoa(Pessoa pessoa){
        boolean erro = false;
        if (pessoa.getNome() == null || "".equals(pessoa.getNome())){
            erro = true;
            edtNome.setError("Campo Nome obrigatório");
        }
        if (pessoa.getEndereco() == null || "".equals(pessoa.getEndereco())){
            erro = true;
            edtEndereco.setError("Campo Endereço obrigatório");
        }
        if (pessoa.getCpfCnpj() == null || "".equals(pessoa.getCpfCnpj())){
            erro = true;
            switch (rbgCpfCnpj.getCheckedRadioButtonId()){
                case R.id.rbtCpf:
                    edtCpfCnpj.setError("Campo CPF obrgatório");
                    break;
                case R.id.rbtCnpj:
                    edtCpfCnpj.setError("Campo CNPJ obrigatório");
                    break;
            }
        }else {
            switch (rbgCpfCnpj.getCheckedRadioButtonId()){
                case R.id.rbtCpf:
                    if(edtCpfCnpj.length() < 14){
                        erro = true;
                        edtCpfCnpj.setError("Campo CPF deve ter 11 caracteres");
                    }
                    break;
                case R.id.rbtCnpj:
                    if(edtCpfCnpj.length() < 18){
                        erro = true;
                        edtCpfCnpj.setError("Campo CNPJ deve ter 14 caracteres");
                    }

                    break;
            }
        }
        if(pessoa.getDtNasc() == null){
            erro = true;
            edtDataNasc.setError("Campo Data Nasc. deve ser preenchido");
        }
        return erro;
    }
    private Pessoa MontarPessoa(){
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(edtNome.getText().toString());
        pessoa.setEndereco(edtEndereco.getText().toString());
        pessoa.setCpfCnpj(edtCpfCnpj.getText().toString());
        switch (rbgCpfCnpj.getCheckedRadioButtonId()){
            case R.id.rbtCpf:
                pessoa.setTipoPessoa(TipoPessoa.FISICA);
                break;
            case R.id.rbtCnpj:
                pessoa.setTipoPessoa(TipoPessoa.JURIDICA);
                break;
        }
        switch (rbgSexo.getCheckedRadioButtonId()){
            case R.id.rbtMasculino:
                pessoa.setSexo(Sexo.MASCULINO);
                break;
            case R.id.rbtFeminino:
                pessoa.setSexo(Sexo.FEMININO);
                break;
        }

        Profissao profissao = Profissao.getProfissao(spnProfissao.getSelectedItemPosition());
        pessoa.setProfissao(profissao);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try{
            Date nascimento = dateFormat.parse(edtDataNasc.getText().toString());
            pessoa.setDtNasc(nascimento);
        }catch (ParseException e){
            e.printStackTrace();
        }

        return pessoa;

    }
}
