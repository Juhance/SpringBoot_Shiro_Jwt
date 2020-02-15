package com.cqust.enitiy;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

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
@TableName("tb_users")
public class User implements Serializable  {
	
	/**
	 * –Ú¡–∫≈ID
	 */
	private static final long serialVersionUID = 7681718663549560099L;
	// Ù–‘
	@TableId(value="user_id",type=IdType.AUTO)
	private int uid;
	@TableField(value="user_name")
	private String userName;
	@TableField(value="login_name")
	private String loginName;
	@TableField(value="password")
	private String password;
	@TableField(value="salt")
	private String salt;
	@TableField(value="sex")
	private String sex;
	@TableField(value="email")
	private String email;
	@TableField(value="header_url")
	private String headerUrl;
	@TableField(value="phone")
	private String phone;
	@TableField(value="status")
	private int status;
	@TableField(value="note")
	private String note;
	@TableField(value="login_method")
	private String loginMethod;
	@TableField(value="create_time")
	private Date createTime;
	@TableField(value="update_time")
	private Date updateTime;
	
}
