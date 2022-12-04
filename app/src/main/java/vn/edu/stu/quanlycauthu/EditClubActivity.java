package vn.edu.stu.quanlycauthu;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.List;

import vn.edu.stu.quanlycauthu.adapter.ClubAdapter;
import vn.edu.stu.quanlycauthu.model.Club;
import vn.edu.stu.quanlycauthu.sqlite.ClubDao;
import vn.edu.stu.quanlycauthu.sqlite.DbHelper;
import vn.edu.stu.quanlycauthu.sqlite.PlayerDao;

public class EditClubActivity extends BaseActivity {
    DbHelper dbHelper;
    private EditText etClubID, etClubName;
    private Button btnCancel, btnSaveEdit, btnDelete;

    private List<Club> list;
    private ClubAdapter clubAdapter;
    Club club;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_club);
        
        addControls();
        addEvents();
    }

    private void addControls() {
        etClubID = findViewById(R.id.etClubID);
        etClubName = findViewById(R.id.etClubName);
        btnCancel = findViewById(R.id.btnCancel);
        btnSaveEdit = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);

        hiethiClub();
    }

    private void hiethiClub() {
        dbHelper = new DbHelper(this);
        Intent intent = getIntent();
        if (intent != null) {
            club = (Club) intent.getSerializableExtra("KEY_CLUB");
            if (club != null) {
                etClubID.setText(club.getId());
                etClubName.setText(club.getName());
            }
        }
    }

    private void addEvents() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyHuy();
            }
        });
        btnSaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyLuu();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyXoa();
            }
        });
    }

    private void xuLyXoa() {
        final CharSequence[] items = {"Có", "Không"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Bạn có chắc chắn muốn xóa đội bóng ?");
        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    String id = etClubID.getText().toString().trim();
                    PlayerDao daoplayer = new PlayerDao(EditClubActivity.this);
                    ClubDao dao = new ClubDao(EditClubActivity.this);
                    dao.delete("" + club.getId());
                    Toast.makeText(EditClubActivity.this, "Xoá thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("KEY_CLUB", "OK");
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
                if (which == 1) {

                }
            }
        });
        dialog.show();
    }

    private void xuLyLuu() {
        String ma = etClubID.getText().toString().trim();
        String ten = etClubName.getText().toString().trim();
        if (ma.isEmpty() || ten.isEmpty()) {
            Toast.makeText(this, "Vui long nhap du lieu", Toast.LENGTH_SHORT).show();
            return;
        }

        club.setId(ma);
        club.setName(ten);
        ClubDao dao = new ClubDao(this);
        if (dao != null) {
            if (dao.updateClub(club) > 0) {
                Toast.makeText(this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("KEY_CLUB", "OK");
                setResult(Activity.RESULT_OK, intent);
                this.finish();
            }else {
                Toast.makeText(this, "Sua that bai", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void xuLyHuy() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
    }

}