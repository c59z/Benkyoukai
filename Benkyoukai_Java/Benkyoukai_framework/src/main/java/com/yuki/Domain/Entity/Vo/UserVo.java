package com.yuki.Domain.Entity.Vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {
    private Long id;
    //用户名
    private String username;
    //昵称
    private String nickname;
//    //用户类型：0代表普通用户，1代表管理员
//    private String type;
    //邮箱
    private String email;
    //用户性别（0男，1女，2未知）
    private String sex;
    //头像
    private String avatar;
//    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createDate;
//    //更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;

}
