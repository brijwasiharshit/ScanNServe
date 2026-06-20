import { Link } from 'react-router-dom';
import { useAllHomestays } from '../hooks/useHomestay.js';
import { Button, Card } from '../components/ui/index.jsx';

export function HomePage() {
  const homestays = useAllHomestays();

  return (
    <div className="home-page">
      <section className="hero-section">
        <div className="hero-content">
          <span className="hero-badge">QR Menu Platform</span>
          <h1>Scan. Browse. Order.</h1>
          <p>
            ScanNServe helps hotel and homestay guests explore menus instantly via QR codes, while
            owners manage items and pricing in real time.
          </p>
          <div className="hero-actions">
            <Link to="/login">
              <Button size="lg">Demo Login</Button>
            </Link>
          </div>
        </div>
      </section>

      <section className="home-section">
        <h2>Try a guest menu</h2>
        <p className="section-desc">Simulate scanning a QR code at one of these properties:</p>
        <div className="homestay-grid">
          {homestays.map((hs) => (
            <Card key={hs.id} className="homestay-preview-card">
              <img src={hs.imageUrl} alt={hs.name} className="homestay-preview-image" />
              <div className="homestay-preview-body">
                <h3>{hs.name}</h3>
                <p>{hs.address}</p>
                <Link to={`/menu/${hs.id}`}>
                  <Button variant="secondary" size="sm">
                    View Menu →
                  </Button>
                </Link>
              </div>
            </Card>
          ))}
        </div>
      </section>

      <section className="home-section roles-section">
        <h2>Three roles, one platform</h2>
        <div className="roles-grid">
          <Card className="role-card">
            <span className="role-icon">👤</span>
            <h3>Guest</h3>
            <p>Scan QR and browse the live menu with prices for that property.</p>
          </Card>
          <Card className="role-card">
            <span className="role-icon">🏨</span>
            <h3>Admin</h3>
            <p>Customize your menu, set prices, and update property details.</p>
          </Card>
          <Card className="role-card">
            <span className="role-icon">⚙️</span>
            <h3>Super Admin</h3>
            <p>Manage the global item catalog and onboard property admins.</p>
          </Card>
        </div>
      </section>
    </div>
  );
}
