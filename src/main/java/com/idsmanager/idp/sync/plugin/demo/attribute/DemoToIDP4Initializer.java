package com.idsmanager.idp.sync.plugin.demo.attribute;

import com.idsmanager.idp.sync.MajorType;
import com.idsmanager.idp.sync.MinorType;
import com.idsmanager.idp.sync.SyncObjectType;
import com.idsmanager.idp.sync.core.dto.template.SyncFieldMappingObject;
import com.idsmanager.idp.sync.core.fields.FieldMappingTemplateInitializer;
import com.idsmanager.idp.sync.log.ConnectorPluginLogger;
import com.idsmanager.idp.sync.plugin.demo.business.MetaBaseConstant;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DemoToIDP4Initializer extends FieldMappingTemplateInitializer {

    public DemoToIDP4Initializer() {
        super(MajorType.APP_STANDARD.name(), MetaBaseConstant.MINORTYPE_NAME,
                MajorType.SCIM.name(), MinorType.IDP4_SCHEMA.name());
    }

    @Override
    public String getName() {
        return MetaBaseConstant.MINORTYPE_INITIALIZER_NAME;
    }

    @Override
    public String getDescription() {
        return MetaBaseConstant.MINORTYPE_INITIALIZER_DESCRITION;
    }

    @Override
    public void handleFieldMappings(List<SyncFieldMappingObject> mappings) {
        //部门
        mappings.add(createFieldMappingObject("组织机构名称", "组织机构(部门)名称",  "name", "AD组织机构(部门)名称", "organizationName", "IDP组织机构(部门)名称", SyncObjectType.ORGANIZATION));
        mappings.add(createFieldMappingObject("组织机构id", "组织机构(部门)id",  "objectGUID", "objectGUID", "externalId", "IDP组织机构(部门)id", SyncObjectType.ORGANIZATION));
        mappings.add(createFieldMappingObject("组织机构父级id", "组织机构(部门)id", "parentUuid", "AD父级组织机构(部门)id", "parentExternalId", "IDP父级组织机构(部门)id", SyncObjectType.ORGANIZATION));
        //人员
        mappings.add(createFieldMappingObject("账户外部id", "账户外部id", "objectGUID", "objectGUID", "externalId", "IDP人员外部id", SyncObjectType.USER));
        mappings.add(createFieldMappingObject("用户名", "用户名", "sAMAccountName", "sAMAccountName", "userName", "IDP人员账号", SyncObjectType.USER));
        mappings.add(createFieldMappingObject("显示名称", "显示名称", "name", "AD显示名称", "displayName", "IDP人员显示名称", SyncObjectType.USER));
        mappings.add(createFieldMappingObject("手机号", "手机号", "mobile", "AD手机号", "phoneNumber", "IDP人员手机号", SyncObjectType.USER));
        mappings.add(createFieldMappingObject("邮箱", "邮箱", "mail", "AD邮箱", "email", "IDP人员邮箱", SyncObjectType.USER));
        mappings.add(createFieldMappingObject("账户所属组织机构", "账户所属组织机构", "parentUuid", "AD所属组织机构", "belong", "IDP人员所属组织机构", SyncObjectType.USER));
        // 群组
        mappings.add(createFieldMappingObject("账户组id", "账户组id", "uuid", "externalId", SyncObjectType.GROUP));
        mappings.add(createFieldMappingObject("组显示名称", "组显示名称", "displayName", "displayName", SyncObjectType.GROUP));
        mappings.add(createFieldMappingObject("所属组织单位(OU)的外部ID", "所属组织单位(OU)的外部ID", "parentUuid", "ouExternalId", SyncObjectType.GROUP));
        mappings.add(createFieldMappingObject("存量成员列表", "存量成员列表", "member", "members", SyncObjectType.GROUP));
    }


}
