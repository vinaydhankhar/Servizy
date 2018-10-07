package kvnb.hostelservicemanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button a;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        a=(Button)findViewById(R.id.button);
        textView=(TextView)findViewById(R.id.register);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(view);

            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(v);
            }
        });
    }
    public  void sendMessage(View view){
        Intent intent =new Intent(this,Main2Activity.class);
        startActivity(intent);
    }
    public void register(View v){
        Intent intent =new Intent(this,Register.class);
        startActivity(intent);
    }
}
