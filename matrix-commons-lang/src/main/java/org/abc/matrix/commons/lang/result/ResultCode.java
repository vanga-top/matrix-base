package org.abc.matrix.commons.lang.result;

/**
 * 1区段 表示成功
 * 5区段  表示系统级异常
 * 2、3区段  表示业务逻辑异常 如未登陆，参数不正确
 * 4004  表示未知异常，（自定义异常MSG）
 *
 * Created by wanjia on 16/10/26.
 */
public  interface ResultCode {

     int SUCCESS = 1001;
     String SUCCESS_MSG = "成功";

     int DATA_NOT_EXIST = 2001;
     String DATA_NOT_EXIST_MSG = "数据不存在";

     int DATA_NOT_UPDATE = 2002;
     String DATA_NOT_UPDATE_MSG = "数据未更新成功";

     int DATA_LIMIT_EXCEED = 2003;
     String DATA_LIMIT_EXCEED_MSG = "数据大小超出限制";

     int ILLEGAL_DATA_ACCESS = 3001;
     String ILLEGAL_DATA_ACCESS_MSG = "没有权限";

     int DATA_CONFLICT = 3002;
     String DATA_CONFLICT_MSG = "数据冲突";

     int INVALID_PARAMETERS = 3003;
     String INVALID_PARAMETERS_MSG = "参数不正确";

     int ILLEGAL_STATE_TRANSFER = 3005;
     String ILLEGAL_STATE_TRANSFER_MSG = "不允许的状态更改";

     int UNKNOW = 4004;
     String UNKNOW_MSG = "未知异常";

     int FLOW_LIMITED = 5001;
     String FLOW_LIMITED_MSG = "系统限流";



}
