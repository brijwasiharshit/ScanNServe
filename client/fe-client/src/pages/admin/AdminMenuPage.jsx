import { useState } from 'react';
import { useAuth } from '../../hooks/useAuth.js';
import { useHomestayByAdmin } from '../../hooks/useHomestay.js';
import { useCatalogItems, useHomestayMenu, useMenuActions } from '../../hooks/useMenu.js';
import { CATEGORY_LABELS } from '../../domain/types.js';
import {
  PageHeader,
  Button,
  Modal,
  Input,
  Select,
  Badge,
  EmptyState,
} from '../../components/ui/index.jsx';
import { formatPrice } from '../../utils/formatPrice.js';

export function AdminMenuPage() {
  const { user } = useAuth();
  const homestay = useHomestayByAdmin(user?.id);
  const menuItems = useHomestayMenu(homestay?.id);
  const catalogItems = useCatalogItems();
  const { addToHomestay, updateHomestayMenuItem, removeFromHomestay } = useMenuActions();

  const [isAddOpen, setIsAddOpen] = useState(false);
  const [editingItem, setEditingItem] = useState(null);
  const [selectedCatalogId, setSelectedCatalogId] = useState('');
  const [customPrice, setCustomPrice] = useState('');

  const linkedItemIds = new Set(menuItems.map((m) => m.item.id));
  const availableCatalog = catalogItems.filter((c) => !linkedItemIds.has(c.id) && c.isAvailable);

  const handleAdd = () => {
    if (!homestay || !selectedCatalogId) return;
    const catalogItem = catalogItems.find((c) => c.id === selectedCatalogId);
    const price = customPrice ? Number(customPrice) : catalogItem?.basePrice ?? 0;
    addToHomestay(homestay.id, selectedCatalogId, price);
    setIsAddOpen(false);
    setSelectedCatalogId('');
    setCustomPrice('');
  };

  const handleUpdate = () => {
    if (!editingItem) return;
    updateHomestayMenuItem(editingItem.homestayMenuItemId, {
      price: Number(editingItem.price),
      isAvailable: editingItem.isAvailable,
    });
    setEditingItem(null);
  };

  const handleRemove = (homestayMenuItemId) => {
    if (confirm('Remove this item from your menu?')) {
      removeFromHomestay(homestayMenuItemId);
    }
  };

  if (!homestay) return <p>No property linked.</p>;

  return (
    <div className="dashboard-page">
      <PageHeader
        title="Menu Items"
        subtitle={`Manage menu for ${homestay.name}`}
        action={
          <Button onClick={() => setIsAddOpen(true)} disabled={availableCatalog.length === 0}>
            + Add Item
          </Button>
        }
      />

      {menuItems.length === 0 ? (
        <EmptyState
          title="No menu items yet"
          description="Add items from the global catalog to build your menu."
          action={
            <Button onClick={() => setIsAddOpen(true)} disabled={availableCatalog.length === 0}>
              Add First Item
            </Button>
          }
        />
      ) : (
        <div className="admin-menu-table-wrap">
          <table className="data-table">
            <thead>
              <tr>
                <th>Item</th>
                <th>Category</th>
                <th>Your Price</th>
                <th>Base Price</th>
                <th>Status</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {menuItems.map((entry) => (
                <tr key={entry.homestayMenuItemId}>
                  <td data-label="Item">
                    <div className="table-item-cell">
                      {entry.item.imageUrl && (
                        <img src={entry.item.imageUrl} alt="" className="table-thumb" loading="lazy" width="48" height="48" />
                      )}
                      <div className="table-item-text">
                        <strong>{entry.item.name}</strong>
                        <p className="table-desc">{entry.item.description}</p>
                      </div>
                    </div>
                  </td>
                  <td data-label="Category">{CATEGORY_LABELS[entry.item.category]}</td>
                  <td data-label="Your Price">{formatPrice(entry.price)}</td>
                  <td data-label="Base Price" className="text-muted">{formatPrice(entry.item.basePrice)}</td>
                  <td data-label="Status">
                    <Badge variant={entry.isAvailable ? 'success' : 'muted'}>
                      {entry.isAvailable ? 'Available' : 'Hidden'}
                    </Badge>
                  </td>
                  <td data-label="Actions">
                    <div className="table-actions">
                      <Button size="sm" variant="ghost" onClick={() => setEditingItem({ ...entry })}>
                        Edit
                      </Button>
                      <Button
                        size="sm"
                        variant="danger"
                        onClick={() => handleRemove(entry.homestayMenuItemId)}
                      >
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

      <Modal isOpen={isAddOpen} onClose={() => setIsAddOpen(false)} title="Add Menu Item">
        <div className="form-stack">
          <Select
            label="Select from catalog"
            value={selectedCatalogId}
            onChange={(e) => {
              setSelectedCatalogId(e.target.value);
              const item = catalogItems.find((c) => c.id === e.target.value);
              setCustomPrice(String(item?.basePrice ?? ''));
            }}
            options={[
              { value: '', label: 'Choose an item...' },
              ...availableCatalog.map((c) => ({
                value: c.id,
                label: `${c.name} (${formatPrice(c.basePrice)})`,
              })),
            ]}
          />
          <Input
            label="Your price (₹)"
            type="number"
            value={customPrice}
            onChange={(e) => setCustomPrice(e.target.value)}
            min="0"
          />
          <div className="form-actions">
            <Button variant="ghost" onClick={() => setIsAddOpen(false)}>
              Cancel
            </Button>
            <Button onClick={handleAdd} disabled={!selectedCatalogId}>
              Add to Menu
            </Button>
          </div>
        </div>
      </Modal>

      <Modal isOpen={!!editingItem} onClose={() => setEditingItem(null)} title="Edit Menu Item">
        {editingItem && (
          <div className="form-stack">
            <p className="edit-item-name">{editingItem.item.name}</p>
            <Input
              label="Your price (₹)"
              type="number"
              value={editingItem.price}
              onChange={(e) => setEditingItem({ ...editingItem, price: e.target.value })}
              min="0"
            />
            <label className="checkbox-field">
              <input
                type="checkbox"
                checked={editingItem.isAvailable}
                onChange={(e) => setEditingItem({ ...editingItem, isAvailable: e.target.checked })}
              />
              Available on menu
            </label>
            <div className="form-actions">
              <Button variant="ghost" onClick={() => setEditingItem(null)}>
                Cancel
              </Button>
              <Button onClick={handleUpdate}>Save Changes</Button>
            </div>
          </div>
        )}
      </Modal>
    </div>
  );
}
