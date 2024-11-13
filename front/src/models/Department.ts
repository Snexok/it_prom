export interface Department {
    id: number;
    name: string;
    note?: string;
    parent?: Department | null;
}
