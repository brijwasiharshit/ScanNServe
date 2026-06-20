import { Badge } from '../ui/index.jsx';
import { formatPrice } from '../../utils/formatPrice.js';

export function MenuItemCard({ name, description, price, imageUrl, isAvailable, showAvailability = false }) {
  return (
    <article className={`menu-item-card ${!isAvailable ? 'menu-item-unavailable' : ''}`}>
      {imageUrl && (
        <div className="menu-item-image">
          <img src={imageUrl} alt={name} loading="lazy" width="88" height="88" />
        </div>
      )}
      <div className="menu-item-content">
        <div className="menu-item-header">
          <h3>{name}</h3>
          <span className="menu-item-price">{formatPrice(price)}</span>
        </div>
        <p className="menu-item-description">{description}</p>
        {showAvailability && (
          <Badge variant={isAvailable ? 'success' : 'muted'}>
            {isAvailable ? 'Available' : 'Unavailable'}
          </Badge>
        )}
      </div>
    </article>
  );
}

export function MenuCategorySection({ category, label, items, renderItem }) {
  if (items.length === 0) return null;

  return (
    <section className="menu-category" id={category}>
      <h2 className="menu-category-title">{label}</h2>
      <div className="menu-category-grid">{items.map(renderItem)}</div>
    </section>
  );
}
