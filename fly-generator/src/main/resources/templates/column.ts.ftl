import {FormColumnType} from "@/components/base-form";
import {FormTypeEnum} from "@/enums/componentEnum";
import {Column} from "@/components/base-table/src/types";
import {listDictKeyList} from "@/api/system/dict";

export function useColumn(action?: any, dialog?: any) {
const filterColumn: FormColumnType[] = [
<#list table.fields as field>
    <#if field.comment?has_content>
        <#assign fieldDesc = "${field.comment}">
    <#else>
        <#assign fieldDesc = "${field.propertyName}">
    </#if>
    {
        fieldName: "${field.propertyName}",
        fieldDesc: "${fieldDesc}",
        fieldType: FormTypeEnum.INPUT
    },
</#list>
];

const tableColumn: Column[] = [
<#list table.fields as field>
    <#if field.comment?has_content>
        <#assign fieldDesc = "${field.comment}">
    <#else>
        <#assign fieldDesc = "${field.propertyName}">
    </#if>
    {
        fieldName: "${field.propertyName}",
        fieldDesc: "${fieldDesc}",
    },
</#list>
    {
        fieldName: "#",
        fieldDesc: "操作",
        formType: "operation",
        width: 375,
        fixed: "right",
            operation: [
            {
                icon: "edit",
                label: "编辑",
                callFunction: action?.edit
            },
            {
                icon: "delete",
                label: "删除",
                callFunction: action?.delete
            }
            ]
    }
];

const dialogColumn: FormColumnType[] = [
<#list table.fields as field>
    <#if field.comment?has_content>
        <#assign fieldDesc = "${field.comment}">
    <#else>
        <#assign fieldDesc = "${field.propertyName}">
    </#if>
    {
    fieldName: "${field.propertyName}",
    fieldDesc: "${fieldDesc}",
    fieldType: FormTypeEnum.INPUT
    },
</#list>
];

return {
filterColumn,
tableColumn,
dialogColumn
};
}
