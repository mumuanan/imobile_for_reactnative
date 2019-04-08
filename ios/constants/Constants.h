//
//  Constants.h
//  Supermap
//
//  Created by Yang Shang Long on 2018/10/30.
//  Copyright © 2018年 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>

extern NSString* const COLLECTION_SENSOR_CHANGE;
extern NSString* const ONLINE_SERVICE_DOWNLOADING;
extern NSString* const ONLINE_SERVICE_DOWNLOADED;
extern NSString* const ONLINE_SERVICE_LOGIN;
extern NSString* const ONLINE_SERVICE_LOGOUT;
extern NSString* const ONLINE_SERVICE_DOWNLOADFAILURE;
extern NSString* const ONLINE_SERVICE_UPLOADING;
extern NSString* const ONLINE_SERVICE_UPLOADED;
extern NSString* const ONLINE_SERVICE_UPLOADFAILURE;
extern NSString* const ONLINE_SERVICE_REVERSEGEOCODING;
extern NSString* const MESSAGE_SERVICE_RECEIVE;

extern NSString* const MEASURE_LENGTH;
extern NSString* const MEASURE_AREA;
extern NSString* const MEASURE_ANGLE;
extern NSString * const ANALYST_MEASURELINE;
extern NSString * const ANALYST_MEASURESQUARE;
extern NSString * const POINTSEARCH_KEYWORDS;
extern NSString * const SSCENE_FLY;
extern NSString * const SSCENE_ATTRIBUTE;
extern NSString * const SSCENE_SYMBOL;
extern NSString * const SSCENE_CIRCLEFLY;
extern NSString * const SSCENE_FAVORITE;

extern NSString * const MAP_LONG_PRESS;
extern NSString * const MAP_SINGLE_TAP;
extern NSString * const MAP_DOUBLE_TAP;
extern NSString * const MAP_TOUCH_BEGAN;
extern NSString * const MAP_TOUCH_END;
extern NSString * const MAP_SCROLL;

extern NSString * const MAP_GEOMETRY_MULTI_SELECTED;
extern NSString * const MAP_GEOMETRY_SELECTED;
extern NSString * const MAP_SCALE_CHANGED;
extern NSString * const MAP_BOUNDS_CHANGED;

@interface Constants : NSObject

@end
