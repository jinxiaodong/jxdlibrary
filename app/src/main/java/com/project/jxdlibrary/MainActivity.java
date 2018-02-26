package com.project.jxdlibrary;

import android.content.Intent;
import android.view.View;

import com.project.jxdlibrary.demo.ThakePhotoDemo;
import com.project.xiaodong.fflibrary.base.BaseActivity;
import com.project.xiaodong.jxdlibrary.R;

public class MainActivity extends BaseActivity {

    @Override
    protected int getHeaderLayoutId() {
        return -1;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }


    private void goToActivity(Class clazz){
        startActivity(new Intent(this,clazz));
    }

    public void photo(View view) {
        goToActivity(ThakePhotoDemo.class);
    }


}
