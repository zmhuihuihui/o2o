package com.util;

public class PathUtil {

    private static String separator=System.getProperty("file.separator");

    /**
     * 项目图片的根路径
     * @return
     */
    public static String getImgBasePath()
    {

        String os=System.getProperty("os.name");
        String basePath="";
        if(os.toLowerCase().startsWith("win"))
        {
            basePath="D:/projectdev/image/";
        }
        else{
            basePath="/Users/zmh/Pictures/";
        }
        return basePath.replace("/",separator);
    }

    /**
     * 项目图片的子路径
     * @param shopId
     * @return
     */
    public static String getShopImgPath(long shopId)
    {
        String imagePath="update/item/shop/"+shopId+"/";
        return imagePath.replace("/",separator);
    }

}
