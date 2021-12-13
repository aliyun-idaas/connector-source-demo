package com.idsmanager.idp.sync.plugin.demo.entity.target;

import com.idsmanager.idp.sync.SyncOperationType;

import java.io.Serializable;

/**
 * 2021/12/10 12:46
 * <p> 目标基础抽象类
 *
 * @author xbj
 * @class DemoTargetBaseEntity
 * @since
 */
public abstract class DemoTargetBaseEntity implements Serializable {

    private String defaultSourceRootUuid;
    private String defaultTargetRootUuid;
    private String recordExchangeUuid;
    private SyncOperationType syncOperationType;


    public String getDefaultSourceRootUuid() {
        return defaultSourceRootUuid;
    }

    public void setDefaultSourceRootUuid(String defaultSourceRootUuid) {
        this.defaultSourceRootUuid = defaultSourceRootUuid;
    }

    public String getDefaultTargetRootUuid() {
        return defaultTargetRootUuid;
    }

    public void setDefaultTargetRootUuid(String defaultTargetRootUuid) {
        this.defaultTargetRootUuid = defaultTargetRootUuid;
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
