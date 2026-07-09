import { Building2, UtensilsCrossed, Users } from "lucide-react";

const statIcons = {
    admins: Users,
    properties: Building2,
    food: UtensilsCrossed,
};

export default function StatCard({ stat }) {
    const Icon = statIcons[stat.id];

    return (
        <div className="rounded-2xl border border-[#E2E8F0] bg-white p-5 shadow">
            <Icon className="text-[#0891B2]" size={28} />

            <h3 className="mt-3 text-slate-500">{stat.label}</h3>

            <p className="text-3xl font-bold text-[#155E75]">{stat.value}</p>
        </div>
    );
}
