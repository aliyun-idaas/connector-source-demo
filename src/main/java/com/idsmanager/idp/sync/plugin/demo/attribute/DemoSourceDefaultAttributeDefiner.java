package com.idsmanager.idp.sync.plugin.demo.attribute;

import com.idsmanager.idp.sync.SyncObjectType;
import com.idsmanager.idp.sync.core.infrastructure.mapping.AttributeDescriptor;

import java.util.ArrayList;
import java.util.List;

/**
 * 2021/12/13 11:46
 * <p>预设的同步字段
 * attributeName为目标系统的具体字段名称，根据实际情况增删
 *
 * @author xbj
 * @class DemoSourceDefaultAttributeDefiner
 * @since
 */
public class DemoSourceDefaultAttributeDefiner {

    /**
     * 支持的属性列表
     *
     * @param syncObjectType 同步类型
     * @return list返回默认支持的属性列表
     */
    public List<AttributeDescriptor> define(SyncObjectType syncObjectType) {
        List<AttributeDescriptor> attributeDescriptors = new ArrayList<>();

        //人员
        if (SyncObjectType.USER.equals(syncObjectType)) {
            //TODO: 编写完全，以下两个为示例
            //用户编码
            AttributeDescriptor userCode = new AttributeDescriptor("userCode", "用户编码", "用户编码");
            attributeDescriptors.add(userCode);

            //用户名称
            AttributeDescriptor userName = new AttributeDescriptor("userName", "用户名称", "用户名称");
            attributeDescriptors.add(userName);


        }

        //组织(即部门)
        if (SyncObjectType.ORGANIZATION.equals(syncObjectType)) {
            //TODO: 组织同上
            //部门主键
            AttributeDescriptor pkDept = new AttributeDescriptor("pkDept", "部门主键", "部门主键");
            attributeDescriptors.add(pkDept);

            //组织、部门common主键
            AttributeDescriptor externalId = new AttributeDescriptor("externalId", "组织、部门common主键", "组织、部门common主键");
            attributeDescriptors.add(externalId);


        }

        return attributeDescriptors;
    }
}
