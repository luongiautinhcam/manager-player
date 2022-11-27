package vn.edu.stu.quanlycauthu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import vn.edu.stu.quanlycauthu.ClubActivity;
import vn.edu.stu.quanlycauthu.R;
import vn.edu.stu.quanlycauthu.model.Club;
import vn.edu.stu.quanlycauthu.sqlite.ClubDao;

public class NewClubDialog extends Dialog {
    private Context context;
    EditText etClubID, etClubName;
    Button btnSave, btnClose;

    public NewClubDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_new_club);

        addControls();
        addEvents();
    }

    private void addControls() {
        etClubID = findViewById(R.id.etClubID);
        etClubName = findViewById(R.id.etClubName);
        btnSave = findViewById(R.id.btnSave);
        btnClose = findViewById(R.id.btnClose);

    }

    private void addEvents() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyThem();
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyThoat();
            }
        });
    }

    private void xuLyThoat() {
        dismiss();
    }

    private void xuLyThem() {
        Club club = new Club();
        club.setName(etClubName.getText().toString());
        club.setId(etClubID.getText().toString());
        ClubDao dao = new ClubDao(context);
        dao.insert(club);

        Toast.makeText(context,"Thêm thành công",Toast.LENGTH_SHORT).show();
        dao.getAll();

        dismiss();
    }


}
