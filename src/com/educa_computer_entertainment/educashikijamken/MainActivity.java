package com.educa_computer_entertainment.educashikijamken;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
    
    private static final int MODE_HELL_DRAWCOUNT = 3;
    private static final int ANIMATION_DURATION = 500;
    private static final String TAG    = "MainActivity";
    private static final Random random = new Random();
    
    private static final class HAND_TYPE {
        private static final int GUH   = 0;
        private static final int CHOKI = 1;
        private static final int PAH   = 2;
    }
    
    private static final class MODE {
        private static final int HELL   = 0;
        private static final int NORMAL = 1;
    }
    
    private static final int[] CATS_DRAWABLE         = { R.drawable.guh_cat, R.drawable.choki_cat, R.drawable.pah_cat };
    private static final int[] PLAYER_CATS_DRAWABLE         = { R.drawable.player_guh, R.drawable.player_choki, R.drawable.player_pah };
    private static final int[] HAND_DRAWABLE         = { R.drawable.guh, R.drawable.choki, R.drawable.pah };
    private static final int[] HAND_BUTTON_ID        = { R.id.guh, R.id.choki, R.id.pah };
    
    private int                drawCount             = 0;
    
    private int                currentMode = MODE.NORMAL;
    private int[]              currentButtonDrawable = new int[HAND_BUTTON_ID.length];
    private ImageButton[]      imageButtons          = new ImageButton[HAND_BUTTON_ID.length];
    
    private ImageView          computerHandImageView;
    private TextView           resultTextView;
    
    private Resources          res;
    private TransitionDrawable backgroundAnimation;
    private boolean isFirst = true;
    private LinearLayout backgroundView;
    private ImageView playerHandImageView;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        res = getResources();
        
        backgroundAnimation = (TransitionDrawable) findViewById(R.id.background).getBackground();
        backgroundView = (LinearLayout) findViewById(R.id.background);
        
        computerHandImageView = (ImageView) findViewById(R.id.enemyhand);
        playerHandImageView = (ImageView) findViewById(R.id.playerhand);
        resultTextView = (TextView) findViewById(R.id.result);
        
        int buttonIndex = 0;
        //じゃんけんボタンのオブジェクトを取得しクリックリスナの登録と各ボタンに指定されてる画像のリソースIDをcurrentButtonDrawableに格納
        for (int buttonId : HAND_BUTTON_ID) {
            imageButtons[buttonIndex] = (ImageButton) findViewById(buttonId);
            imageButtons[buttonIndex].setOnClickListener(this);
            currentButtonDrawable[buttonIndex] = HAND_DRAWABLE[buttonIndex];
            buttonIndex++;
        }
        //取得したじゃんけんボタンに通常モードで画像を設定
        setImageInButtons(MODE.NORMAL);
    }
    
    @Override
    public void onClick(View v) {
        int randomValue = random.nextInt(HAND_BUTTON_ID.length);
        int computerHandDrawable = HAND_DRAWABLE[randomValue];
        Log.d(TAG, "enemyHand : " + randomValue);
        
//        computerHandImageView.setImageResource(computerHandDrawable);
        computerHandImageView.setImageResource(CATS_DRAWABLE[randomValue]);
        
        int pushedButtonId = v.getId();
        
        //押されたボタンの画像のリソースIDを取得
        int pushedButtonType = Arrays.binarySearch(HAND_BUTTON_ID, pushedButtonId);
        int pushedButtonDrawable = currentButtonDrawable[pushedButtonType];
        playerHandImageView.setImageResource(PLAYER_CATS_DRAWABLE[pushedButtonType]);
        
        if (computerHandDrawable == pushedButtonDrawable) {
            resultTextView.setText(res.getString(R.string.draw));
            resultTextView.setTextColor(res.getInteger(R.color.yellow));
            drawCount++;
            if (drawCount >= MODE_HELL_DRAWCOUNT && currentMode == MODE.NORMAL)
                setImageInButtons(MODE.HELL);
        } else if (pushedButtonDrawable == HAND_DRAWABLE[HAND_TYPE.GUH] && computerHandDrawable == HAND_DRAWABLE[HAND_TYPE.CHOKI] ||
                pushedButtonDrawable == HAND_DRAWABLE[HAND_TYPE.CHOKI] && computerHandDrawable == HAND_DRAWABLE[HAND_TYPE.PAH] ||
                pushedButtonDrawable == HAND_DRAWABLE[HAND_TYPE.PAH] && computerHandDrawable == HAND_DRAWABLE[HAND_TYPE.GUH]) {
            resultTextView.setText(res.getString(R.string.win));
            resultTextView.setTextColor(res.getInteger(R.color.red));
            drawCount = 0;
            if(currentMode == MODE.HELL)
                setImageInButtons(MODE.NORMAL);
        } else {
            resultTextView.setText(res.getString(R.string.lose));
            resultTextView.setTextColor(res.getInteger(R.color.blue));
            drawCount = 0;
        }
        
    }
    
    private void setImageInButtons(int mode) {
        int buttonIndex = 0;
        currentMode = mode;
        switch (mode) {
            case MODE.HELL:
//                backgroundAnimation.startTransition(ANIMATION_DURATION);
                Log.d(TAG,"level : " + backgroundAnimation.getLevel());
                
                int hellType = random.nextInt(HAND_BUTTON_ID.length);
                
                for (ImageButton button : imageButtons) {
                    button.setImageResource(HAND_DRAWABLE[hellType]);
                    currentButtonDrawable[buttonIndex] = HAND_DRAWABLE[hellType];
                    buttonIndex++;
                }
                break;
            case MODE.NORMAL:
//                if(!isFirst)
//                    backgroundAnimation.reverseTransition(ANIMATION_DURATION);
                
                isFirst = false;
                
                for (ImageButton button : imageButtons) {
                    button.setImageResource(HAND_DRAWABLE[buttonIndex]);
                    currentButtonDrawable[buttonIndex] = HAND_DRAWABLE[buttonIndex];
                    buttonIndex++;
                }
                break;
            default:
                break;
        }
        
    }
    
}
