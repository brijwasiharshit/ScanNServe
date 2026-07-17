const tabs = [
    { id: "menu", label: "Menu Items" },
    { id: "tables", label: "Tables" },
];

export default function RestaurantDashboardTabs({ activeTab, onTabChange }) {
    return (
        <div className="mt-8 flex gap-6 border-b border-slate-200">
            {tabs.map((tab) => (
                <button
                    key={tab.id}
                    type="button"
                    onClick={() => onTabChange(tab.id)}
                    className={`pb-3 font-medium ${
                        activeTab === tab.id
                            ? "border-b-2 border-[#F59E0B] text-[#F97316]"
                            : "text-slate-500"
                    }`}
                >
                    {tab.label}
                </button>
            ))}
        </div>
    );
}
