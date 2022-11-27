package vn.edu.stu.quanlycauthu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AboutActivity extends AppCompatActivity {
    private static final int REQUEST_CALL =1;
    Button btnMap;
    TextView tvStudentPhoneAuthor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        addControls();
        addEvents();
    }

    private void addControls() {
        tvStudentPhoneAuthor = findViewById(R.id.tvStudentPhoneAuthor);
        btnMap = findViewById(R.id.btnMap);
    }

    private void addEvents() {
        tvStudentPhoneAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyCall();
            }
        });
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyMap();
            }
        });
    }

    private void xuLyMap() {
        Intent intent = new Intent(AboutActivity.this, MapActivity.class);
        startActivity(intent);
    }

    private void xuLyCall() {
        String number = tvStudentPhoneAuthor.getText().toString();
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(intent);
    }
}