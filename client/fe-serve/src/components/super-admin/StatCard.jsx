import { Building2, UtensilsCrossed, Users, LayoutGrid, FileText } from "lucide-react";

const statIcons = {
    admins: Users,
    properties: Building2,
    food: UtensilsCrossed,
    tables: LayoutGrid,
    menu: FileText,
};

export default function StatCard({ stat }) {
    const Icon = statIcons[stat.id];

    return (
        <div className="rounded-2xl border border-[#E2E8F0] bg-white p-5 shadow">
            <Icon className="text-[#F97316]" size={28} />

            <h3 className="mt-3 text-slate-500">{stat.label}</h3>

            <p className="text-3xl font-bold text-[#C2410C]">{stat.value}</p>
        </div>
    );
}
