package com.cqust.enitiy;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_roles")
public class Role {

	@TableId(value="role_id",type=IdType.AUTO)
    private Integer roleId;
	@TableField(value="role_name")
    private String roleName;
	@TableField(value="note")
    private String note;
	@TableField(value="system")
    private Boolean system;
	@TableField(value="status")
    private Integer status;
	@TableField(value="create_time")
    private String createTime;
	@TableField(value="update_time")
    private String updateTime;

}