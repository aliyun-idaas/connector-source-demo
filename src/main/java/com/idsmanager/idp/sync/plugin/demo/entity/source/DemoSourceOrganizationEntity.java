package com.idsmanager.idp.sync.plugin.demo.entity.source;

import com.idsmanager.idp.sync.SyncObjectType;
import com.idsmanager.idp.sync.SyncOperationType;
import com.idsmanager.idp.sync.core.infrastructure.source.SourceDataItem;

/**
 * 2021/12/10 11:25
 * <p>来源组织实体类
 *
 * @author xbj
 * @class DemoSourceOrganizationEntity
 * @since
 */
public class DemoSourceOrganizationEntity extends DemoSourceBaseEntity implements SourceDataItem {
    /**
     * 组织名称
     */
    private String orgName;

    /**
     * 组织id
     */
    private String orgId;


    /**
     * 父级组织id
     */
    private String parentOrgId;


    @Override
    public String uniqueId() {
        return null;
    }

    @Override
    public String uniqueIdType() {
        return null;
    }

    @Override
    public String displayName() {
        return null;
    }

    @Override
    public SyncObjectType objectType() {
        return null;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public void setEnabled(boolean enabled) {

    }

    @Override
    public String getParentUniqueId() {
        return null;
    }

    @Override
    public boolean isSupportRePush() {
        return false;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getParentOrgId() {
        return parentOrgId;
    }

    public void setParentOrgId(String parentOrgId) {
        this.parentOrgId = parentOrgId;
    }
}
