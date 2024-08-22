package com.yuki.Controller;

import com.yuki.Domain.Entity.Kaiwa;
import com.yuki.Domain.Entity.Vo.AudioVo;
import com.yuki.Domain.Entity.Vo.FileVo;
import com.yuki.Domain.Entity.dto.RequestDto;
import com.yuki.Domain.ResponseResult;
import com.yuki.Service.KaiwaService;
import com.yuki.Utils.FileUtil;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@RestController
@RequestMapping("/kaiwa")
public class KaiwaController {

    @Value("${kaiwa-path}")
    private String rootPath;

    @Autowired
    KaiwaService kaiwaService;

    @GetMapping("/info")
    public ResponseResult getKaiWaInfo(){
        return kaiwaService.getKaiWaInfo();
    }

//    /**
//     * 获取获取指定路径下的文件与文件夹（不进行递归）
//     * 直接访问本地文件
//     * @return
//     */
//    @PostMapping("/getCurrentFiles")
//    public ResponseResult GetCurrentPathFiles(@RequestBody RequestDto request){
//        String currentPath = rootPath+request.path;
////        System.out.println("当前所在的路径: "+currentPath);
//        List<FileVo> fileList = FileUtil.getCurrentFolder(rootPath, currentPath);
//        return ResponseResult.okResult(fileList);
//    }

    /**
     * 获取获取指定路径下的文件与文件夹（不进行递归）
     * 通过数据库
     * @return
     */
    @PostMapping("/getCurrentFiles")
    public ResponseResult GetCurrentPathFiles(@RequestBody RequestDto request){
        String currentPath = rootPath+request.path;
        Kaiwa kaiwa = kaiwaService.getCurrentFile(request.path);
        List<Kaiwa> kaiwaList = new ArrayList<>();
        if(kaiwa.getIsFile() != 1){
            // 不是文件
            kaiwaList = kaiwaService.getCurrentFoldersFiles(kaiwa.getId());
        }else{
            return ResponseResult.errorResult(400,"未文件夹");
        }
        return ResponseResult.okResult(200,"success",kaiwaList);
    }

    /**
    @GetMapping("getAudio")
    public ResponseResult getAudio(@RequestParam String filePath){
        String currentPath = rootPath+filePath;
        File audioFile = new File(currentPath);

        if (!audioFile.exists() || !audioFile.isFile()) {
            return ResponseResult.errorResult(400,"请求的文件不存在");
        }
        // 获取同名字幕文件
        String subtitleFilePath = filePath.replace(".mp3", ".lrc");

        File subtitleFile = new File(rootPath + subtitleFilePath);
        AudioVo audioVo = new AudioVo();
        audioVo.setAudioFileName(audioFile.getName());
        try {
            audioVo.setAudioData(Base64.getEncoder().encodeToString(Files.readAllBytes(audioFile.toPath())));
            if (subtitleFile.exists() && subtitleFile.isFile()) {
                audioVo.setSubtitleFileName(subtitleFile.getName());
                audioVo.setSubtitleData(Base64.getEncoder().encodeToString(Files.readAllBytes(subtitleFile.toPath())));
            }
        }catch (Exception e){
            return ResponseResult.errorResult(400,"未知错误");
        }

        return ResponseResult.okResult(200,"获取音频成功",audioVo);

    }
    */



}
