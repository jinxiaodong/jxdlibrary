package com.project.xiaodong.fflibrary.task;

import java.io.Serializable;

/**
 */
public class MSG implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * task是否执行成功
	 * */
	private Boolean isSuccess;
	/**
	 * task执行返回的message
	 * */
	private String msg;
	/**
	 * task执行返回的data,可能为空
	 * */
	private Object obj;

	public MSG() {
		super();
	}

	public MSG(Boolean isSuccess, String msg) {
		super();
		this.isSuccess = isSuccess;
		this.msg = msg;
	}

	public MSG(Boolean isSuccess, String msg, Object obj) {
		super();
		this.isSuccess = isSuccess;
		this.msg = msg;
		this.obj = obj;
	}

	public MSG(Boolean isSuccess, Object obj) {
		super();
		this.isSuccess = isSuccess;
		this.obj = obj;
	}

	/**
	 * task是否执行成功
	 * */
	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	/**
	 * task执行返回的message
	 * */
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * task执行返回的data,可能为空
	 * */
	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	@Override
	public String toString() {
		return "MSG [isSuccess=" + isSuccess + ", msg=" + msg + ", obj=" + obj
				+ "]";
	}
}
