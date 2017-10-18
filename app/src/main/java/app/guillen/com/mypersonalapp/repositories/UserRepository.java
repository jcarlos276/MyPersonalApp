package app.guillen.com.mypersonalapp.repositories;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

import app.guillen.com.mypersonalapp.R;
import app.guillen.com.mypersonalapp.models.User;
import app.guillen.com.mypersonalapp.views.MainActivity;
import app.guillen.com.mypersonalapp.views.RegisterActivity;

/**
 * Created by guillen on 09/10/17.
 */

public class UserRepository {

    public static List<User> list(){
        List<User> users = SugarRecord.listAll(User.class);
        return users;
    }

    public static User read(Long id){
        User user = SugarRecord.findById(User.class, id);
        return user;
    }

    public static void create(String username, String password, String fullname){
        User user = new User(username, password, fullname);
        SugarRecord.save(user);
    }

    public static void update(String username, String password, String fullname, Long id){
        User user = SugarRecord.findById(User.class, id);
        user.setFullname(fullname);
        user.setUsername(username);
        user.setPassword(password);
        SugarRecord.save(user);
    }

    public static void delete(Long id){
        User user = SugarRecord.findById(User.class, id);
        SugarRecord.delete(user);
    }

    //

    public static User login(String username, String password, Context context){
        List<User> users=list();
        for (User user : users){
            if(user.getUsername().equalsIgnoreCase(username)){
                if(user.getPassword().equals(password)) {
                    return user;
                }
                Toast msg = Toast.makeText(context,"Contraseña Incorrecta",Toast.LENGTH_SHORT);
                msg.show();
                return null;
            }
        }
        showDialogLoginFailed(context);
        return null;
    }

    public static Boolean validar(String username){
        List<User> users=list();
        for (User user : users){
            if(user.getUsername().equalsIgnoreCase(username)){
                return false;
            }
        }
        return true;
    }

    public static User getUser(String username){
        List<User> users=list();
        for (User user : users){
            if(user.getUsername().equalsIgnoreCase(username)){
                return user;
            }
        }
        return null;
    }

    public static void showDialogLoginFailed(final Context context){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_dialog);
        // Custom Android Allert Dialog Title
        dialog.setTitle("Datos incorrectos");

        Button cancel = dialog.findViewById(R.id.customDialogCancel);
        Button ok = dialog.findViewById(R.id.customDialogOk);
        // Click cancel to dismiss android custom dialog box
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Vuela a ingresar sus credenciales", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        // Your android custom dialog ok action
        // Action for custom dialog ok button click
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, RegisterActivity.class);
                context.startActivity(intent);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    static {
    User user = new User("jguillen", "admin123", "Juan Carlos");
    SugarRecord.save(user);
    }
}