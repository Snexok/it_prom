<template>
  <div class="container">
    <div v-if="form.id!=0">Изменение для профессии с id {{ form.id }} <span class="delete-row" @click="clearForm">X</span></div>

    <form class="form" @submit.prevent="saveProfession">
      <input v-model="form.name" @keydown.enter.prevent="saveProfession" placeholder="Наименование" required/>
      <input v-model="form.note" @keydown.enter.prevent="saveProfession" placeholder="Примечание" />
      <button type="submit">{{ form.id ? 'Обновить' : 'Добавить' }}</button>
    </form>

    <div class="list-container">
      <div class="list" v-for="profession in professions" :key="profession.id" >
        <span class="row"
              :class="{ editing: profession.id === form.id }"
              @click="editProfession(profession)">
          {{ profession.name }}{{ profession.note ? `: ${profession.note}` : "" }}
        </span>
        <span class="delete-row" @click="removeProfession(profession.id)">X</span>
      </div>
    </div>

  </div>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue';
import { Profession } from '@/models/Profession';
import {
  getProfessions,
  createProfession,
  updateProfession,
  deleteProfession,
} from '@/services/professionService';

export default defineComponent({
  setup() {
    const professions = ref<Profession[]>([]);
    const form = ref<Profession>({
      id: 0,
      name: '',
      note: ''
    });

    const clearForm = () => { form.value = { id: 0, name: '', note: '' } }

    const loadProfessions = async () => {
      professions.value = await getProfessions();
    };

    const saveProfession = async () => {
      if (form.value.id) {
        await updateProfession(form.value);
      } else {
        await createProfession(form.value);
      }
      clearForm();
      await loadProfessions();
    };

    const editProfession = (profession: Profession) => {
      form.value = { ...profession };
    };

    const removeProfession = async (id: number) => {
      clearForm();
      await deleteProfession(id);
      await loadProfessions();
    };

    onMounted(loadProfessions);

    return {
      professions,
      form,
      clearForm,
      saveProfession,
      editProfession,
      removeProfession,
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
.form input {
  border-radius: 8px;
  padding: 4px 8px;
  background-color: darkgrey;
  outline: none;
}
.form input::placeholder {
  color: #494949;
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
