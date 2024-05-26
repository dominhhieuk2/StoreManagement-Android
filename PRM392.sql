
USE master
DROP DATABASE IF EXISTS PRM392
GO


CREATE DATABASE PRM392
GO
USE PRM392
GO

---------------------------------------------------------------------------- Create table for all attribute --------------------------------------------------------------------------------------------------------- 
CREATE TABLE Roles (
    RoleID				INT						IDENTITY(1, 1)   		PRIMARY KEY,
	RoleName			NVARCHAR(50)			UNIQUE					NOT NULL	              
	
)
go


CREATE TABLE Users (
	UsersID				 INT		            IDENTITY(1, 1)		 PRIMARY KEY,
	UserName		     NVARCHAR(50)							     NOT NULL,
	[Password]           NVARCHAR(50)                                NOT NULL,
	AccountName          NVARCHAR(20)                                NOT NULL,
	Avatar               NVARCHAR(20)                                NOT NULL,
	[Address]            NVARCHAR(50) ,
	Phone                NVARCHAR(50)  ,
	UserStatus           BIT,
	RoleID int
	FOREIGN KEY REFERENCES Roles(RoleID)
)
go


--Create table categories
CREATE TABLE Categorie (
	CategoryID				INT						IDENTITY(1, 1)   		PRIMARY KEY,
	CategoryName			NVARCHAR(50)							NOT NULL
)
GO

-- Create table Material
CREATE TABLE Material (
	MaterialID				INT						IDENTITY(1, 1)   		PRIMARY KEY,
	MaterialName			NVARCHAR(20)			    					NOT NULL,
	UsersID INT
	FOREIGN KEY REFERENCES Users(UsersID),

)
GO



--Create table Products
CREATE TABLE Product (
	ProductID			INT 	        IDENTITY(1, 1)     	     PRIMARY KEY,
	ProductLink         NVARCHAR(20)                             NOT NULL,          
	ProductName		    NVARCHAR(50)				             NOT NULL,
	ProductPrice		MONEY			                         NOT NULL,
	ProductStatus       BIT                                      NOT NULL,
	ProductQuantity     INT                                      NOT NULL,
	[Description]       NVARCHAR(100)				             NOT NULL,
	CategoryID		    INT						
	FOREIGN KEY REFERENCES Categorie(CategoryID)
)
GO

--create table Orders
CREATE TABLE Orders (
	OrderID				INT					IDENTITY(1, 1) 			PRIMARY KEY,
	OrderDate		    DATETIME										NOT NULL,
	Total               Money,
	UsersID				INT
	FOREIGN KEY REFERENCES Users(UsersID)
)

--create table Order
CREATE TABLE Orderdetail (
    OrderdetailID                 INT 			IDENTITY(1, 1) 		PRIMARY KEY,
	ProductID		        INT ,    
	Quantity                INT                                     NOT NULL,	           
	OrderID			        INT       
	FOREIGN KEY REFERENCES Orders(OrderID)  
	)
GO

Create table Cart(
  CartID          INT         IDENTITY(1,1)      PRIMARY KEY      NOT NULL,	
  ProdcutID		  INT 
  FOREIGN KEY REFERENCES Product(ProductID), 
  Quantity        INT                                             NOT NULL,
  UsersID	      INT
  FOREIGN KEY REFERENCES Users(UsersID),
  
)
go
