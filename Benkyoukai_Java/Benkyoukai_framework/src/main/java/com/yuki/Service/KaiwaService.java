package com.yuki.Service;

import com.yuki.Domain.Entity.Kaiwa;
import com.yuki.Domain.ResponseResult;

import java.util.List;

public interface KaiwaService {

    ResponseResult getKaiWaInfo();


    Kaiwa getCurrentFile(String path);

    List<Kaiwa> getCurrentFoldersFiles(int id);
}
