import { useState } from 'react';
import { useAuth } from '../../hooks/useAuth.js';
import { useHomestayByAdmin } from '../../hooks/useHomestay.js';
import { useHomestayActions } from '../../hooks/useHomestayActions.js';
import { PageHeader, Button, Input, Textarea, Card } from '../../components/ui/index.jsx';

export function AdminSettingsPage() {
  const { user } = useAuth();
  const homestay = useHomestayByAdmin(user?.id);
  const { updateHomestay } = useHomestayActions();

  const [form, setForm] = useState(null);
  const [saved, setSaved] = useState(false);

  if (!homestay) return <p>No property linked.</p>;

  const data = form ?? homestay;

  const handleChange = (field) => (e) => {
    setForm({ ...data, [field]: e.target.value });
    setSaved(false);
  };

  const handleSave = (e) => {
    e.preventDefault();
    updateHomestay(data);
    setForm(null);
    setSaved(true);
  };

  return (
    <div className="dashboard-page">
      <PageHeader title="Property Settings" subtitle="Update your hotel or homestay details" />

      {saved && <div className="alert alert-success">Settings saved successfully!</div>}

      <Card className="settings-form-card">
        <form onSubmit={handleSave} className="form-stack">
          <Input label="Property Name" value={data.name} onChange={handleChange('name')} required />
          <Textarea
            label="Description"
            value={data.description}
            onChange={handleChange('description')}
            rows={4}
            required
          />
          <Input label="Address" value={data.address} onChange={handleChange('address')} required />
          <Input label="Phone" value={data.phone} onChange={handleChange('phone')} required />
          <Input
            label="Cover Image URL"
            value={data.imageUrl}
            onChange={handleChange('imageUrl')}
            placeholder="https://..."
          />
          {data.imageUrl && (
            <img src={data.imageUrl} alt="Preview" className="settings-image-preview" />
          )}
          <div className="form-actions">
            <Button type="submit">Save Changes</Button>
          </div>
        </form>
      </Card>
    </div>
  );
}
