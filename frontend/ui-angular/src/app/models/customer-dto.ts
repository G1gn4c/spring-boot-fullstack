export interface CustomerDTO {
    id?: number;
    name?: string;
    email?: string;
    age?: number;
    gender?: "MALE" | "FEMALE";
    roles?: Array<string>;
    username?: string;
}