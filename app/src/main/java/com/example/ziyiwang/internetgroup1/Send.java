package com.example.ziyiwang.internetgroup1;

import android.Manifest;
import android.content.Context;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

public class Send extends AppCompatActivity {


    private static final int REQUEST_LOCATION=1;
    private Button btn2;

    private Button btn4;
    private Button btn5;
    private Button btn6;
    private String str1;
    private String str2;
    private EditText et1;
    private EditText et2;
    public String ip;
    public String clientId;
    public MqttAndroidClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        et1=(EditText) findViewById(R.id.editText1);
        et2=(EditText) findViewById(R.id.editText2);

        clientId = MqttClient.generateClientId();
        ip="tcp://broker.hivemq.com:1883";
        //set up the ip address
        client = new MqttAndroidClient(Send.this, ip, clientId);
        btn2= (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //btn2 is to connect

                try {
                    IMqttToken token = client.connect();
                    token.setActionCallback(new IMqttActionListener() {
                        @Override
                        public void onSuccess(IMqttToken asyncActionToken) {
                            // We are connected
                            Toast.makeText(Send.this, "Connected", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                            // Something went wrong e.g. connection timeout or firewall problems
                            Toast.makeText(Send.this, "Not connected", Toast.LENGTH_SHORT).show();

                        }
                    });
                } catch (MqttException e) {
                    e.printStackTrace();
                }


            }
        });
        btn4= (Button) findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //btn4 is to send

                String topic = "foo";
                if (str2 != null)
                    topic = str2;
                String payload = NfcActivity.mTagText +","+Barcode_acitivity.barcode;
                byte[] encodedPayload = new byte[0];
                try {
                    encodedPayload = payload.getBytes("UTF-8");
                    MqttMessage message = new MqttMessage(encodedPayload);
                    message.setRetained(true);
                    client.publish(topic, message);
                    Toast.makeText(Send.this, "Pushed", Toast.LENGTH_SHORT).show();
                } catch (UnsupportedEncodingException | MqttException e) {
                    e.printStackTrace();
                }

            }

        });




        btn5= (Button) findViewById(R.id.btn5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //btn5 is to enable user to set ip
                str1= et1.getText().toString();
                if(str1!=null)
                    ip =str1;
                client = new MqttAndroidClient(Send.this, ip, clientId);
            }
        });
        btn6= (Button) findViewById(R.id.btn6);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str2=et2.getText().toString();
            }
        });
        //btn6 is for user to set topic
    }


}
