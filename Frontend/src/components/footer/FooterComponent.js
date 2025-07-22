import React from 'react';
import { MDBFooter, MDBContainer } from 'mdb-react-ui-kit';

const FooterComponent = () => {
  return (
    <MDBFooter bgColor='primary' className='text-center text-white py-3 shadow-3-strong' style={{position: 'fixed', bottom: 0, width: '100%', zIndex: 1000}}>
      <MDBContainer fluid>
        <span>&copy; 2025 Mitul Ranpariya. All rights reserved.</span>
      </MDBContainer>
    </MDBFooter>
  );
};

export default FooterComponent;
