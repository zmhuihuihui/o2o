package com.util;

import com.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ImageUtil {

    //private static String basePath= Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random random = new Random();
    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    /**
     * 处理缩略图，并返回图片相对路径
     * @param thumbnail
     * @param targetAddr
     * @return
     */
    public static String generateThumbnail(ImageHolder thumbnail, String targetAddr)
    {
        //获取随机名字
        String realFileName = getRandomFileName();
        //获取扩展名
        String extension = getFileExtension(thumbnail.getImageName());
        //创建图片目录所涉及的文件夹
        makeDirPath(targetAddr);
        String relativePath = targetAddr+realFileName+extension;
        logger.debug("current relative path:"+relativePath);
        File dest = new File(PathUtil.getImgBasePath()+relativePath);
        logger.debug("current complete address:"+PathUtil.getImgBasePath()+relativePath);
        try{
            Thumbnails.of(thumbnail.getImage()).size(200,200).watermark(
                    Positions.BOTTOM_RIGHT, ImageIO.read(new File("/Users/zmh/Pictures/watermark.jpg")),0.25f).
                    outputQuality(0.8f).toFile(dest);
        }catch (IOException e){
            logger.error(e.toString());
            e.printStackTrace();
        }
        return  relativePath;
    }

    /**
     * 处理详情图
     * @param thumbnail
     * @param targetAddr
     * @return
     */
    public static String generateNormalImg(ImageHolder thumbnail, String targetAddr) {
        // 获取不重复的随机名
        String realFileName = getRandomFileName();
        // 获取文件的扩展名如png,jpg等
        String extension = getFileExtension(thumbnail.getImageName());
        // 如果目标路径不存在，则自动创建
        makeDirPath(targetAddr);
        // 获取文件存储的相对路径(带文件名)
        String relativeAddr = targetAddr + realFileName + extension;
        logger.debug("current relativeAddr is :" + relativeAddr);
        // 获取文件要保存到的目标路径
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        logger.debug("current complete addr is :" + PathUtil.getImgBasePath() + relativeAddr);
        // 调用Thumbnails生成带有水印的图片
        try {
            Thumbnails.of(thumbnail.getImage()).size(337, 640)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File("/Users/zmh/Pictures/watermark.jpg")), 0.25f)
                    .outputQuality(0.9f).toFile(dest);
        } catch (IOException e) {
            logger.error(e.toString());
            throw new RuntimeException("创建缩图片失败：" + e.toString());
        }
        // 返回图片相对路径地址
        return relativeAddr;
    }

    /**
     * CommonsMultipartFile转File
     * @param cFile
     * @return
     */
    public static File transferCommonsMultipartFileToFile(CommonsMultipartFile cFile)
    {
        File newFile = new File(cFile.getOriginalFilename());
        try {
            cFile.transferTo(newFile);
        } catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return newFile;

    }

    /**
     * 根据时间随机生成文件名称
     * @return
     */
    public static String getRandomFileName()
    {
        int randomNum = random.nextInt(89999)+10000;
        String nowTimeStr = sdf.format(new Date());
        return nowTimeStr+randomNum;

    }


    /**
     *获取扩展名
     * @return
     */
    private static String getFileExtension(String fileName)
    {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 创建目标路径所涉及的目录
     */
    private static void makeDirPath(String targetAddr)
    {
        String realFileParentPath=PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if(!dirPath.exists())
        {
            dirPath.mkdirs();
        }
    }

    /**
     * storePath如果是文件路径则删除文件
     * storePath如果是目录路径则删除路径下所有文件
     * @param storePath
     */
    public static void deleteFileOrPath(String storePath){
        File fileOrPath = new File(PathUtil.getImgBasePath()+storePath);
        if(fileOrPath.exists()){
            if(fileOrPath.isDirectory()){
                File[] fileList = fileOrPath.listFiles();
                for(File file:fileList){
                    file.delete();
                }
            }
            fileOrPath.delete();
        }
    }

}
