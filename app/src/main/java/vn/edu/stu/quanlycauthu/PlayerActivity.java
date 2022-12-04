package vn.edu.stu.quanlycauthu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import vn.edu.stu.quanlycauthu.adapter.ClubAdapter;
import vn.edu.stu.quanlycauthu.adapter.PlayerAdapter;
import vn.edu.stu.quanlycauthu.model.Club;
import vn.edu.stu.quanlycauthu.model.Player;
import vn.edu.stu.quanlycauthu.sqlite.ClubDao;
import vn.edu.stu.quanlycauthu.sqlite.PlayerDao;
import vn.edu.stu.quanlycauthu.util.FormatUtil;

public class PlayerActivity extends BaseActivity {
    FloatingActionButton fabAddPlayer;
    private List<Player> playerList;
    ListView lvPlayer;
    private PlayerAdapter playerAdapter;

    private static final int ADD_ACTIVITY_REQUEST_CODE_ADD = 0;
    private static final int ADD_ACTIVITY_REQUEST_CODE_DEL = 0;
    private static final int ADD_ACTIVITY_REQUEST_CODE_EDIT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        addControls();
        addEvents();
    }

    private void addControls() {
        lvPlayer = findViewById(R.id.lvPlayer);
        fabAddPlayer = findViewById(R.id.fabAddPlayer);

        fillPlayerToListView();
    }

    private void fillPlayerToListView() {
        PlayerDao dao = new PlayerDao(this);
        try {
            playerList = dao.getAll();
            playerAdapter = new PlayerAdapter(this, playerList);
            lvPlayer.setAdapter(playerAdapter);
        } catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(this,"Error: " + ex.getMessage()
                    , Toast.LENGTH_SHORT).show();
        }


    }

    private void addEvents() {
        fabAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyThem();
            }
        });
        lvPlayer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                xuLyChon(position);
            }
        });
    }

    private void xuLyThem() {
        Intent intent = new Intent(PlayerActivity.this, AddPlayerActivity.class);
        startActivityForResult(intent, ADD_ACTIVITY_REQUEST_CODE_ADD);
    }

    private void xuLyChon(int position) {
        Player player = playerList.get(position);
        Intent intent = new Intent(this, PlayerDetailActivity.class);
        intent.putExtra("KEY_PLAYER", player);
        startActivityForResult(intent, ADD_ACTIVITY_REQUEST_CODE_DEL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_ACTIVITY_REQUEST_CODE_ADD){
            if (resultCode == RESULT_OK) {
                fillPlayerToListView();
            }
        }

        if (requestCode == ADD_ACTIVITY_REQUEST_CODE_DEL){
            if (resultCode == RESULT_OK) {
                fillPlayerToListView();
            }
        }

        if (requestCode == ADD_ACTIVITY_REQUEST_CODE_EDIT){
            if (resultCode == RESULT_OK) {
                fillPlayerToListView();
            }
        }
    }
}