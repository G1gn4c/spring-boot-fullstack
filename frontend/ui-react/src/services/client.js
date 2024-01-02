import axios from "axios";

export const getCustomers = async () => {
    try {
        return await axios.get(`${import.meta.env.VITE_API_BASE_URL}/api/v1/customer`);
    } catch (error) {
        throw error;
    }
}

export const createCustomer = async (customer) => {
    try {
        return await axios.post(`${import.meta.env.VITE_API_BASE_URL}/api/v1/customer`, customer);
    } catch (error) {
        throw error;
    }
}

export const deleteCustomer = async (id) => {
    try {
        return await axios.delete(`${import.meta.env.VITE_API_BASE_URL}/api/v1/customer/${id}`);
    } catch (error) {
        throw error;
    }
}

export const updateCustomer = async (id, customer) => {
    try {
        return await axios.put(`${import.meta.env.VITE_API_BASE_URL}/api/v1/customer/${id}`, customer);
    } catch (error) {
        throw error;
    }
}