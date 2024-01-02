import SidebarWithHeader from "./components/shared/SidebarWithHeader";
import { useEffect, useState } from "react";
import { getCustomers } from "./services/client";
import { Spinner, Text } from '@chakra-ui/react'
import SocialProfileWithImage from "./components/SocialProfileWithImage";
import { Wrap, WrapItem } from '@chakra-ui/react'
import DrawerForm from "./components/DrawerForm";
import { errorNotification } from "./services/notification";

function App() {

  const [customers, setCustomers] = useState([]);
  const [loading, setLoading] = useState(false);
  const [err, setError] = useState("");

  function fetchCustomers() {
    setLoading(true);
    getCustomers()
      .then(res => {
        setCustomers(res.data);
      })
      .catch(err => {
        console.log(err);
        errorNotification(err.code, err.response.data.message);
      })
      .finally(() => {
        setLoading(false);
      });
  }

  useEffect(() => {
    fetchCustomers();
  }, []);

  if (loading) {
    return (
      <SidebarWithHeader>
        <Spinner
          thickness='4px'
          speed='0.65s'
          emptyColor='gray.200'
          color='blue.500'
          size='xl'
        />
      </SidebarWithHeader>
    );
  }

  if (err) {
    return (
      <SidebarWithHeader>
        <DrawerForm
          fetchCustomers={fetchCustomers}
        >
        </DrawerForm>
        <Text mt={5}>Ops! There was an error</Text>
      </SidebarWithHeader>
    );
  }

  if (customers.length <= 0) {
    return (
      <SidebarWithHeader>
        <DrawerForm
          fetchCustomers={fetchCustomers}
        >
        </DrawerForm>
        <Text mt={5}>No customers available</Text>
      </SidebarWithHeader>
    );
  }

  return (
    <SidebarWithHeader>
      <DrawerForm
        fetchCustomers={fetchCustomers}
      >
      </DrawerForm>
      <Wrap justify={"center"} spacing={"30px"}>
        {
          customers.map((customer, index, customers) => {
            return (
              <WrapItem key={index}>
                <SocialProfileWithImage
                  {...customer}
                  fetchCustomers={fetchCustomers}
                ></SocialProfileWithImage>
              </WrapItem>
            );
          })
        }
      </Wrap>
    </SidebarWithHeader>
  );
}

export default App;