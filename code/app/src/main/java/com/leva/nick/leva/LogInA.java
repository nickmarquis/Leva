package com.leva.nick.leva;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.SessionDefaultAudience;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;
import com.sromku.simple.fb.listeners.OnLoginListener;


public class LogInA extends Activity {

    private static final String APP_ID = "371005393088854";
    private static final String APP_NAMESPACE = "leva";

    private SimpleFacebook mSimpleFacebook;
    private ImageButton mImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        Permission[] permissions = new Permission[] {
                Permission.PUBLIC_PROFILE,
                Permission.USER_FRIENDS
        };

        SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
                .setAppId(APP_ID)
                .setNamespace(APP_NAMESPACE)
                .setPermissions(permissions)
                .setDefaultAudience(SessionDefaultAudience.FRIENDS)
                .setAskForAllPermissionsAtOnce(false)
                .build();

        SimpleFacebook.setConfiguration(configuration);

        mSimpleFacebook = SimpleFacebook.getInstance(this);
        mImageButton = (ImageButton)findViewById(R.id.loginB);

        setLogin();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mSimpleFacebook.onActivityResult(this, requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
        mSimpleFacebook = SimpleFacebook.getInstance(this);
    }

    private void setLogin() {
        // Login listener
        final OnLoginListener onLoginListener = new OnLoginListener() {

            @Override
            public void onLogin() {
                Toast.makeText(getApplication(), "logged in", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNotAcceptingPermissions(Permission.Type type) {
                Toast.makeText(getApplication(), String.format("You didn't accept %s permissions", type.name()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onThinking() {

            }

            @Override
            public void onException(Throwable throwable) {

            }

            @Override
            public void onFail(String reason) {

            }
        };

        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mSimpleFacebook.login(onLoginListener);
            }
        });
    }
}
