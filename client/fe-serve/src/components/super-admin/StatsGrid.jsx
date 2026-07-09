import StatCard from "./StatCard";

export default function StatsGrid({ stats }) {
    return (
        <div className="grid grid-cols-1 gap-4 sm:grid-cols-3">
            {stats.map((stat) => (
                <StatCard key={stat.id} stat={stat} />
            ))}
        </div>
    );
}
