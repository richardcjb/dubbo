package com.uxuexi.core.web.form;

import lombok.Data;

/**
 * 用于接收bootstrap插件Datatables页面ajax请求发送给服务端的参数
 */
@Data
public abstract class DataTablesParamForm implements SQLParamForm {

	/**
	 * 请求序号
	 * <p>
	 * 因为ajax请求是异步的，你不知道返回的数据对应的是你什么时候发出的请求，
	 * 因此需要一个序号进行对应
	 */
	private int draw;

	/**第一条数据的起始位置，比如0代表第一条数据*/
	private int start;

	/**
	 * 告诉服务器每页显示的条数，这个数字会等于返回的 data集合的记录数
	 */
	private int length;

}
