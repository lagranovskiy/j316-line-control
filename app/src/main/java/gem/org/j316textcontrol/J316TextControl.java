package gem.org.j316textcontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.os.StrictMode;

public class J316TextControl extends AppCompatActivity {

    private RowCommunicator rowCommunicator = new RowCommunicator();
    private Button sendBtn1;
    private Button sendBtn2;
    private Button sendBtn3;
    private Button resetBtn;

    private EditText ipTxt;
    private EditText portTxt;

    private TextView statusTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_j316_text_control);

        this.sendBtn1 = findViewById(R.id.sendTxtBtn1);
        this.sendBtn2 = findViewById(R.id.sendTxtBtn2);
        this.sendBtn3 = findViewById(R.id.sendTxtBtn3);

        this.resetBtn = findViewById(R.id.sendClearBtn);

        this.ipTxt = findViewById(R.id.sendIP);
        this.portTxt = findViewById(R.id.sendPort);

        this.statusTxt = findViewById(R.id.statusTxt);

        this.sendBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = ((EditText) findViewById(R.id.msgTxt1)).getText().toString();
                statusTxt.setText("\n Sending: " + text);
                String protokoll = rowCommunicator.sendTxt(ipTxt.getText().toString(), Integer.parseInt(portTxt.getText().toString()), text);
                statusTxt.append(protokoll);
            }
        });

        this.sendBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = ((EditText) findViewById(R.id.msgTxt2)).getText().toString();
                statusTxt.setText("\n Sending: " + text);
                String protokoll = rowCommunicator.sendTxt(ipTxt.getText().toString(), Integer.parseInt(portTxt.getText().toString()), text);
                statusTxt.append(protokoll);
            }
        });

        this.sendBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = ((EditText) findViewById(R.id.msgTxt3)).getText().toString();
                statusTxt.setText("\n Sending: " + text);
                String protokoll = rowCommunicator.sendTxt(ipTxt.getText().toString(), Integer.parseInt(portTxt.getText().toString()), text);
                statusTxt.append(protokoll);
            }
        });

        this.resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusTxt.setText("\n Clearing");
                String protokoll = rowCommunicator.clear(ipTxt.getText().toString(), Integer.parseInt(portTxt.getText().toString()));
                statusTxt.append(protokoll);
            }
        });
    }
}
