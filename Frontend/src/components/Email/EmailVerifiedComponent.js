// EmailVerifiedComponent.jsx
import React, { useEffect } from 'react';
import Swal from 'sweetalert2';
import { useNavigate } from 'react-router-dom';

const EmailVerifiedComponent = () => {
  const navigate = useNavigate();

  useEffect(() => {
    Swal.fire({
      icon: 'success',
      title: 'Email verified successfully!',
      showConfirmButton: false,
      timer: 2000,
    });

    setTimeout(() => {
      navigate('/login');
    }, 2000);
  }, [navigate]);

  return null; // optional: return spinner or logo
};

export default EmailVerifiedComponent;
