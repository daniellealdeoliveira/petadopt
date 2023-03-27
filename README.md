# petadopt
To run the PetadoptApplication.java class, use a REST request API such as POSTMAN and perform the following operations:

Create a GET request with the path "/api/pets" and include any of these fields in the request body: term, category, createdDate(dd/MM/yyyy), and status to search for all pets with the corresponding filters.

Create a POST request with the path "/api/pets" and include the following fields in the request body: name, description, image, category, createdDate, and status to create a new pet.

Create a PATCH request with the path "/api/pets/{id}/updateStatus" and use status as a request parameter to update the status of the selected pet.
