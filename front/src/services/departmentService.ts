import apiClient from './api';
import { Department } from '@/models/Department';

const path = 'departments';

export const getDepartments = async (): Promise<Department[]> => {
    const response = await apiClient.get<Department[]>(`/${path}`);
    return response.data;
};

export const createDepartment = async (department: Omit<Department, 'id'>): Promise<Department> => {
    const response = await apiClient.post<Department>(`/${path}`, department);
    return response.data;
};

export const updateDepartment = async (department: Department): Promise<Department> => {
    const response = await apiClient.put<Department>(`/${path}/${department.id}`, department);
    return response.data;
};

export const deleteDepartment = async (id: number): Promise<void> => {
    await apiClient.delete(`/${path}/${id}`);
};
