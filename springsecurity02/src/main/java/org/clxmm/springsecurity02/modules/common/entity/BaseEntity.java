package org.clxmm.springsecurity02.modules.common.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author clx
 * @date 2020/7/9 15:57
 */
@Data
public abstract class BaseEntity<T extends Model> extends BaseAddEntity<T> {

    /**
     * 修改时间 - 过去分词表示被动更新
     */
    @ApiModelProperty(value = "修改时间")
    @TableField(value = "gmt_modified", fill = FieldFill.INSERT_UPDATE)
//    @Future(message = "修改时间必须是将来时间")
    private Date gmtModified;


}
