package com.cqust.enitiy;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
@TableName("tb_functions")
public class Function implements Serializable{

	private static final long serialVersionUID = 7780523849120939603L;
	@TableId(value="func_id",type=IdType.AUTO)
    private Integer funcId;
	@TableField(value="func_name")
    private String funcName;
	@TableField(value="func_url")
    private String funcUrl;
	@TableField(value="func_code")
    private String funcCode;
	@TableField(value="parent_id")
    private Integer parentId;
	@TableField(value="func_type")
    private Integer funcType;
	@TableField(value="status")
    private Integer status;
	@TableField(value="sort_num")
    private Integer sortNum;
	@TableField(value="create_time")
    private Date createTime;
	@TableField(value="update_time")
    private Date updateTime;   
}