package com.example.willian_note.appestudo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.willian_note.appestudo.entidade.Profissao;
import com.example.willian_note.appestudo.fragment.DatePickerFragment;
import com.example.willian_note.appestudo.util.Mask;
import com.example.willian_note.appestudo.util.Util;

import java.util.ArrayList;
import java.util.Calendar;

public class PessoaActivity extends AppCompatActivity {

    private Spinner spnProfissao;
    private EditText edtCpfCnpj;
    private RadioGroup rbgCpfCnpj;
    private RadioButton rbtCpf;
    private TextWatcher cpfMask;
    private TextWatcher cnpjMask;
    private TextView txtCpfCnpj;
    private EditText edtDataNasc;
    private int CpfCnpjSelecionado;
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
}
