package gpnu.zhoujie.learnnote.dao.impl;

import android.content.ContentValues;

import java.util.List;

import gpnu.zhoujie.learnnote.entity.AvatarImage;
import gpnu.zhoujie.learnnote.entity.Note;
import gpnu.zhoujie.learnnote.entity.PersonMsg;

/**
 * 注册接口实现
 */
public interface AdminDaoImpl {

    /**
     * 登录
     */
    public int loginAdmin(String ...da);

    /**
     * 注册
     */
    public int registerAdmin(String ...data);

    /**
     * 修改密码
     */
    public int changePassword(String ...data);

    /**
     * 修改个人信息
     */
    public int changeMessage(String id,ContentValues values);

    /**
     * 获取个人信息
     */
    public List<PersonMsg> getMessage(String id);

    /**
     * 删除笔记
     */
    public int delNote(String ...data);

    /**
     * 保存笔记
     */
    public int saveNote(String ...data);

    /**
     * 读取数据
     */
    public List<Note> getListNote(String account);

    /**
     * 返回数据
     */
    public List<Note> showNote(String id);

    /**
     * 获取图标ID
     */
    public List<AvatarImage> getAvatarId(String account);
}
