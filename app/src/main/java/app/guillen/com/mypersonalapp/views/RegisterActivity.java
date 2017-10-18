package app.guillen.com.mypersonalapp.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import app.guillen.com.mypersonalapp.R;
import app.guillen.com.mypersonalapp.repositories.UserRepository;

public class RegisterActivity extends AppCompatActivity {

    private EditText fullnameInput;
    private EditText usernameInput;
    private EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullnameInput = (EditText)findViewById(R.id.fullname_input);
        usernameInput = (EditText)findViewById(R.id.username_input);
        passwordInput = (EditText)findViewById(R.id.password_input);
    }

    public void callRegister(View view){
        String fullname = fullnameInput.getText().toString();
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        if(fullname.isEmpty() || username.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Debe completar estos campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Boolean validacion = UserRepository.validar(username);
        if(validacion==false){
            Toast.makeText(this,"Username en uso. Ingrese otro username", Toast.LENGTH_SHORT).show();
            return;
        }else{
            UserRepository.create(username, password, fullname);
            Intent login = new Intent(this,MainActivity.class);
            startActivity(login);
            finish();
        }

    }

}