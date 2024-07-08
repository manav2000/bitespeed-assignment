## BiteSpeed Assignment

#### <u>APIs</u>
<b>POST</b> : https://bitespeed-latest-jz5i.onrender.com/identity

<u>Request Body</u>
```javascript
{
"email": "abc@gmail.com",
"phoneNumber": "12345"
}
```
<u>Response Body</u>
```javascript
{
    "primaryContactId": 4,
    "emails": [
        "abc@gmail.com"
    ],
    "phoneNumbers": [
        "12345"
    ],
    "secondaryContactIds": []
}
```