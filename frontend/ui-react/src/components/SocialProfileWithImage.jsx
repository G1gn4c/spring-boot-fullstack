'use client'

import {
    Heading,
    Avatar,
    Box,
    Center,
    Image,
    Flex,
    Text,
    Stack,
    Button,
    useColorModeValue,
    transform,
    useDisclosure,
} from '@chakra-ui/react'

import {
    Tag,
    TagLabel,
    TagLeftIcon,
    TagRightIcon,
    TagCloseButton,
} from '@chakra-ui/react'
import React from 'react';

import {
    AlertDialog,
    AlertDialogBody,
    AlertDialogFooter,
    AlertDialogHeader,
    AlertDialogContent,
    AlertDialogOverlay,
    AlertDialogCloseButton,
} from '@chakra-ui/react'

import { deleteCustomer } from '../services/client.js'
import { successNotification, errorNotification } from '../services/notification.js'
import CustomerUpdateDrawerForm from './CustomerUpdateDrawerForm.jsx';

export default function SocialProfileWithImage({ id, name, email, age, gender, fetchCustomers }) {
    let ranmdomGender = gender === "MALE" ? "men" : "women";
    const { isOpen, onOpen, onClose } = useDisclosure()
    const cancelRef = React.useRef()

    return (
        <Center py={6}>
            <Box
                maxW={'300px'}
                minW={"300px"}
                w={'full'}
                m={2}
                bg={useColorModeValue('white', 'gray.800')}
                boxShadow={'lg'}
                rounded={'md'}
                overflow={'hidden'}>
                <Image
                    h={'120px'}
                    w={'full'}
                    src={
                        'https://images.unsplash.com/photo-1612865547334-09cb8cb455da?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80'
                    }
                    objectFit="cover"
                    alt="#"
                />
                <Flex justify={'center'} mt={-12}>
                    <Avatar
                        size={'xl'}
                        src={
                            `https://randomuser.me/api/portraits/${ranmdomGender}/${id}.jpg`
                        }
                        css={{
                            border: '2px solid white',
                        }}
                    />
                </Flex>

                <Box p={6}>
                    <Stack spacing={2} align={'center'} mb={5}>
                        <Tag borderRadius={"full"}>{id}</Tag>
                        <Heading fontSize={'2xl'} fontWeight={500} fontFamily={'body'}>
                            {name}
                        </Heading>
                        <Text color={'gray.500'}>{email}</Text>
                        <Text color={'gray.500'}>Age {age} | {gender}</Text>
                    </Stack>
                </Box>
                <Stack direction={"row"} justify={"center"} spacing={6} p={4}>
                    <Stack>
                        <CustomerUpdateDrawerForm
                            fetchCustomers={fetchCustomers}
                            initialValues={{ id, name, email, age, gender }}
                        >
                        </CustomerUpdateDrawerForm>
                    </Stack>
                    <Stack>
                        <Button
                            bg={"red.400"}
                            color={"white"}
                            rounded={"full"}
                            _hover={{
                                transform: "translateY(-2px)",
                                boxShadow: "lg"
                            }}
                            _focus={{
                                bg: "grey.500"
                            }}
                            onClick={onOpen}
                        >
                            Delete
                        </Button>
                        <AlertDialog
                            isOpen={isOpen}
                            leastDestructiveRef={cancelRef}
                            onClose={onClose}
                        >
                            <AlertDialogOverlay>
                                <AlertDialogContent>
                                    <AlertDialogHeader fontSize='lg' fontWeight='bold'>
                                        Delete Customer
                                    </AlertDialogHeader>

                                    <AlertDialogBody>
                                        Are you sure yopu want to delete {name}? You can't undo this action afterwards.
                                    </AlertDialogBody>

                                    <AlertDialogFooter>
                                        <Button ref={cancelRef} onClick={onClose}>
                                            Cancel
                                        </Button>
                                        <Button colorScheme='red' onClick={() => {
                                            deleteCustomer(id)
                                                .then(res => {
                                                    console.log(res);
                                                    successNotification("Customer deleted", `${name} was successfully deleted`);
                                                    fetchCustomers();
                                                })
                                                .catch(err => {
                                                    console.log(err);
                                                    errorNotification(err.code, err.response.data.message);
                                                })
                                                .finally(() => {
                                                    onClose();
                                                });
                                        }}
                                            ml={3}>
                                            Delete
                                        </Button>
                                    </AlertDialogFooter>
                                </AlertDialogContent>
                            </AlertDialogOverlay>
                        </AlertDialog>
                    </Stack>
                </Stack>
            </Box>
        </Center >
    )
}