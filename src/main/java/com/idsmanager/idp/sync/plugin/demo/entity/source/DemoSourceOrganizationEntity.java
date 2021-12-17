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
}
