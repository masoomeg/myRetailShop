
# MyRetailShop
A simple single page online store project written in react/redux and spring framework.

## features:
- Responsive bootstrap style
- React component based UI
- Redux state management
- Spring restful web services
- Swagger web services documentation
- Hibernate OR mapping
- In memory H2 SQL database
- Unit/integration tests 

this project hast two pat of frontend for UI and backend to get api services.

## frontend - UI  

in this shop you can search a product by name, and also you have crud oprations on your product 

### Install 

- in frontend folder
- run command 
`yarn start`
the ui will be start in http://localhots:3000
the whole page look like :

<img alt="shop" title="shop" src="https://github.com/masoomeg/myRetailShop/blob/develop/www/images/shopMainPage.jpg" width="90%" style="margin: 0px auto; display: block;" />

### guide

you can add a  product by clicking the add button and the pop up page for adding will show like below

<img alt="edit" title="edit" src="https://github.com/masoomeg/myRetailShop/blob/develop/www/images/editProduct.jpg" width="90%"  style="margin: 0px auto; display: block;" />

## backend api services 

my backend is written with spring and h2 memory 

## Install

- on  backend folder,  run command 
`mvn clean package` 
 (if build was successful the myRetailShop-1.0.0.war is on target folder)
- on target folder, run command 
`java -jar myRetailShop-1.0.0.war`
your server will be start at http://localhost:7080

## Guide

you can see the swagger api for explore my rest api on 
http://localhost:7080/swagger-ui.html

the main page look like :

<img alt="swagger" title="swagger" src="https://github.com/masoomeg/myRetailShop/blob/develop/www/images/swaggerMain.jpg" width="80%"  style="margin: 0px auto; display: block;" />

#### examples

##### GET : /api/products

will get all product  
also you can see your product in h2 console on web on this address : 
http://localhost:7080/h2-console
to connect your jdbcUrl must be : jdbc:h2:mem:retail
and user name : sa
your console will be like this :
<img alt="h2" title="h2" src="https://github.com/masoomeg/myRetailShop/blob/develop/www/images/h2.jpg" width="70%"  style="margin: 0px auto; display: block;" />

##### GET :api/products?name=ome 

will use for search product by name like the picture below

<img alt="search" title="search" src="https://github.com/masoomeg/myRetailShop/blob/develop/www/images/searchProduct.jpg" width="50%"  style="margin: 0px auto; display: block;" />

##### POST: api/products

this url will create a product with this input :
```
{
"name": "test",
"description": "my sample test", 
"category": "Home",
"amount": "3333"
} 
```  
and the output will be :
    
<img alt="create" title="create" src="https://github.com/masoomeg/myRetailShop/blob/develop/www/images/creatProduct.JPG" width="40%"  style="margin: 0px auto; display: block;" />
  
the name of product is unique hence in case of inserting already inserted name you will get error code 400 like  :

<img alt="error" title="error" src="https://github.com/masoomeg/myRetailShop/blob/develop/www/images/error.jpg" width="40%"  style="margin: 0px auto; display: block;" />

##### PUT: api/products/{id}

will use for update the product with given id