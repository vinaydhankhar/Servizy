package kvnb.hostelservicemanagement;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class Register extends AppCompatActivity {
    Button b3;
    ImageView im1;
    public void init(){
        b3=(Button)findViewById(R.id.button3);
        im1=(ImageView)findViewById(R.id.imageView);
        b3.setOnClickListener(new OnClickListener(){
            public void onClick(View view){
                Intent myIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(myIntent,0);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap=(Bitmap)data.getExtras().get("data");
        im1.setImageBitmap(bitmap);
    }
}
