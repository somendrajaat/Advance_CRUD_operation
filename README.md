# backend_task_dailype
 
####Endpoints
####1. /create_user
This endpoint handles the creation of a new user.

##Request:

##Method: POST
##Body: JSON object containing user details (full_name, mob_num, pan_num, manager_id)
Response:

##Success: 200 OK with a success message
##Error: Appropriate error message with corresponding HTTP status code
2. /get_users
This endpoint retrieves user records from the database based on different criteria such as user_id, mob_num, or manager_id.

Request:

Method: POST
Body: JSON object containing filter criteria (user_id, mob_num, manager_id)
Response:

Success: 200 OK with a JSON object containing user information
Error: Appropriate error message with corresponding HTTP status code
3. /delete_user
This endpoint handles the deletion of a user based on user_id or mob_num.

Request:

Method: POST
Body: JSON object containing user_id or mob_num
Response:

Success: 200 OK with a success message
Error: Appropriate error message with corresponding HTTP status code
4. /update_user
This endpoint updates user details based on provided user_ids and update_data. It allows bulk updates only for manager_id.

Request:

Method: POST
Body: JSON object containing user_ids and update_data
Response:

Success: 200 OK with a success message
Error: Appropriate error message with corresponding HTTP status code
