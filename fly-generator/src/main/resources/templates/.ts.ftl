<#assign entityUnderline = entity?replace("[A-Z]", "_$0", "r")?upper_case?substring(1)>
<#assign entityLowFirst = entity?uncap_first>
import request from "@/utils/request";

export enum Api {
    PAGE_${entityUnderline} = "/api/v1/${entityLowFirst}/page",
    GET_${entityUnderline} = "/api/v1/${entityLowFirst}/get",
    ADD_${entityUnderline} = "/api/v1/${entityLowFirst}/save",
    REMOVE_${entityUnderline} = "/api/v1/${entityLowFirst}/remove",
    UPDATE_${entityUnderline} = "/api/v1/${entityLowFirst}/update",
    SWITCH_${entityUnderline}_STATUS = "/api/v1/${entityLowFirst}/switch/status"
}

export const page${entity} = (data?: any) => request.get(Api.PAGE_${entityUnderline}, data);
export const get${entity} = (data?: any) => request.get(Api.GET_${entityUnderline}, data);
export const save${entity} = (data: any) => request.post(Api.ADD_${entityUnderline}, data);
export const update${entity} = (data: any) => request.put(Api.UPDATE_${entityUnderline}, data);
export const remove${entity} = (data: any) => request.delete(Api.REMOVE_${entityUnderline}, data);
export const switch${entity}Status = (data: any) => request.put(Api.SWITCH_${entityUnderline}_STATUS, data);
