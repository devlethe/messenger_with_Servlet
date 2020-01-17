package com.test.messeger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import java.util.List;

public class Chat extends AppCompatActivity {
    private WebSocketClient socket;
    List<String> list;
    Button sendbtn;
    EditText sendMsg;
    ListView msgList;
    String myid, friendid, friendnickname;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        sendbtn = findViewById(R.id.chatsendbtn);
        sendMsg = findViewById(R.id.chatingmsg);
        msgList = findViewById(R.id.chatinglist);
        Intent intent = getIntent();
        myid = intent.getExtras().getString("id", "NULL");
        friendid = intent.getExtras().getString("user", "NULL");
        friendnickname = intent.getExtras().getString("nickname", "NULL");
        ((TextView) findViewById(R.id.chatusertxt)).setText(friendnickname + "(" + friendid + ") 님과의 대화");
        list = new ArrayList<>();

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, list);

        msgList.setAdapter(adapter);

        msgList.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        sendbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String data = sendMsg.getText().toString();
                Log.w("NETWORK", data);
                if (data != null && !data.isEmpty()) {
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            socket.send(data);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        list.add("나 : " + data);
                                        msgList.setAdapter(adapter);
                                        msgList.setSelection(adapter.getCount() - 1);
                                    } catch (Exception e) {
                                    }
                                }
                            });
                        }
                    });

                }
            }
        });
        connectWebSocket();

    }

    private void connectWebSocket() {
        URI uri;
        try {
            uri = new URI("ws://192.168.0.16:8090/messenger/webchat?id=" + myid + "&person=" + friendid);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        socket = new WebSocketClient(uri, new Draft_17()) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Opened");
            }

            @Override
            public void onMessage(String s) {
                final String message = s;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject obj = new JSONObject(message);
                            String type = obj.getString("from");
                            list.add( (type.compareTo(myid) == 0? "나" : type   )  + " : " + obj.getString("msg"));
                            msgList.setAdapter(adapter);
                            msgList.setSelection(adapter.getCount() - 1);
                        }catch(Exception e){}
                    }
                });
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("Websocket", "Closed " + s);
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };
        socket.connect();
    }

    @Override
    public void finish() {
        socket.close();
        super.finish();
    }
}
