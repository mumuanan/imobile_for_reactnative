//
//  PixelFormat.h
//  Visualization
//
//  版权所有 （c）2013 北京超图软件股份有限公司。保留所有权利。
//

#import <Foundation/Foundation.h>

/** 该类定义了栅格与影像数据存储的像素格式类型常量。
 *  
 * 光栅数据结构实际上就是像元的阵列，像元（或像素）是光栅数据的最基本信息存储单位。在 SuperMap 中有两种类型的光栅数据：栅格数据集  <DatasetGrid> 和影像数据集 <DatasetImage> ，栅格数据集多用来进行栅格分析，因而其像元值为地物的属性值，如高程，降水量等；而影像数据集一般用来进行显示或作为底图，因而其像元值为颜色值或颜色的索引值。
 *
 */
typedef enum{
   
   /** 每个像元用1个比特表示。
          *
	* 对栅格数据集来说，可表示0和1两种值；对影像数据集来说，可表示黑白两种颜色，对应单色影像数据。
	*/ 
    UBIT1 = 1,
	
	/** 每个像元用4个比特表示。
	*
	* 对栅格数据集来说，可表示0-15共16个整数值；对影像数据集来说，可表示16种颜色，这16种颜色为索引色，在其颜色表中定义，对应16色的影像数据。
	*/ 
    UBIT4 = 4,
	
	/** 每个像元用8个比特表示。
	*
	*即1个字节表示，对栅格数据集来说，可表示0-255共256个整数值；对影像数据集来说，可表示256种渐变的颜色，这256种颜色为索引色，在其颜色表中定义，对应256色的影像数据。
	*/ 
    UBIT8 = 8,
	
	/** 每个像元用16个比特表示。
	*
	*即2个字节表示，对栅格数据集来说，可表示0-65535共65536个整数值；对影像数据集来说，16个比特中，红，绿，蓝各用5比特来表示，剩余1比特未使用，对应彩色的影像数据。
	*/ 
    UBIT16 = 16,
	
	/** 每个像元用24个比特表示。
	*
	* 即3个字节来表示，对栅格数据集来说，可表示0-16777215共16777216个整数值；对影像数据集来说，24比特中，红，绿，蓝各用8比特来表示，对应真彩色的影像数据。
	*/ 
    UBIT24 = 24,
	
	/** 每个像元用32个比特表示。
	*
	* 即4个字节来表示，对栅格数据集来说，可表示0-4294967295共4294967296个整数值；对影像数据集来说，32比特中，红，绿，蓝和alpha各用8比特来表示，对应增强真彩色的影像数据。该格式支持 <DatasetGrid> ，  <DatasetImage>  （仅支持多波段）。
	*/ 
    BIT32 = 320,
	
	/** 每个像元用32个比特。
               * 
	    *  即4个字节来表示，对应影像的增强真彩色。该格式支持 <DatasetImage> (不支持多波段)。
              */
    UBIT32 = 32,
	
	/** 每个像元用64个比特。
             *
	   *  即8个字节来表示，可表示的像元值范围为0-18446744073709551615，只提供给 <DatasetGrid> （栅格数据集）使用。
             */
    BIT64 = 64,
	
	/**  每个像元用4个字节来表示。
               * 
	   * 可表示范围在1.5E-45到3.4E+38范围内的单精度浮点数，只提供给 <DatasetGrid> （栅格数据集）使用。
              */
    SINGLE = 3200,
    
    DOUBLE = 6400
}OCPixelFormat;
