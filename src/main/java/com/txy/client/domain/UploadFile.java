package com.txy.client.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Data
@Accessors(chain = true)
//文件实体类
public class UploadFile {
    private String uuid;
    private String name;
    private String size;
    private String type;
    private String address;
    private String time;
    private String password;

}
