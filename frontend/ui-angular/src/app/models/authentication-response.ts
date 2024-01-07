import { CustomerDTO } from "./customer-dto";

export interface AuthenticationResponse {
    customerDTO?: CustomerDTO;
    token?: string;
}