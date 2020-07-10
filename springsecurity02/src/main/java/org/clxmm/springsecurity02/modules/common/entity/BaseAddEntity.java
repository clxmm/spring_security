package org.clxmm.springsecurity02.modules.common.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Past;
import java.util.Date;

/**
 * @author clx
 * @date 2020/7/9 15:53
 */
@Data
public abstract class BaseAddEntity<T extends Model> {

    @ApiModelProperty(value = "创建日期")
    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    @Past(message = "创建时间必须是过去时间")
    private Date gmtCreate;


}
