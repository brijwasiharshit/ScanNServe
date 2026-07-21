import { useEffect, useState } from "react";
import { X, TrendingUp, CalendarDays, ShoppingBag, Clock, Loader2 } from "lucide-react";
import { getSalesReport } from "../../services/restaurantService";
import { AreaChart, Area, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from "recharts";

export default function SalesAnalysisModal({ onClose }) {
    const [loading, setLoading] = useState(true);
    const [data, setData] = useState(null);

    useEffect(() => {
        const fetchReport = async () => {
            try {
                const report = await getSalesReport();
                setData(report.data); // extract the 'data' payload from StandardResponse
            } catch (err) {
                console.error("Failed to load sales report", err);
            } finally {
                setLoading(false);
            }
        };
        fetchReport();
    }, []);

    // Helper to format currency
    const formatCurrency = (amount) => {
        const val = Number(amount) || 0;
        return new Intl.NumberFormat('en-IN', { style: 'currency', currency: 'INR', minimumFractionDigits: 2, maximumFractionDigits: 2 }).format(val);
    };

    return (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-slate-900/40 backdrop-blur-sm p-4 animate-in fade-in duration-200">
            <div className="bg-white rounded-3xl shadow-2xl w-full max-w-5xl max-h-[90vh] overflow-y-auto flex flex-col relative border border-slate-100">
                {/* Header */}
                <div className="sticky top-0 bg-white/80 backdrop-blur-md z-10 p-6 border-b border-slate-100 flex items-center justify-between">
                    <div>
                        <h2 className="text-2xl font-bold bg-gradient-to-r from-purple-600 to-indigo-600 bg-clip-text text-transparent flex items-center gap-2">
                            <TrendingUp className="text-purple-600" />
                            Sales Analysis Dashboard
                        </h2>
                        <p className="text-slate-500 text-sm mt-1">Real-time performance and weekly insights</p>
                    </div>
                    <button
                        onClick={onClose}
                        className="p-2 rounded-full hover:bg-slate-100 text-slate-400 hover:text-slate-700 transition-colors"
                    >
                        <X size={24} />
                    </button>
                </div>

                {/* Body */}
                <div className="p-6 bg-slate-50/50">
                    {loading ? (
                        <div className="flex flex-col items-center justify-center py-20">
                            <Loader2 className="w-10 h-10 text-indigo-500 animate-spin mb-4" />
                            <p className="text-slate-500 font-medium">Loading analytics...</p>
                        </div>
                    ) : !data ? (
                        <div className="py-20 text-center text-slate-500">Failed to load data.</div>
                    ) : (
                        <div className="space-y-8">
                            {/* Compute sorted peak hours */}
                            {(() => {
                                const sortedPeakHours = data.peakSalesHours ? [...data.peakSalesHours].sort((a, b) => b.sales - a.sales || b.orders - a.orders) : [];
                                const topPeak = sortedPeakHours.length > 0 ? sortedPeakHours[0] : null;

                                return (
                                    <>
                                        {/* Key Metrics Cards */}
                                        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
                                            <MetricCard 
                                                title="Total Sales (All Time)" 
                                                value={formatCurrency(data.totalSalesAllTime)} 
                                                icon={<TrendingUp />} 
                                                color="from-emerald-500 to-teal-400" 
                                            />
                                            <MetricCard 
                                                title="Sales This Month" 
                                                value={formatCurrency(data.totalSalesThisMonth)} 
                                                icon={<CalendarDays />} 
                                                color="from-purple-500 to-indigo-500" 
                                            />
                                            <MetricCard 
                                                title="Orders This Month" 
                                                value={data.ordersThisMonth} 
                                                icon={<ShoppingBag />} 
                                                color="from-blue-500 to-cyan-400" 
                                            />
                                            <MetricCard 
                                                title="Happy Hour (Peak)" 
                                                value={topPeak && topPeak.sales > 0 ? `${topPeak.hour}:00` : "N/A"} 
                                                subtitle={topPeak && topPeak.sales > 0 ? `${topPeak.orders} orders` : "No sales yet"}
                                                icon={<Clock />} 
                                                color="from-orange-400 to-rose-400" 
                                            />
                                        </div>

                                        {/* Chart Area */}
                                        <div className="bg-white p-6 rounded-2xl shadow-sm border border-slate-100 overflow-hidden">
                                            <h3 className="text-lg font-bold text-slate-700 mb-6 flex items-center gap-2">
                                                Weekly Sales Overview
                                                <span className="text-xs font-normal px-2 py-1 bg-indigo-50 text-indigo-600 rounded-full">Last 7 Days</span>
                                            </h3>
                                            <div className="h-[300px] w-full">
                                                <ResponsiveContainer width="100%" height="100%">
                                                    <AreaChart data={data.salesLast7Days} margin={{ top: 10, right: 30, left: 0, bottom: 0 }}>
                                                        <defs>
                                                            <linearGradient id="colorSales" x1="0" y1="0" x2="0" y2="1">
                                                                <stop offset="5%" stopColor="#6366f1" stopOpacity={0.3} />
                                                                <stop offset="95%" stopColor="#6366f1" stopOpacity={0} />
                                                            </linearGradient>
                                                        </defs>
                                                        <XAxis dataKey="date" axisLine={false} tickLine={false} tick={{fill: '#94a3b8', fontSize: 12}} dy={10} />
                                                        <YAxis axisLine={false} tickLine={false} tick={{fill: '#94a3b8', fontSize: 12}} dx={-10} tickFormatter={(value) => `₹${value}`} />
                                                        <CartesianGrid strokeDasharray="3 3" vertical={false} stroke="#e2e8f0" />
                                                        <Tooltip 
                                                            contentStyle={{ borderRadius: '12px', border: 'none', boxShadow: '0 10px 15px -3px rgb(0 0 0 / 0.1)' }}
                                                            formatter={(value) => [`₹${value}`, 'Sales']}
                                                            labelStyle={{ color: '#64748b', fontWeight: 500, marginBottom: '4px' }}
                                                        />
                                                        <Area 
                                                            type="monotone" 
                                                            dataKey="sales" 
                                                            stroke="#6366f1" 
                                                            strokeWidth={3}
                                                            fillOpacity={1} 
                                                            fill="url(#colorSales)" 
                                                        />
                                                    </AreaChart>
                                                </ResponsiveContainer>
                                            </div>
                                        </div>
                                        
                                        {/* Peak Hours Table (Optional detail) */}
                                        {sortedPeakHours.length > 0 && sortedPeakHours[0].sales > 0 && (
                                            <div className="bg-white p-6 rounded-2xl shadow-sm border border-slate-100">
                                                <h3 className="text-lg font-bold text-slate-700 mb-4">Peak Operating Hours</h3>
                                                <div className="flex flex-wrap gap-3">
                                                    {sortedPeakHours.filter(ph => ph.sales > 0).slice(0, 5).map((ph, idx) => (
                                                        <div key={idx} className="flex flex-col bg-slate-50 px-4 py-3 rounded-xl border border-slate-100 min-w-[120px]">
                                                            <span className="text-slate-500 text-xs font-medium uppercase tracking-wider">{ph.hour}:00 - {ph.hour + 1}:00</span>
                                                            <span className="text-slate-800 font-bold mt-1 text-lg">{ph.orders} Orders</span>
                                                            <span className="text-emerald-600 font-medium text-sm">₹{ph.sales}</span>
                                                        </div>
                                                    ))}
                                                </div>
                                            </div>
                                        )}
                                    </>
                                );
                            })()}
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
}

function MetricCard({ title, value, subtitle, icon, color }) {
    return (
        <div className="bg-white rounded-2xl p-5 shadow-sm border border-slate-100 flex items-start gap-4 relative overflow-hidden group hover:shadow-md transition-shadow">
            <div className={`p-3 rounded-xl bg-gradient-to-br ${color} text-white shadow-inner`}>
                {icon}
            </div>
            <div>
                <p className="text-slate-500 text-sm font-medium">{title}</p>
                <h3 className="text-2xl font-bold text-slate-800 mt-1">{value}</h3>
                {subtitle && <p className="text-slate-400 text-xs mt-1">{subtitle}</p>}
            </div>
        </div>
    );
}
