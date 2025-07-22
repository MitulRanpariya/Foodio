import React, { useState, useEffect } from 'react';
import MealService from '../../services/MealService';
import { Form } from 'react-bootstrap';
import './ListMealComponent.css';

const CreateMealComponent = ({ meal, file, handleSubmit, isEdit, user }) => {
  const [mealTypes, setMealTypes] = useState([]);

  useEffect(() => {
    MealService.getAllMealTypes()
      .then((response) => setMealTypes(response.data))
      .catch((error) => console.log(error));
  }, []);

  const onChoseFile = (e) => {
    file.setSelectedFile(e.target.files[0]);
  };

  if (!user || user.role !== 'ADMIN') return null;

  return (
    <div className='container-add-meal'>
      <form onSubmit={handleSubmit}>
        <div className='form-group mb-2'>
          <label className='form-label'>Name:</label>
          <input
            type="text"
            name="name"
            className="form-control"
            placeholder="Insert name"
            value={meal.name}
            onChange={(e) => meal.setName(e.target.value)}
          />
        </div>

        <div className='form-group mb-2'>
          <label className='form-label'>Description:</label>
          <input
            type="text"
            name="description"
            className="form-control"
            placeholder="Insert description"
            value={meal.description}
            onChange={(e) => meal.setDescription(e.target.value)}
          />
        </div>

        <div className='form-group mb-2'>
          <label className='form-label'>Price:</label>
          <input
            type="text"
            name="price"
            className="form-control"
            placeholder="Insert price"
            value={meal.price}
            onChange={(e) => meal.setPrice(e.target.value)}
          />
        </div>

        <div className='form-group mb-2'>
          <label className='form-label'>Meal Type:</label>
          <Form.Select onChange={(e) => meal.setMealType(JSON.parse(e.target.value))}>
            <option disabled selected>Select Meal Type</option>
            {mealTypes.map((type) => (
              <option key={type.id} value={JSON.stringify(type)}>
                {type.typeName}
              </option>
            ))}
          </Form.Select>
        </div>

        <div className='form-group mb-2'>
          <label className='form-label'>Upload image:</label>
          <input
            type="file"
            name="image"
            className="form-control"
            onChange={onChoseFile}
          />
        </div>

        <button type="submit" className="btn btn-success">
          {isEdit ? 'Update Meal' : 'Add Meal'}
        </button>
      </form>
    </div>
  );
};

export default CreateMealComponent;
