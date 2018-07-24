package com.supermap.rnsupermap;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.supermap.RNUtils.N_R_EventSender;
import com.supermap.data.Dataset;
import com.supermap.data.GeoStyle;
import com.supermap.data.Geometry;
import com.supermap.data.Point2D;
import com.supermap.mapping.MapControl;
import com.supermap.mapping.MapView;
import com.supermap.mapping.collector.CollectionChangedListener;
import com.supermap.mapping.collector.Collector;
import com.supermap.mapping.collector.CollectorElement;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yangshanglong on 2018/7/20.
 */
public class JSCollector extends ReactContextBaseJavaModule {
    public static final String REACT_CLASS = "JSCollector";
    private static final String COLLECTION_CHANGE = "com.supermap.RN.JSMapcontrol.collection_change";
    private CollectionChangedListener mCollectionChangedListener;
    ReactContext mReactContext;

    public static Map<String,Collector> mCollectorList=new HashMap();
    Collector mCollector;

    public JSCollector(ReactApplicationContext context){
        super(context);
        mReactContext = context;
    }

    public static Collector getObjFromList(String id) { return mCollectorList.get(id); }

    public static String registerId(Collector collector){
        if(!mCollectorList.isEmpty()) {
            for(Map.Entry entry:mCollectorList.entrySet()){
                if(collector.equals(entry.getValue())){
                    return (String)entry.getKey();
                }
            }
        }

        Calendar calendar=Calendar.getInstance();
        String id=Long.toString(calendar.getTimeInMillis());
        mCollectorList.put(id,collector);
        return id;
    }

    @Override
    public String getName(){
        return REACT_CLASS;
    }

//    @ReactMethod
//    public void createObj(Promise promise){
//        try{
//            Collector theme = new Collector();
//            String themeId = registerId(theme);
//
//            promise.resolve(themeId);
//        }catch (Exception e){
//            promise.reject(e);
//        }
//    }

    /**
     * 添加点,GPS获取的点
     * @param collectorId
     * @param promise
     */
    @ReactMethod
    public void addGPSPoint(String collectorId, Promise promise){
        try{
            Collector collector = mCollectorList.get(collectorId);
            boolean result = collector.addGPSPoint();
            promise.resolve(result);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    /**
     * 添加点,GPS获取的点
     * @param collectorId
     * @param pnt2DId
     * @param promise
     */
    @ReactMethod
    public void addGPSPointByPoint(String collectorId, String pnt2DId, Promise promise){
        try{
            Collector collector = mCollectorList.get(collectorId);
            Point2D point2D = JSPoint2D.getObjFromList(pnt2DId);
            boolean result = collector.addGPSPoint(point2D);
            promise.resolve(result);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    /**
     * 关闭GPS
     * @param collectorId
     * @param promise
     */
    @ReactMethod
    public void closeGPS(String collectorId, Promise promise){
        try{
            Collector collector = mCollectorList.get(collectorId);
            collector.closeGPS();
            promise.resolve(true);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    /**
     * 创建指定类型的采集对象
     * @param collectorId
     * @param type
     * @param promise
     */
    @ReactMethod
    public void createElement(String collectorId, String type, Promise promise){
        try{
            Collector collector = mCollectorList.get(collectorId);
            CollectorElement.GPSElementType type1 = Enum.valueOf(CollectorElement.GPSElementType.class, type);
            boolean result = collector.createElement(type1);
            promise.resolve(result);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    /**
     * 获取当前的几何对象
     * @param collectorId
     * @param promise
     */
    @ReactMethod
    public void getCurGeometry(String collectorId, Promise promise){
        try{
            Collector collector = mCollectorList.get(collectorId);
            Geometry geometry = collector.getCurGeometry();
            String geometryId = JSGeometry.registerId(geometry);
            promise.resolve(geometryId);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    /**
     * 获取当前编辑节点的宽度,单位是10mm
     * @param collectorId
     * @param promise
     */
    @ReactMethod
    public void getEditNodeWidth(String collectorId, Promise promise){
        try{
            Collector collector = mCollectorList.get(collectorId);
            double value = collector.getEditNodeWidth();
            promise.resolve(value);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    /**
     * 获取当前采集对象
     * @param collectorId
     * @param promise
     */
    @ReactMethod
    public void getElement(String collectorId, Promise promise){
        try{
            Collector collector = mCollectorList.get(collectorId);
            CollectorElement element = collector.getElement();
            CollectorElement.GPSElementType type = element.getType();
            String id;
            if (type == CollectorElement.GPSElementType.LINE) {
                id = JSElementLine.registerId(element);
            } else if (type == CollectorElement.GPSElementType.POINT) {
                id = JSElementPoint.registerId(element);
            } else {
                id = JSElementPolygon.registerId(element);
            }
            WritableMap map = Arguments.createMap();
            map.putString("id", id);
            map.putString("type", type.name());

            promise.resolve(map);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    /**
     * 获取当前位置
     * @param collectorId
     * @param promise
     */
    @ReactMethod
    public void getGPSPoint(String collectorId, Promise promise){
        try{
            Collector collector = mCollectorList.get(collectorId);
            Point2D point2D = collector.getGPSPoint();
            String point2DId = JSPoint2D.registerId(point2D);

            promise.resolve(point2DId);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    /**
     * 获取绘制风格采集对象的绘制风格
     * @param collectorId
     * @param promise
     */
    @ReactMethod
    public void getStyle(String collectorId, Promise promise){
        try{
            Collector collector = mCollectorList.get(collectorId);
            GeoStyle style = collector.getStyle();
            String styleId = JSGeoStyle.registerId(style);

            promise.resolve(styleId);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    /**
     * 获取是否采用手势打点
     * @param collectorId
     * @param promise
     */
    @ReactMethod
    public void IsSingleTapEnable(String collectorId, Promise promise){
        try{
            Collector collector = mCollectorList.get(collectorId);
            boolean result = collector.IsSingleTapEnable();

            promise.resolve(result);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    /**
     * 定位地图到当前位置
     * @param collectorId
     * @param promise
     */
    @ReactMethod
    public void moveToCurrent(String collectorId, Promise promise){
        try{
            Collector collector = mCollectorList.get(collectorId);
            collector.moveToCurrent();

            promise.resolve(true);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    /**
     * 定位地图到当前位置
     * @param collectorId
     * @param promise
     */
    @ReactMethod
    public void openGPS(String collectorId, Promise promise){
        try{
            Collector collector = mCollectorList.get(collectorId);
            boolean result = collector.openGPS();

            promise.resolve(result);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    /**
     * 重做操作
     * @param collectorId
     * @param promise
     */
    @ReactMethod
    public void redo(String collectorId, Promise promise){
        try{
            Collector collector = mCollectorList.get(collectorId);
            collector.redo();

            promise.resolve(true);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    /**
     * 设置定位变化监听
     * @param collectorId
     * @param promise
     */
    @ReactMethod
    public void setCollectionChangedListener(String collectorId, Promise promise){
        try{
            Collector collector = mCollectorList.get(collectorId);
            mCollectionChangedListener = new CollectionChangedListener() {
                @Override
                public void collectionChanged(Point2D point2D, double v) {
                    WritableMap map = Arguments.createMap();
                    map.putInt("x", (int) point2D.getX());
                    map.putInt("y", (int) point2D.getY());
                    map.putDouble("dAccuracy", v);

                    mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                            .emit(COLLECTION_CHANGE, map);
                }
            };
            collector.setCollectionChangedListener(mCollectionChangedListener);

            promise.resolve(true);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    /**
     * 设置用于存储采集数据的数据集
     * @param collectorId
     * @param datasetId
     * @param promise
     */
    @ReactMethod
    public void setDataset(String collectorId, String datasetId, Promise promise){
        try{
            Collector collector = mCollectorList.get(collectorId);
            Dataset dataset = JSDataset.getObjById(datasetId);
            collector.setDataset(dataset);

            promise.resolve(true);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    /**
     * 设置当前编辑节点的宽度,单位是10mm
     * @param collectorId
     * @param value
     * @param promise
     */
    @ReactMethod
    public void setEditNodeWidth(String collectorId, double value, Promise promise){
        try{
            Collector collector = mCollectorList.get(collectorId);
            collector.setEditNodeWidth(value);

            promise.resolve(true);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    /**
     * 设置地图控件
     * @param collectorId
     * @param mapControlId
     * @param promise
     */
    @ReactMethod
    public void setMapControl(String collectorId, String mapControlId, Promise promise){
        try{
            Collector collector = mCollectorList.get(collectorId);
            MapControl mapControl = JSMapControl.getObjFromList(mapControlId);
            collector.setMapControl(mapControl);

            promise.resolve(true);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    /**
     * 设置GPS式几何对象采集类关联的主控件
     * @param collectorId
     * @param mapViewId
     * @param promise
     */
    @ReactMethod
    public void setMapView(String collectorId, String mapViewId, Promise promise){
        try{
            Collector collector = mCollectorList.get(collectorId);
            MapView mapView = JSMapView.getObjById(mapViewId);
            collector.setMapView(mapView);

            promise.resolve(true);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    /**
     * 设置是否采用手势打点
     * @param collectorId
     * @param value
     * @param promise
     */
    @ReactMethod
    public void setSingleTapEnable(String collectorId, boolean value, Promise promise){
        try{
            Collector collector = mCollectorList.get(collectorId);
            collector.setSingleTapEnable(value);

            promise.resolve(true);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    /**
     * 设置采集对象的绘制风格
     * @param collectorId
     * @param styleId
     * @param promise
     */
    @ReactMethod
    public void setStyle(String collectorId, String styleId, Promise promise){
        try{
            Collector collector = mCollectorList.get(collectorId);
            GeoStyle style = JSGeoStyle.getObjFromList(styleId);
            collector.setStyle(style);

            promise.resolve(true);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    /**
     * 显示提示信息
     * @param collectorId
     * @param info
     * @param promise
     */
    @ReactMethod
    public void showInfo(String collectorId, String info, Promise promise){
        try{
            Collector collector = mCollectorList.get(collectorId);
            collector.showInfo(info);

            promise.resolve(true);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    /**
     * 提交
     * @param collectorId
     * @param promise
     */
    @ReactMethod
    public void submit(String collectorId, Promise promise){
        try{
            Collector collector = mCollectorList.get(collectorId);
            boolean result = collector.submit();

            promise.resolve(result);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    /**
     * 回退操作
     * @param collectorId
     * @param promise
     */
    @ReactMethod
    public void undo(String collectorId, Promise promise){
        try{
            Collector collector = mCollectorList.get(collectorId);
            collector.undo();

            promise.resolve(true);
        }catch(Exception e){
            promise.reject(e);
        }
    }

}
