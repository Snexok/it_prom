import { Department } from './Department';
import { Profession } from "@/models/Profession";

export interface Employee {
    id: number;
    fullName: string;
    note?: string;
    profession: Profession | null;
    department: Department | null;
}
