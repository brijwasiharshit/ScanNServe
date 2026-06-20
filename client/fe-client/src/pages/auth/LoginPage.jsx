import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../hooks/useAuth.js';
import { useAdmins } from '../../hooks/useAdmins.js';
import { Button, Card } from '../../components/ui/index.jsx';

export function LoginPage() {
  const { loginAs } = useAuth();
  const admins = useAdmins();
  const navigate = useNavigate();

  const handleGuest = () => {
    loginAs('guest');
    navigate('/menu/hs-1');
  };

  const handleSuperAdmin = () => {
    loginAs('superadmin');
    navigate('/superadmin');
  };

  const handleAdmin = (adminId) => {
    loginAs('admin', adminId);
    navigate('/admin');
  };

  return (
    <div className="login-page">
      <div className="login-container">
        <div className="login-header">
          <span className="brand-icon">🍽</span>
          <h1>ScanNServe</h1>
          <p>Select a demo role to explore the application</p>
        </div>

        <div className="login-roles">
          <Card className="login-role-card">
            <h2>Guest</h2>
            <p>Browse a property menu as if you scanned a QR code.</p>
            <Button onClick={handleGuest} className="login-btn">
              Continue as Guest
            </Button>
          </Card>

          <Card className="login-role-card">
            <h2>Property Admin</h2>
            <p>Manage menu and settings for your hotel or homestay.</p>
            <div className="admin-login-list">
              {admins.map((admin) => (
                <Button key={admin.id} variant="secondary" onClick={() => handleAdmin(admin.id)}>
                  {admin.name}
                </Button>
              ))}
            </div>
          </Card>

          <Card className="login-role-card">
            <h2>Super Admin</h2>
            <p>Manage global catalog and property administrators.</p>
            <Button variant="outline" onClick={handleSuperAdmin} className="login-btn">
              Continue as Super Admin
            </Button>
          </Card>
        </div>
      </div>
    </div>
  );
}
