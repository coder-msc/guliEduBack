package com.atguigu.tools.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/*
 * 文章内容传递到MQ 然后放入ES中 供查询用
 * */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ESBlogDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    //        @TableId(value = "id", type = IdType.AUTO)
    private String id;

//    private String userId;

//        private String blogTag;
//
        private String blogAvatar;

    private String title;

    private String description;

//        private String content;

    private Date createdTime;


}
