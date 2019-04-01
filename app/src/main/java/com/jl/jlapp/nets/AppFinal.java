package com.jl.jlapp.nets;

/**
 * Created by fyf on 2017/10/19.
 */

public class AppFinal {

    public static final String SHAREDPREFERENCES_FILE_NAME= "JLAppData";
    public static final String VERSIONCODE= "versionCode";//版本号
    public static final String VERSIONNAME= "versionName";//版本名
    public static final String USERID= "userId";//空值
    public static final String USERPHONE= "mobile";//空值
    public static final String USERPICURL= "userPicUrl";//空值
    public static final String USERNAME= "userName";//空值
    public static final String ISVIP= "isVip";//空值

    public static final String S_NULL = "";//空值
    public static final String NET_ERROR = "网络错误";
    public static final String T_NET_CON_FAIL = "网络连接失败";
    public static final String BASEURL = "http://39.105.115.214:8080/JLMIS/";
    public static final String BASE_IP = "http://39.105.115.214";
    public static final String BASEURL_JLKF = "http://39.105.115.214:8002/JLKF/";
    //public static final String BASEURL = "http://172.16.8.39:8080/JLMIS/";

    public static final String APKURL = "http://39.105.115.214:8080/jlapk/jlfood.apk";


    public static final int ERR_SUCCESS = 0;
    // 图片压缩默认尺寸
    public static final int PIC_W = 640;
    public static final int PIC_H = 640;

    // 图片压缩默认最大尺寸
    public static final long PIC_MAX_SIZE = 1024 * 200;
    /**
     * 标签数
     */
    public static final int TAB_COUNT = 3;
    public static final String TAB_HOME = "首页";
    public static final String TAB_MAIL_LIST = "通讯录";
    public static final String TAB_MY = "";
    /**
     * 男0
     * 女1
     */
    public static final String GENDER_MAN = "0";
    public static final String GENDER_WOMAN = "1";
    public static final String SEX_MAN = "男";
    public static final String SEX_WOMEN = "女";

    public static final String SCHOOL_ADMIN = "学校管理员";
    public static final String SYSTEM_ADMIN = "教育局管理员";
    public static final String PRINCIPAL_ADMIN = "学校信息员";

    public static final int DANGER_LIST = 1;
    public static final int NOTICE_LIST = 2;
    public static final int TASK_LIST = 3;
    public static final int SAFETY_TYPE_LIST = 5;
    public static final int LOCATION_LIST = 6;


    public static final String SP_CHATINFO = "chatInfo";

    public static class Share {
        public static final String SP_DEVICEID = "deviceId";//设备唯一ID
        public static final String SP_TOKEN = "token";//访问令牌
        public static final String SP_AVATAR = "avatar";//头像
        public static final String SP_USER_NAME = "user_name";//用户名
        public static final String SP_PHONE = "phone";
        public static final String SP_NICKNAME = "nickname";//昵称
        public static final String SP_SCHOOLNAME = "schoolName";
        public static final String SP_SCHOOLID = "schoolId";
        public static final String SP_T = "t";//账号类型
        public static final String SP_ACCOUNTID = "accountId";//账户id
        public static final String SP_HXGROUPID = "hxgroupId";//环信群聊id
        public static final String SP_LONGITUDE = "longitude";
        public static final String SP_LATITUDE = "latitude";
        public static final String SP_ADDRESS = "address";
//        public static final String SP_ID = "id";
    }

    /**
     * 参数请求
     */
    public static class Nets {
        public static final String P_SORTTYPE = "sortType";
        public static final String P_PRICESORT = "priceSort";
        public static final String P_SEARCHNAME = "searchName";//手机软件系统版本号
        public static final String P_ISHASGOODS = "isHasGoods";//手机名称
        public static final String P_MINPRICE = "minPrice";//屏幕的宽
        public static final String P_MAXPRICE = "maxPrice";//屏幕的高
        public static final String P_BRANDNAME = "brandName";//当前的app版本
        public static final String P_CLASSIFICATIONID = "classificationId";//手机号




        public static final String P_DEVICEID = "deviceId";//设备ID
        public static final String P_PASSWORD = "password";//密码
        public static final String P_CLIENTID = "clientId";//个推ID
        public static final String P_CHECKCODE = "checkcode";//短信验证码
        public static final String P_TOKEN = "token";//访问令牌
        public static final String P_ACCOUNTID = "accountId";
        public static final String P_LONGITUDE = "longitude";//经度
        public static final String P_LATITUDE = "latitude";//纬度
        public static final String P_OLDPASSWORD = "oldPassword";
        public static final String P_INFO = "info";//反馈内容
        public static final String P_COMPLATE = "complate";//完成  未完成
        public static final String P_TASKID = "taskId";//任务id
        public static final String P_NOTICEID = "noticeId";//通知消息id
        public static final String P_DANGERID = "dangerId";//隐患id
        public static final String P_TYPEID = "typeId";
        public static final String P_SCHOOLIDS = "schoolIds";
        public static final String P_PAGE = "page";//页数
        public static final String P_PAGESIZE = "pagesize";//页数大小
        public static final String P_ATTACHES = "attaches";//附件id
        public static final String P_T = "";//隐患类型
        public static final String P_TYPE = "type";//隐患类型
        public static final String P_STATE = "state";//隐患完成状态

    }

}
