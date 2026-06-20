import { useEffect, useState } from 'react';
import { Link, NavLink, useLocation, useNavigate } from 'react-router-dom';
import { useAuth } from '../../hooks/useAuth.js';
import { Button } from '../ui/index.jsx';

export function GuestLayout({ children }) {
  return (
    <div className="layout layout-guest">
      <header className="topbar topbar-guest">
        <Link to="/" className="brand" aria-label="ScanNServe home">
          <span className="brand-icon" aria-hidden="true">🍽</span>
          ScanNServe
        </Link>
      </header>
      <main className="layout-main layout-main-guest">{children}</main>
    </div>
  );
}

function DashboardShell({ navItems, children }) {
  const { user, logout } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();
  const [menuForPath, setMenuForPath] = useState(null);

  const menuOpen = menuForPath === location.pathname;

  useEffect(() => {
    document.body.classList.toggle('nav-open', menuOpen);
    return () => document.body.classList.remove('nav-open');
  }, [menuOpen]);

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <div className="layout layout-dashboard">
      <header className="mobile-dashboard-header">
        <button
          type="button"
          className="menu-toggle"
          onClick={() => setMenuForPath(menuOpen ? null : location.pathname)}
          aria-expanded={menuOpen}
          aria-controls="dashboard-sidebar"
          aria-label={menuOpen ? 'Close navigation menu' : 'Open navigation menu'}
        >
          <span className="menu-toggle-bar" aria-hidden="true" />
          <span className="menu-toggle-bar" aria-hidden="true" />
          <span className="menu-toggle-bar" aria-hidden="true" />
        </button>
        <Link to="/" className="brand brand-mobile" aria-label="ScanNServe home">
          <span className="brand-icon" aria-hidden="true">🍽</span>
          ScanNServe
        </Link>
      </header>

      <button
        type="button"
        className={`sidebar-overlay ${menuOpen ? 'sidebar-overlay-visible' : ''}`}
        onClick={() => setMenuForPath(null)}
        aria-label="Close navigation menu"
        tabIndex={menuOpen ? 0 : -1}
      />

      <aside
        id="dashboard-sidebar"
        className={`sidebar ${menuOpen ? 'sidebar-open' : ''}`}
        aria-label="Dashboard navigation"
      >
        <div className="sidebar-top">
          <Link to="/" className="brand brand-sidebar" onClick={() => setMenuForPath(null)}>
            <span className="brand-icon" aria-hidden="true">🍽</span>
            ScanNServe
          </Link>
          <button
            type="button"
            className="sidebar-close"
            onClick={() => setMenuForPath(null)}
            aria-label="Close navigation menu"
          >
            ×
          </button>
        </div>
        <nav className="sidebar-nav">
          {navItems.map(({ to, end, label }) => (
            <NavLink
              key={to}
              to={to}
              end={end}
              className={({ isActive }) => (isActive ? 'nav-link active' : 'nav-link')}
              onClick={() => setMenuForPath(null)}
            >
              {label}
            </NavLink>
          ))}
        </nav>
        <div className="sidebar-footer">
          <p className="sidebar-user">{user?.name}</p>
          <Button variant="ghost" size="sm" className="btn-sidebar-logout" onClick={handleLogout}>
            Sign out
          </Button>
        </div>
      </aside>

      <main className="layout-main layout-main-dashboard">{children}</main>
    </div>
  );
}

const ADMIN_NAV = [
  { to: '/admin', end: true, label: 'Dashboard' },
  { to: '/admin/menu', label: 'Menu Items' },
  { to: '/admin/settings', label: 'Property Settings' },
];

const SUPER_ADMIN_NAV = [
  { to: '/superadmin', end: true, label: 'Dashboard' },
  { to: '/superadmin/items', label: 'Global Catalog' },
  { to: '/superadmin/admins', label: 'Manage Admins' },
];

export function AdminLayout({ children }) {
  return <DashboardShell navItems={ADMIN_NAV}>{children}</DashboardShell>;
}

export function SuperAdminLayout({ children }) {
  return <DashboardShell navItems={SUPER_ADMIN_NAV}>{children}</DashboardShell>;
}
