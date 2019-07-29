//
//  InfoCallout.h
//  Supermap
//
//  Created by Shanglong Yang on 2019/5/13.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import "SuperMap/Callout.h"
#import "SuperMap/Layer.h"

@interface InfoCallout : Callout

@property (strong, nonatomic) NSString* ID;
@property (strong, nonatomic) NSString* description;
@property (strong, nonatomic) NSString* layerName;
@property (strong, nonatomic) NSString* mediaName;
@property (strong, nonatomic) NSArray* mediaFilePaths;
//@property (strong, nonatomic) NSString* type;
@property (assign, nonatomic) int geoID;
@property (strong, nonatomic) NSString* modifiedDate;
@property (strong, nonatomic) NSString* httpAddress;

- (id)initWithMapControl:(MapControl *)mapControl BackgroundColor:(UIColor*)customcolor Alignment:(CalloutAlignment)calloutAlignment;
- (id)initWithMapControl:(MapControl *)mapControl;

- (void)setGeoID:(int)geoID;
- (void)setLayerName:(NSString *)layerName;

@end
