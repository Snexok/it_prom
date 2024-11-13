<template>
  <div class="container">
    <div v-if="form.id!=0">Изменение для отдела с id {{ form.id }} <span class="delete-row" @click="clearForm">X</span></div>
    <form class="form" @submit.prevent="saveDepartment">
      <input v-model="form.name" @keydown.enter.prevent="saveDepartment" placeholder="Наименование" required />
      <input v-model="form.note" @keydown.enter.prevent="saveDepartment" placeholder="Примечание" />

      <label>Родительский отдел:</label>
      <select v-model="form.parent">
        <option :value="null">Нет</option>
        <option v-for="dept in departments.filter(d => form.id != d.id && !isDescendant(form, d))"
                :key="dept.id"
                :value="dept">
          {{ dept.name }}
        </option>
      </select>

      <button type="submit">{{ form.id ? 'Обновить' : 'Добавить' }}</button>
    </form>

    <div class="list-container">
      <div class="list" v-for="department in departments" :key="department.id">
        <span class="row"
              :class="{ editing: department.id === form.id }"
              @click="editDepartment(department)">
          {{ department.name }}{{ department.note ? `: ${department.note}` : "" }} ({{ department.parent?.name || 'Нет родителя' }})
        </span>
        <span class="delete-row" @click="removeDepartment(department.id)">X</span>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue';
import { Department } from '@/models/Department';
import {
  getDepartments,
  createDepartment,
  updateDepartment,
  deleteDepartment
} from '@/services/departmentService';

export default defineComponent({
  setup() {
    const departments = ref<Department[]>([]);
    const form = ref<Department>({
      id: 0,
      name: '',
      note: '',
      parent: null,
    });

    const clearForm = () => { form.value = { id: 0, name: '', note: '', parent: null } }

    const loadDepartments = async () => {
      departments.value = await getDepartments();
    };

    const saveDepartment = async () => {
      if (form.value.id) {
        await updateDepartment(form.value);
      } else {
        await createDepartment({
          name: form.value.name,
          note: form.value.note,
          parent: form.value.parent,
        });
      }

      clearForm();
      await loadDepartments();
    };

    const editDepartment = (department: Department) => {
      form.value = {
        id: department.id,
        name: department.name,
        note: department.note,
        parent: department.parent || null,
      };
    };

    const removeDepartment = async (id: number) => {
      await deleteDepartment(id);
      await loadDepartments();
    };

    const isDescendant = (currentDepartment: Department, potentialChild: Department): boolean => {
      if (!potentialChild.parent) return false;
      if (potentialChild.parent.id === currentDepartment.id) return true;

      const parent = departments.value.find(dept => dept.id === potentialChild.parent?.id);
      return parent ? isDescendant(currentDepartment, parent) : false;
    };

    onMounted(loadDepartments);

    return {
      departments,
      form,
      isDescendant,
      clearForm,
      saveDepartment,
      editDepartment,
      removeDepartment,
    };
  },
});
</script>

<style scoped>
.container {
  width: 400px;
  margin-top: 20px;
  display: flex;
  flex-direction: column;
  gap: 20px;
}
.form {
  width: 200px;
  display: flex;
  flex-direction: column;
  gap: 4px;
}
.form input::placeholder {
  color: #494949;
}
.form input,
.form select {
  border-radius: 8px;
  padding: 4px 8px;
  background-color: darkgrey;
  outline: none;
}
.form button {
  border-radius: 8px;
  background-color: darkgrey;
  cursor: pointer;
}
.list-container {
  width: fit-content;
  display: flex;
  flex-direction: column;
  gap: 8px;
}
.list {
  display: flex;
  justify-content: space-between;
  gap: 8px;
}
.row {
  user-select: none;
  cursor: pointer;
}
.editing {
  color: darkorange;
}
.delete-row {
  color: indianred;
  cursor: pointer;
}
</style>
