import React, { useState } from 'react';
import Swal from 'sweetalert2';
import { Link, useNavigate } from 'react-router-dom';
import UserService from '../../services/UserService';
import './RegistrationComponent.css';

const RegistrationComponent = () => {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [phoneNumber, setPhoneNumber] = useState('');
  const [password, setPassword] = useState('');
  const [address, setAddress] = useState('');
  const [role, setRole] = useState('USER'); // default role

  const navigate = useNavigate();

  const createUser = (e) => {
    e.preventDefault();

    if (
      firstName.trim() === '' ||
      lastName.trim() === '' ||
      email.trim() === '' ||
      username.trim() === '' ||
      phoneNumber.trim() === '' ||
      password.trim() === '' ||
      address.trim() === ''
    ) {
      alertInvalid('Invalid input, make sure everything is filled correctly and try again!');
    } else if (validateEmail() === false) {
      alertInvalid('Invalid email! Make sure email is valid and try again!');
    } else if (!isValidNumber(phoneNumber)) {
      alertInvalid('Invalid phone number or it has less than 5 digits');
    } else {
      const user = { firstName, lastName, email, username, phoneNumber, password, address, role };

      UserService.registration(user)
        .then((response) => {
          console.log(response.data);

          //email pop up
          if (response.data.toString() === 'verificationEmailSent') {
            alertSuccess('Verification email sent! Please check your inbox.');
            setTimeout(() => {
              navigate('/email-verification-sent');
            }, 1600); // Wait for SweetAlert to finish
          }
          
          
           else if (response.data.toString() === 'invalid') {
            alertInvalid('Invalid input, make sure everything is filed correctly and try again!');
          } else if (response.data.toString() === 'emailNotUnique') {
            alertInvalid('Email already exists! Try again!');
          } else if (response.data.toString() === 'usernameNotUnique') {
            alertInvalid('Username already exists! Try again!');
          }
        })
        .catch((error) => {
          console.log('Error: ' + error);
        });
    }
  };

  const alertSuccess = (message) => {
    Swal.fire({
      position: 'top',
      icon: 'success',
      title: message,
      showConfirmButton: false,
      timer: 1500,
    });
  };

  const validateEmail = () => {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  };

  const alertInvalid = (message) => {
    Swal.fire({
      icon: 'error',
      title: 'Oops...',
      text: message,
    });
  };

  const isValidNumber = (input) => {
    return !isNaN(input) && /^\d{5,}$/.test(input);
  };

  return (
    <div className="registration-main-container">
      <div className="card-registration">
        <div className="title-registration">
          <svg
            className="icon-registration"
            xmlns="http://www.w3.org/2000/svg"
            height="1em"
            viewBox="0 0 448 512"
          >
            <path d="M224 256A128 128 0 1 0 224 0a128 128 0 1 0 0 256zm-45.7 48C79.8 304 0 383.8 0 482.3C0 498.7 13.3 512 29.7 512H418.3c16.4 0 29.7-13.3 29.7-29.7C448 383.8 368.2 304 269.7 304H178.3z" />
          </svg>
          Registration
        </div>
        <div className="card-body">
          <form>
            <div className="form-group mb-2">
              <label className="form-label">First name:</label>
              <input
                type="text"
                placeholder="Insert first name"
                name="firstName"
                className="form-control"
                value={firstName}
                onChange={(e) => setFirstName(e.target.value)}
              />
            </div>

            <div className="form-group mb-2">
              <label className="form-label">Last name:</label>
              <input
                type="text"
                placeholder="Insert last name"
                name="lastName"
                className="form-control"
                value={lastName}
                onChange={(e) => setLastName(e.target.value)}
              />
            </div>

            <div className="form-group mb-2">
              <label className="form-label">Email:</label>
              <input
                type="text"
                placeholder="Insert email"
                name="email"
                className="form-control"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
            </div>

            <div className="form-group mb-2">
              <label className="form-label">Phone number:</label>
              <input
                type="text"
                placeholder="Insert phone number"
                name="phoneNumber"
                className="form-control"
                value={phoneNumber}
                onChange={(e) => setPhoneNumber(e.target.value)}
              />
            </div>

            <div className="form-group mb-2">
              <label className="form-label">Username:</label>
              <input
                type="text"
                placeholder="Insert username"
                name="username"
                className="form-control"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
              />
            </div>

            <div className="form-group mb-2">
              <label className="form-label">Password:</label>
              <input
                type="password"
                placeholder="Insert password"
                name="password"
                className="form-control"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
            </div>

            <div className="form-group mb-2">
              <label className="form-label">Address:</label>
              <input
                type="text"
                placeholder="Insert address"
                name="address"
                className="form-control"
                value={address}
                onChange={(e) => setAddress(e.target.value)}
              />
            </div>

            <div className="form-group mb-2">
              <label className="form-label">Role:</label>
              <select
                className="form-control"
                value={role}
                onChange={(e) => setRole(e.target.value)}
              >
                <option value="USER">USER</option>
                <option value="ADMIN">ADMIN</option>
                <option value="EMPLOYEE">EMPLOYEE</option>
              </select>
            </div>

            <button
              id="registrationBtn"
              className="btn btn-success mt-2"
              onClick={(e) => createUser(e)}
            >
              Submit
            </button>
            <Link to="/menu" className="btn btn-danger mt-2" style={{ marginLeft: '5px' }}>
              Cancel
            </Link>
          </form>
        </div>
      </div>
    </div>
  );
};

export default RegistrationComponent;
