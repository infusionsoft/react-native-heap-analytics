package com.rnheap;

import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.heapanalytics.android.Heap;

import java.util.HashMap;

public class HeapModule extends ReactContextBaseJavaModule {

    public static final String TAG = HeapModule.class.getSimpleName();

    public HeapModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "RNHeap";
    }

    @ReactMethod
    public void setAppId(String appId) {
        Heap.init(getReactApplicationContext(), appId, true);
    }

    @ReactMethod
    public void enableVisualizer() {
        Log.w(TAG, "enableVisualizer Not available on Android");
    }

    @ReactMethod
    public void track(String event, ReadableMap properties) {
        HashMap<String, String> map = toHashMap(properties);
        Heap.track(event, map);
    }

    @ReactMethod
    public void identify(String identity) {
        Heap.identify(identity);
    }

    @ReactMethod
    public void addUserProperties(ReadableMap properties) {
        HashMap<String, String> map = toHashMap(properties);
        Heap.addUserProperties(map);
    }

    @ReactMethod
    public void addEventProperties(ReadableMap properties) {
        HashMap<String, String> map = toHashMap(properties);
        Heap.addEventProperties(map);
    }

    @ReactMethod
    public void removeEventProperty(String property) {
        Heap.removeEventProperty(property);
    }

    @ReactMethod
    public void clearEventProperties() {
        Heap.clearEventProperties();
    }

    @ReactMethod
    public void changeInterval(Double interval) {
        throw new UnsupportedOperationException();
    }

    private HashMap<String, String> toHashMap(ReadableMap readableMap) {
        HashMap<String, String> deconstructedMap = null;
        if (readableMap != null) {
            deconstructedMap = new HashMap<>();
            ReadableMapKeySetIterator iterator = readableMap.keySetIterator();
            while (iterator.hasNextKey()) {
                String key = iterator.nextKey();
                ReadableType type = readableMap.getType(key);
                if (type == ReadableType.String) {
                    deconstructedMap.put(key, readableMap.getString(key));
                }
            }
        }

        return deconstructedMap;
    }
}