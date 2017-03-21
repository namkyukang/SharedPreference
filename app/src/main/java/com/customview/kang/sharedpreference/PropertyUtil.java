package com.customview.kang.sharedpreference;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * Created by kang on 2017-03-21.
 */

public class PropertyUtil {
    //Singleton
    private String PROP_FILE= "sp.properties";
    private String internalStorage;
    private static PropertyUtil instance = null;
    private static Context context;

    //생성자가 호출될 때 internal Storage를 세팅해줘야 됩니다.
    private PropertyUtil(){
        internalStorage =context.getFilesDir().getAbsolutePath();
    }

    public static PropertyUtil getInstance(Context ctx){
        context = ctx;
        if(instance == null) {
            instance = new PropertyUtil();
            //항상 context를 세팅해줘야한다. singleton같은 경우 activity가 2개 일때 A Activity의 context를 받았다고
            //하면 생성될때만 context를 받게되면 계속 A Activity의 context가 되기때문에 항상 context를 불러줘야한다.
        }
        return instance;
    }

    public void saveProperty(String key, String value){
        Properties prop = new Properties();
        prop.put(key, value);

        try {
            //앱의 내부저장소 /files/test.properties파일을 저장.
            FileOutputStream fos = new FileOutputStream(internalStorage +"/"+ PROP_FILE);

            prop.store(fos, "comment");
            //저장후 파일을 닫아준다.
            fos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getProperty(String key){
        String value = "";
        Properties prop = new Properties();
        try {
            FileInputStream fis = new FileInputStream(internalStorage + "/" + PROP_FILE);
            prop.load(fis);
            fis.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        prop.list(System.out);  //프로퍼티 목록 전체 나열하기.
        value = prop.getProperty(key);

        return value;
    }
}
