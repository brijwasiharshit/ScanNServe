export function groupMenuByCategory(menuItems) {
  /** @type {Record<string, typeof menuItems>} */
  const grouped = {};
  for (const entry of menuItems) {
    const cat = entry.item.category;
    if (!grouped[cat]) grouped[cat] = [];
    grouped[cat].push(entry);
  }
  return grouped;
}
