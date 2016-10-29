package com.example.willian_note.appestudo.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

/**
 * Created by Willian-Note on 27/10/2016.
 */

public class DatePickerFragment extends DialogFragment {

    private DatePickerDialog.OnDateSetListener listener;

    private int Dia,Mes, Ano;

    public DatePickerFragment(){

    }

    public void setDateListener(DatePickerDialog.OnDateSetListener listener){
        this.listener = listener;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        Dia = args.getInt("dia");
        Mes = args.getInt("mes");
        Ano = args.getInt("ano");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(),listener, Ano, Mes, Dia);

    }
}
