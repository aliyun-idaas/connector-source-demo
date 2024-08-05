package com.idsmanager.idp.sync.plugin.demo.client;

import com.idsmanager.idp.sync.SCIMException;
import com.idsmanager.idp.sync.SyncErrorCode;
import com.idsmanager.idp.sync.SyncMode;
import com.idsmanager.idp.sync.SyncObjectType;
import com.idsmanager.idp.sync.commons.DeltaFlagCondition;
import com.idsmanager.idp.sync.core.infrastructure.ApiResult;
import com.idsmanager.idp.sync.core.infrastructure.DataTransformContext;
import com.idsmanager.idp.sync.core.infrastructure.DelegateAuthentication;
import com.idsmanager.idp.sync.core.infrastructure.mapping.AttributeDescriptor;
import com.idsmanager.idp.sync.core.infrastructure.source.SourceDataItem;
import com.idsmanager.idp.sync.core.infrastructure.source.SourceDataPullClient;
import com.idsmanager.idp.sync.log.ConnectorPluginLogger;
import com.idsmanager.idp.sync.plugin.demo.attribute.DemoSourceDefaultAttributeDefiner;
import com.idsmanager.idp.sync.plugin.demo.business.service.DemoSourceService;
import com.idsmanager.idp.sync.plugin.demo.business.source.DemoSourceClientConfiguration;
import com.idsmanager.idp.sync.plugin.demo.entity.source.DemoSourceAccountEntity;
import com.idsmanager.idp.sync.plugin.demo.entity.source.DemoSourceOrganizationEntity;
import com.idsmanager.micro.commons.utils.DateUtils;
import com.idsmanager.micro.commons.web.filter.RIDHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 2021/12/13 10:50
 * <p>
 *
 * @author xbj
 * @class DemoSourceDataPullClient
 * @since
 */
public class DemoSourceDataPullClient implements SourceDataPullClient {

    private static final Logger LOG = LoggerFactory.getLogger(DemoSourceDataPullClient.class);

    private DemoSourceClientConfiguration configuration;

    private DemoSourceService demoSourceService;

    public DemoSourceDataPullClient(DemoSourceClientConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * TODO 分页模式拉取数据-方法声明
     * 按需实现（对应PAGE_SCHEME）
     *
     * @param syncObjectType 对象类型
     * @param context        任务执行的上下文
     * @param parent         父对象
     * @param page           第几页
     * @param pageSize       每页数量限制
     */
    @Override
    public List<SourceDataItem> pullFullRegularChildrenByPage(SyncObjectType syncObjectType, DataTransformContext context, SourceDataItem parent, int page, int pageSize) throws SCIMException {
        return Collections.emptyList();
    }

    /**
     * TODO 拉取常规的指定对象类型的数据--方法实现
     * 对应 CLASSIC
     * @param syncObjectType    对象类型
     * @param mode              拉取方式，增量或者全量
     * @param context           任务执行的上下文
     * @param deltFlagCondition 增量查询条件
     * @return 待处理的同步数据集合
     */
    @Override
    public List<SourceDataItem> pullRegularData(SyncObjectType syncObjectType, SyncMode mode, DataTransformContext context, DeltaFlagCondition deltFlagCondition) throws SCIMException {
        if (!support(syncObjectType)) {
            throw new SCIMException(SyncErrorCode.SCIM_ERR_UNSUPPORTED_OBJECT_TYPE, "不支持的同步对象类型[" + syncObjectType + "]");
        }
        List<SourceDataItem> items = new ArrayList<>();
        //TODO 按实际需求实现数据拉取
        //如果是增量查询的话需要拼接查询条件
        if (context.getTask().incrSync() && context.lastSuccessTime() != null) {
            Date begainTimeDate = context.lastSuccessTime();
            String begainTime = DateUtils.toDateText(begainTimeDate, "yyyy-MM-dd HH:mm:ss");
            ConnectorPluginLogger.warn("[{}]-获取到上次更新时间 begainTime ：{}", RIDHolder.id(), begainTime);
            //拉取数据
            switch (syncObjectType) {
                case ORGANIZATION:
                    items = demoSourceService.getOrgsByTime(begainTime);
                    break;
                case USER:
                    items = demoSourceService.getUserByTime(begainTime);
                    break;
                default:
                    break;
            }
        } else {
            String parentDeptId = null;
            String defaultSourceRoot = context.getTask().getDefaultSourceRoot();
            parentDeptId = defaultSourceRoot;
            //拉取数据
            switch (syncObjectType) {
                case ORGANIZATION:
                    items = demoSourceService.getOrgsByParentCode(parentDeptId, context.getTask().incrSync());
                    break;
                case USER:
                    items = demoSourceService.getUserByOrgCode(parentDeptId, context.getTask().incrSync());
                    break;
                default:
                    break;
            }
        }
        return items;
    }

    /**
     * 拉取已删除的指定对象类型的数据-方法实现
     *
     * @param syncObjectType 对象类型
     * @param mode           拉取方式，增量或者全量
     * @param context        任务执行的上下文
     * @return 待处理的同步数据集合
     */
    @Override
    public List<SourceDataItem> pullDeletedData(SyncObjectType syncObjectType, SyncMode mode, DataTransformContext context, DeltaFlagCondition deltFlagCondition) throws SCIMException {
        return Collections.emptyList();
    }

    /**
     * TODO 拉取指定的组织机构的直属的子组织机构或者直属用户，只查询一级
     * 按需实现
     * 默认实现（对应AD_SCHEME）
     * @param syncObjectType 要拉取的数据类型，组织机构或者数据
     * @param context        本次操作的上下文对象
     * @param parent         父级
     * @return
     * @throws SCIMException
     */
    @Override
    public List<SourceDataItem> pullFullRegularOneLevelChildren(SyncObjectType syncObjectType, DataTransformContext context, SourceDataItem parent) throws SCIMException {
        if (!support(syncObjectType)) {
            throw new SCIMException(SyncErrorCode.SCIM_ERR_UNSUPPORTED_OBJECT_TYPE, "不支持的同步对象类型[" + syncObjectType + "]");
        }
        if (parent != null) {
            if (!(parent instanceof DemoSourceOrganizationEntity) && !(parent instanceof DemoSourceAccountEntity)) {
                throw new SCIMException(SyncErrorCode.SCIM_ERR_UNSUPPORTED_OBJECT_IMPL, "不支持的实例对象[" + parent.getClass() + "]");
            }
        }
        String parentDeptId = null;
        if (null == parent) {
            String defaultSourceRoot = context.getTask().getDefaultSourceRoot();
            parentDeptId = defaultSourceRoot;
        } else {
            parentDeptId = parent.uniqueId();
        }
        //拉取数据
        List<SourceDataItem> items = null;
        switch (syncObjectType) {
            case ORGANIZATION:
                items = demoSourceService.getOrgsByParentCode(parentDeptId, context.getTask().incrSync());
                break;
            case USER:
                items = demoSourceService.getUserByOrgCode(parentDeptId, context.getTask().incrSync());
                break;
            default:
                break;
        }
        return items;
    }

    /**
     * @param item            仅支持AdEntity类型
     * @param currentDeltFlag 当前增量位置
     * @return DeltFlagCondition 返回修改后的节点信息
     */
    @Override
    public DeltaFlagCondition updateDeltFlagIfNeed(SourceDataItem item, DataTransformContext context, DeltaFlagCondition currentDeltFlag) {
        return null;
    }

    @Override
    public boolean supportDelegateAuthenticate() {
        return false;
    }

    @Override
    public ApiResult authenticate(DelegateAuthentication authentication) {
        return ApiResult.UNSUPPORTED;
    }

    @Override
    public SourceDataItem buildSourceItemByJson(String sourceJson, SyncObjectType syncObjectType) {
        return null;
    }

//    @Override
    public SourceDataItem buildSourceItemByJson(String sourceJson) {
        return null;
    }

    @Override
    public SourceDataItem getSourceDataItemByCondition(Object conditon) throws SCIMException {
        return null;
    }

    /**
     * 支持组织和用户同步，一般不需要修改
     */
    @Override
    public boolean support(SyncObjectType type) {
        return type == SyncObjectType.ORGANIZATION || type == SyncObjectType.USER;
    }

    /**
     * 执行对 SourceDataPushClient 对象的初始化
     * 里边比如可以初始化HttpClient连接或者LDAP连接信息等（具体取决于不同的插件）。
     * 当目标插件的配置发生变更后，引擎会重新构建 SourceDataPushClient  对象，构建之前会调用旧对象的 destroy方法 ，保证内存等资源的释放
     * <p>
     * 按需实现
     *
     * @param
     * @return void
     * @date 2021/12/10 19:05
     **/
    @Override
    public void initialize() throws SCIMException {

    }

    /**
     * 执行对 SourceDataPushClient 对象的销毁
     * <p>
     * 按需实现
     *
     * @param
     * @return void
     * @date 2021/12/10 19:05
     **/
    @Override
    public void destroy() {

    }


    /**
     * 所支持的属性描述
     *
     * @param syncObjectType 实体类型(User或Organization)
     * @return List<AttributeDescriptor> 属性描述列表
     */
    private DemoSourceDefaultAttributeDefiner attribute = new DemoSourceDefaultAttributeDefiner();

    @Override
    public Collection<AttributeDescriptor> listSupportedAttributes(SyncObjectType syncObjectType) {
        return attribute.define(syncObjectType);
    }

}
