## Introduction

This document specifies how BackendRestAPI service used for user registration and login with registered users.

**Endpoints**

**Registration:**  
Endpoint: ‘/addStudent’  
Method:POST  
Description:Register a new user  
RequestBody:
{  
"firstName": “uday”,  
"lastname": "testing",  
"gender": "male",  
"phoneNumber": "3730127301",  
"address1": "add1",  
"city": "redmond",  
"state": "wa",  
"country": "usa",  
"emailAddress": “uday@gmail.com",  
"password": “uday”  
}  
Response:  
StatusCode: 200  
ResponseBody:  
{  
Registration successful  
}

**Login:**  
Endpoint:’/login’  
Method:POST  
Description:Logging with existing user  
RequestBody:  
{  
"emailAddress": “uday@gmail.com",  
"password": “uday”  
}  
Response:  
StatusCode: 200  
ResponseBody:  
{  
True  
}


**Error Responses:**  
400:User already registered  
500:Server error
