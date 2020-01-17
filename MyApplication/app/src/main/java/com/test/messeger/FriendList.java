package com.test.messeger;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.test.messeger.Web.getGET;
import static com.test.messeger.Web.getPOST;

public class FriendList extends AppCompatActivity {

    Button flbtn;
    EditText fltxt;
    ListView fllist;
    ListViewAdapter adapter;
    String myid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);


        fllist = findViewById(R.id.listview1);

        Intent intent = getIntent();
        myid = intent.getExtras().getString("id", "NULL");


        if (myid == "NULL") {
            Toast.makeText(getApplicationContext(), "에러", Toast.LENGTH_LONG).show();
            finish();
        }

        flbtn = findViewById(R.id.friendsearchbtn);
        fllist = findViewById(R.id.friendsearchlist);
        fltxt = findViewById(R.id.friendsearch);

        flbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fltxt.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "검색하실 닉네임을 넣어주세요", Toast.LENGTH_LONG).show();
                } else {

                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            final Map<String, String> parm = new HashMap<String, String>();
                            parm.put("nickname", fltxt.getText().toString());
                            parm.put("id", myid);
                            try {
                                JSONObject result = getGET("http://192.168.0.16:8090/messenger/find", parm);
                                final JSONArray jar = result.getJSONArray("userdata");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            adapter = new ListViewAdapter();
                                            for (int i = 0; i < jar.length(); i++) {
                                                JSONObject jo = jar.getJSONObject(i);
                                                adapter.addItem(jo.getString("name"), jo.getString("nickname"), jo.getString("status"), jo.getString("user"));
                                            }

                                            fllist.setAdapter(adapter);
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
        });

        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        fllist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                UserItemView item = (UserItemView) parent.getItemAtPosition(position);
                final String userid = item.getId();
                final String usernickname = item.getNickname();
                final String username = item.getName();
                Log.d("userdata", userid + "/" + usernickname + "/" + username);

                alertBuilder
                        .setTitle("친구추가")
                        .setMessage(usernickname + "(" + username + ")님을 친구 추가하시겠습니까?")
                        .setCancelable(true)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                AsyncTask.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        Map<String, String> parm = new HashMap<String, String>();
                                        parm.put("user", myid);
                                        parm.put("friend", username);
                                        parm.put("type", "add");
                                        try {
                                            JSONObject result = getPOST("http://192.168.0.16:8090/messenger/friend", parm);
                                            if (result != null && result.getInt("code") == 200) {
                                                Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(getApplicationContext(), "에러", Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (Exception e) {
                                        }
                                    }
                                });

                            }
                        })
                        .setNegativeButton("취소", null);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog dialog = alertBuilder.create();
                        dialog.show();

                    }
                });

            }
        });


        /*
        adapter = new ListViewAdapter();
        adapter.addItem(jo.getString("f_name"), jo.getString("f_nickname"), jo.getString("f_status"), jo.getString("f_user"));
        fllist.setAdapter(adapter);*/
    }
}
