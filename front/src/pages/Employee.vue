<template>
  <div class="container">
    <div v-if="form.id!=0">Изменение для сотрудника с id {{ form.id }} <span class="delete-row" @click="clearForm">X</span></div>
    <form class="form" @submit.prevent="saveEmployee">
      <input v-model="form.fullName" @keydown.enter.prevent="saveEmployee" placeholder="ФИО" required />
      <input v-model="form.note" @keydown.enter.prevent="saveEmployee" placeholder="Примечание" />

      <label>Профессия:</label>
      <select v-model="form.profession" required>
        <option :value="null">Выберите профессию</option>
        <option v-for="profession in professions" :key="profession.id" :value="profession">
          {{ profession.name }}
        </option>
      </select>

      <label>Отдел:</label>
      <select v-model="form.department">
        <option :value="null">Выберите отдел</option>
        <option v-for="dept in departments" :key="dept.id" :value="dept">
          {{ dept.name }}
        </option>
      </select>

      <button type="submit">{{ form.id ? 'Обновить' : 'Добавить' }}</button>
    </form>

    <div class="list-container">
      <div class="list" v-for="employee in employees" :key="employee.id">
        <span class="row"
              :class="{ editing: employee.id === form.id }"
              @click="editEmployee(employee)">
          {{ employee.fullName }}{{ employee.note ? `: ${employee.note}` : "" }} - {{ employee.profession?.name || 'Без профессии' }} ({{ employee.department?.name || 'Без отдела' }})
        </span>
        <span class="delete-row" @click="removeEmployee(employee.id)">X</span>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue';
import { Employee } from '@/models/Employee';
import { Department } from '@/models/Department';
import { Profession } from '@/models/Profession';
import { getEmployees, createEmployee, updateEmployee, deleteEmployee } from '@/services/employeeService';
import { getDepartments } from '@/services/departmentService';
import { getProfessions } from '@/services/professionService';

export default defineComponent({
  setup() {
    const employees = ref<Employee[]>([]);
    const departments = ref<Department[]>([]);
    const professions = ref<Profession[]>([]);
    const form = ref<Employee>({
      id: 0,
      fullName: '',
      note: '',
      profession: null,
      department: null,
    });

    const clearForm = () => {
      form.value = { id: 0, fullName: '', note: '', profession: null, department: null };
    };

    const loadData = async () => {
      employees.value = await getEmployees();
      departments.value = await getDepartments();
      professions.value = await getProfessions();
    };

    const saveEmployee = async () => {
      if (form.value.id) {
        await updateEmployee(form.value);
      } else {
        await createEmployee({
          fullName: form.value.fullName,
          note: form.value.note,
          profession: form.value.profession,
          department: form.value.department,
        });
      }

      clearForm();
      await loadData();
    };

    const editEmployee = (employee: Employee) => {
      form.value = { ...employee };
    };

    const removeEmployee = async (id: number) => {
      await deleteEmployee(id);
      await loadData();
    };

    onMounted(loadData);

    return {
      employees,
      departments,
      professions,
      form,
      clearForm,
      saveEmployee,
      editEmployee,
      removeEmployee,
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
