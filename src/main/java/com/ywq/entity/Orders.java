package com.ywq.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class Orders implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

//    private String number;

    // 1 under paying 2 under sending 3 send 4 done 5 cancel
    private Integer status;

//    private Long userId;

//    private Long addressBookId;

//    private LocalDateTime orderTime;

    private LocalDateTime checkoutTime;

//    private Integer payMethod;

//    private BigDecimal amount;

    private String remark;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

//    private String phone;

//    private String address;

//    private String userName;

//    private String consignee;
}
