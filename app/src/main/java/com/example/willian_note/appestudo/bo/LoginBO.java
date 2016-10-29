package com.example.willian_note.appestudo.bo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.willian_note.appestudo.Validation.LoginValidation;
import com.example.willian_note.appestudo.repository.LoginRepository;
import com.example.willian_note.appestudo.util.Util;

/**
 * Created by Willian-Note on 16/10/2016.
 */

public class LoginBO {

    private LoginRepository loginRepository;

    public LoginBO(Activity activity){
        loginRepository = new LoginRepository(activity);
        loginRepository.listarLogins(activity);
    }
    public boolean ValidarCamposLogin(LoginValidation validation)
    {
        boolean Resultado = true;
        if(validation.getLogin() == null || "".equals(validation.getLogin())){
            validation.getEdtLogin().setError("Campo Obrigatório");
            Resultado =  false;
        }
        if(validation.getSenha() == null || "".equals(validation.getSenha())){
            validation.getEdtSenha().setError("Campo Obrigatório");
            Resultado =  false;
        }
        if (Resultado){
            //loginRepository.AdicionarLogin(validation.getLogin(), validation.getSenha());
            //loginRepository.UpdateLogin(validation.getLogin(), validation.getSenha());
            loginRepository.DeletarLogin(validation.getLogin(), validation.getSenha());
            if (!validation.getLogin().equals("admin") || !validation.getSenha().equals("admin")){
                //Toast.makeText(validation.getActivity(),"Login/Senha inválidos",Toast.LENGTH_LONG).show();
                Util.showMsgToast(validation.getActivity(), "Login/Senha inválidos!");
                Resultado = false;
            }
            else{
                SharedPreferences.Editor editor = validation.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE).edit();
                editor.putString("login", validation.getLogin());
                editor.putString("senha", validation.getSenha());
                editor.commit();

            }

        }

        return Resultado;
    }

    public void deslogar(){

    }
}
