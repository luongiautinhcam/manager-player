package vn.edu.stu.quanlycauthu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import vn.edu.stu.quanlycauthu.adapter.ClubAdapter;
import vn.edu.stu.quanlycauthu.model.Club;
import vn.edu.stu.quanlycauthu.model.Player;
import vn.edu.stu.quanlycauthu.sqlite.ClubDao;
import vn.edu.stu.quanlycauthu.sqlite.DbHelper;
import vn.edu.stu.quanlycauthu.sqlite.PlayerDao;
import vn.edu.stu.quanlycauthu.util.FormatUtil;

public class EditPlayerActivity extends BaseActivity {
    DbHelper dbHelper;
    EditText etPlayerID, etPlayerName, etPlayerDob, etPlayerValue;
    ImageButton btnDatePicker;
    Spinner spClub;
    Button btnChooseImage ,btnSave, btnCancel;
    ImageView imgImage;

    Player player;
    Calendar calendar;

    private ClubAdapter clubAdapter;
    private List<Player> playerList;
    private List<Club> clubList;

    public static final int REQUEST_CHOOSE_PHOTO = 321;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_player);

        addControls();
        addEvents();
    }

    private void addControls() {

        etPlayerID = findViewById(R.id.etPlayerID);
        etPlayerID.setEnabled(false);
        etPlayerName = findViewById(R.id.etPlayerName);
        etPlayerDob = findViewById(R.id.etPlayerDob);
        etPlayerValue = findViewById(R.id.etPlayerValue);
        btnDatePicker = findViewById(R.id.btnDatePicker);

        spClub = findViewById(R.id.spClub);

        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        btnChooseImage = findViewById(R.id.btnChooseImage);
        imgImage = findViewById(R.id.imgImage);

        hienThiClub();
        hienThiPlayer();

    }

    private void hienThiPlayer(){
        dbHelper = new DbHelper(this);
        Intent intent = getIntent();
        if (intent != null) {
            player = (Player) intent.getSerializableExtra("PLAYER_EDIT");
            if (player != null) {
                etPlayerID.setText("" + player.getId());
                etPlayerName.setText(player.getName());
                etPlayerDob.setText(FormatUtil.toString(player.getDob()));
                etPlayerValue.setText("" + player.getValue());

                byte[] image = player.getImage();
                Bitmap bitmap = BitmapFactory.decodeByteArray(image,0 ,image.length);
                imgImage.setImageBitmap(bitmap);
            }
        }
    }

    private void hienThiClub() {
        ClubDao dao = new ClubDao(this);
        clubList = dao.getAll();
        ClubAdapter clubAdapter = new ClubAdapter(this, clubList);
        spClub.setAdapter(clubAdapter);
    }

    private void addEvents() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyHuy();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLySua();
            }
        });
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyChonNgay();
            }
        });
        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyChonHinh();
            }
        });
    }

    private void xuLyChonHinh() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,REQUEST_CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CHOOSE_PHOTO && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgImage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void xuLySua() {
        try {
        String ten = etPlayerName.getText().toString().trim();
        String ngay = etPlayerDob.getText().toString().trim();
        Integer gia = Integer.valueOf(etPlayerValue.getText().toString().trim());

        Club clubs = (Club) spClub.getSelectedItem();

        BitmapDrawable bitmapDrawable = (BitmapDrawable) imgImage.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
        byte[] image = byteArray.toByteArray();

        if (ten.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập dữ liệu", Toast.LENGTH_SHORT).show();
            return;
        }
        player.setName(ten);
        player.setDob(FormatUtil.toDate(ngay));
        player.setValue(gia);
        player.setImage(image);
        player.setIdClub(clubs.getId());
        player.setNameClub(clubs.getName());

        PlayerDao dao = new PlayerDao(this);
        if (dao != null) {
            if (dao.updatePlayer(player) > 0) {
                Toast.makeText(this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("PLAYER_EDIT", "OK");
                setResult(PlayerActivity.RESULT_OK, intent);
                this.finish();
            }

        }
        }catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(this,"Error: " + ex.getMessage()
                    , Toast.LENGTH_SHORT).show();
        }

    }

    private void xuLyHuy() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
    }

    private void xuLyChonNgay() {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DATE, dayOfMonth);
                etPlayerDob.setText(FormatUtil.toString(
                        calendar.getTime())
                );

            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE)
        );
        datePickerDialog.show();
    }
}