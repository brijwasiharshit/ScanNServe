import StatCard from "../super-admin/StatCard";

export default function RestaurantStats({ stats }) {
    return (
        <div className="grid grid-cols-1 gap-4 sm:grid-cols-2">
            {stats.map((stat) => (
                <StatCard key={stat.id} stat={stat} />
            ))}
        </div>
    );
}
