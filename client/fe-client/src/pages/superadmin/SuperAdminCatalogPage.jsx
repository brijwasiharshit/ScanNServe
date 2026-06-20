import { useState } from 'react';
import { CATEGORY_LABELS, MENU_CATEGORIES } from '../../domain/types.js';
import { useCatalogItems, useMenuActions } from '../../hooks/useMenu.js';
import { CatalogItemForm } from '../../components/menu/CatalogItemForm.jsx';
import {
  PageHeader,
  Button,
  Modal,
  Badge,
  EmptyState,
} from '../../components/ui/index.jsx';
import { formatPrice } from '../../utils/formatPrice.js';

const emptyForm = {
  name: '',
  description: '',
  basePrice: '',
  category: 'mains',
  isAvailable: true,
  imageUrl: '',
};

export function SuperAdminCatalogPage() {
  const catalogItems = useCatalogItems();
  const { addCatalogItem, updateCatalogItem, deleteCatalogItem } = useMenuActions();

  const [isAddOpen, setIsAddOpen] = useState(false);
  const [editingItem, setEditingItem] = useState(null);
  const [form, setForm] = useState(emptyForm);

  const openAdd = () => {
    setForm(emptyForm);
    setIsAddOpen(true);
  };

  const openEdit = (item) => {
    setEditingItem(item);
    setForm({
      name: item.name,
      description: item.description,
      basePrice: String(item.basePrice),
      category: item.category,
      isAvailable: item.isAvailable,
      imageUrl: item.imageUrl ?? '',
    });
  };

  const handleAdd = () => {
    addCatalogItem({
      name: form.name,
      description: form.description,
      basePrice: Number(form.basePrice),
      category: form.category,
      isAvailable: form.isAvailable,
      imageUrl: form.imageUrl || undefined,
    });
    setIsAddOpen(false);
    setForm(emptyForm);
  };

  const handleUpdate = () => {
    if (!editingItem) return;
    updateCatalogItem(editingItem.id, {
      name: form.name,
      description: form.description,
      basePrice: Number(form.basePrice),
      category: form.category,
      isAvailable: form.isAvailable,
      imageUrl: form.imageUrl || undefined,
    });
    setEditingItem(null);
    setForm(emptyForm);
  };

  const handleDelete = (id, name) => {
    if (confirm(`Delete "${name}" from the global catalog? This removes it from all property menus.`)) {
      deleteCatalogItem(id);
    }
  };

  const categoryOptions = MENU_CATEGORIES.map((c) => ({ value: c, label: CATEGORY_LABELS[c] }));

  return (
    <div className="dashboard-page">
      <PageHeader
        title="Global Catalog"
        subtitle="Manage menu items available to all properties"
        action={<Button onClick={openAdd}>+ Add Item</Button>}
      />

      {catalogItems.length === 0 ? (
        <EmptyState
          title="Catalog is empty"
          description="Add items that property admins can add to their menus."
          action={<Button onClick={openAdd}>Add First Item</Button>}
        />
      ) : (
        <div className="admin-menu-table-wrap">
          <table className="data-table">
            <thead>
              <tr>
                <th>Item</th>
                <th>Category</th>
                <th>Base Price</th>
                <th>Status</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {catalogItems.map((item) => (
                <tr key={item.id}>
                  <td data-label="Item">
                    <div className="table-item-cell">
                      {item.imageUrl && (
                        <img src={item.imageUrl} alt="" className="table-thumb" loading="lazy" width="48" height="48" />
                      )}
                      <div className="table-item-text">
                        <strong>{item.name}</strong>
                        <p className="table-desc">{item.description}</p>
                      </div>
                    </div>
                  </td>
                  <td data-label="Category">{CATEGORY_LABELS[item.category]}</td>
                  <td data-label="Base Price">{formatPrice(item.basePrice)}</td>
                  <td data-label="Status">
                    <Badge variant={item.isAvailable ? 'success' : 'muted'}>
                      {item.isAvailable ? 'Active' : 'Inactive'}
                    </Badge>
                  </td>
                  <td data-label="Actions">
                    <div className="table-actions">
                      <Button size="sm" variant="ghost" onClick={() => openEdit(item)}>
                        Edit
                      </Button>
                      <Button size="sm" variant="danger" onClick={() => handleDelete(item.id, item.name)}>
                        Delete
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      <Modal isOpen={isAddOpen} onClose={() => setIsAddOpen(false)} title="Add Catalog Item">
        <div className="form-stack">
          <CatalogItemForm form={form} onChange={setForm} categoryOptions={categoryOptions} />
          <div className="form-actions">
            <Button variant="ghost" onClick={() => setIsAddOpen(false)}>
              Cancel
            </Button>
            <Button onClick={handleAdd} disabled={!form.name || !form.basePrice}>
              Add Item
            </Button>
          </div>
        </div>
      </Modal>

      <Modal isOpen={!!editingItem} onClose={() => setEditingItem(null)} title="Edit Catalog Item">
        <div className="form-stack">
          <CatalogItemForm form={form} onChange={setForm} categoryOptions={categoryOptions} />
          <div className="form-actions">
            <Button variant="ghost" onClick={() => setEditingItem(null)}>
              Cancel
            </Button>
            <Button onClick={handleUpdate}>Save Changes</Button>
          </div>
        </div>
      </Modal>
    </div>
  );
}
