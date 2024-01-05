import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import './index.css'
import { ChakraProvider } from '@chakra-ui/react'
import { createStandaloneToast } from '@chakra-ui/react'
import { RouterProvider, createBrowserRouter } from 'react-router-dom'
import Login from './components/login/Login.jsx'
import AuthProvider from './components/context/AuthContext.jsx'
import ProtectedRoute from './components/shared/ProtectedRoute.jsx'
import Signup from './components/signup/Signup.jsx'

const { ToastContainer } = createStandaloneToast()

const router = createBrowserRouter([
  {
    path: "/",
    element: <Login></Login>
  },
  {
    path: "/signup",
    element: <Signup></Signup>
  },
  {
    path: "dashboard",
    element: <ProtectedRoute><App></App></ProtectedRoute>
  }
]);

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <ChakraProvider>
      <AuthProvider>
        <RouterProvider router={router}></RouterProvider>
      </AuthProvider>
      <ToastContainer></ToastContainer>
    </ChakraProvider>
  </React.StrictMode>,
)
