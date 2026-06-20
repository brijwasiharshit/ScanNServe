import { useParams } from 'react-router-dom';
import { CATEGORY_LABELS, MENU_CATEGORIES } from '../../domain/types.js';
import { useHomestay } from '../../hooks/useHomestay.js';
import { useHomestayMenu } from '../../hooks/useMenu.js';
import { MenuCategorySection, MenuItemCard } from '../../components/menu/MenuItemCard.jsx';
import { groupMenuByCategory } from '../../utils/menuUtils.js';
import { EmptyState } from '../../components/ui/index.jsx';

export function GuestMenuPage() {
  const { homestayId } = useParams();
  const homestay = useHomestay(homestayId);
  const menuItems = useHomestayMenu(homestayId);
  const grouped = groupMenuByCategory(menuItems.filter((m) => m.isAvailable));

  if (!homestay) {
    return (
      <EmptyState
        title="Property not found"
        description="This QR code may be invalid or the property is no longer listed."
      />
    );
  }

  return (
    <div className="guest-menu-page">
      <div className="guest-hero">
        <img src={homestay.imageUrl} alt="" className="guest-hero-image" loading="eager" width="800" height="480" />
        <div className="guest-hero-overlay">
          <h1>{homestay.name}</h1>
          <p>{homestay.description}</p>
          <div className="guest-hero-meta">
            <span>📍 {homestay.address}</span>
            <span>📞 {homestay.phone}</span>
          </div>
        </div>
      </div>

      <nav className="category-nav" aria-label="Menu categories">
        {MENU_CATEGORIES.map((cat) =>
          grouped[cat]?.length ? (
            <a key={cat} href={`#${cat}`} className="category-nav-link">
              {CATEGORY_LABELS[cat]}
            </a>
          ) : null,
        )}
      </nav>

      <div className="guest-menu-content">
        {menuItems.filter((m) => m.isAvailable).length === 0 ? (
          <EmptyState title="No items available" description="Check back later for updated menu items." />
        ) : (
          MENU_CATEGORIES.map((cat) => (
            <MenuCategorySection
              key={cat}
              category={cat}
              label={CATEGORY_LABELS[cat]}
              items={grouped[cat] ?? []}
              renderItem={(entry) => (
                <MenuItemCard
                  key={entry.homestayMenuItemId}
                  name={entry.item.name}
                  description={entry.item.description}
                  price={entry.price}
                  imageUrl={entry.item.imageUrl}
                  isAvailable={entry.isAvailable}
                />
              )}
            />
          ))
        )}
      </div>
    </div>
  );
}
