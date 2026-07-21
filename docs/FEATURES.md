# Feature Documentation & Code Explanations

This document highlights the major features of the ScanNServe system and explains the key codebase components responsible for them.

---

## 1. Multi-Tenant Restaurant Onboarding

### Overview
Super Admins can register new restaurants on the platform. Creating a restaurant sets up a dedicated tenant space where the restaurant's admins can manage their own menu, tables, and staff.

### Key Components
- **`ISuperAdminController`**: Handles the API route for creating a restaurant.
- **`RestaurantEntity`**: The core data model representing the tenant. Fields like `subscriptionExpiry` determine active status.
- **`PropertyForm.jsx` (Frontend)**: The React component used by the Super Admin to input restaurant details. Uses TailwindCSS for styling and Axios for form submission.

---

## 2. Centralized Master Food Catalog

### Overview
Instead of each restaurant manually typing out common food items (e.g., "Coca Cola", "French Fries"), the system maintains a "Master Food Catalog". Restaurants simply subscribe to these items and set their own local price.

### Key Components
- **`FoodCategoryEntity` & `FoodItemEntity`**: Master tables managed by the Super Admin.
- **`RestaurantMenuItemEntity`**: The mapping table. It links a `RestaurantEntity` to a master `FoodItemEntity` and adds restaurant-specific details like `price` and `customImage`.
- **`IFoodItemController`**: API endpoints for managing the master catalog.
- **`IRestaurantAdminController.subscribeItem()`**: API endpoint allowing a restaurant to add a master item to their specific menu.

---

## 3. QR Table Generation

### Overview
Restaurant admins can generate QR codes for their physical tables. Scanning a QR code provides the customer with a session token to view the menu and place orders.

### Key Components
- **`RestaurantTableEntity`**: Stores the `tableNumber` and generates a secure `tableToken`.
- **QR Generation Logic**: The frontend takes the `tableToken` and converts it into a visual QR code for printing using standard React QR libraries.

---

## 4. Frontend Component Highlights

### `StatCard.jsx`
A reusable dashboard component in the React frontend.
- **Purpose**: Displays analytical data (e.g., Total Revenue, Active Orders).
- **Responsibilities**: Accepts props for title, value, and icon. Applies responsive Tailwind classes for a unified aesthetic.
- **Usage**: Heavily used in both the Super Admin and Restaurant Admin dashboard views.

### `ProtectedRoute.jsx`
- **Purpose**: Secures frontend routes.
- **Responsibilities**: Checks the React Context/Redux store for a valid user session and role. If the user is unauthenticated or lacks the required role (e.g., a Kitchen Staff trying to access the Admin panel), they are redirected to the login page.
