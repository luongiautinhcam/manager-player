package vn.edu.stu.quanlycauthu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import vn.edu.stu.quanlycauthu.model.Club;
import vn.edu.stu.quanlycauthu.sqlite.ClubDao;

public class AddClubActivity extends BaseActivity {
    EditText etClubID, etClubName;
    Button btnAdd, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_club);

        addControls();
        addEvents();
    }

    private void addControls() {
        etClubID = findViewById(R.id.etClubID);
        etClubName = findViewById(R.id.etClubName);

        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void addEvents() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyThem();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyHuy();
            }
        });
    }

    private void xuLyHuy() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
    }

    private void xuLyThem() {
        Club club = new Club();
        club.setName(etClubName.getText().toString());
        club.setId(etClubID.getText().toString());
        ClubDao dao = new ClubDao(this);
        dao.insert(club);
        Toast.makeText(this,"Thêm thành công",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra("KEY_ADD_CLUB", "OK");
        setResult(Activity.RESULT_OK, intent);

        this.finish();
    }
}