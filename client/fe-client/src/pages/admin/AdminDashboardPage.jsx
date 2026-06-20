import { Link } from 'react-router-dom';
import { useAuth } from '../../hooks/useAuth.js';
import { useHomestayByAdmin } from '../../hooks/useHomestay.js';
import { useHomestayMenu } from '../../hooks/useMenu.js';
import { PageHeader, Card, Button } from '../../components/ui/index.jsx';
import { formatPrice } from '../../utils/formatPrice.js';

export function AdminDashboardPage() {
  const { user } = useAuth();
  const homestay = useHomestayByAdmin(user?.id);
  const menuItems = useHomestayMenu(homestay?.id);
  const availableCount = menuItems.filter((m) => m.isAvailable).length;

  if (!homestay) {
    return <p>No property linked to your account.</p>;
  }

  return (
    <div className="dashboard-page">
      <PageHeader
        title="Dashboard"
        subtitle={`Welcome back, ${user?.name}`}
        action={
          <Link to={`/menu/${homestay.id}`} target="_blank">
            <Button variant="secondary">Preview Guest Menu</Button>
          </Link>
        }
      />

      <div className="stats-grid">
        <Card className="stat-card">
          <span className="stat-label">Property</span>
          <span className="stat-value">{homestay.name}</span>
        </Card>
        <Card className="stat-card">
          <span className="stat-label">Menu Items</span>
          <span className="stat-value">{menuItems.length}</span>
        </Card>
        <Card className="stat-card">
          <span className="stat-label">Available</span>
          <span className="stat-value">{availableCount}</span>
        </Card>
        <Card className="stat-card">
          <span className="stat-label">Avg. Price</span>
          <span className="stat-value">
            {menuItems.length
              ? formatPrice(menuItems.reduce((s, m) => s + m.price, 0) / menuItems.length)
              : '—'}
          </span>
        </Card>
      </div>

      <section className="dashboard-section">
        <h2>Quick Actions</h2>
        <div className="quick-actions">
          <Link to="/admin/menu">
            <Card className="action-card">
              <span>📋</span>
              <h3>Manage Menu</h3>
              <p>Add, edit, or remove items from your menu</p>
            </Card>
          </Link>
          <Link to="/admin/settings">
            <Card className="action-card">
              <span>⚙️</span>
              <h3>Property Settings</h3>
              <p>Update description, contact, and details</p>
            </Card>
          </Link>
        </div>
      </section>
    </div>
  );
}
