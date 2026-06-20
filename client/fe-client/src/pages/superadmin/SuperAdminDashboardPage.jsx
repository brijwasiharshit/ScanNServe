import { useCatalogItems } from '../../hooks/useMenu.js';
import { useAdmins } from '../../hooks/useAdmins.js';
import { useAllHomestays } from '../../hooks/useHomestay.js';
import { PageHeader, Card } from '../../components/ui/index.jsx';

export function SuperAdminDashboardPage() {
  const catalogItems = useCatalogItems();
  const admins = useAdmins();
  const homestays = useAllHomestays();
  const activeAdmins = admins.filter((a) => a.isActive);

  return (
    <div className="dashboard-page">
      <PageHeader title="Super Admin Dashboard" subtitle="Platform overview and management" />

      <div className="stats-grid">
        <Card className="stat-card">
          <span className="stat-label">Properties</span>
          <span className="stat-value">{homestays.length}</span>
        </Card>
        <Card className="stat-card">
          <span className="stat-label">Active Admins</span>
          <span className="stat-value">{activeAdmins.length}</span>
        </Card>
        <Card className="stat-card">
          <span className="stat-label">Catalog Items</span>
          <span className="stat-value">{catalogItems.length}</span>
        </Card>
        <Card className="stat-card">
          <span className="stat-label">Available Items</span>
          <span className="stat-value">{catalogItems.filter((i) => i.isAvailable).length}</span>
        </Card>
      </div>

      <section className="dashboard-section">
        <h2>Registered Properties</h2>
        <div className="properties-list">
          {homestays.map((hs) => {
            const admin = admins.find((a) => a.id === hs.adminId);
            return (
              <Card key={hs.id} className="property-list-item">
                <img src={hs.imageUrl} alt="" className="table-thumb" />
                <div>
                  <strong>{hs.name}</strong>
                  <p className="text-muted">{hs.address}</p>
                  {admin && <p className="text-sm">Admin: {admin.name}</p>}
                </div>
              </Card>
            );
          })}
        </div>
      </section>
    </div>
  );
}
