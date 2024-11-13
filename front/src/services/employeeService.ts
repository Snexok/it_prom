import apiClient from './api';
import { Employee } from '@/models/Employee';

const path = 'employees';

export const getEmployees = async (): Promise<Employee[]> => {
    const response = await apiClient.get<Employee[]>(`/${path}`);
    return response.data;
};

export const createEmployee = async (employee: Omit<Employee, 'id'>): Promise<Employee> => {
    const response = await apiClient.post<Employee>(`/${path}`, employee);
    return response.data;
};

export const updateEmployee = async (employee: Employee): Promise<Employee> => {
    const response = await apiClient.put<Employee>(`/${path}/${employee.id}`, employee);
    return response.data;
};

export const deleteEmployee = async (id: number): Promise<void> => {
    await apiClient.delete(`/${path}/${id}`);
};
