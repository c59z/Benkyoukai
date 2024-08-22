package com.yuki.Service.impl;

import com.yuki.Domain.Entity.Kaiwa;
import com.yuki.Domain.ResponseResult;
import com.yuki.Mapper.KaiwaMapper;
import com.yuki.Service.KaiwaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KaiwaServiceImpl implements KaiwaService {

    @Autowired
    KaiwaMapper kaiwaMapper;

    @Override
    public ResponseResult getKaiWaInfo() {
        List<Kaiwa> kaiwaInfo = kaiwaMapper.getKaiWaInfo();
        return ResponseResult.okResult(200,"获取会话练习成功",kaiwaInfo);
    }

    @Override
    public Kaiwa getCurrentFile(String path) {
        return kaiwaMapper.getCurrentFile(path);
    }

    @Override
    public List<Kaiwa> getCurrentFoldersFiles(int id) {
        return kaiwaMapper.getCurrentFoldersFiles(id);
    }
}
