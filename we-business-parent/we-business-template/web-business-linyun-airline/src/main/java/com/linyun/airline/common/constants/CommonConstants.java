package com.linyun.airline.common.constants;

import com.linyun.airline.common.access.AccessConfig;
import com.linyun.airline.common.access.sign.MD5;

/**
 * 通用常量
 * @author 朱晓川
 *
 */
public class CommonConstants {

	/**超级管理员*/
	public static final String SUPER_ADMIN = "lxl";

	/**数据状态:@see DataStatusEnum*/
	/**数据状态-启用中*/
	public static final Integer DATA_STATUS_VALID = 1;

	/**数据状态-冻结*/
	public static final Integer DATA_STATUS_INVALID = 0;

	/**数据状态-删除*/
	public static final Integer DATA_STATUS_DELETE = 2;

	/**销售渠道类型-代理-代码，不可更改*/
	public static final String SALES_CHANNEL_PROXY_TYPECODE = "0001";

	/**销售渠道类型-非代理-代码，不可更改*/
	public static final String SALES_CHANNEL_NONPROXY_TYPECODE = "0002";

	/**项目字符编码*/
	public static final String CHARACTER_ENCODING_PROJECT = "UTF-8";

	/**
	 * 验证码-session key
	 */
	public static final String CONFIRMCODE = "confirmcode";

	/**
	 * 图片服务器地址
	 */
	public static final String IMAGES_SERVER_ADDR = "http://oluwc01ms.bkt.clouddn.com/";

	/**无效数据id*/
	public static final int INVALID_DATA_ID = -1;

	/**用户初始密码*/
	public static final String INITIAL_PASSWORD = MD5.sign("000000", AccessConfig.password_secret,
			AccessConfig.INPUT_CHARSET);

	/**个人信息id，不可随意更改，必须与数据库保持一致*/
	public static final int PERSON_ID = 43;

	/**操作台id，不可随意更改，必须与数据库保持一致*/
	public static final int DESKTOP_ID = 44;
}
