import { useState } from 'react';
import { useAdmins, useAdminActions } from '../../hooks/useAdmins.js';
import { useAllHomestays } from '../../hooks/useHomestay.js';
import {
  PageHeader,
  Button,
  Modal,
  Input,
  Select,
  Badge,
  EmptyState,
} from '../../components/ui/index.jsx';

export function SuperAdminAdminsPage() {
  const admins = useAdmins();
  const homestays = useAllHomestays();
  const { createAdmin, updateAdmin, removeAdmin } = useAdminActions();

  const [isAddOpen, setIsAddOpen] = useState(false);
  const [form, setForm] = useState({ name: '', email: '', homestayId: '' });

  const homestayOptions = [
    { value: '', label: 'Create new property' },
    ...homestays.map((hs) => ({ value: hs.id, label: hs.name })),
  ];

  const getHomestayName = (homestayId) => homestays.find((h) => h.id === homestayId)?.name ?? '—';

  const handleAdd = () => {
    createAdmin({
      name: form.name,
      email: form.email,
      homestayId: form.homestayId || undefined,
      isActive: true,
    });
    setIsAddOpen(false);
    setForm({ name: '', email: '', homestayId: '' });
  };

  const toggleActive = (admin) => {
    updateAdmin(admin.id, { isActive: !admin.isActive });
  };

  const handleRemove = (admin) => {
    if (confirm(`Remove admin "${admin.name}"? They will lose access to their dashboard.`)) {
      removeAdmin(admin.id);
    }
  };

  return (
    <div className="dashboard-page">
      <PageHeader
        title="Manage Admins"
        subtitle="Add or remove property administrators"
        action={<Button onClick={() => setIsAddOpen(true)}>+ Add Admin</Button>}
      />

      {admins.length === 0 ? (
        <EmptyState
          title="No admins yet"
          description="Create an admin account for a property owner."
          action={<Button onClick={() => setIsAddOpen(true)}>Add Admin</Button>}
        />
      ) : (
        <div className="admin-menu-table-wrap">
          <table className="data-table">
            <thead>
              <tr>
                <th>Name</th>
                <th>Email</th>
                <th>Property</th>
                <th>Status</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {admins.map((admin) => (
                <tr key={admin.id}>
                  <td data-label="Name"><strong>{admin.name}</strong></td>
                  <td data-label="Email">{admin.email}</td>
                  <td data-label="Property">{getHomestayName(admin.homestayId)}</td>
                  <td data-label="Status">
                    <Badge variant={admin.isActive ? 'success' : 'muted'}>
                      {admin.isActive ? 'Active' : 'Inactive'}
                    </Badge>
                  </td>
                  <td data-label="Actions">
                    <div className="table-actions">
                      <Button size="sm" variant="ghost" onClick={() => toggleActive(admin)}>
                        {admin.isActive ? 'Deactivate' : 'Activate'}
                      </Button>
                      <Button size="sm" variant="danger" onClick={() => handleRemove(admin)}>
                        Remove
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      <Modal isOpen={isAddOpen} onClose={() => setIsAddOpen(false)} title="Add Admin">
        <div className="form-stack">
          <Input
            label="Full Name"
            value={form.name}
            onChange={(e) => setForm({ ...form, name: e.target.value })}
            required
          />
          <Input
            label="Email"
            type="email"
            value={form.email}
            onChange={(e) => setForm({ ...form, email: e.target.value })}
            required
          />
          <Select
            label="Link to Property"
            value={form.homestayId}
            onChange={(e) => setForm({ ...form, homestayId: e.target.value })}
            options={homestayOptions}
          />
          <div className="form-actions">
            <Button variant="ghost" onClick={() => setIsAddOpen(false)}>
              Cancel
            </Button>
            <Button onClick={handleAdd} disabled={!form.name || !form.email}>
              Create Admin
            </Button>
          </div>
        </div>
      </Modal>
    </div>
  );
}
