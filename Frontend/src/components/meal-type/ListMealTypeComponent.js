import MealTypeService from '../../services/MealTypeService'
import React, { useState, useEffect } from 'react'
import { Link } from 'react-router-dom';
import Swal from 'sweetalert2';
import { Modal, Button } from 'react-bootstrap'
import CreateMealTypeComponent from './CreateMealTypeComponent'
import EditMealTypeComponent from './EditMealTypeComponent';
import './ListMealTypeComponent.css';

const ListMealTypeComponent = () => {

    const [mealTypes, setMealTypes] = useState([]);
    const fd = new FormData();

    const [id, setId] = useState(0);
    const [typeName, setTypeName] = useState('');

    const [image, setImage] = useState('');
    const [imageName, setImageName] = useState('');
    const [description, setDescription] = useState('');

    const [show, setShow] = useState(false);
    const [showEdit, setShowEdit] = useState(false);

    const [selectedFile, setSelectedFile] = useState(undefined);

    const file = { selectedFile, setSelectedFile };

    const mealType = { id, typeName, description, image, imageName, setImage, setImageName, setTypeName, setDescription }

    const user = { role: localStorage.getItem("role") };
    const [searchTerm, setSearchTerm] = useState("");

    useEffect(() => {
        getAllMealTypes();
    }, [])

    const getAllMealTypes = () => {
        MealTypeService.getAllMealTypes().then((response) => {
            setMealTypes(response.data);
            sortMealTypesById();
        }).catch(error => {
            console.log(error);
        })
    }

    const sortMealTypesById = () => {
        mealTypes.sort((a, b) => a.id - b.id);
    }

    const handleClose = () => {
        setShow(false);
        mealType.setTypeName('');
        mealType.setDescription('');
    }

    const handleCloseEdit = () => {
        setShowEdit(false);
        //meal.setId(0);
        mealType.setTypeName('');
        mealType.setDescription('');
    }

    const handleShow = () => {
        setShow(true);
        setId(null); //mora ovako da se setuje, kada se vrsi izmena, nakon toga zapamti id od starog pa radi izmenu
    };

    const handleShowEdit = (mealType) => {
        setShowEdit(true);
        setId(mealType.id);
        setTypeName(mealType.typeName);
        setDescription(mealType.description);
    };

    const handleSubmitEdit = () => {
        if (mealType.typeName.trim() === "" || mealType.description.trim() === "") {
            alert("Invalid input");
            return;
        }
        if (selectedFile) {
            const fd = new FormData();
            fd.append('image', selectedFile);
            fd.append('mealType', JSON.stringify(mealType));
            MealTypeService.updateMealTypeWithImage(fd).then((response) => {
                const responseFromServer = response.data;
                if (responseFromServer === "success") {
                    alert("Successfully updated meal type (category)!");
                    handleCloseEdit();
                    getAllMealTypes();
                } else if (responseFromServer === "fail") {
                    alert("Failed to update meal type!");
                } else if (responseFromServer === "invalid") {
                    alert("Invalid input! Try again!");
                }
            });
        } else {
            MealTypeService.updateMealType(mealType).then((response) => {
                const responseFromServer = response.data;
                if (responseFromServer === "success") {
                    alert("Successfully updated meal type (category)!");
                    handleCloseEdit();
                    getAllMealTypes();
                } else if (responseFromServer === "fail") {
                    alert("Failed to update meal type!");
                } else if (responseFromServer === "invalid") {
                    alert("Invalid input! Try again!");
                }
            });
        }
    }

    const handleSubmit = () => {
        if (mealType.typeName.trim() === "" || mealType.description.trim() === "") {
            alert("Invalid input");
        }
        if (selectedFile != null && selectedFile != undefined) {
            fd.append('image', selectedFile);
            fd.append('mealType', JSON.stringify(mealType));
            console.log("Selected fileeee" + selectedFile);
        }
        else {
            fd.append('image', '');
            fd.append('mealType', JSON.stringify(mealType));
        }
        MealTypeService.createMealType(fd).then((response) => {
            const responseFromServer = response.data;
            if (responseFromServer === "success") {
                alert("Succesfully created meal type (category)!");
                handleClose();
                getAllMealTypes();
            }
            else if (responseFromServer === "fail") {
                alert("Failted to create meal type!")
            }
            else if (responseFromServer === "invalid") {
                alert("Invalid input! Try again!");
            }
        })
    }


    const deleteMealType = (mealTypeId) => {
        MealTypeService.deleteMealType(mealTypeId).then((response) => {
            if (response.data === "success") {
                alert("Succesfully deleted meal type (category)!");
                getAllMealTypes();
            }
            else if (response.data === "fail") {
                alert("Failed to delete meal type (category)!");
            }
        }).catch(error => {
            console.log(error);
        })
    }

    const alertAreYouSureDelete = (id) => {
        Swal.fire({
            title: 'Are you sure?',
            text: "If you click yes, meal type will be deleted!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                deleteMealType(id);
            }
        })
    }

    // Filter meal types based on search term
    const filteredMealTypes = mealTypes.filter(mt => {
        const term = searchTerm.toLowerCase();
        return (
            mt.typeName.toLowerCase().includes(term) ||
            mt.description.toLowerCase().includes(term)
        );
    });

    return (
        <>
            <div className='container'>
                <h2 className='text-center'>Meal type list</h2>
                {/* Search Bar */}
                <div style={{ maxWidth: 400, margin: '0 auto 20px auto' }}>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Search meal types by name or description..."
                    value={searchTerm}
                    onChange={e => setSearchTerm(e.target.value)}
                  />
                </div>
                <button className="btn btn-success mb-2" onClick={handleShow}>Create new meal type</button>
                <table id='table' className='table table-hover nesto'>
                    <thead className='thead-name'>
                        <tr>
                            <th className='theadth'>No.</th>
                            <th className='theadth'>Image</th>
                            <th className='theadth'>Type name</th>
                            <th className='theadth'>Description</th>
                            <th className='theadth'>Action</th>
                        </tr>
                    </thead>
                    {/*mora src={"data:image/png;base64," + meal.image}, ne moze samo src={meal.image}  */}
                    <tbody>
                        {filteredMealTypes.map((mealType, index) => (
                            <tr key={mealType.id}>
                                <td className='td-content'>{index + 1}</td>
                                <td className='td-content-img'>
                                    <img
                                        src={"data:image/png;base64," + mealType.image}
                                        alt={mealType.typename}
                                        style={{ width: '30%', height: 'auto', borderRadius: '8px' }}
                                    />
                                </td>
                                <td className='td-content'>{mealType.typeName}</td>
                                <td className='td-content'>{mealType.description}</td>
                                <td className='td-content'>
                                    <button className='btn btn-success' onClick={() => handleShowEdit(mealType)}>Update</button>
                                    <button className='btn btn-danger' onClick={() => alertAreYouSureDelete(mealType.id)}
                                        style={{ marginLeft: "5px" }}>Delete</button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Create new meal type</Modal.Title>
                </Modal.Header>

                <Modal.Body>
                    <CreateMealTypeComponent mealType={mealType} file={file} user={user} />
                </Modal.Body>

                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>Close</Button>
                    <Button variant="primary" onClick={handleSubmit}>Save changes</Button>
                </Modal.Footer>
            </Modal>

            <Modal show={showEdit} onHide={handleCloseEdit}>
                <Modal.Header closeButton>
                    <Modal.Title>Edit meal type</Modal.Title>
                </Modal.Header>

                <Modal.Body>
                    <EditMealTypeComponent mealType={mealType} file={file} />
                </Modal.Body>

                <Modal.Footer>
                    <Button variant="secondary" onClick={handleCloseEdit}>Close</Button>
                    <Button variant="primary" onClick={handleSubmitEdit}>Save changes</Button>
                </Modal.Footer>
            </Modal>
        </>
    )
}

export default ListMealTypeComponent