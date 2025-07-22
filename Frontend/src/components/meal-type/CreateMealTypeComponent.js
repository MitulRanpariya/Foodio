import React from 'react';
import './ListMealTypeComponent.css';

const CreateMealTypeComponent = ({ mealType, file, handleSubmit, isEdit, user }) => {
  const onChoseFile = (e) => {
    file.setSelectedFile(e.target.files[0]);
  };

  if (!user || user.role !== 'ADMIN') return null;

  return (
    <div className='container-add-meal'>
      <form onSubmit={handleSubmit}>
        <div className='form-group mb-2'>
          <label className='form-label'>Type name:</label>
          <input
            type="text"
            placeholder="Insert name"
            name="typeName"
            className="form-control"
            value={mealType.typeName}
            onChange={(e) => mealType.setTypeName(e.target.value)}
          />
        </div>

        <div className='form-group mb-2'>
          <label className='form-label'>Description:</label>
          <input
            type="text"
            placeholder="Insert description"
            name="description"
            className="form-control"
            value={mealType.description}
            onChange={(e) => mealType.setDescription(e.target.value)}
          />
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
          {isEdit ? 'Update MealType' : 'Add MealType'}
        </button>
      </form>
    </div>
  );
};

export default CreateMealTypeComponent;
