package vn.edu.stu.quanlycauthu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends BaseActivity {
    Button btnPlayer, btnClub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
    }

    private void addControls() {
        btnPlayer = findViewById(R.id.btnPlayer);
        btnClub = findViewById(R.id.btnClub);
    }

    private void addEvents() {
        btnPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyPlayer();
            }
        });
        btnClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyClub();
            }
        });
    }

    private void xuLyClub() {
        Intent intent = new Intent(MainActivity.this, ClubActivity.class);
        startActivity(intent);
    }

    private void xuLyPlayer() {
        Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
        startActivity(intent);
    }
}