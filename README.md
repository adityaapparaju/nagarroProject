This project makes use of java and spring boot technology and allows users to view bank statements using simple date and amount ranges.

The below features are used for the application:

User1 (admin): The user is the admin that can view bank statements for both date and amount ranges.

User2 (user): The user can only view statements for three month statements.


The user is required to login to the application:

login as admin:

![login - admin](https://user-images.githubusercontent.com/83164368/115991184-763a8e00-a5d8-11eb-8f83-13024a256cda.png)

After successful login, user is directed to main page to view statements:

![main page](https://user-images.githubusercontent.com/83164368/115991219-99653d80-a5d8-11eb-8d6c-7a4fbf0b0361.png)


The user has the option to view statements by account id input, and results will populate:
![find by account id](https://user-images.githubusercontent.com/83164368/115994896-f87f7e00-a5e9-11eb-8434-2ee06e6b1d6d.png)


The user has the option to view statements by account id, from and to amount input, and results will populate:

![find by account id - from amount - to amount - result](https://user-images.githubusercontent.com/83164368/115994851-cec65700-a5e9-11eb-984b-d4d4d29bc097.png)


The user has the option to view statements by account id, from and to date input, and results will populate:


![find by account id - from date - to date](https://user-images.githubusercontent.com/83164368/115994841-bfdfa480-a5e9-11eb-80da-ee1b232b41d2.png)


The user has the option to view statements by account id, from and to amount,from and to date input, and results will populate:

![find by account id all detail](https://user-images.githubusercontent.com/83164368/115994834-b6eed300-a5e9-11eb-9f0d-dbb230a1e367.png)

If account id is not given, user is requested to provide account id

![no account id](https://user-images.githubusercontent.com/83164368/115991617-6754db00-a5da-11eb-862a-7723f50beb75.png)


if any value is given invalid (e.g. negative value), invalid parameter message provided

![invalid from amount](https://user-images.githubusercontent.com/83164368/115991644-86ec0380-a5da-11eb-97ef-7be75b797ffb.png)


If date is given in wrong format( correct format dd-mm-yyyy), invalid date format message provided

![invalid from date format](https://user-images.githubusercontent.com/83164368/115991680-b0a52a80-a5da-11eb-9c18-4e3d9f67b117.png)








