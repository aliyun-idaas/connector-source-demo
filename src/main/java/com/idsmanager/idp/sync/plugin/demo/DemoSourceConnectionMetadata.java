package com.idsmanager.idp.sync.plugin.demo;

import com.alibaba.fastjson.JSONObject;
import com.idsmanager.idp.sync.InvalidConfigException;
import com.idsmanager.idp.sync.MajorType;
import com.idsmanager.idp.sync.MinorType;
import com.idsmanager.idp.sync.SyncObjectType;
import com.idsmanager.idp.sync.core.infrastructure.ConnectionMetadataImpl;
import com.idsmanager.idp.sync.core.infrastructure.ConnectionTestResult;
import com.idsmanager.idp.sync.core.infrastructure.mapping.AttributeDescriptor;
import com.idsmanager.idp.sync.core.infrastructure.mapping.AttributeGetter;
import com.idsmanager.idp.sync.core.infrastructure.source.SourceConnectionConfiguration;
import com.idsmanager.idp.sync.core.infrastructure.source.SourceConnectionMetadata;
import com.idsmanager.idp.sync.core.infrastructure.source.SourceConnectionPlugin;
import com.idsmanager.idp.sync.core.infrastructure.source.SourceDataPullClient;
import com.idsmanager.idp.sync.plugin.demo.attribute.DemoAttributeGetter;
import com.idsmanager.idp.sync.plugin.demo.attribute.DemoSourceDefaultAttributeDefiner;
import com.idsmanager.idp.sync.plugin.demo.business.source.DemoSourceClientConfiguration;
import com.idsmanager.idp.sync.plugin.demo.client.DemoSourceDataPullClient;
import com.idsmanager.micro.commons.web.filter.RIDHolder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * 2021/12/13 11:18
 * <p>
 *
 * @author xbj
 * @class DemoSourceConnectionMetadata
 * @since
 */
@SourceConnectionPlugin
public class DemoSourceConnectionMetadata extends ConnectionMetadataImpl implements SourceConnectionMetadata {

    private static final Logger LOG = LoggerFactory.getLogger(DemoSourceConnectionMetadata.class);

    /**
     * TODO 自定义主类型和子类型
     * 一般情况下，定义子类型即可，注意，子类型需要以 _SCHEMA 结尾，才能正常获取到表单json信息
     */
    public DemoSourceConnectionMetadata() {
        super(MajorType.APP_STANDARD.name(), MinorType.DEMO_SCHEMA.name());
    }

    /**
     * TODO 是否支持被动接同步数据-方法实现
     *
     * @return boolean true:支持 false：不支持
     */
    @Override
    public boolean supportPassiveReception() {
        return false;
    }

    /**
     * TODO 是否支持被动接收指定类型的数据-方法实现
     *
     * @param syncObjectType 同步对象类型
     * @return boolean true:支持 false：不支持
     */
    @Override
    public boolean supportPassiveReception(SyncObjectType syncObjectType) {
        return false;
    }

    /**
     * TODO 是否支持主动拉取同步数据-方法实现 (页面可能会配置主动拉取，但是能不能拉取通过这里设定)
     *
     * @return boolean true:支持 false：不支持
     */
    @Override
    public boolean supportInitiativePullData() {
        return true;
    }

    /**
     * TODO 是否支持主动拉取指定类型的数据-方法实现
     *
     * @return boolean true:支持 false：不支持
     */
    @Override
    public boolean supportInitiativePullData(SyncObjectType syncObjectType) {
//        return syncObjectType == SyncObjectType.USER || syncObjectType == SyncObjectType.ORGANIZATION;
        return false;
    }

    /**
     * 解析前端传递的配置信息(前端将整个数据源配置对象转为了json字符串传到后端，后端需要自己解析得到具体参数值)
     */
    @Override
    public SourceConnectionConfiguration parse(String json) throws InvalidConfigException {
        return parseJsonConfig(json);
    }

    /**
     * //TODO 解析前端传递的配置信息(前端将整个数据源配置对象转为了json字符串传到后端，后端需要自己解析得到具体参数值)
     *
     **/
    private DemoSourceClientConfiguration parseJsonConfig(String jsonConfig) throws InvalidConfigException {
        JSONObject json = doValidate(jsonConfig);
        DemoSourceClientConfiguration configuration = new DemoSourceClientConfiguration();
        configuration.setJsonConfig(jsonConfig);
        //具体的参数
        String baseUrl = json.getString("baseUrl");
        configuration.setBaseUrl(baseUrl);
        return configuration;
    }

    @Override
    public SourceDataPullClient build(SourceConnectionConfiguration configuration) throws InvalidConfigException {
        return new DemoSourceDataPullClient(convert(configuration));
    }

    private DemoSourceClientConfiguration convert(SourceConnectionConfiguration configuration) throws InvalidConfigException {
        if (configuration instanceof DemoSourceClientConfiguration) {
            return (DemoSourceClientConfiguration) configuration;
        }
        DemoSourceClientConfiguration demoSourceClientConfiguration = parseJsonConfig(configuration.getJsonConfig());
        demoSourceClientConfiguration.setUuid(configuration.getUuid());
        demoSourceClientConfiguration.setId(configuration.getId());
        demoSourceClientConfiguration.setName(configuration.getName());
        return demoSourceClientConfiguration;
    }

    @Override
    public AttributeGetter getAttributeGetter() {
        return new DemoAttributeGetter();
    }


    /**
     * 校验创建来源时，提交的表单json参数
     */
    @Override
    public void validate(String json) throws InvalidConfigException {
        doValidate(json);
    }

    /**
     * 校验json参数
     */
    private JSONObject doValidate(String json) throws InvalidConfigException {
        //第一步：非空校验
        if (StringUtils.isBlank(json)) {
            throw new InvalidConfigException("配置信息不能为空");
        }
        //第二步：key校验
        JSONObject config = (JSONObject) JSONObject.parse(json);
        //TODO 具体的参数
        String[] dbKey = {"baseUrl"};
        for (String key : dbKey) {
            //开发时打开校验
            //ensureParamNotNull(key, config.getString(key));
        }
        return config;
    }

    /**
     * TODO 修改测试来源系统连接
     * 测试数据源接口或者数据库等是否能连通
     **/
    @Override
    public ConnectionTestResult testConnection(String json) {
        try {
            DemoSourceClientConfiguration configuration = parseJsonConfig(json);

            //编写测试连接的代码，
            return new ConnectionTestResult(true, "测试连接成功");

        } catch (Exception e) {
            LOG.warn("[{}]-Test Connect error", RIDHolder.id(), e);
            return new ConnectionTestResult(false, e.getMessage());
        }
    }


    /**
     * 在配置源和目标的属性映射时，首先要获取源默认支持哪些属性，该方法用于获取源默认支持的属性集合。
     * 在实际配置时，一个源的具体配置实例，支持的属性是可能与默认支持的属性集合不同的，比如IDP中的用
     * 户如果不加的数据字典，默认只支持：账号、姓名、手机、邮箱、排序编码，但是扩展字典可以随意扩展属
     * 性。获取具体实例的支持的属性，请调用
     * {@link SourceDataPullClient#listSupportedAttributes(SyncObjectType)} 方法。
     *
     * @param syncObjectType 对象类型
     * @return 支持的属性对象集合
     */
    private DemoSourceDefaultAttributeDefiner attribute = new DemoSourceDefaultAttributeDefiner();

    @Override
    public Collection<AttributeDescriptor> listDefaultSupportedAttributes(SyncObjectType syncObjectType) {
        return attribute.define(syncObjectType);
    }
}
