const tabs = [
    { id: "admins", label: "Admins" },
    { id: "food", label: "Food Items" },
];

export default function DashboardTabs({ activeTab, onTabChange }) {
    return (
        <div className="mt-8 flex gap-6 border-b border-slate-200">
            {tabs.map((tab) => (
                <button
                    key={tab.id}
                    type="button"
                    onClick={() => onTabChange(tab.id)}
                    className={`pb-3 font-medium ${
                        activeTab === tab.id
                            ? "border-b-2 border-[#2563EB] text-[#2563EB]"
                            : "text-slate-500"
                    }`}
                >
                    {tab.label}
                </button>
            ))}
        </div>
    );
}
