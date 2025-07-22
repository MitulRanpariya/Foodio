import MealService from '../../services/MealService';
import React, { useState, useEffect } from 'react';
import Swal from 'sweetalert2';
import { Modal, Button } from 'react-bootstrap';
import CreateMealComponent from './CreateMealComponent';
import EditMealComponent from './EditMealComponent';
import './ListMealComponent.css';

const ListMealComponent = () => {
  const [meals, setMeals] = useState([]);
  const [id, setId] = useState(0);
  const [name, setName] = useState('');
  const [price, setPrice] = useState('');
  const [image, setImage] = useState('');
  const [imageName, setImageName] = useState('');
  const [mealType, setMealType] = useState(undefined);
  const [description, setDescription] = useState('');
  const [show, setShow] = useState(false);
  const [showEdit, setShowEdit] = useState(false);
  const [selectedFile, setSelectedFile] = useState(undefined);
  const fd = new FormData();
  const [searchTerm, setSearchTerm] = useState("");

  const file = { selectedFile, setSelectedFile };
  const meal = {
    id,
    name,
    price,
    image,
    imageName,
    mealType,
    description,
    setDescription,
    setName,
    setPrice,
    setImage,
    setImageName,
    setMealType
  };

  // get role from localStorage
  const role = localStorage.getItem("role");
  const user = { role };

  useEffect(() => {
    getAllMeals();
  }, []);

  const getAllMeals = () => {
    MealService.getAllMeals()
      .then((response) => {
        setMeals(response.data);
        if (response.data[0]?.mealType) setMealType(response.data[0].mealType);
      })
      .catch((error) => console.log(error));
  };

  const handleClose = () => {
    setShow(false);
    meal.setName('');
    meal.setPrice('');
    meal.setDescription('');
    meal.setMealType(meals[0]?.mealType || {});
  };

  const handleCloseEdit = () => {
    setShowEdit(false);
    meal.setName('');
    meal.setPrice('');
    meal.setDescription('');
    meal.setMealType(meals[0]?.mealType || {});
  };

  const handleShow = () => {
    setShow(true);
    setId(null);
  };

  const handleShowEdit = (mealData) => {
    setShowEdit(true);
    setId(mealData.id);
    setName(mealData.name);
    setPrice(mealData.price);
    setMealType(mealData.mealType);
    setDescription(mealData.description);
  };

  const handleSubmitEdit = () => {
    if (meal.name.trim() === "" || isNaN(parseInt(meal.price)) || parseInt(meal.price) < 0) {
      alert("Invalid input");
      return;
    }

    if (selectedFile) {
      const fd = new FormData();
      fd.append('meal', JSON.stringify(meal));
      fd.append('image', selectedFile);
      MealService.updateMealWithImage(fd).then((response) => {
        const res = response.data;
        if (res === "success") {
          alert("Successfully edited meal");
          handleCloseEdit();
          getAllMeals();
        } else if (res === "invalid") {
          alert("Invalid input");
        } else {
          alert("Failed to edit meal");
        }
      });
    } else {
      MealService.updateMeal(meal).then((response) => {
        const res = response.data;
        if (res === "success") {
          alert("Successfully edited meal");
          handleCloseEdit();
          getAllMeals();
        } else if (res === "invalid") {
          alert("Invalid input");
        } else {
          alert("Failed to edit meal");
        }
      });
    }
  };

  const handleSubmit = () => {
    if (
      meal.name.trim() === "" ||
      meal.description.trim() === "" ||
      isNaN(parseInt(meal.price)) ||
      parseInt(meal.price) < 0
    ) {
      alert("Invalid input");
      return;
    }

    fd.append('meal', JSON.stringify(meal));
    fd.append('image', selectedFile);

    MealService.createMeal(fd).then((response) => {
      const res = response.data;
      if (res === "success") {
        alert("Successfully added meal!");
        handleClose();
        getAllMeals();
      } else if (res === "invalid") {
        alert("Invalid input");
      } else {
        alert("Failed to add new meal");
      }
    });
  };

  const deleteMeal = (mealId) => {
    MealService.deleteMeal(mealId)
      .then((response) => {
        if (response.data === "success") {
          alert("Successfully deleted meal!");
          getAllMeals();
        } else {
          alert("Failed to delete meal!");
        }
      })
      .catch((error) => console.log(error));
  };

  const alertAreYouSureDelete = (id) => {
    Swal.fire({
      title: 'Are you sure?',
      text: "If you click yes, meal will be deleted!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
      if (result.isConfirmed) {
        deleteMeal(id);
      }
    });
  };

  // Filter meals based on search term
  const filteredMeals = meals.filter(meal => {
    const term = searchTerm.toLowerCase();
    return (
      meal.name.toLowerCase().includes(term) ||
      meal.description.toLowerCase().includes(term) ||
      (meal.mealType && meal.mealType.typeName.toLowerCase().includes(term))
    );
  });

  return (
    <>
      <div className='container'>
        <h2 className='text-center'>Meal list</h2>
        {/* Search Bar */}
        <div style={{ maxWidth: 400, margin: '0 auto 20px auto' }}>
          <input
            type="text"
            className="form-control"
            placeholder="Search meals by name, description, or type..."
            value={searchTerm}
            onChange={e => setSearchTerm(e.target.value)}
          />
        </div>
        {user.role === "ADMIN" && (
          <button className="btn btn-blue mb-2" onClick={handleShow}>
            Create new meal
          </button>
        )}

        <table id="table" className='table table-hover tableElement'>
          <thead className='thead-name'>
            <tr>
              <th className='theadth'>No.</th>
              {/* <th className='theadth'>Meal ID</th> */}
              <th className='theadth'>Image</th>
              <th className='theadth'>Name</th>
              <th className='theadth'>Type</th>
              <th className='theadth'>Description</th>
              <th className='theadth'>Price</th>
              <th className='theadth'>Action</th>
            </tr>
          </thead>
          <tbody>
            {filteredMeals.map((meal, index) => (
              <tr key={meal.id}>
                <td className='td-content'>{index + 1}</td>
                <td className='td-content-pic'>
                  <img
                    src={"data:image/png;base64," + meal.image}
                    alt={meal.name}
                    style={{ width: '30%', height: 'auto', borderRadius: '10px' }}
                  />

                </td>

                <td className='td-content'>{meal.name}</td>
                <td className='td-content'>{meal.mealType.typeName}</td>
                <td className='td-content'>{meal.description}</td>
                <td className='td-content'>{meal.price}</td>
                <td className='td-content'>
                  {user.role === "ADMIN" && (
                    <>
                      <button className='btn btn-blue' onClick={() => handleShowEdit(meal)}>Update</button>
                      <button
                        className='btn btn-outline-blue'
                        onClick={() => alertAreYouSureDelete(meal.id)}
                        style={{ marginLeft: "5px" }}
                      >
                        Delete
                      </button>
                    </>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {/* Create Meal Modal (ADMIN only) */}
      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Create new meal</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <CreateMealComponent meal={meal} file={file} user={user} />
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>Close</Button>
          <Button variant="primary" onClick={handleSubmit}>Save changes</Button>
        </Modal.Footer>
      </Modal>

      {/* Edit Meal Modal */}
      <Modal show={showEdit} onHide={handleCloseEdit}>
        <Modal.Header closeButton>
          <Modal.Title>Edit meal</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <EditMealComponent meal={meal} file={file} />
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleCloseEdit}>Close</Button>
          <Button variant="primary" onClick={handleSubmitEdit}>Save changes</Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};

export default ListMealComponent;
