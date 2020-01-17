package com.test.messeger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.test.messeger.Web.getPOST;
import static com.test.messeger.Web.getStringFromInputStream;

public class MainActivity extends AppCompatActivity {

    Button loginbtn;
    EditText idtext;
    EditText pwtext;
    String deviceToken = null;
    SharedPreferences auto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("getInstanceId failed", task.getException());
                            return;
                        }

                        deviceToken = task.getResult().getToken();

                        Log.d("test", deviceToken);
                    }
                });

        auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);

        String id = auto.getString("id", null);
        String pw = auto.getString("pw", null);
        if (id != null && pw != null) {
            getLogin(id, pw);
        }
        idtext = findViewById(R.id.editText);
        pwtext = findViewById(R.id.editText3);
        loginbtn = findViewById(R.id.button);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLogin(idtext.getText().toString(), pwtext.getText().toString());
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), register.class);
                startActivity(intent);
            }
        });

    }

    private void getLogin(final String idtxt, final String pwtxt) {
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Map<String, String> parm = new HashMap<String, String>();
                parm.put("id", idtxt);
                parm.put("password", pwtxt);
                parm.put("mob", "test");
                if(deviceToken != null)
                    parm.put("deviceid", deviceToken);

                try {
                    JSONObject result = getPOST("http://192.168.0.16:8090/messenger/login", parm);

                    if (result == null) {
                        alertBuilder
                                .setTitle("알림")
                                .setMessage("알수없는오류")
                                .setCancelable(true)
                                .setPositiveButton("확인", null);
                    } else {
                        if (result.getInt("code") == 200) {
                            SharedPreferences.Editor autoLogin = auto.edit();
                            autoLogin.putString("id", idtxt);
                            autoLogin.putString("pw", pwtxt);
                            autoLogin.commit();

                            alertBuilder
                                    .setTitle("알림")
                                    .setMessage("성공")
                                    .setCancelable(true)
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(getApplicationContext(), main.class);
                                            intent.putExtra("id", idtxt);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }
                                    });
                        } else {
                            alertBuilder
                                    .setTitle("알림")
                                    .setMessage("실패")
                                    .setCancelable(true)
                                    .setPositiveButton("확인", null);
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog dialog = alertBuilder.create();
                            dialog.show();
                        }
                    });
                } catch (Exception e) {

                }

            }
        });
    }

}
