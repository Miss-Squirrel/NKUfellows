package nkubs.myapplication;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


public class Fellows extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fellows);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); //enable 返回<
        actionBar.setTitle(" 我的校友");

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_friendsList:
                /*Toast.makeText(this, "List", Toast.LENGTH_SHORT).show();*/
                Intent intent1 = new Intent();
                intent1.setClass(Fellows.this, Fellows_Friends.class);
                startActivity(intent1);
                return true;
            case R.id.action_add:
                /*Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show();*/
                Intent intent2 = new Intent();
                intent2.setClass(Fellows.this, Fellows_Find.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
