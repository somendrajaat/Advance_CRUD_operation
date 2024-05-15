# backend_task_dailype

//PREREQUISITES
1. Spring boot should be installed.
2. PostgreSQL is used so make sure to install first.
3. java should be installed

 
Endpoints
1. /create_user
This endpoint handles the creation of a new user.

Request:

Method: POST
##Body: {
    "fullName": "Random User",
    "mobNum": "+911234567890",
    "panNum": "ABCDE1234F",
    "managerId": "3fa85f64-5717-4562-b3fc-2c963f66afa6"
}

Response:

##Success: 200 OK with a success message
##Error: Appropriate error message with corresponding HTTP status code


2. /get_users
This endpoint retrieves user records from the database based on different criteria such as user_id, mob_num, or manager_id.

Request:

Method: POST
Body: URL parameters containing userid or manager id or mobile number
Response:

Success: 200 OK with a JSON object containing user information
Error: Appropriate error message with corresponding HTTP status code

3. /delete_user
This endpoint handles the deletion of a user based on user_id or mob_num.

Request:

Method: POST
Body: JSON object containing userId or mobNum
Response:

Success: 200 OK with a success message
Error: Appropriate error message with corresponding HTTP status code
4. /update_user
This endpoint updates user details based on provided user_ids and update_data. It allows bulk updates only for manager_id.

Request:

Method: POST
Body:{
  "userIds": ["3fa85f64-5717-4562-b3fc-2c963f66afa6"],
  "updateData": {
    "fullName": "New Full Name",
    "mobNum": "9876543210",
    "panNum": "ABCDE1234F",
    "managerId": "3fa85f64-5717-4562-b3fc-2c963f66afa7"
  }
}
Response:

Success: 200 OK with a success message
Error: Appropriate error message with corresponding HTTP status code


