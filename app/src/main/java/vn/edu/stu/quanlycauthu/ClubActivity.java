package vn.edu.stu.quanlycauthu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import vn.edu.stu.quanlycauthu.adapter.ClubAdapter;
import vn.edu.stu.quanlycauthu.dialog.NewClubDialog;
import vn.edu.stu.quanlycauthu.model.Club;
import vn.edu.stu.quanlycauthu.sqlite.ClubDao;

public class ClubActivity extends BaseActivity {
    FloatingActionButton fabAddClub;
    private ListView lvClub;
    private List<Club> list;

    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    private static final int SECOND_ACTIVITY_REQUEST_CODE_ADD = 0;

    private ClubAdapter clubAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club);

        addControls();
        addEvents();
    }

    private void addControls() {
        lvClub = findViewById(R.id.lvClub);
        fabAddClub = findViewById(R.id.fabAddClub);
        fillClubListView();
        lvClub.setAdapter(clubAdapter);

        fillClubListView();
    }


    private void fillClubListView() {
        ClubDao dao = new ClubDao(this);
        list = dao.getAll();

        clubAdapter = new ClubAdapter(this, list);
        lvClub.setAdapter(clubAdapter);
        clubAdapter.notifyDataSetChanged();
    }


    private void addEvents() {
        fabAddClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyThem();
            }
        });
        lvClub.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                xuLyChon2(position);
            }
        });
    }

    private void xuLyThem() {
        Intent intent = new Intent(this, AddClubActivity.class);
        startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE_ADD);
    }

    private void xuLyChon2(int position) {
        Club club = list.get(position);

        Intent intent = new Intent(this, EditClubActivity.class);
        intent.putExtra("KEY_CLUB",club);
        startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE){
            if (resultCode == RESULT_OK) {
                fillClubListView();
            }
        }

        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE_ADD){
            if (resultCode == RESULT_OK) {
                fillClubListView();
            }
        }


    }
}