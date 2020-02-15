package com.cqust.enitiy;

import java.io.Serializable;

import org.springframework.stereotype.Component;

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
@TableName("tab_record")
public class Record implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Ù–‘
	@TableId(value="RID",type=IdType.AUTO)
	private int RID;
	@TableField(value="UID")
	private int uid;
	@TableField(value="RecordType")
	private String RecordType;
	@TableField(value="RecordTime")
	private String  RecordTime;
	@TableField(value="Money")
	private Float   Money;
	@TableField(value="Memo")
	private String  Memo;

	
}
