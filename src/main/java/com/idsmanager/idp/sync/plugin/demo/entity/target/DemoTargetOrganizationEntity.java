package com.idsmanager.idp.sync.plugin.demo.entity.target;

import com.idsmanager.idp.sync.SyncObjectType;
import com.idsmanager.idp.sync.core.infrastructure.target.TargetDataItem;

/**
 * 2021/12/10 11:25
 * <p>目标组织实体类
 *
 * @author xbj
 * @class DemoTargetOrganizationEntity
 * @since
 */
public class DemoTargetOrganizationEntity extends DemoTargetBaseEntity implements TargetDataItem {

    /**
     * 组织uuid
     **/
    private String organizationUuid;

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
     * TODO 组织显示名称字段
     **/
    @Override
    public String displayName() {
        return null;
    }

    @Override
    public SyncObjectType objectType() {
        return SyncObjectType.ORGANIZATION;
    }

    /**
     * TODO 组织启用状态字段
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
     * TODO 父级组织唯一标识字段
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

    public String getOrganizationUuid() {
        return organizationUuid;
    }

    public void setOrganizationUuid(String organizationUuid) {
        this.organizationUuid = organizationUuid;
    }

}
