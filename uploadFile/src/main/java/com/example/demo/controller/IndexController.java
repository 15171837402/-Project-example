package com.example.demo.controller;

import com.example.demo.vo.MsgVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController {

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/play",method = RequestMethod.GET)
    public String play(){
        return "video";
    }

    @RequestMapping(value = "/upload/exist",method = RequestMethod.POST)
    @ResponseBody
    public Boolean fileExits(@RequestParam("chunkName") String chunkName){
        //判断文件是否曾上传过
        File filePath;
        if(chunkName.lastIndexOf("_")==-1 && chunkName.lastIndexOf(".")!=-1){
            filePath=new File("F:/file/"+chunkName.substring(0,chunkName.indexOf(".")));
        }else{
            filePath=new File("F:/file/"+chunkName.substring(0,chunkName.indexOf("_")));
        }
        if(filePath.exists()){
            //判断文件位置
            File file = new File(filePath,chunkName);
            if(file.exists()){
                return true;
            }
        }
        return false;
    }

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ResponseBody
    public int uploadFile(@RequestParam("name") String name,
                          @RequestParam("file") MultipartFile file){
        File filePath=new File("F:/file/"+name.substring(0,name.indexOf("_")));
        if (!filePath.exists()){
            filePath.mkdir();
        }

        File winFile=new File(filePath,"/"+name);

        try {

            file.transferTo(winFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println("=============file:"+filePath);
        return 1;
    }


    @RequestMapping(value = "/upload/merge",method = RequestMethod.POST)
    @ResponseBody
    public MsgVo<Object> fileMerge(@RequestParam("fileName") String fileName,
                                   @RequestParam("size") int size,
                                   @RequestParam("type") String type){
        System.out.println(size);
//        合文件
//        打开文件
        File filePath = new File("F:/file/" + fileName);
        if(!filePath.exists()){
            return MsgVo.error("文件不存在",null);
        }
        File savePath=new File(filePath,fileName+"."+type.substring(type.indexOf("/")+1));
//        logger.info("文件后缀为{}"+savePath);
        BufferedOutputStream bufferedOutputStream;
        BufferedInputStream bufferedInputStream;
        File file;
        try {
            bufferedOutputStream=new BufferedOutputStream(new FileOutputStream(savePath));
            for (int i=0;i<size;i++){
                file=new File(filePath,fileName+"_"+(i+1));
                bufferedInputStream=new BufferedInputStream(new FileInputStream(file));
                byte[] b=new byte[1024];
                int current;
                while ((current=bufferedInputStream.read(b))!=-1){
                    bufferedOutputStream.write(b,0,current);
                }
                bufferedInputStream.close();
                file.delete();
            }
            bufferedOutputStream.close();
            Map<String,Object> map = new HashMap<>();
            map.put("name",fileName);
            map.put("path","/move/"+savePath.getName());
//            logger.info(savePath.getName());
            return MsgVo.ok("文件长传成功",map);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return MsgVo.error("位置错误，文件上传失败",null);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
