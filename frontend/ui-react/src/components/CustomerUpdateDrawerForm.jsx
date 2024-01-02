import {
    Drawer,
    DrawerBody,
    DrawerFooter,
    DrawerHeader,
    DrawerOverlay,
    DrawerContent,
    DrawerCloseButton,
    Button,
    useDisclosure,
    Input,
} from '@chakra-ui/react'
import SignupForm from './SignupForm';
import CustomerUpdateSignupForm from './CustomerUpdateSignupForm';

let AddIcon = () => {
    return "+";
};

let CloseIcon = () => {
    return "x";
};

export default function CustomerUpdateDrawerForm({ fetchCustomers, initialValues }) {
    const { isOpen, onOpen, onClose } = useDisclosure()
    return (
        <>
            <Button
                bg={"gray.200"}
                color={"black"}
                rounded={"full"}
                _hover={{
                    transform: "translateY(-2px)",
                    boxShadow: "lg"
                }}
                onClick={onOpen}
            >
                Update customer
            </Button>
            <Drawer isOpen={isOpen} onClose={onClose} size={"xl"}>
                <DrawerOverlay />
                <DrawerContent>
                    <DrawerCloseButton />
                    <DrawerHeader>Update {initialValues.name}</DrawerHeader>

                    <DrawerBody>
                        <CustomerUpdateSignupForm
                            fetchCustomers={fetchCustomers}
                            onClose={onClose}
                            initialValues={initialValues}
                        >
                        </CustomerUpdateSignupForm>
                    </DrawerBody>

                    <DrawerFooter>
                        <Button
                            leftIcon={<CloseIcon></CloseIcon>}
                            colorScheme="teal"
                            onClick={onClose}
                        >
                            Close
                        </Button>
                    </DrawerFooter>
                </DrawerContent>
            </Drawer>
        </>
    );
}