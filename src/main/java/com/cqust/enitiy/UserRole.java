package com.cqust.enitiy;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@TableName("tb_user_role")
public class UserRole {
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;
	@TableField(value="user_id")
    private Integer userId;
	@TableField(value="role_id")
    private Integer roleId;
}