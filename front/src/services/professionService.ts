import apiClient from './api';
import { Profession } from '@/models/Profession';

const path = 'professions';

export const getProfessions = async (): Promise<Profession[]> => {
    const response = await apiClient.get<Profession[]>(`/${path}`);
    return response.data;
};

export const createProfession = async (profession: Omit<Profession, 'id'>): Promise<Profession> => {
    const response = await apiClient.post<Profession>(`/${path}`, profession);
    return response.data;
};

export const updateProfession = async (profession: Profession): Promise<Profession> => {
    const response = await apiClient.put<Profession>(`/${path}/${profession.id}`, profession);
    return response.data;
};

export const deleteProfession = async (id: number): Promise<void> => {
    await apiClient.delete(`/${path}/${id}`);
};
