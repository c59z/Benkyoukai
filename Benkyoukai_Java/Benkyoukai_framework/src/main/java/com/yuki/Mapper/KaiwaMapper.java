package com.yuki.Mapper;

import com.yuki.Domain.Entity.Kaiwa;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface KaiwaMapper {

    List<Kaiwa> getKaiWaInfo();

    Kaiwa getCurrentFile(String path);

    List<Kaiwa> getCurrentFoldersFiles(int id);
}
