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

let AddIcon = () => {
    return "+";
};

let CloseIcon = () => {
    return "x";
};

export default function DrawerForm({ fetchCustomers }) {
    const { isOpen, onOpen, onClose } = useDisclosure()
    return (
        <>
            <Button
                leftIcon={<AddIcon></AddIcon>}
                colorScheme="teal"
                onClick={onOpen}
            >
                Create customer
            </Button>
            <Drawer isOpen={isOpen} onClose={onClose} size={"xl"}>
                <DrawerOverlay />
                <DrawerContent>
                    <DrawerCloseButton />
                    <DrawerHeader>Create new customer</DrawerHeader>

                    <DrawerBody>
                        <SignupForm
                            fetchCustomers={fetchCustomers}
                            onClose={onClose}
                        >
                        </SignupForm>
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