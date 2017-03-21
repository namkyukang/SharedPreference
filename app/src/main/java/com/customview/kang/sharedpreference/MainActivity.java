package com.customview.kang.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;
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
    public static final String SHARED_FILE = "test.prop";
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
        //세팅된 값을 가져와서 화면에 뿌린다.
        loadSetting();
    }
    public void closeHelp(View view){
        layout.setVisibility(View.GONE);
        propertyUtil.saveProperty("firstOpen","false");

    }
    public void saveSetting(View view){
        //1.preference 생성하기
        //SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE); 액티비티 단위로 쓰기 위해서 사용했다. 따로 파일명을 만들지 않아도 됐다.
        SharedPreferences sharedPref = getSharedPreferences(SHARED_FILE,Context.MODE_PRIVATE);  //서로다른 액티비티에서 공유하기위해 사용.
        //2. editor를 가져와야한다. shared preference에 값을 입력하기 위해서는 에디터를 통해서만 가능하다.
        SharedPreferences.Editor editor = sharedPref.edit();

        //editor.putInt("키", "값");
        editor.putString("email",editName.getText().toString());
        editor.putBoolean("shuffle", switchShuffle.isChecked());

        //3. 입력된 값을 반영한다.
        editor.commit();
    }

    public void loadSetting(){
        SharedPreferences sharedPref = getSharedPreferences(SHARED_FILE,Context.MODE_PRIVATE);

        //int defaultValue = getResources().getInteger(R.string.saved_high_score_default); 키값을 정의해놓고 사용하는 부분
        //프로퍼티 가져오기.
        String email = sharedPref.getString("email","");
        boolean shuffle = sharedPref.getBoolean("shuffle",false);

        //화면에 세팅
        editName.setText(email);
        switchShuffle.setChecked(shuffle);
    }

}
