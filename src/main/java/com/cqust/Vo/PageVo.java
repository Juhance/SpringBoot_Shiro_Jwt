package com.cqust.Vo;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString
public class PageVo implements Serializable{

	private static final long serialVersionUID = -4480854826795587920L;

	@Getter @Setter
	private long pageNo = 1L;
	@Getter @Setter
	private long pageSize = 10L;
}
