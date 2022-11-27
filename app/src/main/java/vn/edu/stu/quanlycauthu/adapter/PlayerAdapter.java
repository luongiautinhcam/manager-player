package vn.edu.stu.quanlycauthu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.edu.stu.quanlycauthu.R;
import vn.edu.stu.quanlycauthu.model.Club;
import vn.edu.stu.quanlycauthu.model.Player;
import vn.edu.stu.quanlycauthu.util.FormatUtil;

public class PlayerAdapter extends BaseAdapter {
    private Context context;
    private List<Player> list;

    public PlayerAdapter(Context context, List<Player> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_player_item, null);
        }

        TextView tvPlayerId = view.findViewById(R.id.tvPlayerId);
        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvClub = view.findViewById(R.id.tvClub);
        ImageView imageView = view.findViewById(R.id.imageView);


        Player player = list.get(i);
        tvPlayerId.setText("" + player.getId());
        tvName.setText(player.getName());
        tvClub.setText(player.getNameClub());

        byte[] image = player.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image,0 ,image.length);
        imageView.setImageBitmap(bitmap);

        return view;
    }
}
