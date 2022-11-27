package vn.edu.stu.quanlycauthu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import vn.edu.stu.quanlycauthu.BaseActivity;
import vn.edu.stu.quanlycauthu.R;
import vn.edu.stu.quanlycauthu.model.Club;

public class ClubAdapter extends BaseAdapter {
    private Context context;
    private List<Club> list;

    public ClubAdapter(Context context, List<Club> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.layout_club_item, null);
        }

        TextView tvId = view.findViewById(R.id.tvClubId);
        TextView tvName = view.findViewById(R.id.tvName);

        Club club = list.get(i);
        tvId.setText("" + club.getId());
        tvName.setText("" + club.getName());
        return view;
    }
}
