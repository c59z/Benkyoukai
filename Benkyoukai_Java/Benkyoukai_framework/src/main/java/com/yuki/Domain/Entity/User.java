package com.yuki.Domain.Entity;

//import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;


//@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
//@TableName("sys_user")
public class User {
    //主键@TableId
    private Long id;

    //用户名
    private String username;
    //昵称
    private String nickname;
    //密码
    private String password;
    //用户类型：0代表普通用户，1代表管理员
    private String type;
    //账号状态（0正常 1停用）
    private Integer status;
    //邮箱
    private String email;
    //手机号
    private String phonenumber;
    //用户性别（0男，1女，2未知）
    private String sex;
    //头像
    private String avatar;

    // 角色身份
    private Integer role_id;

    //创建时间
    private LocalDateTime createDate;
    //更新时间
    private Date updateDate;

}

