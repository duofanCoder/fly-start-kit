<#assign entityUnderline = entity?replace("[A-Z]", "_$0", "r")?upper_case?substring(1)>
<#assign entityLowFirst = entity?uncap_first>
<template>
    <base-dialog ref="baseDialogRef" width="560px" :title="getDialogTitle" @save="handleSave">
        <base-form ref="baseFormRef" :columns="getColumn" :rules="userRules" :model="state.formModel">
        </base-form>
    </base-dialog>
</template>

<script lang="ts" setup>
    import {useColumn} from "../column";
    import {useMessage} from "@/hooks";
    import {save${entity}, update${entity}} from "@/api/${project}/${entityLowFirst}";

    const emit = defineEmits(["success"]);

    const {success} = useMessage();

    const baseDialogRef = ref();

    const baseFormRef = ref();

    const state = reactive({
        isEdit: false,
        treeData: [],
        treeCheckData: [],
        formModel:{}
    });
    const getColumn = computed(() => {
        return useColumn(undefined, state).dialogColumn;
    });

    const userRules = {
        title: [
            {
                required: true,
                message: "请输入名称",
                trigger: ["blur", "change"]
            }
        ],typeId: [
            {
                required: true,
                message: "请输入分类",
                trigger: ["blur", "change"]
            }
        ],
        description: [
            {
                required: true,
                message: "请输入描述",
                trigger: ["blur", "change"]
            }
        ]
    };

    const getDialogTitle = computed(() => (state.isEdit ? "修改" : "新增"));

    const showDialog = (val: any = {}) => {
        unref(baseDialogRef).showDialog();
        nextTick(async () => {
            unref(baseFormRef).instance.resetFields();
            state.isEdit = !!val.id;
            if (state.isEdit) {
                Object.assign(state.formModel, val);
            }
        });
    };

    const hideDialog = () => {
        unref(baseDialogRef).hideDialog();
    };

    const handleSave = async (loading: (flag: boolean) => void) => {
        await unref(baseFormRef).instance.validate(async (valid: boolean) => {
            if (!valid) return;
            loading(true);
            try {
                state.isEdit ? await update${entity}(state.formModel) : await save${entity}(state.formModel);
                success(state.isEdit ? "修改成功" : "新增成功！");
                emit("success");
                hideDialog();
            } finally {
                loading(false);
            }
        });
    };

    defineExpose({
        showDialog,
        hideDialog
    });
</script>

<style scoped></style>
