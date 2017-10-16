package app.guillen.com.mypersonalapp.views;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import app.guillen.com.mypersonalapp.R;
import app.guillen.com.mypersonalapp.models.User;
import app.guillen.com.mypersonalapp.repositories.UserRepository;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private EditText etUsuario;
    private EditText etClave;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsuario = (EditText) findViewById(R.id.etUsuario);
        etClave= (EditText) findViewById(R.id.etClave);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        // init SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // username remember
        String username = sharedPreferences.getString("username", null);
        if(username != null){
            etUsuario.setText(username);
            etClave.requestFocus();
        }

        // islogged remember
        if(sharedPreferences.getBoolean("islogged", false)){
            // Go to Dashboard
            goDashboard();
        }

    }

    public void callLogin(View view){

        String username = etUsuario.getText().toString();
        String password = etClave.getText().toString();

        if(username.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Debe completar estos campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Login logic
        User user = UserRepository.login(username, password,this);

        if(user == null){
            //Toast.makeText(this, "Username or password invalid", Toast.LENGTH_SHORT).show();
            showDialogLoginFailed();
        }else{
        Toast.makeText(this, "Bienvenido " + user.getFullname(), Toast.LENGTH_SHORT).show();

        // Save to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean success = editor
                .putString("username", user.getUsername())
                .putBoolean("islogged", true)
                .commit();

        // Go to Dashboard
        goDashboard();
        }
    }

    private void goDashboard(){
        startActivity(new Intent(this, DashboardActivity.class));
        finish();
    }

    private void goRegister(){
        startActivity(new Intent(this, RegisterActivity.class));
        finish();
    }

    public void showDialogLoginFailed(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);
        // Custom Android Allert Dialog Title
        dialog.setTitle("Datos incorrectos");

        Button cancel = (Button) dialog.findViewById(R.id.customDialogCancel);
        Button ok = (Button) dialog.findViewById(R.id.customDialogOk);
        // Click cancel to dismiss android custom dialog box
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Vuela a ingresar sus credenciales", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        // Your android custom dialog ok action
        // Action for custom dialog ok button click
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goRegister();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
