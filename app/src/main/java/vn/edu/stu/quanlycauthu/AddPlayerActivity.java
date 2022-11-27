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
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

import vn.edu.stu.quanlycauthu.adapter.ClubAdapter;
import vn.edu.stu.quanlycauthu.model.Club;
import vn.edu.stu.quanlycauthu.model.Player;
import vn.edu.stu.quanlycauthu.sqlite.ClubDao;
import vn.edu.stu.quanlycauthu.sqlite.PlayerDao;
import vn.edu.stu.quanlycauthu.util.FormatUtil;

public class AddPlayerActivity extends BaseActivity {
    EditText etPlayerID, etPlayerName, etPlayerDob;
    RadioButton radPlayerPostionGK, radPlayerPostionDF, radPlayerPostionMF, radPlayerPostionCF;


    Calendar calendar;
    ImageButton btnDatePicker;
    Spinner spClub;
    private Button btnCancel, btnAddPlayer, btnAddImage;
    private ImageView imgImage;
    List<Club> clubList;

    public static final int REQUEST_CHOOSE_PHOTO = 321;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);

        addControls();
        addEvents();

        hienThiClub();
    }

    private void hienThiClub() {
        ClubDao dao = new ClubDao(this);
        clubList = dao.getAll();
        ClubAdapter clubAdapter = new ClubAdapter(this, clubList);
        spClub.setAdapter(clubAdapter);
    }

    private void addControls() {
        etPlayerID = findViewById(R.id.etPlayerID);
        etPlayerID.setEnabled(false);
        etPlayerName = findViewById(R.id.etPlayerName);
        etPlayerDob = findViewById(R.id.etPlayerDob);

        radPlayerPostionGK = findViewById(R.id.radPlayerPostionGK);
        radPlayerPostionDF = findViewById(R.id.radPlayerPostionDF);
        radPlayerPostionMF = findViewById(R.id.radPlayerPostionMF);
        radPlayerPostionCF = findViewById(R.id.radPlayerPostionCF);

        btnAddImage = findViewById(R.id.btnAddImage);
        imgImage = findViewById(R.id.imgImage);

        etPlayerDob = findViewById(R.id.etPlayerDob);
        calendar = Calendar.getInstance();
        btnDatePicker = findViewById(R.id.btnDatePicker);

        btnAddPlayer = findViewById(R.id.btnAddPlayer);
        btnCancel = findViewById(R.id.btnCancel);

        spClub = findViewById(R.id.spClub);
    }

    private void addEvents() {
        btnAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyThem();
            }
        });
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyChonNgay();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyHuy();
            }
        });
        btnAddImage.setOnClickListener(new View.OnClickListener() {
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

    private void xuLyHuy() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
    }

    private void xuLyThem() {
        PlayerDao dao = new PlayerDao(this);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imgImage.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
        byte[] image = byteArray.toByteArray();
        try {
            Player player = new Player();
            player.setName(etPlayerName.getText().toString());
            player.setDob(FormatUtil.toDate(etPlayerDob.getText().toString()));

            if (radPlayerPostionGK.isChecked()) {
                player.setPostion(radPlayerPostionGK.getText().toString());
            } else if (radPlayerPostionDF.isChecked()) {
                player.setPostion(radPlayerPostionDF.getText().toString());
            } else if (radPlayerPostionMF.isChecked()) {
                player.setPostion(radPlayerPostionMF.getText().toString());
            } else {
                player.setPostion(radPlayerPostionCF.getText().toString());
            }
            player.setImage(image);

            Club clubs = (Club) spClub.getSelectedItem();
            player.setIdClub(clubs.getId());
            player.setNameClub(clubs.getName());

            dao.insert(player);

            Toast.makeText(this, "Thêm cầu thủ thành công!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent();
            intent.putExtra("KEY_PLAYER_ADD", "OK");
            setResult(Activity.RESULT_OK, intent);
            finish();

        }catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(this,"Error: " + ex.getMessage()
            , Toast.LENGTH_SHORT).show();
        }
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