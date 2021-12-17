package com.idsmanager.idp.sync.plugin.demo.business;

import com.idsmanager.idp.sync.MajorType;
import com.idsmanager.idp.sync.MinorType;
import com.idsmanager.idp.sync.core.infrastructure.ConnectionConfiguration;

/**
 * 2021/12/10 10:40
 * <p> 来源和目标管理中，通用的字段定义
 *
 * @author xbj
 * @class DemoConfiguration
 * @since
 */
public class DemoConfiguration implements ConnectionConfiguration {

    //**********************基础必须的字段**********************//
    /**
     * 唯一ID，通常对应的数据库id
     */
    private long id;
    /**
     * 唯一标识，通常对应的数据库uuid
     */
    private String uuid;
    /**
     * 名称
     */
    private String name;
    /**
     * 详细配置的JSON字符串
     */
    private String jsonConfig;

    //**********************基础必须的字段**********************//

    //**********************自定义字段开始**********************//

    //TODO 在这里定义创建来源和目标时，通用的字段， 参数按实际调用接口需要的参数添加
    //比如，在此添加一个服务的默认接口url参数
    private String baseUrl;

    //**********************自定义字段结束**********************//

    public void setId(long id) {
        this.id = id;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setJsonConfig(String jsonConfig) {
        this.jsonConfig = jsonConfig;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public String getMajorType() {
        return MajorType.APP_STANDARD.name();
    }

    /**
     * //TODO 修改为具体的子类型
     **/
    @Override
    public String getMinorType() {
        return MinorType.DEMO_SCHEMA.name();
    }

    @Override
    public String getJsonConfig() {
        return jsonConfig;
    }




    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
