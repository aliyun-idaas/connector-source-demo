package com.idsmanager.idp.sync.plugin.demo.attribute;

import com.idsmanager.idp.sync.SCIMException;
import com.idsmanager.idp.sync.SyncErrorCode;
import com.idsmanager.idp.sync.core.infrastructure.mapping.AttributeGetter;
import com.idsmanager.idp.sync.core.infrastructure.mapping.FieldAttributeGetter;
import com.idsmanager.idp.sync.core.infrastructure.source.SourceDataItem;
import com.idsmanager.idp.sync.plugin.demo.entity.source.DemoSourceAccountEntity;
import com.idsmanager.idp.sync.plugin.demo.entity.source.DemoSourceOrganizationEntity;
import com.idsmanager.micro.commons.web.filter.RIDHolder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 2021/12/10 15:48
 * <p>属性的设置  一般不需要修改
 *
 * @author xbj
 * @class DemoAttributeGetter
 * @since
 */
public class DemoAttributeGetter implements AttributeGetter {

    private static Logger LOG = LoggerFactory.getLogger(DemoAttributeGetter.class);

    public static final String extendAttributePrefix = "extendField.";

    private FieldAttributeGetter fieldAttributeGetter = new FieldAttributeGetter();

    /**
     * 某具体属性值获取
     *
     * @param item          源数据条目对象
     * @param attributeName 属性名
     * @return Object 属性值
     */
    @Override
    public Object getAttribute(SourceDataItem item, String attributeName) throws SCIMException {
        if (item == null || StringUtils.isBlank(attributeName)) {
            throw new SCIMException(SyncErrorCode.SCIM_ERR_MAPPING_GET_ATTRIBUTE, "属性映射错误，来源对象为空或者属性名为空");
        }
        if (!(item instanceof DemoSourceAccountEntity) && !(item instanceof DemoSourceOrganizationEntity)) {
            throw new SCIMException(SyncErrorCode.SCIM_ERR_MAPPING_GET_ATTRIBUTE, "属性映射错误，读取属性失败，不支持的对象实例[" + item.getClass() + "]");
        }

        try {
            //根据属性名称从 entity 中反射获取数据
            Object entity = item;
            FieldAttributeGetter.FieldGetResult result;
            if (attributeName.startsWith(extendAttributePrefix)) {
                attributeName = attributeName.substring(extendAttributePrefix.length());
            }
            //先从对象本身的属性获取值
            result = fieldAttributeGetter.get(entity, attributeName);
            if (result.find) {
                return result.value;
            }

            return null;
        } catch (Throwable e) {
            LOG.error("[{}]- Attribute mapping failed on get attribute [{}] from object [{}][{}={}]", RIDHolder.id(), attributeName, item.objectType(), item.uniqueIdType(), item.uniqueId(), e);
            throw new SCIMException(SyncErrorCode.SCIM_ERR_UNSUPPORTED_MAPPING_TYPE, "属性映射错误，获取对象属性[" + attributeName + "]异常");
        }
    }
}
