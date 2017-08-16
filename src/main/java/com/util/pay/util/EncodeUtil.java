package com.util.pay.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

/**
 * 二维码解析帮助类
 * @author Administrator
 *
 */
public class EncodeUtil {
	
	/** 
     * 生成二维码图片 不存储 直接以流的形式输出到页面 
     * @param content 
     * @param response 
     */  
    @SuppressWarnings({ "unchecked", "rawtypes" })  
    public static void encodeQrcode(String content,HttpServletResponse response){  
        if(content==null || "".equals(content))  
            return;  
       MultiFormatWriter multiFormatWriter = new MultiFormatWriter();  
       Hashtable hints = new Hashtable();  
       hints.put(EncodeHintType.CHARACTER_SET, "UTF-8"); //设置字符集编码类型  
       BitMatrix bitMatrix = null;  
       try {  
           bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 300, 300,hints);  
           BufferedImage image = toBufferedImage(bitMatrix);  
           //输出二维码图片流  
           try {  
               ImageIO.write(image, "png", response.getOutputStream());  
           } catch (IOException e) {  
               e.printStackTrace();  
           }  
       } catch (WriterException e1) {  
           e1.printStackTrace();  
       }           
    }
    
    
    
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;
	private static BufferedImage toBufferedImage(BitMatrix bitMatrix) {
		int width = bitMatrix.getWidth();
	      int height = bitMatrix.getHeight();
	      BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	      for (int x = 0; x < width; x++) {
	        for (int y = 0; y < height; y++) {
	          image.setRGB(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);
	        }
	      }
	      return image;
	}

}
