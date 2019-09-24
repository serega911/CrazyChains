package com.example.crazychains;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.TextViewCompat;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CC_Game implements View.OnClickListener {

    private static Map<Integer, CC_Game> games = new HashMap<>();

    CC_Cell[][] array;
    Integer n;
    CC_Cell selected = null;
    AppCompatActivity activity;

    private CC_Game(Integer n) {
        array = new CC_Cell[n][n];
        this.n = n;
    }

    public static CC_Game getInstance(Integer n) {
        CC_Game game;
        if (games.containsKey(n))
            game = games.get(n);
        else {
            game = new CC_Game(n);
            games.put(n, game);
        }
        return game;
    }

    public void reset(Context context, AppCompatActivity activity) {
        this.activity = activity;

        ConstraintLayout constraintLayout = activity.findViewById(R.id.gameLayout);
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        constraintLayout.addView(tableLayout);

        for (Integer i = 0; i < n; i++) {
            TableRow row = new TableRow(context);
            tableLayout.addView(row);
            row.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1));

            for (Integer j = 0; j < n; j++) {
                TextView textView = new TextView(context);
                textView.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1));
                TextViewCompat.setAutoSizeTextTypeWithDefaults(textView, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                textView.setGravity(Gravity.CENTER);
                //textView.setId(ViewCompat.generateViewId());
                row.addView(textView);
                array[i][j] = new CC_Cell(new CC_CellView(textView), i + 1, toColor(j));
                textView.setOnClickListener(this);
            }
        }

        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            Integer a = rand.nextInt(n);
            Integer b = rand.nextInt(n);
            Integer c = rand.nextInt(n);
            Integer d = rand.nextInt(n);
            array[a][b].swap(array[c][d]);
        }
    }

    private Integer toColor(Integer num) {
        switch (num) {
            case 0:
                return Color.RED;
            case 1:
                return Color.GREEN;
            case 2:
                return Color.BLUE;
            case 3:
                return Color.MAGENTA;
            case 4:
                return Color.CYAN;
            default:
                return Color.BLACK;
        }
    }

    private boolean checkIfGameFinished() {
        boolean result = true;
        for (Integer i = 0; i < n; i++) {
            for (Integer j = 1; j < n; j++) {
                if (!array[i][j].checkIsNext(array[i][j - 1])) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    private CC_Cell lookupCellByTextView(TextView tableView) {
        for (Integer i = 0; i < n; i++) {
            for (Integer j = 0; j < n; j++) {
                if (((CC_CellView) array[i][j].getView()).getView() == tableView)
                    return array[i][j];
            }
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        CC_Cell cell = lookupCellByTextView((TextView) v);
        if (cell != null) {
            if (selected == null) {
                selected = cell;
                selected.getView().select(true);
            } else {
                if (selected.check(cell)) {
                    selected.swap(cell);
                }
                selected.getView().select(false);
                selected = null;
            }

            if (checkIfGameFinished()) {
                activity.finish();
            }
        }
    }
}
