import {
    Button,
    Checkbox,
    Flex,
    Text,
    FormControl,
    FormLabel,
    Heading,
    Input,
    Stack,
    Image,
    Box,
    Alert,
    AlertIcon,
} from '@chakra-ui/react'
import { Form, Formik, useField } from 'formik';
import * as Yup from 'yup';
import { useAuth } from '../context/AuthContext';
import { errorNotification } from '../../services/notification';
import { useNavigate } from 'react-router-dom';

const MyTextInput = ({ label, ...props }) => {
    // useField() returns [formik.getFieldProps(), formik.getFieldMeta()]
    // which we can spread on <input>. We can use field meta to show an error
    // message if the field is invalid and it has been touched (i.e. visited)
    const [field, meta] = useField(props);
    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Input className="text-input" {...field} {...props} />
            {meta.touched && meta.error ? (
                <Alert className="error" status='error' mt={2}>
                    <AlertIcon></AlertIcon>
                    {meta.error}
                </Alert>
            ) : null}
        </Box>
    );
};

function LoginForm() {
    const { customer, login } = useAuth();
    const navigate = useNavigate();

    return (
        <Formik
            validateOnMount={true}
            validationSchema={
                Yup.object({
                    username: Yup.string().email("Must be a valid email").required("Email is required"),
                    password: Yup.string().max(20, "Password cannot be more than 20 characters").required("Password is required")
                })
            }
            initialValues={{
                username: "",
                password: ""
            }}
            onSubmit={(values, { setSubmitting }) => {
                setSubmitting(true);
                login(values)
                    .then(res => {
                        navigate("/dashboard");
                        console.log("Successfully logged in");
                    })
                    .catch(err => {
                        errorNotification(err.code, err.response.data.message);
                    })
                    .finally(() => {
                        setSubmitting(false);
                    });
            }}
        >
            {({ isValid, isSubmitting }) => {
                return (
                    <Form>
                        <Stack spacing={15}>
                            <MyTextInput
                                label={"Email"}
                                name={"username"}
                                type={"email"}
                                placeholder={"helloworld@example.com"}
                            >
                            </MyTextInput>
                            <MyTextInput
                                label={"Password"}
                                name={"password"}
                                type={"password"}
                                placeholder={"Type your password"}
                            >
                            </MyTextInput>
                            <Button
                                type='submit'
                                disabled={!isValid || isSubmitting}
                            >
                                Login
                            </Button>
                        </Stack>
                    </Form>
                )
            }}
        </Formik>
    );
}

export default function Login() {
    return (
        <Stack minH={'100vh'} direction={{ base: 'column', md: 'row' }}>
            <Flex p={8} flex={1} align={'center'} justify={'center'}>
                <Stack spacing={4} w={'full'} maxW={'md'}>
                    <Heading fontSize={'2xl'} mb={15}>Sign in to your account</Heading>
                    <LoginForm></LoginForm>
                </Stack>
            </Flex>
            <Flex flex={1}>
                <Image
                    alt={'Login Image'}
                    objectFit={'cover'}
                    src={
                        'https://images.unsplash.com/photo-1486312338219-ce68d2c6f44d?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1352&q=80'
                    }
                />
            </Flex>
        </Stack>
    )
}