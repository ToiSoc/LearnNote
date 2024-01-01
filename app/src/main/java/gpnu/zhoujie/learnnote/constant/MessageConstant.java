package gpnu.zhoujie.learnnote.constant;

import java.util.HashMap;

public class MessageConstant {
    public static HashMap infoMap = null;
    public static HashMap normalMap = null;

    static{
        normalMap = new HashMap();
        normalMap.put("0","请输入账号！！");
        normalMap.put("1","请输入密码！！");
        normalMap.put("2","请输入姓名！！");
        normalMap.put("3","请输入联系方式！！");
        normalMap.put("5","请输入年龄！！");
    }

    public static final String ACCOUNT_ISNULL = "账号不能为空！";
    public static final String PWD_ISNULL = "密码不能为空！";
    public static final String PWD_ENDURE_ISNULL = "请输入确认密码！";
    public static final String NAME_ISNULL = "请输入姓名！";
    public static final String PHONE_ISNULL = "请输入联系方式！";
    public static final String AGE_ISNULL = "请输入年龄！";

    public static final String REGISTER_TRUE = "注册成功！";
    public static final String REGISTER_FALSE = "注册失败！";

    public static final int INTERRUPT_STATUS = 200;

    public static final int COUNT_ZERO_ERR = 0;

    public static final String NOT_ACCOUNT_ERR_PWD = "账号不存在或密码错误";

    public static final String INPUT_PWD = "请输入密码！";

    public static final String PWD_DIFFERENT = "两次输入密码不一致！";

    public static final String PWD_CHANGE_SUCCESS = "密码修改成功！";

    public static final String PWD_CHANGE_FAIL = "密码修改失败！";
    public static final String UNKNOWN_ERROR = "未知错误！";

    /**
     * 个人信息修改状态
     */
    public static final String MESSAGE_UPDATE_SUCCESS = "个人信息修改成功！";
    public static final String MESSAGE_UPDATE_FAIL = "个人信息修改失败！";

    public static final String MESSAGE_NO_UPDATE = "个人信息无修改！";


    /**
     * 笔记保存、删除
     */
    public static final String NOTE_DEL_FAIL = "笔记删除失败！";
    public static final String NOTE_DEL_SUCCESS = "笔记删除成功！";
    public static final String NOTE_SAVE_FAIL = "笔记保存失败！";
    public static final String NOTE_SAVE_SUCCESS = "笔记保存成功！";

    /**
     * 图标修改
     */
    public static final String AVATAR_UPDATE_SUCCESS = "图标修改成功！";
    public static final String AVATAR_NOT_UPDATE = "图标无修改！";

}
