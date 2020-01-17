package com.test.messeger;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.test.messeger.Web.getGET;
import static com.test.messeger.Web.getPOST;

public class register extends AppCompatActivity {
    Button checkid, submit;
    EditText id, pw, nickname, name, status;
    TextView ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        checkid = findViewById(R.id.idcheck);
        submit = findViewById(R.id.submit);
        id = findViewById(R.id.idtext);
        ids = findViewById(R.id.idtextss);
        pw = findViewById(R.id.pwtext);
        nickname = findViewById(R.id.nicknametext);
        name = findViewById(R.id.nametext);
        status = findViewById(R.id.statustext);


        checkid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        Map<String, String> parm = new HashMap<String, String>();
                        parm.put("id", id.getText().toString());
                        JSONObject result = getGET("http://192.168.0.16:8090/messenger/checkID", parm);
                        try {
                            if (result != null && result.getInt("code") == 200) {
                                ids.setText("사용할 수 있는 아이디입니다.");
                                id.setFocusable(false);
                                id.setClickable(false);
                            } else {
                                ids.setText("아이디가 중복됩니다.");
                            }
                        } catch (Exception e) {

                        }
                    }
                });
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        Map<String, String> parm = new HashMap<String, String>();
                        parm.put("id", id.getText().toString());
                        parm.put("password", pw.getText().toString());
                        parm.put("nickname", nickname.getText().toString());
                        parm.put("name", name.getText().toString());
                        parm.put("status", status.getText().toString());
                        parm.put("mob", "yes");
                        JSONObject result = getPOST("http://192.168.0.16:8090/messenger/register", parm);
                        try {
                            if (result != null && result.getInt("code") == 200) {
                                alertBuilder
                                        .setTitle("알림")
                                        .setMessage("성공")
                                        .setCancelable(true)
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        });

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
        });
    }
}
