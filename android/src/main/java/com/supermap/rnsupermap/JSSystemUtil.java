package com.supermap.rnsupermap;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

import java.io.File;

public class JSSystemUtil extends ReactContextBaseJavaModule {
    public static final String REACT_CLASS = "JSSystemUtil";

    public JSSystemUtil(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @ReactMethod
    public void getHomeDirectory(Promise promise) {
        try {
            String homeDirectory = android.os.Environment.getExternalStorageDirectory().getAbsolutePath().toString();

            WritableMap map = Arguments.createMap();
            map.putString("homeDirectory", homeDirectory);
            promise.resolve(map);
        } catch (Exception e) {
            promise.reject(e);
        }
    }

    @ReactMethod
    public void getDirectoryContent(String path, Promise promise){
        try{
            File flist = new File(path);
            String[] mFileList = flist.list();

            WritableArray arr = Arguments.createArray();
            for(String str: mFileList){
                String type = "";
                if (new File(path + "/" + str).isDirectory()) {
                    type = "directory";
                } else {
                    type = "file";
                }
                WritableMap map = Arguments.createMap();
                map.putString("name", str);
                map.putString("type", type);

                arr.pushMap(map);
            }

            promise.resolve(arr);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void fileIsExist(String path, Promise promise) {
        try {
            Boolean isExist = false;
            File file = new File(path);

            if (file.exists()) {
                isExist = true;
            }

            WritableMap map = Arguments.createMap();
            map.putBoolean("isExist", isExist);
            promise.resolve(map);
        } catch (Exception e) {
            promise.reject(e);
        }
    }

    @ReactMethod
    public void fileIsExistInHomeDirectory(String path, Promise promise) {
        try {
            String homeDirectory = android.os.Environment.getExternalStorageDirectory().getAbsolutePath().toString();

            Boolean isExist = false;
            File file = new File(homeDirectory + "/" + path);

            if (file.exists()) {
                isExist = true;
            }

            WritableMap map = Arguments.createMap();
            map.putBoolean("isExist", isExist);
            promise.resolve(map);
        } catch (Exception e) {
            promise.reject(e);
        }
    }
}

