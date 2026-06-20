import { Input, Textarea, Select } from '../ui/index.jsx';

export function CatalogItemForm({ form, onChange, categoryOptions }) {
  return (
    <>
      <Input label="Name" value={form.name} onChange={(e) => onChange({ ...form, name: e.target.value })} required />
      <Textarea
        label="Description"
        value={form.description}
        onChange={(e) => onChange({ ...form, description: e.target.value })}
        rows={3}
        required
      />
      <Input
        label="Base Price (₹)"
        type="number"
        value={form.basePrice}
        onChange={(e) => onChange({ ...form, basePrice: e.target.value })}
        min="0"
        required
      />
      <Select
        label="Category"
        value={form.category}
        onChange={(e) => onChange({ ...form, category: e.target.value })}
        options={categoryOptions}
      />
      <Input
        label="Image URL"
        value={form.imageUrl}
        onChange={(e) => onChange({ ...form, imageUrl: e.target.value })}
        placeholder="https://..."
      />
      <label className="checkbox-field">
        <input
          type="checkbox"
          checked={form.isAvailable}
          onChange={(e) => onChange({ ...form, isAvailable: e.target.checked })}
        />
        Available in catalog
      </label>
    </>
  );
}
