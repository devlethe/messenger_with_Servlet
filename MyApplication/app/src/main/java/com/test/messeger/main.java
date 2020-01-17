package com.test.messeger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.test.messeger.Web.getGET;
import static com.test.messeger.Web.getPOST;

public class main extends AppCompatActivity {

    ListView listview;
    ListViewAdapter adapter;
    String myid;
    LinearLayout profile;
    TextView username, usernickname, userstauts, userids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        profile = findViewById(R.id.profile);
        username = findViewById(R.id.username);
        usernickname = findViewById(R.id.usernickname);
        userstauts = findViewById(R.id.userstauts);
        userids = findViewById(R.id.useridinfo);


        listview = findViewById(R.id.listview1);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                UserItemView item = (UserItemView) parent.getItemAtPosition(position) ;
                String userid = item.getId();
                String usernickname = item.getNickname();
                String username = item.getName();
                Log.d("userdata", userid + "/" + usernickname + "/" + username);
                Intent intent = new Intent(getApplicationContext(), Chat.class);
                intent.putExtra("id", myid);
                intent.putExtra("user", username);
                intent.putExtra("nickname", usernickname);
                startActivity(intent);

            }
        }) ;

        Intent intent = getIntent();
        myid = intent.getExtras().getString("id", "NULL");
        if (myid != "NULL") {
            getProfile();
            getFriendList();
        } else {
            Toast.makeText(getApplicationContext(), "에러", Toast.LENGTH_LONG).show();
            finish();
        }

        findViewById(R.id.refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getProfile();
                getFriendList();
            }
        });

        findViewById(R.id.friendadd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), FriendList.class);

                intent.putExtra("id", myid);
                startActivity(intent);

            }
        });

        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        Map<String, String> parm = new HashMap<String, String>();
                        parm.put("id", myid);
                        try {
                            JSONObject result = getPOST("http://192.168.0.16:8090/messenger/logout", parm);
                            if (result != null && result.getInt("code") == 200) {
                                SharedPreferences pref = getSharedPreferences("auto", MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.clear();
                                editor.commit();

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        } catch (Exception e) {
                        }
                    }
                });
            }
        });

    }

    private void getFriendList() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final Map<String, String> parm = new HashMap<String, String>();
                parm.put("id", myid);
                try {
                    JSONObject result = getGET("http://192.168.0.16:8090/messenger/friend", parm);
                    final JSONArray jar = result.getJSONArray("userdata");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                adapter = new ListViewAdapter();
                                for (int i = 0; i < jar.length(); i++) {
                                    JSONObject jo = jar.getJSONObject(i);
                                    adapter.addItem(jo.getString("f_name"), jo.getString("f_nickname"), jo.getString("f_status"), jo.getString("f_user"));
                                }

                                listview.setAdapter(adapter);
                            } catch (Exception e) {
                            }
                        }
                    });
                } catch (Exception e) {
                }
            }
        });
    }

    private void getProfile() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Map<String, String> parm = new HashMap<String, String>();
                parm.put("id", myid);
                try {
                    final JSONObject result = getGET("http://192.168.0.16:8090/messenger/profile", parm);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject res = result.getJSONObject("info");
                                username.setText(res.getString("name"));
                                usernickname.setText(res.getString("nickname"));
                                userstauts.setText(res.getString("status"));
                                userids.setText(res.getString("user"));
                            } catch (Exception e) {
                            }
                        }
                    });
                } catch (Exception e) {
                }
            }
        });
    }
}
