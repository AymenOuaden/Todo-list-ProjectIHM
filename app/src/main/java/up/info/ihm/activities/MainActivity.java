package up.info.ihm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import up.info.ihm.R;
import up.info.ihm.db.SQLiteDB;
import up.info.ihm.fragments.AllFragment;
import up.info.ihm.fragments.OtherFragment;
import up.info.ihm.fragments.PersonnelFragment;
import up.info.ihm.fragments.ShcoolFragment;
import up.info.ihm.fragments.ShoppingFragment;
import up.info.ihm.fragments.SportFragment;
import up.info.ihm.models.Task;
import up.info.ihm.sharedPreferences.SharedPrefrences;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public enum FragmentCategory {
        ALL,
        PERSONAL,
        SHOPPING,
        SCHOOL,
        SPORT,
        OTHER
    }

    public enum Filtre {
        ALL,
        NON_COMMENCE,
        EN_ENCOURS,
        TERMINE,
        RETARD
    }

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private View headerView;
    private AllFragment allFragment;
    private PersonnelFragment personnelFragment;
    private ShcoolFragment shcoolFragment;
    private ShoppingFragment shoppingFragment;
    private SportFragment sportFragment;
    private OtherFragment otherFragment;
    private FragmentCategory currentFragment;
    String language, mode;
    SharedPrefrences sharedPrefrences;


    private Button btn_parametres;
    SQLiteDB sqLiteDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        drawerLayout = findViewById(R.id.my_drawer_layout);
        navigationView = findViewById(R.id.main_nav_view);
        toolbar = findViewById(R.id.toolbar);
        sqLiteDB = new SQLiteDB(getApplicationContext());
        sqLiteDB.addDefaultTasksTest();
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle togel = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(togel);
        togel.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_today);
        personnelFragment = new PersonnelFragment();
        shoppingFragment = new ShoppingFragment();
        sportFragment = new SportFragment();
        shcoolFragment = new ShcoolFragment();
        otherFragment = new OtherFragment();

        replaceFragment(personnelFragment);
        replaceFragment(shoppingFragment);
        replaceFragment(shcoolFragment);
        replaceFragment(sportFragment);
        replaceFragment(otherFragment);

        currentFragment = FragmentCategory.ALL;
        allFragment = new AllFragment();
        replaceFragment(allFragment);

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_today);

        headerView = navigationView.getHeaderView(0);
        btn_parametres = headerView.findViewById(R.id.btn_parametre);
        btn_parametres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), Parametres.class);
                startActivity(intent);
            }
        });
        sharedPrefrences = new SharedPrefrences(getApplicationContext());
        mode = sharedPrefrences.getMode();
        language = sharedPrefrences.getAppLanguage();

    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_today:
                currentFragment = FragmentCategory.ALL;
                replaceFragment(allFragment = new AllFragment());
                break;
            case R.id.nav_personnel:
                currentFragment = FragmentCategory.PERSONAL;
                replaceFragment(personnelFragment = new PersonnelFragment());
                break;
            case R.id.nav_shopping:
                currentFragment = FragmentCategory.SHOPPING;
                replaceFragment(shoppingFragment = new ShoppingFragment());
                break;
            case R.id.nav_shcool:
                currentFragment = FragmentCategory.SCHOOL;
                replaceFragment(shcoolFragment = new ShcoolFragment());
                break;
            case R.id.nav_sport:
                currentFragment = FragmentCategory.SPORT;
                replaceFragment(sportFragment = new SportFragment());
                break;
            case R.id.nav_other:
                currentFragment = FragmentCategory.OTHER;
                replaceFragment(otherFragment = new OtherFragment());
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filtre_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case R.id.id_all:
                filtreTask(currentFragment, Filtre.ALL);
                break;
            case R.id.id_notStated_filtre:
                filtreTask(currentFragment, Filtre.NON_COMMENCE);
                break;
            case R.id.id_completed_filtre:
                filtreTask(currentFragment, Filtre.TERMINE);
                break;
            case R.id.id_Doing:
                filtreTask(currentFragment, Filtre.EN_ENCOURS);
                break;
            case R.id.id_lated:
                filtreTask(currentFragment, Filtre.RETARD);
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.id_frame_layout, fragment);
        fragmentTransaction.commit();
    }

    public void floatingButtonOnClick(View V) {
        Intent intent = new Intent(this, AddTaskActivity.class);
        this.startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Boolean taskAdded = data.getBooleanExtra("taskAdded", false);
            int taskID = data.getIntExtra("taskID", 0);
            Task.Categorie categorie = (Task.Categorie) data.getSerializableExtra("taskCategory");
            if (taskAdded) {

                switch (categorie) {
                    case PERSONNEL:
                        personnelFragment.refreshAddingTask(taskID);
                        break;
                    case COURSES:
                        shoppingFragment.refreshAddingTask(taskID);
                        break;
                    case ETUDE:
                        shcoolFragment.refreshAddingTask(taskID);
                        break;
                    case SPORT:
                        sportFragment.refreshAddingTask(taskID);
                        break;
                    case AUTRE:
                        otherFragment.refreshAddingTask(taskID);
                        break;
                }
                allFragment.refreshAddingTask(taskID);
                Log.d("log", "new task added <refreshing the view>");
            }
        } else {
            Log.d("log", "adding task canceled");
        }
    }

    void filtreTask(FragmentCategory category, Filtre filtre) {
        switch (category) {
            case ALL:
                switch (filtre) {
                    case ALL:
                        allFragment.filterByAllTask();
                        break;
                    case NON_COMMENCE:
                        allFragment.filterByNotStatedTask();
                        break;
                    case EN_ENCOURS:
                        allFragment.filterByDoingTask();
                        break;
                    case TERMINE:
                        allFragment.filterByCompletedTask();
                        break;
                    case RETARD:
                        allFragment.filterByLateTask();
                        break;
                }
                break;
            case PERSONAL:
                switch (filtre) {
                    case ALL:
                        personnelFragment.filterByAllTask();
                        break;
                    case NON_COMMENCE:
                        personnelFragment.filterByNotStatedTask();
                        break;
                    case EN_ENCOURS:
                        personnelFragment.filterByDoingTask();
                        break;
                    case TERMINE:
                        personnelFragment.filterByCompletedTask();
                        break;
                    case RETARD:
                        personnelFragment.filterByLateTask();
                        break;
                }
                break;
            case SHOPPING:
                switch (filtre) {
                    case ALL:
                        shoppingFragment.filterByAllTask();
                        break;
                    case NON_COMMENCE:
                        shoppingFragment.filterByNotStatedTask();
                        break;
                    case EN_ENCOURS:
                        shoppingFragment.filterByDoingTask();
                        break;
                    case TERMINE:
                        shoppingFragment.filterByCompletedTask();
                        break;
                    case RETARD:
                        shoppingFragment.filterByLateTask();
                        break;
                }
                break;
            case SCHOOL:
                switch (filtre) {
                    case ALL:
                        shcoolFragment.filterByAllTask();
                        break;
                    case NON_COMMENCE:
                        shcoolFragment.filterByNotStatedTask();
                        break;
                    case EN_ENCOURS:
                        shcoolFragment.filterByDoingTask();
                        break;
                    case TERMINE:
                        shcoolFragment.filterByCompletedTask();
                        break;
                    case RETARD:
                        shcoolFragment.filterByLateTask();
                        break;
                }
                break;
            case SPORT:
                switch (filtre) {
                    case ALL:
                        sportFragment.filterByAllTask();
                        break;
                    case NON_COMMENCE:
                        sportFragment.filterByNotStatedTask();
                        break;
                    case EN_ENCOURS:
                        sportFragment.filterByDoingTask();
                        break;
                    case TERMINE:
                        sportFragment.filterByCompletedTask();
                        break;
                    case RETARD:
                        sportFragment.filterByLateTask();
                        break;
                }
                break;
            case OTHER:
                switch (filtre) {
                    case ALL:
                        otherFragment.filterByAllTask();
                        break;
                    case NON_COMMENCE:
                        otherFragment.filterByNotStatedTask();
                        break;
                    case EN_ENCOURS:
                        otherFragment.filterByDoingTask();
                        break;
                    case TERMINE:
                        otherFragment.filterByCompletedTask();
                        break;
                    case RETARD:
                        otherFragment.filterByLateTask();
                        break;
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!language.equals(sharedPrefrences.getAppLanguage())) {
            finish();
            startActivity(getIntent());
        }
        if (!mode.equals(sharedPrefrences.getMode())) {
            finish();
            startActivity(getIntent());
        }
    }

}


