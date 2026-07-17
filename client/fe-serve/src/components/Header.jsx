import { QrCode } from "lucide-react";

export default function Header() {
    return (
        <header className="bg-white shadow-sm border-b border-slate-200">
            <div className="max-w-7xl mx-auto px-6 py-4 flex items-center justify-between">
                {/* Logo */}
                <div className="flex items-center gap-3">
                    <div className="bg-[#F97316] p-2.5 rounded-xl shadow-md">
                        <QrCode className="text-white" size={26} />
                    </div>

                    <div>
                        <h1 className="text-2xl font-bold text-slate-900 tracking-tight">
                            Scan N Serve
                        </h1>

                        <p className="text-sm text-slate-500">
                            Smart QR Ordering & Restaurant Management
                        </p>
                    </div>
                </div>


            </div>
        </header>
    );
}