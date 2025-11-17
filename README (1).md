# Smart Life Website – University Group Project

This project is a Smart Living e-commerce style web application built as a university group assignment. It includes admin and user roles, login authentication, dynamic PHP headers, product browsing, a localStorage cart system, and database interaction via MySQL.

## 🚀 Features Overview

### 👤 User Roles

#### Admin
- Add new users  
- Add new products  
- Manage existing users and products  
- Sees additional navigation links: “Users” and “Products”

**Admin Login**
Email: admin@gmail.com  
Password: Admin123!

#### General User
- Browse products  
- Use “Quick View” for enlarged image and description  
- Add items to cart  
- Fake checkout button showing thank-you message  
- Sees “Cart” instead of admin links

**User Login**
Email: user@gmail.com  
Password: User789/!

## 🔐 Login & Security

- Unauthorized access redirects automatically to login.html  
- Password validation included on sign-up  
- Dynamic header changes based on user role  
  - Admin sees Users + Products  
  - User sees Cart

## 🛒 Shopping Cart System

- Powered by localStorage  
- Items stay in cart on refresh  
- Cart resets only if cache is deleted  

## 🖼 Quick View Feature

- Expands product image  
- Shows large preview and product description  

## 🗂 Project Structure

```
SmartLife_ARA/
│
├── HTML/               # Login, signup, products, admin pages
├── Coursework/         # smartlivingdb.sql
├── Screenshots/        # All UI screenshots
├── CSS/                # Styling files
├── JS/                 # JavaScript (cart, validation, quick view)
└── Includes/           # PHP header + footer components
```

## 🗄 Database

Database name:
smartlivingdb

SQL file location:
SmartLife_ARA/Coursework/smartlivingdb.sql

## 🧪 How to Run Using XAMPP

1. Start services  
   - Open XAMPP  
   - Start Apache  
   - Start MySQL  

2. Open phpMyAdmin  
   - Click Admin next to MySQL  

3. Create database  
   smartlivingdb  
   Import: SmartLife_ARA/Coursework/smartlivingdb.sql

4. Copy project into htdocs  
   xampp/htdocs/SmartLife_ARA

5. Run the website  
   http://localhost:8083/SmartLife_ARA/HTML/Login.html  
   If port 8083 doesn’t work:  
   http://localhost/SmartLife_ARA/HTML/Login.html

## 📸 Screenshots

Screenshots are included in:
SmartLife_ARA/Screenshots/

## 📚 Notes

- Cart uses localStorage  
- Admin pages require admin login  
- Navigation updates based on role
