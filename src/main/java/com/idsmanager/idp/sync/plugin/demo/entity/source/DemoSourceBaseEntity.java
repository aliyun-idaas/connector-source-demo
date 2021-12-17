package com.idsmanager.idp.sync.plugin.demo.entity.source;

import com.idsmanager.idp.sync.SyncOperationType;

import java.io.Serializable;

/**
 * 2021/12/10 12:46
 * <p> 来源抽象类
 *
 * @author xbj
 * @class DemoSourceBaseEntity
 * @since
 */
public abstract class DemoSourceBaseEntity implements Serializable {

    private String defaultParentUuid;
    private String defaultSourceRootUuid;
    private String recordExchangeUuid;
    private SyncOperationType syncOperationType;

    public String getDefaultParentUuid() {
        return defaultParentUuid;
    }

    public void setDefaultParentUuid(String defaultParentUuid) {
        this.defaultParentUuid = defaultParentUuid;
    }

    public String getDefaultSourceRootUuid() {
        return defaultSourceRootUuid;
    }

    public void setDefaultSourceRootUuid(String defaultSourceRootUuid) {
        this.defaultSourceRootUuid = defaultSourceRootUuid;
    }

    public String getRecordExchangeUuid() {
        return recordExchangeUuid;
    }

    public void setRecordExchangeUuid(String recordExchangeUuid) {
        this.recordExchangeUuid = recordExchangeUuid;
    }

    public SyncOperationType getSyncOperationType() {
        return syncOperationType;
    }

    public void setSyncOperationType(SyncOperationType syncOperationType) {
        this.syncOperationType = syncOperationType;
    }
}
