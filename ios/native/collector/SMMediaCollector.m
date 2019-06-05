//
//  SMMediaCollector.m
//  Supermap
//
//  Created by Shanglong Yang on 2019/5/24.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import "SMMediaCollector.h"

static SMMediaCollector* sMediaCollector = nil;

@implementation SMMediaCollector

+ (instancetype)singletonInstance{
    
    static dispatch_once_t once;
    
    dispatch_once(&once, ^{
        sMediaCollector = [[self alloc] init];
    });
    
    return sMediaCollector;
}

+ (BOOL)hasMediaData:(Layer *)layer {
    DatasetVector* dsVector = (DatasetVector *)layer.dataset;
    int tag1 = (int)[dsVector.fieldInfos indexOfWithFieldName:@"MediaFileName"];
    //        int tag2 = (int)[dsVector.fieldInfos indexOfWithFieldName:@"MediaFileType"];
    int tag3 = (int)[dsVector.fieldInfos indexOfWithFieldName:@"ModifiedDate"];
    int tag4 = (int)[dsVector.fieldInfos indexOfWithFieldName:@"MediaFilePaths"];
    int tag5 = (int)[dsVector.fieldInfos indexOfWithFieldName:@"Description"];
    int tag6 = (int)[dsVector.fieldInfos indexOfWithFieldName:@"HttpAddress"];
    
    if(tag1==-1 || tag3==-1 || tag4==-1 || tag5==-1 || tag6==-1)
        return NO;
    if([dsVector.fieldInfos get:tag1].fieldType!=FT_TEXT)
        return NO;
    //        if([dsVector.fieldInfos get:tag2].fieldType!=FT_INT16)
    //            return NO;
    if([dsVector.fieldInfos get:tag3].fieldType!=FT_TEXT)
        return NO;
    if([dsVector.fieldInfos get:tag4].fieldType!=FT_TEXT)
        return NO;
    if([dsVector.fieldInfos get:tag5].fieldType!=FT_TEXT)
        return NO;
    if([dsVector.fieldInfos get:tag6].fieldType!=FT_TEXT)
        return NO;
//    if(dsVector.prjCoordSys.type != PCST_EARTH_LONGITUDE_LATITUDE)
//        return NO;
    
    return YES;
}

+ (SMMedia *)findMediaByLayer:(Layer *)layer geoID:(int)geoID {
    SMMedia* media = [[SMMedia alloc] init];
    
    DatasetVector* dsVector = (DatasetVector *)layer.dataset;
    Recordset* recordset;
    
    if (![SMMediaCollector hasMediaData:layer]) {
        return nil;
    }
    
    NSString* filter = [NSString stringWithFormat:@"SmID=%d", geoID];
    QueryParameter* queryParams = [[QueryParameter alloc] init];
    [queryParams setAttriButeFilter:filter];
    [queryParams setCursorType:STATIC];
    recordset = [dsVector query:queryParams];
    
    media.datasourse = layer.dataset.datasource;
    media.dataset = layer.dataset;
    media.fileName = (NSString *)[recordset getFieldValueWithString:@"MediaFileName"];
    
    NSString* paths = (NSString *)[recordset getFieldValueWithString:@"MediaFilePaths"];
    media.paths = [paths componentsSeparatedByString:@","];
    
//    if ([media.paths count] > 0) {
//        media.mediaDicPath = [media.paths[0] stringByDeletingLastPathComponent];
//    }
    
    double x =  [recordset.geometry getInnerPoint].x;
    double y =  [recordset.geometry getInnerPoint].y;
    
    
    media.location = [[Point2D alloc] initWithX:x Y:y];
    
    [recordset dispose];
    
    return media;
}

+ (NSArray *)findMediasByLayer:(Layer *)layer {
    NSMutableArray* medias = [[NSMutableArray alloc] init];
    
    DatasetVector* dsVector = (DatasetVector *)layer.dataset;
    Recordset* recordset;
    
    if (![SMMediaCollector hasMediaData:layer]) {
        return nil;
    }
    
    recordset = [dsVector recordset:NO cursorType:STATIC];
    
    [recordset moveFirst];
    
    while (![recordset isEOF]) {
        SMMedia* media = [[SMMedia alloc] init];
        
        media.datasourse = layer.dataset.datasource;
        media.dataset = layer.dataset;
        media.fileName = (NSString *)[recordset getFieldValueWithString:@"MediaFileName"];
        
        NSString* paths = (NSString *)[recordset getFieldValueWithString:@"MediaFilePaths"];
        media.paths = [paths componentsSeparatedByString:@","];
        
        double x =  [recordset.geometry getInnerPoint].x;
        double y =  [recordset.geometry getInnerPoint].y;
        media.location = [[Point2D alloc] initWithX:x Y:y];
        
        [medias addObject:media];
        
        [recordset moveNext];
    }
    
    [recordset dispose];
    
    return medias;
}

+ (void)addMediasByLayer:(Layer *)layer gesture:(UITapGestureRecognizer *)gesture {
    DatasetVector* dsVector = (DatasetVector *)layer.dataset;
    Recordset* recordset;
    
    if ([SMMediaCollector hasMediaData:layer]) {
        recordset = [dsVector recordset:NO cursorType:STATIC];
        
        [recordset moveFirst];
        
        dispatch_async(dispatch_get_main_queue(), ^{
            int recordsetIndex = 0;
            while (![recordset isEOF]) {
                SMMedia* media = [[SMMedia alloc] init];
                
                media.datasourse = layer.dataset.datasource;
                media.dataset = layer.dataset;
                media.fileName = (NSString *)[recordset getFieldValueWithString:@"MediaFileName"];
                
                NSString* paths = (NSString *)[recordset getFieldValueWithString:@"MediaFilePaths"];
                media.paths = [paths componentsSeparatedByString:@","];
                
                double x =  [recordset.geometry getInnerPoint].x;
                double y =  [recordset.geometry getInnerPoint].y;
                media.location = [[Point2D alloc] initWithX:x Y:y];
                
                InfoCallout* callout = [SMLayer addCallOutWithLongitude:x latitude:y image:media.paths[0]];
                callout.mediaFileName = media.fileName;
                callout.mediaFilePaths = media.paths;
                //            callout.type = media.mediaType;
                callout.layerName = layer.name;
                callout.httpAddress = @"";
                callout.description = @"";
                NSDate* date = [NSDate date];
                
                NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
                [dateFormatter setDateFormat:@"yyyy-MM-dd HH:mm:ss"];
                callout.modifiedDate = [dateFormatter stringFromDate:date];
                
                callout.geoID = ((NSNumber *)[recordset getFieldValueWithString:@"SmID"]).intValue;
                
                if (gesture) {
                    [callout addGestureRecognizer:gesture];
                }
                
                if ([recordset moveNext]) {
                    recordsetIndex++;
                }
            }
            
            [recordset dispose];
        });
    }
}

+ (void)addCalloutByMedia:(SMMedia *)media recordset:(Recordset *)rs layerName:(NSString *)layerName segesturelector:(UITapGestureRecognizer *)gesture {
    
    double longitude = [rs.geometry getInnerPoint].x;
    double latitude =  [rs.geometry getInnerPoint].y;
    
    InfoCallout* callout = [SMLayer addCallOutWithLongitude:longitude latitude:latitude image:media.paths[0]];
    callout.mediaFileName = media.fileName;
    callout.mediaFilePaths = media.paths;
    //            callout.type = media.mediaType;
    callout.layerName = layerName;
    callout.httpAddress = @"";
    callout.description = @"";
    NSDate* date = [NSDate date];
    
    NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
    [dateFormatter setDateFormat:@"yyyy-MM-dd HH:mm:ss"];
    callout.modifiedDate = [dateFormatter stringFromDate:date];
    
    callout.geoID = ((NSNumber *)[rs getFieldValueWithString:@"SmID"]).intValue;
    
    [rs edit];
    
    NSMutableString* paths = [NSMutableString string];
    //            for (NSString* path in callout.mediaFilePaths) {
    //                paths appendFormat:<#(nonnull NSString *), ...#>
    //            }
    for (int i = 0; i < callout.mediaFilePaths.count; i++) {
        [paths appendString:callout.mediaFilePaths[i]];
        if (i < callout.mediaFilePaths.count - 1) {
            [paths appendString:@","];
        }
    }
    
    [rs setFieldValueWithString:@"ModifiedDate" Obj:callout.modifiedDate];
    [rs setFieldValueWithString:@"MediaFilePaths" Obj:paths];
    [rs setFieldValueWithString:@"Description" Obj:callout.description];
    [rs setFieldValueWithString:@"HttpAddress" Obj:callout.httpAddress];
    
    [rs update];
    
    if (gesture) {
        [callout addGestureRecognizer:gesture];
    }
}

@end