package com.customview.kang.sharedpreference;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class MainActivity extends AppCompatActivity {
    EditText editName;
    Switch switchShuffle;

    RelativeLayout layout;

    //내부저장소 절대경로 가져오기 /data/data/패키지명/files
    String internalStoragePath;
    //context가 없는 상황에서 final을 넣어주면 오류난다. context가 필요한 함수가 실행됐기때문에 에러
    //final String internalStoragePath = getFilesDir().getAbsolutePath();

    PropertyUtil propertyUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        propertyUtil = PropertyUtil.getInstance(this);

        internalStoragePath = getFilesDir().getAbsolutePath();
        editName = (EditText)findViewById(R.id.editText);
        switchShuffle = (Switch)findViewById(R.id.switchShuffle);
        layout = (RelativeLayout)findViewById(R.id.layout2);
        //firstOpen이 체크되어 있으면 도움말 레이아웃을 닫아준다.
        if("false".equals(propertyUtil.getProperty("firstOpen"))){
            //getProperty("firstOpen").equals("false") 이게 조건문으로 들어가면 getProperty에서 null이 생기면 문제가 생긴다.
            // 그래서 "false"를 앞으로 보내면 firstOpen에서 null이 생겨도 null과 문자열을 비교하기 때문에 문제가 없다.
            layout.setVisibility(View.GONE);
            Log.d("MainActivity","onCreate=============");
        }
    }
    public void closeHelp(View view){
        layout.setVisibility(View.GONE);
        propertyUtil.saveProperty("firstOpen","false");

    }
    public void saveSetting(View view){

    }


}
