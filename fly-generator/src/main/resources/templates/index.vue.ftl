<#assign entityUnderline = entity?replace("[A-Z]", "_$0", "r")?upper_case?substring(1)>
<#assign entityLowFirst = entity?uncap_first>
<script setup lang="ts">
    import { page${entity}, remove${entity} } from "@/api/${project}/${entityLowFirst}";
    import ${entity}Dialog from "./components/${entity}Dialog.vue";
    import { useColumn } from "./column";
    import { useMessage } from "@/hooks";

    const router = useRouter();

    const { tableColumn, filterColumn } = useColumn({
        edit: handleEdit,
        delete: handleDelete,
        switchStatus: handleChangeStatus
    });

    const { success } = useMessage();

    const filterConfig = reactive({
        columns: filterColumn,
        onSearch: handleSearch,
        showOpen: false,
        searchInfo: {}
    });

    const tableConfig = reactive({
        columns: tableColumn,
        data: [],
        pagination: {
            pageIndex: 1,
            pageSize: 10,
            pageCount: 0
        },
        onRefresh: handleSearch
    });

    const dialogRef = ref();

    onMounted(() => {
        handleSearch();
    });

    /**
     * 查询
     */
    async function handleSearch() {
        const { pageSize, pageIndex } = tableConfig.pagination;
        const data = await page${entity}({ ...filterConfig.searchInfo, pageIndex, pageSize });
        tableConfig.data = data.list || [];

        tableConfig.pagination.pageCount = data.pageCount;
    }

    function handleAddType() {
        unref(dialogRef).showDialog();
    }

    function handleEdit(scope: any) {
        unref(dialogRef).showDialog(scope.row);
    }

    async function handleDelete(scope: any) {
        const { id } = scope.row;

        await remove${entity}({ id });
        success("删除成功");
        await handleSearch();
    }
    <#noparse>
    async function handleChangeStatus(val: any) {
        // return messageBox(`确认要${val.isVisible === "0" ? "显示" : "隐藏"}该记录吗?`).then(async () => {
        //   await switchStatus({status: val.status === "1" ? "0" : "1", id: val.id});
        //   success(`${val.status === "0" ? "显示" : "隐藏"}成功}`);
        //   await handleSearch();
        // });
    }
    </#noparse>
</script>

<template>
    <div class="">
        <base-page-table table-title="列表" :filter-config="filterConfig" :table-config="tableConfig">
            <template #buttons>
                <base-button type="primary" @click="handleAddType">新增</base-button>
            </template>
        </base-page-table>
        <${entity}Dialog ref="dialogRef" @success="handleSearch" />
    </div>
</template>

<style scoped lang="scss"></style>
