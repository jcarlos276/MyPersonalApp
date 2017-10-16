package app.guillen.com.mypersonalapp.repositories;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

import app.guillen.com.mypersonalapp.models.User;

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
            }
        }
        return null;
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

    static {
    User user = new User("jguillen", "admin123", "Juan Carlos");
    SugarRecord.save(user);
    }
}