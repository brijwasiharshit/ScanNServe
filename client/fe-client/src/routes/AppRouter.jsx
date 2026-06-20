import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { RepositoryProvider } from '../context/RepositoryProvider.jsx';
import { AuthProvider } from '../context/AuthProvider.jsx';
import { ProtectedRoute } from './ProtectedRoute.jsx';
import { GuestLayout, AdminLayout, SuperAdminLayout } from '../components/layout/DashboardLayout.jsx';
import { HomePage } from '../pages/HomePage.jsx';
import { LoginPage } from '../pages/auth/LoginPage.jsx';
import { GuestMenuPage } from '../pages/guest/GuestMenuPage.jsx';
import { AdminDashboardPage } from '../pages/admin/AdminDashboardPage.jsx';
import { AdminMenuPage } from '../pages/admin/AdminMenuPage.jsx';
import { AdminSettingsPage } from '../pages/admin/AdminSettingsPage.jsx';
import { SuperAdminDashboardPage } from '../pages/superadmin/SuperAdminDashboardPage.jsx';
import { SuperAdminCatalogPage } from '../pages/superadmin/SuperAdminCatalogPage.jsx';
import { SuperAdminAdminsPage } from '../pages/superadmin/SuperAdminAdminsPage.jsx';

export function AppRouter() {
  return (
    <BrowserRouter>
      <RepositoryProvider>
        <AuthProvider>
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/login" element={<LoginPage />} />

            <Route
              path="/menu/:homestayId"
              element={
                <GuestLayout>
                  <GuestMenuPage />
                </GuestLayout>
              }
            />

            <Route
              path="/admin"
              element={
                <ProtectedRoute allowedRoles={['admin']}>
                  <AdminLayout>
                    <AdminDashboardPage />
                  </AdminLayout>
                </ProtectedRoute>
              }
            />
            <Route
              path="/admin/menu"
              element={
                <ProtectedRoute allowedRoles={['admin']}>
                  <AdminLayout>
                    <AdminMenuPage />
                  </AdminLayout>
                </ProtectedRoute>
              }
            />
            <Route
              path="/admin/settings"
              element={
                <ProtectedRoute allowedRoles={['admin']}>
                  <AdminLayout>
                    <AdminSettingsPage />
                  </AdminLayout>
                </ProtectedRoute>
              }
            />

            <Route
              path="/superadmin"
              element={
                <ProtectedRoute allowedRoles={['superadmin']}>
                  <SuperAdminLayout>
                    <SuperAdminDashboardPage />
                  </SuperAdminLayout>
                </ProtectedRoute>
              }
            />
            <Route
              path="/superadmin/items"
              element={
                <ProtectedRoute allowedRoles={['superadmin']}>
                  <SuperAdminLayout>
                    <SuperAdminCatalogPage />
                  </SuperAdminLayout>
                </ProtectedRoute>
              }
            />
            <Route
              path="/superadmin/admins"
              element={
                <ProtectedRoute allowedRoles={['superadmin']}>
                  <SuperAdminLayout>
                    <SuperAdminAdminsPage />
                  </SuperAdminLayout>
                </ProtectedRoute>
              }
            />
          </Routes>
        </AuthProvider>
      </RepositoryProvider>
    </BrowserRouter>
  );
}
