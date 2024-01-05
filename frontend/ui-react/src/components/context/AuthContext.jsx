import { useEffect, useState } from 'react';
import { useContext } from 'react';
import { createContext } from 'react';
import { login as performLogin } from './../../services/client'
import { jwtDecode } from 'jwt-decode';

const AuthContext = createContext({});

const AuthProvider = ({ children }) => {
    const [customer, setCustomer] = useState(null);

    const setCustomerFromToken = () => {
        let token = localStorage.getItem("access_token");
        if (token) {
            token = jwtDecode(token);
            setCustomer({
                username: token.sub,
                roles: token.scopes
            });
        }
    };

    useEffect(() => {
        setCustomerFromToken();
    }, []);

    const login = async (credentials) => {
        return new Promise((resolve, reject) => {
            performLogin(credentials)
                .then(res => {
                    const jwtToken = res.headers["authorization"];
                    localStorage.setItem("access_token", jwtToken);
                    const decodedToken = jwtDecode(jwtToken);
                    setCustomer({
                        username: decodedToken.sub,
                        roles: decodedToken.scopes
                    });
                    resolve(res);
                })
                .catch(err => {
                    reject(err);
                });
        });
    };

    const logout = () => {
        localStorage.removeItem("access_token");
        setCustomer(null);
    };

    const isAuthenticated = () => {
        const token = localStorage.getItem("access_token");
        if (!token) {
            return false;
        }
        const decodedToken = jwtDecode(token);
        if (Date.now() > decodedToken.exp * 1000) {
            logout();
            return false;
        }
        return true;
    };

    return <AuthContext.Provider
        value={
            {
                customer,
                login,
                logout,
                isAuthenticated,
                setCustomerFromToken
            }
        }
    >
        {children}
    </AuthContext.Provider>
};

export const useAuth = () => useContext(AuthContext);
export default AuthProvider;