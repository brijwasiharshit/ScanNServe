import { QrCode } from "lucide-react";

export default function TableList({ tables }) {
    if (!tables || tables.length === 0) {
        return <div className="mt-6 text-slate-500">No tables found.</div>;
    }

    return (
        <div className="mt-6 grid gap-4 sm:grid-cols-2 lg:grid-cols-3">
            {tables.map((table) => {
                const menuUrl = `${window.location.origin}/menu/${table.tableToken}`;
                const qrUrl = `https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=${encodeURIComponent(menuUrl)}`;

                return (
                    <div
                        key={table.id || table.tableNumber}
                        className="rounded-2xl border border-[#E2E8F0] bg-white p-5 flex flex-col items-center shadow-sm"
                    >
                        <h3 className="text-xl font-bold text-[#C2410C] mb-4">Table {table.tableNumber}</h3>
                        <img src={qrUrl} alt={`QR Code for Table ${table.tableNumber}`} className="w-32 h-32 mb-4" />
                        <a 
                            href={menuUrl} 
                            target="_blank" 
                            rel="noreferrer"
                            className="flex items-center gap-2 text-sm text-[#F97316] hover:underline"
                        >
                            <QrCode size={16} />
                            View Menu Link
                        </a>
                    </div>
                );
            })}
        </div>
    );
}
