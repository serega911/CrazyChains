package com.example.crazychains;

import android.graphics.Color;
import android.widget.TextView;

public class CC_CellView implements CC_I_CellView {

    private TextView view;

    public CC_CellView(TextView view){
        this.view = view;
    }

    @Override
    public void setNum(Integer num) {
        view.setText(num.toString());
    }

    @Override
    public void setColor(Integer color) {
        view.setTextColor(color);
    }

    @Override
    public void select(boolean selected) {
        if (selected) {
            view.setShadowLayer(50, 0, 0, Color.GRAY);
        } else {
            view.setShadowLayer(0, 0, 0, Color.GRAY);
        }
    }

    public TextView getView() {
        return view;
    }
}
