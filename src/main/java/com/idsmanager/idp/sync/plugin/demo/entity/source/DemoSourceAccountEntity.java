package com.idsmanager.idp.sync.plugin.demo.entity.source;

import com.idsmanager.idp.sync.SyncObjectType;
import com.idsmanager.idp.sync.SyncOperationType;
import com.idsmanager.idp.sync.core.infrastructure.source.SourceDataItem;

/**
 * 2021/12/10 11:01
 * <p>来源用户实体类
 *
 * @author xbj
 * @class DemoSourceAccountEntity
 * @since
 */
public class DemoSourceAccountEntity extends DemoSourceBaseEntity implements SourceDataItem {

    /**
     * 用户id
     **/
    private String userId;

    /**
     * TODO 唯一标识字段
     **/
    @Override
    public String uniqueId() {
//        return userId;
        return null;
    }

    /**
     * TODO 唯一标识的字段名
     **/
    @Override
    public String uniqueIdType() {
//        return "userId";
        return null;
    }

    /**
     * TODO 用户显示名称字段
     **/
    @Override
    public String displayName() {
        return null;
    }

    @Override
    public SyncObjectType objectType() {
        return SyncObjectType.USER;
    }

    /**
     * TODO 用户启用状态字段
     **/
    @Override
    public boolean isEnabled() {
//        return enabled;
        return false;
    }

    @Override
    public void setEnabled(boolean enabled) {
//        this.enabled = enabled;
    }

    /**
     * TODO 用户所属组织唯一标识字段
     **/
    @Override
    public String getParentUniqueId() {
        return null;
    }

    /**
     * 是否支持失败重推
     **/
    @Override
    public boolean isSupportRePush() {
        return false;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
