import { createStandaloneToast } from '@chakra-ui/react'

const { ToastContainer, toast } = createStandaloneToast();

function notification(title, description, status) {
    toast({
        title: title,
        description: description,
        status: status,
        duration: 4000,
        isClosable: true,
    })
}

export function successNotification(title, description) {
    notification(title, description, "success");
}

export function errorNotification(title, description) {
    notification(title, description, "error");
}