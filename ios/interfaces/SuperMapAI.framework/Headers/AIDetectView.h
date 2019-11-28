//
//  AIDetectView.h
//  SuperMapAI
//
//  Created by zhouyuming on 2019/11/15.
//  Copyright © 2019年 wnmng. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <Foundation/Foundation.h>
#import <AVFoundation/AVFoundation.h>
@class AIDetectViewInfo;

@interface AIDetectView : UIView

@property (nonatomic,assign) long detectInterval;


-(void)initData;
/** 设置数据 **/
-(void) setDetectInfo:(AIDetectViewInfo*) detectViewInfo;
/** 设置设置间隔时间 **/
//-(void) setDetectInterval:(long) detectInterval;
/** 开启摄像头 **/
-(void) startCameraPreview;
/** 关闭摄像头 **/
-(void) stopCameraPreview;
/** 开始识别 **/
-(void) resumeDetect;
/** 停止识别 **/
-(void) pauseDetect;
/** 设置是否显示检测目标名称 **/
-(void) setIsShowDetectLabel:(BOOL) isShowDetectLabel;
/** 设置是否显示检测目标可信度 **/
-(void) setIsShowDetectConfidence:(BOOL) isShowDetectConfidence;
@end
