package nkubs.myapplication;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

public class SignUp extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signuptab);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("注册");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false); //移除icon
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        initActionBarTabs(actionBar);
    }

    private void initActionBarTabs(ActionBar actionBar){

        Tab tab1 = actionBar.newTab();
        tab1.setText("学号注册").setTabListener(new TabListener<TabFragment_Sid>(this,"SignUpBySid",TabFragment_Sid.class));
        actionBar.addTab(tab1);


        Tab tab2 = actionBar.newTab();
        tab2.setText("邀请码注册").setTabListener(new TabListener<TabFragment_Invite>(this,"SignUpByInvite",TabFragment_Invite.class));
        actionBar.addTab(tab2);
    }

    /**
     * 定义TabListener监听器
     */
    private static class TabListener<T extends Fragment> implements ActionBar.TabListener{

        private Fragment mFragment;
        private final Activity mActivity;
        private final String mTag;
        private final Class<T> mClass;

        /** Constructor used each time a new tab is created.
         * @param activity  The host Activity, used to instantiate the fragment
         * @param tag  The identifier tag for the fragment
         * @param clz  The fragment's Class, used to instantiate the fragment
         */


        public TabListener(Activity activity, String tag, Class<T> clz){
            mActivity = activity;
            mTag = tag;
            mClass = clz;}

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
            // Check if the fragment is already initialized
            if (mFragment == null) {
                // If not, instantiate and add it to the activity
                mFragment = Fragment.instantiate(mActivity, mClass.getName());
                fragmentTransaction.add(android.R.id.content, mFragment, mTag);
               /* Log.i("信息","1");*/
            } else {
                // If it exists, simply attach it in order to show it
                fragmentTransaction.attach(mFragment);
                /*Log.i("信息","2");*/
            }
           /* Log.i("信息", "Tab被选中");*/

        }
        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
            if(mFragment!=null){
                fragmentTransaction.detach(mFragment);
            }
        }

        @Override
        public void onTabReselected(Tab tab, FragmentTransaction fragmentTransaction) {
        }

    }



}

