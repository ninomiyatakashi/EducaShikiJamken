package com.educa_computer_entertainment.educashikijamken;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
    
    private static final String TAG = "MainActivity";
    
    private static final class HAND_TYPE {
        private static final int GUH = 0;
        private static final int CHOKI = 1;
        private static final int PAH = 2;
    }
    
    private static final int[] HAND_DRAWABLE = { R.drawable.guh, R.drawable.choki, R.drawable.pah };
    private static final int[] HAND_BUTTON_ID = { R.id.guh, R.id.choki, R.id.pah };

    private ImageView hand;
    private TextView result;

    private Resources res;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        res = getResources();
        
        hand = (ImageView) findViewById(R.id.enemyhand);
        result = (TextView) findViewById(R.id.result);
        
        findViewById(R.id.guh).setOnClickListener(this);
        findViewById(R.id.choki).setOnClickListener(this);
        findViewById(R.id.pah).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int enemyHandType = Math.round((float) Math.random() * 2);
        Log.d(TAG, "enemyHand : " + enemyHandType);
        
        hand.setImageResource(HAND_DRAWABLE[enemyHandType]);
        
        int pushButtonId = v.getId();
        
        int pushButtonType = 0;
        for (int itemId : HAND_BUTTON_ID) {
            if(itemId == pushButtonId) break;
            pushButtonType++;
        }
        
        if(enemyHandType == pushButtonType) {
            result.setText(res.getString(R.string.draw));
            result.setTextColor(res.getInteger(R.color.yellow));
        }else if(pushButtonType == HAND_TYPE.GUH && enemyHandType == HAND_TYPE.CHOKI || 
                pushButtonType == HAND_TYPE.CHOKI && enemyHandType == HAND_TYPE.PAH || 
                pushButtonType == HAND_TYPE.PAH && enemyHandType == HAND_TYPE.GUH){
            result.setText(res.getString(R.string.win));
            result.setTextColor(res.getInteger(R.color.red));
        } else {
            result.setText(res.getString(R.string.lose));
            result.setTextColor(res.getInteger(R.color.blue));
        }
        
        
    }

    
}
