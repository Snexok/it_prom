import { createRouter, createWebHistory } from 'vue-router';
import Profession from '@/pages/Profession.vue';
import Department from '@/pages/Department.vue';
import Employee from '@/pages/Employee.vue';

const routes = [
    { path: '/profession', name: 'Profession', component: Profession },
    { path: '/department', name: 'Department', component: Department },
    { path: '/employee', name: 'Employee', component: Employee },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
