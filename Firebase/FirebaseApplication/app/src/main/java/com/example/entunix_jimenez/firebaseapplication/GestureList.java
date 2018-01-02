package com.example.entunix_jimenez.firebaseapplication;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mpjimenez on 1/3/2018.
 */

public class GestureList extends ArrayAdapter<GestureDetails> {
    private Activity context;
    private List<GestureDetails> gestureList;

    public GestureList(Activity context, List<GestureDetails> gestureList){
        super(context, R.layout.list_layout, gestureList);
        this.context = context;
        this.gestureList = gestureList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView tv_attemptID = listViewItem.findViewById(R.id.tv_attemptID);
        TextView tv_gesture_x = listViewItem.findViewById(R.id.tv_gesture_x);
        TextView tv_gesture_y = listViewItem.findViewById(R.id.tv_gesture_y);
        TextView tv_gesture_sX = listViewItem.findViewById(R.id.tv_gesture_sX);
        TextView tv_gesture_sY = listViewItem.findViewById(R.id.tv_gesture_sY);
        TextView tv_gesture_fX = listViewItem.findViewById(R.id.tv_gesture_fX);
        TextView tv_gesture_fY = listViewItem.findViewById(R.id.tv_gesture_fY);
        TextView tv_gesture_totalTime = listViewItem.findViewById(R.id.tv_gesture_totalTime);
        TextView tv_gesture_myName = listViewItem.findViewById(R.id.tv_gesture_myName);
        TextView tv_gesture_devName = listViewItem.findViewById(R.id.tv_gesture_devName);
        TextView tv_gesture_devMan = listViewItem.findViewById(R.id.tv_gesture_devMan);
        TextView tv_gesture_sTap = listViewItem.findViewById(R.id.tv_gesture_sTap);
        TextView tv_gesture_dTap = listViewItem.findViewById(R.id.tv_gesture_dTap);
        TextView tv_gesture_lPress = listViewItem.findViewById(R.id.tv_gesture_lPress);
        TextView tv_gesture_scroll = listViewItem.findViewById(R.id.tv_gesture_scroll);
        TextView tv_gesture_fling = listViewItem.findViewById(R.id.tv_gesture_fling);
        TextView tv_gesture_swX = listViewItem.findViewById(R.id.tv_gesture_swX);
        TextView tv_gesture_swY = listViewItem.findViewById(R.id.tv_gesture_swY);

        return listViewItem;
    }
}
