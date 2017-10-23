package app.guillen.com.mypersonalapp.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vstechlab.easyfonts.EasyFonts;

import app.guillen.com.mypersonalapp.R;
import app.guillen.com.mypersonalapp.models.User;
import app.guillen.com.mypersonalapp.preferences.MyPreferencesActivity;
import app.guillen.com.mypersonalapp.repositories.UserRepository;

public class DashboardActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    private static final String TAG = DashboardActivity.class.getSimpleName();

    // SharedPreferences
    private SharedPreferences sharedPreferences;

    private TextView fullnameText;
    private TextView welcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        User user = new User();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        fullnameText = (TextView)findViewById(R.id.fullname_text);
        welcomeText = (TextView)findViewById(R.id.welcome_text);

        // init SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // get username from SharedPreferences
        String fullname = sharedPreferences.getString("fullname", null);
        Log.d(TAG, "Fullname: " + fullname);

        if(getIntent().getExtras() != null) {
            Intent intent = getIntent();
            user = (User)intent.getSerializableExtra("Usuario");
            if(user!=null) {
                UserRepository.updateFullname(fullname, user.getId());
                fullnameText.setText(user.getFullname());
            }
        }

        //Get theme from SharedPreferences
        String theme = sharedPreferences.getString("tema", null);

        //Get font from SharedPreferences
        String font = sharedPreferences.getString("preferenciaFuente", null);
        if(font != null){
            switch (font){
                case "1":
                    changeTexto1();
                    break;
                case "2":
                    changeTexto2();
                    break;
                case "3":
                    changeTexto3();
                    break;
            }
        }else{
            Toast.makeText(this,"Ninguna fuente fue seleccionada",Toast.LENGTH_SHORT).show();
        }

       //

        // Setear Toolbar como action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set DrawerLayout
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Set drawer toggle icon
        /*final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }*/

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, android.R.string.ok, android.R.string.cancel);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Set NavigationItemSelectedListener
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                // Do action by menu item id
                switch (menuItem.getItemId()){
                    case R.id.nav_inicio:
                        Toast.makeText(DashboardActivity.this, "Inicio...", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_perfil:
                        Toast.makeText(DashboardActivity.this, "Mis datos...", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_settings:
                        Toast.makeText(DashboardActivity.this, "Configuraciones...", Toast.LENGTH_SHORT).show();
                        configuration();
                        break;
                    case R.id.nav_logout:
                        Toast.makeText(DashboardActivity.this, "Cerrar Sesion...", Toast.LENGTH_SHORT).show();
                        callLogout();
                        break;
                }

                // Close drawer
                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });

        // Change navigation header information
        ImageView photoImage = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.menu_photo);
        photoImage.setBackgroundResource(R.drawable.ic_profile);

        TextView fullnameText = (TextView) navigationView.getHeaderView(0).findViewById(R.id.menu_fullname);
        if(user!=null){
            fullnameText.setText(user.getFullname());
        }else{
            fullnameText.setText("Usuario");
        }

        TextView emailText = (TextView) navigationView.getHeaderView(0).findViewById(R.id.menu_email);
        if(user!=null){
            emailText.setText(user.getUsername());
        }else{
            emailText.setText("Correo");
        }
        //
    }

    public void changeTexto1(){
        welcomeText.setTypeface(EasyFonts.droidSerifBoldItalic(this));
        fullnameText.setTypeface(EasyFonts.droidSerifBoldItalic(this));
    }
    public void changeTexto2(){
        welcomeText.setTypeface(EasyFonts.caviarDreams(this));
        fullnameText.setTypeface(EasyFonts.caviarDreams(this));
    }
    public void changeTexto3(){
        welcomeText.setTypeface(EasyFonts.robotoBlackItalic(this));
        fullnameText.setTypeface(EasyFonts.robotoBlackItalic(this));
    }

    public void callLogout(){
        // remove from SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean success = editor.putBoolean("islogged", false).commit();
//        boolean success = editor.clear().commit(); // not recommended
        Intent main=new Intent(this,MainActivity.class);
        startActivity(main);
        finish();
    }

    public void configuration(){
        Intent intent = new Intent(this,MyPreferencesActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: // Option open drawer
                if(!drawerLayout.isDrawerOpen(GravityCompat.START))
                    drawerLayout.openDrawer(GravityCompat.START);   // Open drawer
                else
                    drawerLayout.closeDrawer(GravityCompat.START);    // Close drawer
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
