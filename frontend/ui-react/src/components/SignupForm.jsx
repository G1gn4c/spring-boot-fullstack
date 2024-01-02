import React from 'react';
import ReactDOM from 'react-dom';
import { Formik, Form, useField } from 'formik';
import * as Yup from 'yup';
import { Alert, AlertIcon, Box, Button, FormLabel, Input, Select, Stack } from '@chakra-ui/react';
import { createCustomer } from '../services/client';
import { errorNotification, successNotification } from '../services/notification';

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

const MySelect = ({ label, ...props }) => {
    const [field, meta] = useField(props);
    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Select {...field} {...props} />
            {meta.touched && meta.error ? (
                <Alert className="error" status='error' mt={2}>
                    <AlertIcon></AlertIcon>
                    {meta.error}
                </Alert>
            ) : null}
        </Box>
    );
};

// And now we can use these
const SignupForm = ({ fetchCustomers, onClose }) => {
    return (
        <>
            <Formik
                initialValues={{
                    name: '',
                    email: '',
                    age: 0,
                    gender: '',
                }}
                validationSchema={Yup.object({
                    name: Yup.string()
                        .max(15, 'Must be 15 characters or less')
                        .required('Required'),
                    email: Yup.string()
                        .email('Invalid email address')
                        .required('Required'),
                    age: Yup.number()
                        .min(16, "Must be at least 16")
                        .max(100, "Must be less than 101")
                        .required('Required'),
                    gender: Yup.string()
                        .oneOf(
                            ['MALE', 'FEMALE'],
                            'Invalid gender'
                        )
                        .required('Required'),
                })}
                onSubmit={(values, { setSubmitting }) => {
                    setSubmitting(true);
                    createCustomer(values)
                        .then(res => {
                            console.log(res);
                            successNotification("Customer saved", `${values.name} was successfully saved`);
                            onClose();
                            fetchCustomers();
                        })
                        .catch(err => {
                            console.log(err);
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
                            <Stack spacing={"24px"}>
                                <MyTextInput
                                    label="Name"
                                    name="name"
                                    type="text"
                                    placeholder="Jane"
                                />

                                <MyTextInput
                                    label="Email"
                                    name="email"
                                    type="email"
                                    placeholder="jane@formik.com"
                                />

                                <MyTextInput
                                    label="Age"
                                    name="age"
                                    type="number"
                                    placeholder="18"
                                />

                                <MySelect label="Gender" name="gender">
                                    <option value="">Select a gender</option>
                                    <option value="MALE">Male</option>
                                    <option value="FEMALE">Female</option>
                                </MySelect>

                                <Button disabled={!isValid || isSubmitting} type="submit">Submit</Button>
                            </Stack>
                        </Form>
                    )
                }}
            </Formik>
        </>
    );
};

export default SignupForm;