# System Architecture

ScanNServe is a multi-tenant SaaS application that allows restaurants to manage their menus, tables, and incoming orders via QR code scanning. 

## 1. High Level Architecture

The application is built using a modern decoupled architecture:
- **Frontend**: React (Vite) Single Page Application (SPA).
- **Backend**: Java Spring Boot providing a RESTful API.
- **Database**: PostgreSQL for relational data storage.

```mermaid
graph TD
    Client[Client Browser / Mobile] -->|HTTPS / REST API| API[Spring Boot Backend API]
    API -->|JPA / Hibernate| DB[(PostgreSQL Database)]
    
    subgraph Frontend [React Application]
        SA[Super Admin Dashboard]
        RA[Restaurant Admin Dashboard]
        CA[Customer QR Ordering App]
    end
    
    Client -.-> Frontend
```

---

## 2. Authentication Flow

Authentication is managed using JSON Web Tokens (JWT) and HTTP-only cookies to ensure security against XSS.

```mermaid
sequenceDiagram
    participant User
    participant Frontend
    participant AuthController
    participant DB

    User->>Frontend: Enters Credentials
    Frontend->>AuthController: POST /api/v1/auth/token
    AuthController->>DB: Validate User
    DB-->>AuthController: User Valid
    AuthController-->>Frontend: 200 OK + JWT (Response) + RefreshToken (Cookie)
    Frontend->>Frontend: Store Access Token in Memory
```

---

## 3. Multi-Tenancy Design

The system implements logical multi-tenancy. A single instance of the backend and database serves multiple restaurants.
- **SuperAdmin**: Global access. Can register new `RestaurantEntity`.
- **RestaurantAdmin**: Bound to a specific `restaurant_id`. They can only manage data that has a matching `restaurant_id`.
- **Customers (QR)**: The QR code contains a `tableToken` which intrinsically links the customer session to a specific table and restaurant.

---

## 4. Kitchen & Order Workflow (Conceptual)

While the full order socket flow is handled internally, the conceptual workflow is as follows:

```mermaid
stateDiagram-v2
    [*] --> ScannedQR: Customer scans table QR
    ScannedQR --> BrowsingMenu: Token validated
    BrowsingMenu --> PlacedOrder: Customer submits cart
    PlacedOrder --> KitchenDashboard: Order sent via WebSocket/Polling
    KitchenDashboard --> Preparing: Chef accepts order
    Preparing --> Served: Order delivered to table
    Served --> [*]
```

## 5. Folder Structure

### Backend (`src/main/java/com/app/ScanNServe`)
- `controller/`: REST API endpoints and interface definitions.
- `service/`: Business logic layer.
- `domain/entity/`: JPA entities representing database tables.
- `domain/repository/`: Spring Data JPA repository interfaces.
- `dto/`: Data Transfer Objects for API requests and responses.
- `config/`: Spring Security and Bean configurations.
- `exception/`: Global exception handlers.

### Frontend (`client/fe-serve`)
- `src/components/`: Reusable UI elements (Buttons, Inputs).
- `src/pages/`: Main route views (Dashboards, Login).
- `src/context/` or `src/store/`: State management.
- `src/utils/`: Axios interceptors, formatting helpers.
