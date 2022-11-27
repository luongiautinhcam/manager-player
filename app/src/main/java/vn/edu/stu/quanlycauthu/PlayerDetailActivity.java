package vn.edu.stu.quanlycauthu;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;

import java.util.List;

import vn.edu.stu.quanlycauthu.adapter.PlayerAdapter;
import vn.edu.stu.quanlycauthu.model.Player;
import vn.edu.stu.quanlycauthu.sqlite.ClubDao;
import vn.edu.stu.quanlycauthu.sqlite.DbHelper;
import vn.edu.stu.quanlycauthu.sqlite.PlayerDao;
import vn.edu.stu.quanlycauthu.util.FormatUtil;

public class PlayerDetailActivity extends BaseActivity {
    DbHelper dbHelper;
    TextView tvPlayerID, tvPlayerName, tvPlayerDob, tvPlayerPostion, tvClub;
    ImageView imgPlayer;
    Button btnDelete, btnEdit;

    private static final int ADD_ACTIVITY_REQUEST_CODE_EDIT = 0;

    private List<Player> listPlayer;
    private PlayerAdapter playerAdapter;
    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_detail);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        addControls();
        addEvents();
    }

    private void addControls() {
        tvPlayerID = findViewById(R.id.tvPlayerID);
        tvPlayerName = findViewById(R.id.tvPlayerName);
        tvPlayerDob = findViewById(R.id.tvPlayerDob);
        tvPlayerPostion = findViewById(R.id.tvPlayerPostion);
        tvClub = findViewById(R.id.tvClub);
        imgPlayer = findViewById(R.id.imgPlayer);
        btnDelete = findViewById(R.id.btnDelete);
        btnEdit = findViewById(R.id.btnEdit);


        hienThiPlayer();
        setTitle("Chi tiết cầu thủ " + player.getName().toUpperCase());

    }

    private void hienThiPlayer() {
        dbHelper = new DbHelper(this);
        Intent intent = getIntent();
        if (intent != null) {
            player = (Player) intent.getSerializableExtra("KEY_PLAYER");
            if (player != null) {
                imgPlayer.setImageResource(R.drawable.ic_avatar_ronaldo);
                tvPlayerID.setText("" + player.getId());
                tvPlayerName.setText(player.getName());
                tvPlayerDob.setText(FormatUtil.toString(player.getDob()));
                tvPlayerPostion.setText(player.getPostion());
                tvClub.setText(player.getNameClub());
                byte[] image = player.getImage();
                Bitmap bitmap = BitmapFactory.decodeByteArray(image,0 ,image.length);
                imgPlayer.setImageBitmap(bitmap);

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addEvents() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyXoa();
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLySua();
            }
        });
    }

    private void xuLySua() {
        dbHelper = new DbHelper(this);
        Intent intent = getIntent();
        player = (Player) intent.getSerializableExtra("KEY_PLAYER");
        Intent intents = new Intent(PlayerDetailActivity.this, EditPlayerActivity.class);
        intents.putExtra("PLAYER_EDIT", player);
        startActivityForResult(intents, ADD_ACTIVITY_REQUEST_CODE_EDIT);
    }

    private void xuLyXoa() {
        final CharSequence[] items = {"Có", "Không"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Bạn có chắc chắn muốn xóa cầu thủ ?");
        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    PlayerDao dao = new PlayerDao(PlayerDetailActivity.this);
                    dao.delete(""+ player.getId());
                    Toast.makeText(PlayerDetailActivity.this, "Xoá thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("KEY_PLAYER", "OK");
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
                if (which == 1) {

                }
            }
        });
        dialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_ACTIVITY_REQUEST_CODE_EDIT){
            if (resultCode == RESULT_OK) {
                hienThiPlayer();
            }
        }
    }
}