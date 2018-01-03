package pbl.latestgestures;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ListView extends ArrayAdapter<MainActivity> {

     private Activity context;
     private List<MainActivity> singleTapList;

     public ListView(Activity context, List<MainActivity> singleTapList) {
         super(context, R.layout.list_layout, singleTapList);
         this.context = context;
         this.singleTapList = singleTapList;
     }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       LayoutInflater inflater = context.getLayoutInflater();

       View listViewItem = inflater.inflate(R.layout.list_layout, null, true);


        TextView textX = (TextView) listViewItem.findViewById(R.id.textX);
        TextView textY = (TextView) listViewItem.findViewById(R.id.textY);

        MainActivity mainActivity = singleTapList.get(position);
        textX.setText((int) mainActivity.getX());
        textY.setText((int) mainActivity.getY());

        return listViewItem;
    }
}
