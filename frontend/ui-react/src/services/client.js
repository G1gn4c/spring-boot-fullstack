import axios from "axios";

const getAuthConfig = () => {
    return {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("access_token")}`
        }
    };
};

export const getCustomers = async () => {
    try {
        return await axios.get(`${import.meta.env.VITE_API_BASE_URL}/api/v1/customer`, getAuthConfig());
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
        return await axios.delete(`${import.meta.env.VITE_API_BASE_URL}/api/v1/customer/${id}`, getAuthConfig());
    } catch (error) {
        throw error;
    }
}

export const updateCustomer = async (id, customer) => {
    try {
        return await axios.put(`${import.meta.env.VITE_API_BASE_URL}/api/v1/customer/${id}`, customer, getAuthConfig());
    } catch (error) {
        throw error;
    }
}

export const login = async (credentials) => {
    try {
        return await axios.post(`${import.meta.env.VITE_API_BASE_URL}/api/v1/auth/login`, credentials);
    } catch (error) {
        throw error;
    }
}